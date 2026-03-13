package com.github.chore3.fishingsizes.common;

import com.github.chore3.fishingsizes.Fishingsizes;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Component.Serializer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Fishingsizes.MOD_ID)
public class FishSizeApplier {
    @SubscribeEvent
    public static void onItemFished(ItemFishedEvent event) {
        if (!event.getEntity().level().isClientSide) {
            for (ItemStack stack : event.getDrops()) {
                if (!stack.isEmpty()) {
                    CompoundTag displayTag = stack.getOrCreateTagElement("display");
                    ListTag loreTag = new ListTag();
                    loreTag.add(StringTag.valueOf(Serializer.toJson(Component.literal("test"))));
                    displayTag.put("Lore", loreTag);
                }
            }
            event.getEntity().sendSystemMessage(Component.literal("釣りイベントが発生しました"));
        }
    }
}
