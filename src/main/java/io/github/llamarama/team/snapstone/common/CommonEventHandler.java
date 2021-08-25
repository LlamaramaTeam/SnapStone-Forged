package io.github.llamarama.team.snapstone.common;

import io.github.llamarama.team.snapstone.SnapStoneForged;
import io.github.llamarama.team.snapstone.common.network.ModNetworking;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class CommonEventHandler {

    @Mod.EventBusSubscriber(modid = SnapStoneForged.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static final class ForgeEvents {

    }

    @Mod.EventBusSubscriber(modid = SnapStoneForged.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static final class ModEvents {

        @SubscribeEvent
        public static void commonInit(final FMLCommonSetupEvent event) {
            ModNetworking.registerMessages();
        }

    }

}
