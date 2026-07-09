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

    //Register block qualities
    public static final Block EMPTY_FISH_BOWL_BLOCK = registerBlockWithoutBlockItem("empty_fish_bowl_block",
            properties -> new EmptyFishBowlBlock(properties
                    .breakInstantly().sounds(BlockSoundGroup.GLASS)
                    .ticksRandomly().pistonBehavior(PistonBehavior.NORMAL)));
    public static final Block FILLED_FISH_BOWL_BLOCK = registerBlockWithoutBlockItem("filled_fish_bowl_block",
            properties -> new FilledFishBowlBlock(properties
                    .breakInstantly().sounds(BlockSoundGroup.GLASS)
                    .pistonBehavior(PistonBehavior.NORMAL)));

    //Register blocks while preventing the creation of a block item
    private static Block registerBlockWithoutBlockItem(String name, Function<AbstractBlock.Settings, Block> function) {
        return Registry.register(Registries.BLOCK, Identifier.of(TSOMod.MOD_ID, name),
                function.apply(AbstractBlock.Settings.create()));
    }

    //Log registries
    public static void registerModBlocks() {
        TSOMod.LOGGER.info("Registering Mod Blocks for " + TSOMod.MOD_ID);
    }
}