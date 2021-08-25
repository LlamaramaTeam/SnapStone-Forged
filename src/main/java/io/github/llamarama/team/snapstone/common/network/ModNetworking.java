package io.github.llamarama.team.snapstone.common.network;

import io.github.llamarama.team.snapstone.SnapStoneForged;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fmllegacy.network.NetworkRegistry;
import net.minecraftforge.fmllegacy.network.simple.SimpleChannel;

public class ModNetworking {

    private static final String PROTOCOL_VERSION = "1.0.0";

    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(SnapStoneForged.MODID, "snap"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void registerMessages() {
        int id = 0;
        INSTANCE.messageBuilder(SnapMessage.class, id++)
                .decoder(SnapMessage::new)
                .encoder(SnapMessage::encode)
                .consumer(SnapMessage::handle)
                .add();
    }

}
