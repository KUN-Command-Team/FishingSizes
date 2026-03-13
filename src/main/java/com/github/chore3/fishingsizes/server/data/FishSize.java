package com.github.chore3.fishingsizes.server.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.RandomSource;

public record FishSize(float min, float max) {
    public static final Codec<FishSize> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.FLOAT.fieldOf("min").forGetter(FishSize::min),
            Codec.FLOAT.fieldOf("max").forGetter(FishSize::max)
    ).apply(instance, FishSize::new));

    public float randomSize(RandomSource random) {
        if (max <= min) return min;
        float randNum1 = min + random.nextFloat() * (max - min);
        float randNum2 = min + random.nextFloat() * (max - min);
        return (randNum1 + randNum2) / 2f;
    }
}
