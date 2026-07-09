package net.spottedtoad.toads_simple_origins.block.entity;

import mod.azure.azurelib.common.animation.AzAnimatorConfig;
import mod.azure.azurelib.common.animation.controller.AzAnimationController;
import mod.azure.azurelib.common.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.common.animation.impl.AzBlockAnimator;
import net.minecraft.util.Identifier;
import net.spottedtoad.toads_simple_origins.TSOMod;
import org.jetbrains.annotations.NotNull;

public class FilledFishBowlBlockEntityAnimator extends AzBlockAnimator<FilledFishBowlBlockEntity> {
    public FilledFishBowlBlockEntityAnimator(AzAnimatorConfig config) {
        super(config);
    }

    //Define paths for animations
    private static final Identifier DEFAULT = Identifier.of(TSOMod.MOD_ID, "animations/block/filled_fish_bowl_block.animation.json");


    //Register animation controller
    @Override
    public void registerControllers(AzAnimationControllerContainer<FilledFishBowlBlockEntity> animationControllerContainer) {
        animationControllerContainer.add(
                AzAnimationController.builder(this, "waterlog_controller")
                        .build()
        );
    }

    //Define animations with path
    @Override
    public @NotNull Identifier getAnimationLocation(FilledFishBowlBlockEntity animatable) {
        return DEFAULT;
    }
}