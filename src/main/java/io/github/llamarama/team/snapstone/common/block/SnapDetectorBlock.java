package io.github.llamarama.team.snapstone.common.block;

import io.github.llamarama.team.snapstone.common.register.ModSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;
import java.util.stream.Stream;

public class SnapDetectorBlock extends Block {

    public static final BooleanProperty TRIGGERED = BooleanProperty.create("triggered");
    public static final IntegerProperty POWER = BlockStateProperties.POWER;

    private static final VoxelShape SHAPE_NORMAL = Stream.of(
                    Block.box(2.0, 8.0, 2.0, 14.0, 11.0, 14.0),
                    Block.box(3.0, 3.0, 3.0, 13.0, 8.0, 13.0),
                    Block.box(2.0, 0.0, 2.0, 14.0, 3.0, 14.0),
                    Block.box(3.0, 11.0, 3.0, 13.0, 13.0, 13.0)
            ).reduce((voxelShape, voxelShape2) -> Shapes.join(voxelShape, voxelShape2, BooleanOp.OR))
            .orElseGet(() -> Block.box(0, 0, 0, 0, 0, 0));
    private static final VoxelShape SHAPE_TRIGGERED = Stream.of(
                    Block.box(2.0, 8.0, 2.0, 14.0, 11.0, 14.0),
                    Block.box(3.0, 3.0, 3.0, 13.0, 8.0, 13.0),
                    Block.box(2.0, 0.0, 2.0, 14.0, 3.0, 14.0),
                    Block.box(3.0, 11.0, 3.0, 13.0, 12.0, 13.0)
            ).reduce((voxelShape, voxelShape2) -> Shapes.join(voxelShape, voxelShape2, BooleanOp.OR))
            .orElseGet(() -> Block.box(0, 0, 0, 0, 0, 0));

    public SnapDetectorBlock(Properties props) {
        super(props);
        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(TRIGGERED, false)
                .setValue(POWER, 0)
        );
    }

    public void trigger(ServerLevel level, BlockState state, BlockPos pos, Vec3 playerPos, ServerPlayer player) {
        if (!level.getBlockTicks().hasScheduledTick(pos, this)) {
            level.playSound(null, player, ModSoundEvents.SNAP.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
            level.setBlockAndUpdate(pos,
                    state.setValue(TRIGGERED, true).setValue(POWER, this.calculatePower(playerPos, pos))
            );
            level.getBlockTicks().scheduleTick(pos, this, 30);
            level.updateNeighborsAt(pos.below(), this);
        }
    }

    protected int calculatePower(Vec3 playerPos, BlockPos pos) {
        double distance = playerPos.distanceTo(Vec3.atCenterOf(pos));

        return (int) Mth.clamp(distance, 0, 15);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isSignalSource(BlockState state) {
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public int getSignal(BlockState state, BlockGetter level, BlockPos blockPos, Direction direction) {
        return state.getValue(TRIGGERED) ? 15 : 0;
    }

    @SuppressWarnings("deprecation")
    @Override
    public int getDirectSignal(BlockState state, BlockGetter level, BlockPos blockPos, Direction direction) {
        return direction == Direction.UP ? this.getSignal(state, level, blockPos, direction) : 0;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return state.getValue(TRIGGERED);
    }

    @SuppressWarnings("deprecation")
    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos blockPos) {
        return state.getValue(POWER);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos blockPos, Random random) {
        level.setBlockAndUpdate(blockPos, state.setValue(TRIGGERED, false));
        level.updateNeighborsAt(blockPos.below(), this);
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos blockPos, CollisionContext context) {
        return state.getValue(TRIGGERED) ? SHAPE_TRIGGERED : SHAPE_NORMAL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TRIGGERED, POWER);
    }

    @SuppressWarnings("deprecation")
    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.BLOCK;
    }

}
