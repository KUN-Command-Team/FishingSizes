package com.github.chore3.fishingsizes.server.data;

import com.github.chore3.fishingsizes.Fishingsizes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.util.*;

public class FishSizesReloadListener extends SimplePreparableReloadListener<Map<String, JsonObject>> {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private static final ResourceLocation LOCATION = ResourceLocation.fromNamespaceAndPath(Fishingsizes.MOD_ID, "fish_sizes.json");

    private static FishSizesReloadListener INSTANCE;

    private final Map<Item, FishSize> fishSizes = new HashMap<>();
    private FishSize defaultSize = null;
    private final List<ResourceLocation> keys = new ArrayList<>();

    public static FishSizesReloadListener create() {
        INSTANCE = new FishSizesReloadListener();
        return INSTANCE;
    }

    public static FishSizesReloadListener getOrCreate() {
        if (INSTANCE == null) INSTANCE = new FishSizesReloadListener();
        return INSTANCE;
    }

    @Override
    protected Map<String, JsonObject> prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
        Map<String, JsonObject> collected = new LinkedHashMap<>();

        for (Resource resource : resourceManager.getResourceStack(LOCATION)) {
            try (BufferedReader reader = resource.openAsReader()) {
                JsonObject json = GsonHelper.fromJson(GSON, reader, JsonObject.class);
                if (json != null) {
                    collected.put(resource.sourcePackId(), json);
                }
            } catch (Exception e) {
                Fishingsizes.LOGGER.error("Failed to read {} from pack {}", LOCATION, resource.sourcePackId(), e);
            }
        }

        return collected;
    }

    @Override
    protected void apply(Map<String, JsonObject> prepared, ResourceManager resourceManager, ProfilerFiller profiler) {
        fishSizes.clear();
        keys.clear();
        defaultSize = null;

        for (Map.Entry<String, JsonObject> entry : prepared.entrySet()) {
            String packId = entry.getKey();
            JsonObject json = entry.getValue();

            FishSizeList parsed = FishSizeList.CODEC.parse(JsonOps.INSTANCE, json)
                    .resultOrPartial(error -> Fishingsizes.LOGGER.error("Failed to parse fish_sizes.json in {}: {}", packId, error))
                    .orElse(null);

            if (parsed == null) continue;

            if (parsed.replace()) {
                fishSizes.clear();
                keys.clear();
                defaultSize = null;
            }

            parsed.defaultSize().ifPresent(size -> defaultSize = size);

            for (Map.Entry<ResourceLocation, FishSize> sizeEntry : parsed.sizes().entrySet()) {
                ResourceLocation id = sizeEntry.getKey();
                Item item = ForgeRegistries.ITEMS.getValue(id);
                if (item == null || item == net.minecraft.world.item.Items.AIR) {
                    Fishingsizes.LOGGER.warn("Unknown item in fish_sizes.json: {}", id);
                    continue;
                }

                fishSizes.put(item, sizeEntry.getValue());
                if (!keys.contains(id)) keys.add(id);
            }
        }

        Fishingsizes.LOGGER.info("Loaded {} fish size entries (defaultSize: {})", fishSizes.size(), defaultSize != null);
    }

    @Nullable
    public static FishSize getSize(@Nullable ItemStack stack) {
        FishSizesReloadListener listener = getOrCreate();
        if (stack == null || stack.isEmpty()) return listener.defaultSize;
        return listener.fishSizes.getOrDefault(stack.getItem(), listener.defaultSize);
    }

    public static List<ResourceLocation> getKeys() {
        return Collections.unmodifiableList(getOrCreate().keys);
    }

    public Map<Item, FishSize> getData() {
        return Collections.unmodifiableMap(fishSizes);
    }
}
