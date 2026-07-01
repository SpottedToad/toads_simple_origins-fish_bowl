package net.spottedtoad.toads_simple_origins.item.armor;

import mod.azure.azurelib.common.render.armor.AzArmorRenderer;
import mod.azure.azurelib.common.render.armor.AzArmorRendererConfig;
import net.minecraft.util.Identifier;
import net.spottedtoad.toads_simple_origins.TSOMod;

public class EmptyFishBowlRenderer extends AzArmorRenderer {
    private static final Identifier MODEL = Identifier.of(
            TSOMod.MOD_ID,
            "geo/empty_fish_bowl.geo.json"
    );

    private static final Identifier TEXTURE = Identifier.of(
            TSOMod.MOD_ID,
            "textures/armor/empty_fish_bowl.png"
    );

    public EmptyFishBowlRenderer() {
        super(AzArmorRendererConfig.builder(MODEL, TEXTURE)
                .setBoneProvider(new EmptyFishBowlBoneProvider())
                .build()
        );
    }

}