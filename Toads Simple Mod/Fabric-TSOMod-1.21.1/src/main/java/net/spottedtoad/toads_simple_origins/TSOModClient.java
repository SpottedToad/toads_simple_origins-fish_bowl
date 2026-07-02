package net.spottedtoad.toads_simple_origins;

import mod.azure.azurelib.common.render.armor.AzArmorRendererRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import net.spottedtoad.toads_simple_origins.block.ModBlocks;
import net.spottedtoad.toads_simple_origins.item.ModItems;
import net.spottedtoad.toads_simple_origins.item.armor.EmptyFishBowlRenderer;

public class TSOModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        AzArmorRendererRegistry.register(EmptyFishBowlRenderer::new,
                ModItems.EMPTY_FISH_BOWL);
        AzArmorRendererRegistry.register(EmptyFishBowlRenderer::new,
                ModItems.FILLED_FISH_BOWL);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.EMPTY_FISH_BOWL_BLOCK, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FILLED_FISH_BOWL_BLOCK, RenderLayer.getTranslucent());
    }
}
