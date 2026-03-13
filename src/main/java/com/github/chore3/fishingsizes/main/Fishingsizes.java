package com.github.chore3.fishingsizes.main;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("fishingsizes")
public class Fishingsizes {
    public static final String MOD_ID = "fishingsizes";

    public Fishingsizes(){
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
    }
}
