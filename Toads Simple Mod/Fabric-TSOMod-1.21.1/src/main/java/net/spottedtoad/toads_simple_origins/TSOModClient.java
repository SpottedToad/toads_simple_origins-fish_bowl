package net.spottedtoad.toads_simple_origins;

import mod.azure.azurelib.common.render.armor.AzArmorRendererRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import net.spottedtoad.toads_simple_origins.block.ModBlocks;
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

        //Register block renderers on initialize
        BlockRenderLayerMap.INSTANCE.putBlock(
                ModBlocks.EMPTY_FISH_BOWL_BLOCK, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(
                ModBlocks.FILLED_FISH_BOWL_BLOCK, RenderLayer.getTranslucent());
    }
}
