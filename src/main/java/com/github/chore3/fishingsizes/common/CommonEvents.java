package com.github.chore3.fishingsizes.common;

import com.github.chore3.fishingsizes.Fishingsizes;
import com.github.chore3.fishingsizes.server.data.FishSizesReloadListener;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Fishingsizes.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonEvents {
    @SubscribeEvent
    public static void onAddReloadListeners(AddReloadListenerEvent event) {
        event.addListener(FishSizesReloadListener.create());
        Fishingsizes.LOGGER.info("Registered FishSizesReloadListener");
    }

    @SubscribeEvent
    public static void onItemFished(ItemFishedEvent event) {
        if (event.getEntity().level().isClientSide) return;

        for (ItemStack stack : event.getDrops()) {
            FishSizeApplier.applyToStack(stack, event.getEntity().getRandom());
        }
    }
}
