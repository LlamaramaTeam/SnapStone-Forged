package io.github.llamarama.team.snapstone;

import io.github.llamarama.team.snapstone.common.register.ModBlocks;
import io.github.llamarama.team.snapstone.common.register.ModItems;
import io.github.llamarama.team.snapstone.common.register.ModSoundEvents;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(SnapStoneForged.MODID)
public final class SnapStoneForged {

    public static final String MODID = "snapstone";
    private static final Logger LOGGER = LogManager.getLogger();

    public SnapStoneForged() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModSoundEvents.SOUND_EVENTS.register(modEventBus);

        LOGGER.info("SnapStone has been initialized");
    }

    public static Logger getLogger() {
        return LOGGER;
    }

}
