package com.github.chore3.fishingsizes;

import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod("fishingsizes")
public class Fishingsizes {
    public static final String MOD_ID = "fishingsizes";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Fishingsizes(){
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
    }
}
