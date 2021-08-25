package io.github.llamarama.team.snapstone.common.register;

import io.github.llamarama.team.snapstone.SnapStoneForged;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, SnapStoneForged.MODID);

    private ModItems() {

    }

}
