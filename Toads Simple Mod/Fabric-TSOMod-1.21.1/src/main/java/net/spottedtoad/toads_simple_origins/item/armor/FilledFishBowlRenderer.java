package net.spottedtoad.toads_simple_origins.item.armor;

import mod.azure.azurelib.common.render.armor.AzArmorRenderer;
import mod.azure.azurelib.common.render.armor.AzArmorRendererConfig;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.spottedtoad.toads_simple_origins.TSOMod;

import static java.awt.Transparency.TRANSLUCENT;

public class FilledFishBowlRenderer extends AzArmorRenderer {

    //Define armor shape
    private static final Identifier MODEL = Identifier.of(
            TSOMod.MOD_ID,
            "geo/filled_fish_bowl.geo.json"
    );

    //Define armor texture
    private static final Identifier TEXTURE = Identifier.of(
            TSOMod.MOD_ID,
            "textures/armor/filled_fish_bowl.png"
    );

    //Attach armor shape, texture, bones, and transparent type to renderer
    public FilledFishBowlRenderer() {
        super(AzArmorRendererConfig.builder(MODEL, TEXTURE)
                .setBoneProvider(new FilledFishBowlBoneProvider())
                .setRenderType((entity, itemStack) -> RenderLayer.getEntityTranslucent(TEXTURE))
                .build()
        );
    }

}