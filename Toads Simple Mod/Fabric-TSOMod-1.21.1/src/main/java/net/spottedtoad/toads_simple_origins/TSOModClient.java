package net.spottedtoad.toads_simple_origins;

import mod.azure.azurelib.common.render.armor.AzArmorRendererRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.spottedtoad.toads_simple_origins.item.ModItems;
import net.spottedtoad.toads_simple_origins.item.armor.EmptyFishBowlRenderer;

public class TSOModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        AzArmorRendererRegistry.register(EmptyFishBowlRenderer::new,
                ModItems.EMPTY_FISH_BOWL);
    }
}
