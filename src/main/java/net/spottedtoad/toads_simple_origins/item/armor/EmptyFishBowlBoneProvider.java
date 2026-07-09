package net.spottedtoad.toads_simple_origins.item.armor;

import mod.azure.azurelib.common.model.AzBakedModel;
import mod.azure.azurelib.common.model.AzBone;
import mod.azure.azurelib.common.render.armor.bone.AzDefaultArmorBoneProvider;

public class EmptyFishBowlBoneProvider extends AzDefaultArmorBoneProvider {

    //Provide head bone
    @Override
    public AzBone getHeadBone(AzBakedModel model){
        return model.getBone("armorHead").orElse(null);
    }
}
