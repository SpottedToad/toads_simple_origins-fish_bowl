package net.spottedtoad.toads_simple_origins.item.armor;

import mod.azure.azurelib.common.render.armor.AzArmorRenderer;
import mod.azure.azurelib.common.render.armor.AzArmorRendererConfig;
import net.minecraft.util.Identifier;
import net.spottedtoad.toads_simple_origins.TSOMod;

public class EmptyFishBowlRenderer extends AzArmorRenderer {

    //Define armor shape
    private static final Identifier MODEL = Identifier.of(
            TSOMod.MOD_ID,
            "geo/armor/empty_fish_bowl.geo.json"
    );

    //Define armor texture
    private static final Identifier TEXTURE = Identifier.of(
            TSOMod.MOD_ID,
            "textures/armor/empty_fish_bowl.png"
    );

    //Attach armor shape, texture, and bones to renderer
    public EmptyFishBowlRenderer() {
        super(AzArmorRendererConfig.builder(MODEL, TEXTURE)
                .setBoneProvider(new EmptyFishBowlBoneProvider())
                .build()
        );
    }

}