package com.github.chore3.fishingsizes.main;

import net.minecraft.network.chat.Component;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Fishingsizes.MOD_ID)
public class EventOverride {
    @SubscribeEvent
    public static void onItemFished(ItemFishedEvent event) {
        if (!event.getEntity().level().isClientSide) {
            event.getEntity().sendSystemMessage(Component.literal("釣りイベントが発生しました"));
        }
    }
}
