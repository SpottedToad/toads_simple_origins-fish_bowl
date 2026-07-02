package net.spottedtoad.toads_simple_origins.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.spottedtoad.toads_simple_origins.TSOMod;
import net.spottedtoad.toads_simple_origins.block.custom.EmptyFishBowlBlock;
import net.spottedtoad.toads_simple_origins.block.custom.FilledFishBowlBlock;

import java.util.function.Function;

public class ModBlocks {

    public static final Block EMPTY_FISH_BOWL_BLOCK = registerBlockWithoutBlockItem("empty_fish_bowl_block",
            properties -> new EmptyFishBowlBlock(properties.noCollision()
                    .ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GLASS)
                    .pistonBehavior(PistonBehavior.NORMAL)));
    public static final Block FILLED_FISH_BOWL_BLOCK = registerBlockWithoutBlockItem("filled_fish_bowl_block",
            properties -> new FilledFishBowlBlock(properties.noCollision()
                    .ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GLASS)
                    .pistonBehavior(PistonBehavior.NORMAL)));


    private static Block registerBlockWithoutBlockItem(String name, Function<AbstractBlock.Settings, Block> function) {
        return Registry.register(Registries.BLOCK, Identifier.of(TSOMod.MOD_ID, name),
                function.apply(AbstractBlock.Settings.create().nonOpaque()));
    }

    public static void registerModBlocks() {
        TSOMod.LOGGER.info("Registering Mod Blocks for " + TSOMod.MOD_ID);
    }
}