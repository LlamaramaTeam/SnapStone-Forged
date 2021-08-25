package io.github.llamarama.team.snapstone.client;

import com.mojang.blaze3d.platform.InputConstants;
import io.github.llamarama.team.snapstone.SnapStoneForged;
import io.github.llamarama.team.snapstone.common.network.ModNetworking;
import io.github.llamarama.team.snapstone.common.network.SnapMessage;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fmlclient.registry.ClientRegistry;

public class ClientEventHandler {

    public static final KeyMapping SNAP_KEY =
            new KeyMapping("snapstone.snap.key", KeyConflictContext.IN_GAME, KeyModifier.NONE,
                    InputConstants.Type.KEYSYM, InputConstants.KEY_H, "snapstone.snap.category");

    @Mod.EventBusSubscriber(modid = SnapStoneForged.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public static class ForgeEvents {

        @SubscribeEvent
        public static void clientTick(final TickEvent.ClientTickEvent event) {
            if (event.phase == TickEvent.Phase.END) {
                if (SNAP_KEY.isDown() && SNAP_KEY.consumeClick()) {
                    if (Minecraft.getInstance().player != null) {
                        Vec3 position = Minecraft.getInstance().player.position();
                        ModNetworking.INSTANCE.sendToServer(new SnapMessage(position));
                    }
                }
            }
        }

    }

    @Mod.EventBusSubscriber(modid = SnapStoneForged.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ModEvents {

        @SubscribeEvent
        public static void clientInit(final FMLClientSetupEvent event) {
            ClientRegistry.registerKeyBinding(SNAP_KEY);
        }

    }

}
