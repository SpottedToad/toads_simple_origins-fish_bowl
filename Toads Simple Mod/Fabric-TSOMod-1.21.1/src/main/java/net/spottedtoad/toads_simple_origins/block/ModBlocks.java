package net.spottedtoad.toads_simple_origins.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.spottedtoad.toads_simple_origins.TSOMod;
import net.spottedtoad.toads_simple_origins.block.custom.FishBowlBlock;

import java.util.function.Function;

public class ModBlocks {

    public static final Block FISH_BOWL_BLOCK = registerBlockWithoutBlockItem("fish_bowl_block",
            properties -> new FishBowlBlock(properties.noCollision()
                    .ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GLASS)
                    .pistonBehavior(PistonBehavior.NORMAL)));


    private static Block registerBlockWithoutBlockItem(String name, Function<AbstractBlock.Settings, Block> function) {
        return Registry.register(Registries.BLOCK, Identifier.of(TSOMod.MOD_ID, name),
                function.apply(AbstractBlock.Settings.create()));
    }

    public static void registerModBlocks() {
        TSOMod.LOGGER.info("Registering Mod Blocks for " + TSOMod.MOD_ID);
    }
}