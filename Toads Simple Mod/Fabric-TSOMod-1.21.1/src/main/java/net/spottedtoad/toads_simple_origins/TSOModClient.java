package net.spottedtoad.toads_simple_origins;

import mod.azure.azurelib.common.render.armor.AzArmorRendererRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.spottedtoad.toads_simple_origins.block.ModBlocks;
import net.spottedtoad.toads_simple_origins.block.entity.EmptyFishBowlBlockEntityRenderer;
import net.spottedtoad.toads_simple_origins.block.entity.FilledFishBowlBlockEntityRenderer;
import net.spottedtoad.toads_simple_origins.block.entity.ModBlockEntities;
import net.spottedtoad.toads_simple_origins.item.ModItems;
import net.spottedtoad.toads_simple_origins.item.armor.EmptyFishBowlRenderer;
import net.spottedtoad.toads_simple_origins.item.armor.FilledFishBowlRenderer;

public class TSOModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        //Register armor renderers on initialize
        AzArmorRendererRegistry.register(EmptyFishBowlRenderer::new,
                ModItems.EMPTY_FISH_BOWL);
        AzArmorRendererRegistry.register(FilledFishBowlRenderer::new,
                ModItems.FILLED_FISH_BOWL);

        //Register block entity renderers on initialize
        BlockEntityRendererFactories.register(
                ModBlockEntities.EMPTY_FISH_BOWL_BLOCK_ENTITY,
                ctx -> new EmptyFishBowlBlockEntityRenderer());

        BlockEntityRendererFactories.register(
                ModBlockEntities.FILLED_FISH_BOWL_BLOCK_ENTITY,
                ctx -> new FilledFishBowlBlockEntityRenderer()
        );
    }
}
