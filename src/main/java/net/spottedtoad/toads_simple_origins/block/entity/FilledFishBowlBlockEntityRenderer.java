package net.spottedtoad.toads_simple_origins.block.entity;

import mod.azure.azurelib.common.animation.AzAnimatorConfig;
import mod.azure.azurelib.common.render.block.AzBlockEntityRenderer;
import mod.azure.azurelib.common.render.block.AzBlockEntityRendererConfig;
import net.minecraft.util.Identifier;
import net.spottedtoad.toads_simple_origins.TSOMod;

public class FilledFishBowlBlockEntityRenderer extends AzBlockEntityRenderer<FilledFishBowlBlockEntity> {
    //Define model and texture paths
    private static final Identifier MODEL = Identifier.of(TSOMod.MOD_ID, "geo/block/filled_fish_bowl_block.geo.json");
    private static final Identifier TEXTURE = Identifier.of(TSOMod.MOD_ID, "textures/block/filled_fish_bowl_block.png");

    public FilledFishBowlBlockEntityRenderer() {
        super(AzBlockEntityRendererConfig.<FilledFishBowlBlockEntity>builder(MODEL, TEXTURE)
                .setAnimatorProvider(() -> new FilledFishBowlBlockEntityAnimator(new AzAnimatorConfig(0.0, false, false)))
                .build()
        );
    }
}