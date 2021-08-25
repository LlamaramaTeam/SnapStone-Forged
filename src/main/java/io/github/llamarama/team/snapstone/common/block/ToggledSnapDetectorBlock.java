package io.github.llamarama.team.snapstone.common.block;

import io.github.llamarama.team.snapstone.common.register.ModSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;
import java.util.stream.Stream;

public class ToggledSnapDetectorBlock extends SnapDetectorBlock {

    public ToggledSnapDetectorBlock(Properties props) {
        super(props);
    }

    @Override
    public void trigger(ServerLevel level, BlockState state, BlockPos pos, Vec3 playerPos, ServerPlayer player) {
        if (!level.getBlockTicks().hasScheduledTick(pos, this)) {
            int oldPower = state.getValue(POWER);
            int newPower = oldPower == 0 ? this.calculatePower(playerPos, pos) : 0;
            level.playSound(null, player, ModSoundEvents.SNAP.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
            level.setBlockAndUpdate(pos,
                    state.setValue(TRIGGERED, !state.getValue(TRIGGERED)).setValue(POWER, newPower));
            level.getBlockTicks().scheduleTick(pos, this, 35);
            level.updateNeighborsAt(pos.below(), this);
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos blockPos, Random random) {

    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos blockPos, CollisionContext context) {
        return Stream.of(
                Block.box(2.0, 8.0, 2.0, 14.0, 11.0, 14.0),
                Block.box(3.0, 3.0, 3.0, 13.0, 8.0, 13.0),
                Block.box(2.0, 0.0, 2.0, 14.0, 3.0, 14.0)
        ).reduce((voxelShape, voxelShape2) -> Shapes.join(voxelShape, voxelShape2, BooleanOp.OR))
                .orElseGet(() -> Block.box(0, 0, 0, 0, 0, 0));
    }

}
