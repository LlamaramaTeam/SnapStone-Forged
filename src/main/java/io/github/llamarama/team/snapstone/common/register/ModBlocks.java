package io.github.llamarama.team.snapstone.common.register;

import io.github.llamarama.team.snapstone.SnapStoneForged;
import io.github.llamarama.team.snapstone.common.block.SnapDetectorBlock;
import io.github.llamarama.team.snapstone.common.block.ToggledSnapDetectorBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, SnapStoneForged.MODID);

    private static final BlockBehaviour.Properties STONE_PROPS = BlockBehaviour.Properties.copy(Blocks.STONE);

    public static final RegistryObject<SnapDetectorBlock> SNAP_DETECTOR = register("snap_detector",
            () -> new SnapDetectorBlock(STONE_PROPS));
    public static final RegistryObject<ToggledSnapDetectorBlock> TOGGLED_SNAP_DETECTOR = register("toggled_snap_detector",
            () -> new ToggledSnapDetectorBlock(STONE_PROPS));


    private ModBlocks() {
    }

    private static <T extends Block> RegistryObject<T> register(String id, Supplier<T> blockSupplier) {
        RegistryObject<T> blockObject = registerNoItem(id, blockSupplier);
        ModItems.ITEMS.register(id, () -> new BlockItem(blockObject.get(),
                new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE))
        );

        return blockObject;
    }

    private static <T extends Block> RegistryObject<T> registerNoItem(String id, Supplier<T> block) {
        return BLOCKS.register(id, block);
    }

}
