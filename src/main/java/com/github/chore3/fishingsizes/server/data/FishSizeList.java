package com.github.chore3.fishingsizes.server.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;
import java.util.Optional;

public record FishSizeList(
        boolean replace,
        Map<ResourceLocation, FishSize> sizes,
        Optional<FishSize> defaultSize
) {
    public static final Codec<FishSizeList> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.optionalFieldOf("replace", false).forGetter(FishSizeList::replace),
            Codec.unboundedMap(ResourceLocation.CODEC, FishSize.CODEC)
                    .fieldOf("sizes")
                    .forGetter(FishSizeList::sizes),
            FishSize.CODEC.optionalFieldOf("defaultSize").forGetter(FishSizeList::defaultSize)
    ).apply(instance, FishSizeList::new));
}
