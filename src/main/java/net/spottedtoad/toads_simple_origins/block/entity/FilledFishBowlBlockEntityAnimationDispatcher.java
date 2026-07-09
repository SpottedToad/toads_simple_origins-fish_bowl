package net.spottedtoad.toads_simple_origins.block.entity;

import mod.azure.azurelib.common.animation.dispatch.command.AzCommand;
import mod.azure.azurelib.common.animation.play_behavior.AzPlayBehaviors;
import net.minecraft.block.entity.BlockEntity;

public class FilledFishBowlBlockEntityAnimationDispatcher {
    private static final AzCommand WATERLOG_COMMAND = AzCommand.create(
            "waterlog_controller",
            "waterlog",
            AzPlayBehaviors.LOOP
    );
    private static final AzCommand UNWATERLOG_COMMAND = AzCommand.create(
            "waterlog_controller",
            "idle",
            AzPlayBehaviors.LOOP
    );

    private final BlockEntity blockEntity;

    public FilledFishBowlBlockEntityAnimationDispatcher(BlockEntity blockEntity) {
        this.blockEntity = blockEntity;
    }

    public void waterlog(BlockEntity BlockEntity) {
        WATERLOG_COMMAND.sendForBlockEntity(BlockEntity);
    }
    public void unwaterlog(BlockEntity blockEntity) {
        UNWATERLOG_COMMAND.sendForBlockEntity(blockEntity);
    }
}