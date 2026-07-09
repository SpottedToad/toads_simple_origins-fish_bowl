package net.spottedtoad.toads_simple_origins.block.entity;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.spottedtoad.toads_simple_origins.TSOMod;
import net.spottedtoad.toads_simple_origins.block.ModBlocks;

public class ModBlockEntities {
    //Registers empty fish bowl block entity
    public static final BlockEntityType<EmptyFishBowlBlockEntity> EMPTY_FISH_BOWL_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            Identifier.of(TSOMod.MOD_ID, "empty_fish_bowl_block_entity"),
            BlockEntityType.Builder.create(EmptyFishBowlBlockEntity::new, ModBlocks.EMPTY_FISH_BOWL_BLOCK).build(null)
    );

    //Registers filled fish bowl block entity
    public static final BlockEntityType<FilledFishBowlBlockEntity> FILLED_FISH_BOWL_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            Identifier.of(TSOMod.MOD_ID, "filled_fish_bowl_block_entity"),
            BlockEntityType.Builder.create(FilledFishBowlBlockEntity::new, ModBlocks.FILLED_FISH_BOWL_BLOCK).build(null)
    );

    //Registers block entities
    public static void registerModBlockEntities() {
        TSOMod.LOGGER.info("Registering Mod Block Entities for " + TSOMod.MOD_ID);
    }
}