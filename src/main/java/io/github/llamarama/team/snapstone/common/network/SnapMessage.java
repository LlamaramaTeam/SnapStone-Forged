package io.github.llamarama.team.snapstone.common.network;

import io.github.llamarama.team.snapstone.common.block.SnapDetectorBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class SnapMessage {

    private final Vec3 playerPos;

    public SnapMessage(Vec3 playerPos) {
        this.playerPos = playerPos;
    }

    public SnapMessage(FriendlyByteBuf buf) {
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();

        this.playerPos = new Vec3(x, y, z);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeDouble(this.playerPos.x);
        buf.writeDouble(this.playerPos.y);
        buf.writeDouble(this.playerPos.z);
    }

    public boolean handle(Supplier<NetworkEvent.Context> contextSupplier) {
        final AtomicBoolean result = new AtomicBoolean();
        contextSupplier.get().enqueueWork(() -> {
            ServerPlayer sender = contextSupplier.get().getSender();
            if (sender != null) {
                ServerLevel level = sender.getLevel();

                BlockPos.withinManhattanStream(new BlockPos(this.playerPos), 15, 15, 15)
                        .filter((pos) -> level.getBlockState(pos).getBlock() instanceof SnapDetectorBlock)
                        .forEach((pos) -> {
                            BlockState state = level.getBlockState(pos);
                            Block block = state.getBlock();
                            ((SnapDetectorBlock) block).trigger(level, state, pos, this.playerPos, sender);
                        });
            }
        });

        return false;
    }

}
