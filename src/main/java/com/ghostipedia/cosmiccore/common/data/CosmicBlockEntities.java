package com.ghostipedia.cosmiccore.common.data;

import com.ghostipedia.cosmiccore.common.block.pipelike.HeatPipeBlock;
import com.ghostipedia.cosmiccore.common.blockentity.pipelike.HeatPipeBlockEntity;
import com.ghostipedia.cosmiccore.ember.blockentity.CosmicEmberEmitterBlockEntity;
import com.ghostipedia.cosmiccore.ember.blockentity.CosmicEmberReceptorBlockEntity;

import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import com.gregtechceu.gtceu.api.GTValues;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.entry.BlockEntry;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import java.util.Locale;
import java.util.Map;

import static com.ghostipedia.cosmiccore.api.registries.CosmicRegistration.REGISTRATE;
import static com.ghostipedia.cosmiccore.common.data.CosmicBlocks.EMBER_EMITTER_BLOCKS;
import static com.ghostipedia.cosmiccore.common.data.CosmicBlocks.EMBER_RECEPTOR_BLOCKS;

public class CosmicBlockEntities {

    public static final BlockEntityEntry<HeatPipeBlockEntity> HEAT_PIPE = null;

//    public static final BlockEntityEntry<HeatPipeBlockEntity> HEAT_PIPE = CosmicRegistration.REGISTRATE
//            .blockEntity("heat_pipe", HeatPipeBlockEntity::new)
//            .validBlocks(CosmicBlockEntities.HEAT_PIPE_BLOCKS.values().toArray(BlockEntry[]::new))
//            .register();

    public static final Map<Integer, BlockEntityEntry<CosmicEmberEmitterBlockEntity>> COSMIC_EMBER_EMITTER_BE = registerEmberEmitters();
    public static final Map<Integer, BlockEntityEntry<CosmicEmberReceptorBlockEntity>> COSMIC_EMBER_RECEIVER_BE = registerEmberReceptors();

    static ImmutableTable.Builder<TagPrefix, Material, BlockEntry<HeatPipeBlock>> HEAT_PIPE_BLOCKS_BUILDER = ImmutableTable.builder();
    public static Table<TagPrefix, Material, BlockEntry<HeatPipeBlock>> HEAT_PIPE_BLOCKS;

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

//    public static void generateHeatPipeBlocks() {
//        CosmicCore.LOGGER.debug("generating cosmic core material blocks...");
//
//        for(var heatPipeType : HeatPipeType.values()) {
//            for(MaterialRegistry registry : GTCEuAPI.materialManager.getRegistries()) {
//                GTRegistrate registrate = registry.getRegistrate();
//                for(Material mat : registry.getAllMaterials()) {
//                    if(allowHeatPipeBlock(mat, heatPipeType)) {
//                        registerHeatPipeBlock(mat, heatPipeType, registrate);
//                    }
//                }
//            }
//        }
//        HEAT_PIPE_BLOCKS = HEAT_PIPE_BLOCKS_BUILDER.build();
//    }
//
//    private static boolean allowHeatPipeBlock(Material mat, HeatPipeType type) {
//        return mat.hasProperty(CosmicPropertyKeys.HEAT) && !type.getTagPrefix().isIgnored(mat);
//    }
//
//    private static void registerHeatPipeBlock(Material mat, HeatPipeType heatPipeType, GTRegistrate registrate) {
//        var entry = registrate
//                .block("%s_%s_heat_pipe".formatted(mat.getName(), heatPipeType.name().toLowerCase(Locale.ROOT)),
//                        p -> new HeatPipeBlock(p, heatPipeType, mat))
//                .initialProperties(() -> Blocks.IRON_BLOCK)
//                .properties(p -> {
//                    return p.dynamicShape().noOcclusion().noLootTable().forceSolidOn();
//                })
//                .transform(GTBlocks.unificationBlock(heatPipeType.getTagPrefix(), mat))
//                .blockstate(NonNullBiConsumer.noop())
//                .setData(ProviderType.LANG, NonNullBiConsumer.noop())
//                .setData(ProviderType.LOOT, NonNullBiConsumer.noop())
//                .addLayer(()-> RenderType::cutoutMipped)
//                .color(() -> MaterialPipeBlock::tintedColor)
//                .item(MaterialPipeBlockItem::new)
//                .color(() -> MaterialPipeBlockItem::tintColor)
//                .model(NonNullBiConsumer.noop())
//                .build()
//                .register();
//        HEAT_PIPE_BLOCKS_BUILDER.put(heatPipeType.getTagPrefix(), mat, entry);
//    }

    public static void init() {
    }
}
