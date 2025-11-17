package com.ghostipedia.cosmiccore.common.data;

import com.ghostipedia.cosmiccore.CosmicCore;
import com.ghostipedia.cosmiccore.api.material.CosmicPropertyKeys;
import com.ghostipedia.cosmiccore.api.registries.CosmicRegistration;
import com.ghostipedia.cosmiccore.common.block.pipelike.HeatPipeBlock;
import com.ghostipedia.cosmiccore.common.blockentity.pipelike.HeatPipeBlockEntity;
import com.ghostipedia.cosmiccore.common.pipelike.heat.HeatPipeType;
import com.ghostipedia.cosmiccore.ember.blockentity.CosmicEmberEmitterBlockEntity;
import com.ghostipedia.cosmiccore.ember.blockentity.CosmicEmberReceptorBlockEntity;

import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.GTValues;

import com.gregtechceu.gtceu.api.block.MaterialPipeBlock;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.registry.MaterialRegistry;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.item.MaterialPipeBlockItem;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import com.gregtechceu.gtceu.common.blockentity.FluidPipeBlockEntity;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.data.GTMaterialBlocks;
import com.gregtechceu.gtceu.common.registry.GTRegistration;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Blocks;

import java.util.Locale;
import java.util.Map;

import static com.ghostipedia.cosmiccore.api.registries.CosmicRegistration.REGISTRATE;
import static com.ghostipedia.cosmiccore.common.data.CosmicBlocks.EMBER_EMITTER_BLOCKS;
import static com.ghostipedia.cosmiccore.common.data.CosmicBlocks.EMBER_RECEPTOR_BLOCKS;

public class CosmicBlockEntities {

    //public static final BlockEntityEntry<HeatPipeBlockEntity> HEAT_PIPE = null;

//    public static final BlockEntityEntry<HeatPipeBlockEntity> HEAT_PIPE = CosmicRegistration.REGISTRATE
//            .blockEntity("heat_pipe", HeatPipeBlockEntity::new)
//            .validBlocks(CosmicBlockEntities.HEAT_PIPE_BLOCKS.values().toArray(BlockEntry[]::new))
//            .register();



    public static final Map<Integer, BlockEntityEntry<CosmicEmberEmitterBlockEntity>> COSMIC_EMBER_EMITTER_BE = registerEmberEmitters();
    public static final Map<Integer, BlockEntityEntry<CosmicEmberReceptorBlockEntity>> COSMIC_EMBER_RECEIVER_BE = registerEmberReceptors();

    private static Map<Integer, BlockEntityEntry<CosmicEmberEmitterBlockEntity>> registerEmberEmitters() {
        var emitters = new it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap<BlockEntityEntry<CosmicEmberEmitterBlockEntity>>();
        for (int i = 0; i < 15; i++) {
            final int tier = i;
            final String key = (i == 0 ? "steam" : GTValues.VN[i].toLowerCase(Locale.ROOT));

            var be = REGISTRATE.<CosmicEmberEmitterBlockEntity>blockEntity(
                    "cosmic_%s_ember_emitter_be".formatted(key),
                    (type, pos, state) -> new CosmicEmberEmitterBlockEntity(type, pos, state, tier))
                    .validBlock(EMBER_EMITTER_BLOCKS.get(i))   // safe now
                    .register();

            emitters.put(i, be);
        }
        return emitters;
    }

    private static Map<Integer, BlockEntityEntry<CosmicEmberReceptorBlockEntity>> registerEmberReceptors() {
        var receptors = new Int2ObjectOpenHashMap<BlockEntityEntry<CosmicEmberReceptorBlockEntity>>();
        for (int i = 0; i < 15; i++) {
            final int tier = i;
            final String key = (i == 0 ? "steam" : GTValues.VN[i].toLowerCase(Locale.ROOT));

            var be = REGISTRATE.<CosmicEmberReceptorBlockEntity>blockEntity(
                    "cosmic_%s_ember_receiver_be".formatted(key),
                    (type, pos, state) -> new CosmicEmberReceptorBlockEntity(type, pos, state, tier))
                    .validBlock(EMBER_RECEPTOR_BLOCKS.get(i))
                    .register();

            receptors.put(i, be);
        }
        return receptors;
    }

    public static void init() {}
}
