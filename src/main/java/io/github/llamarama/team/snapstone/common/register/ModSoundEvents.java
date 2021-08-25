package io.github.llamarama.team.snapstone.common.register;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import io.github.llamarama.team.snapstone.SnapStoneForged;

public final class ModSoundEvents {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, SnapStoneForged.MODID);

    public static final RegistryObject<SoundEvent> SNAP = SOUND_EVENTS.register("snap",
            () -> new SoundEvent(new ResourceLocation(SnapStoneForged.MODID, "snap")));

    private ModSoundEvents() {

    }
}
