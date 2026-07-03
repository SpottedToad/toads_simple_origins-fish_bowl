package net.spottedtoad.toads_simple_origins.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.item.ArmorItem;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.spottedtoad.toads_simple_origins.TSOMod;
import net.spottedtoad.toads_simple_origins.block.ModBlocks;
import net.spottedtoad.toads_simple_origins.item.ModItems;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    //Register models for blocks
    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        //Define block id path
        Identifier emptyFishBowlBlockModelId = Identifier.of(TSOMod.MOD_ID, "block/empty_fish_bowl_block");
        Identifier filledFishBowlBlockModelId = Identifier.of(TSOMod.MOD_ID, "block/filled_fish_bowl_block");

        //Attach particle textures
        //blockStateModelGenerator.registerBuiltinWithParticle(ModBlocks.EMPTY_FISH_BOWL_BLOCK, emptyFishBowlBlockModelId);
        //blockStateModelGenerator.registerBuiltinWithParticle(ModBlocks.FILLED_FISH_BOWL_BLOCK, filledFishBowlBlockModelId);
    }

    //Register models for items
    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {

    }
}
