package net.spottedtoad.toads_simple_origins.block.entity;

import mod.azure.azurelib.common.render.block.AzBlockEntityRenderer;
import mod.azure.azurelib.common.render.block.AzBlockEntityRendererConfig;
import net.minecraft.util.Identifier;
import net.spottedtoad.toads_simple_origins.TSOMod;

public class EmptyFishBowlBlockEntityRenderer extends AzBlockEntityRenderer<EmptyFishBowlBlockEntity> {
    private static final Identifier MODEL = Identifier.of(TSOMod.MOD_ID, "geo/block/empty_fish_bowl_block.geo.json");
    private static final Identifier TEXTURE = Identifier.of(TSOMod.MOD_ID, "textures/block/empty_fish_bowl_block.png");
    public EmptyFishBowlBlockEntityRenderer() {
        super(AzBlockEntityRendererConfig.<EmptyFishBowlBlockEntity>builder(MODEL, TEXTURE).build()
        );
    }
}