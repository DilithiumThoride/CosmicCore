package com.ghostipedia.cosmiccore.common.data;

import com.ghostipedia.cosmiccore.api.registries.CosmicRegistration;
import com.ghostipedia.cosmiccore.common.blockentity.pipelike.HeatPipeBlockEntity;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.entry.BlockEntry;

public class CosmicHeatPipe {
    //TODO: Determine if this is the appropriate way/place to do this? Having it in CosmicMaterialBlocks throws an exception
    @SuppressWarnings("unchecked")
    public static final BlockEntityEntry<HeatPipeBlockEntity> HEAT_PIPE = CosmicRegistration.REGISTRATE
            .blockEntity("heat_pipe", HeatPipeBlockEntity::new)
            .onRegister(HeatPipeBlockEntity::onBlockEntityRegister)
            .validBlocks(CosmicMaterialBlocks.HEAT_PIPE_BLOCKS.values().toArray(BlockEntry[]::new))
            .register();

    public static void init() {}
}
