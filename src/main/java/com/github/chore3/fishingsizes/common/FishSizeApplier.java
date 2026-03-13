package com.github.chore3.fishingsizes.common;

import com.github.chore3.fishingsizes.Fishingsizes;
import com.github.chore3.fishingsizes.server.data.FishSize;
import com.github.chore3.fishingsizes.server.data.FishSizesReloadListener;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Component.Serializer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

public class FishSizeApplier {
    public static final String SIZE_KEY = Fishingsizes.MOD_ID + ":size";

    private FishSizeApplier() {
    }

    public static void applyToStack(ItemStack stack, RandomSource random) {
        if (stack == null || stack.isEmpty()) return;

        FishSize config = FishSizesReloadListener.getSize(stack);
        if (config == null) return;

        float size = config.randomSize(random);

        stack.getOrCreateTag().putFloat(SIZE_KEY, size);

        CompoundTag displayTag = stack.getOrCreateTagElement("display");
        ListTag loreTag = displayTag.contains("Lore", net.minecraft.nbt.Tag.TAG_LIST)
                ? displayTag.getList("Lore", net.minecraft.nbt.Tag.TAG_STRING)
                : new ListTag();

        loreTag.add(StringTag.valueOf(Serializer.toJson(
                Component.literal(String.format("Size: %.2f", size))
        )));
        displayTag.put("Lore", loreTag);
    }
}
