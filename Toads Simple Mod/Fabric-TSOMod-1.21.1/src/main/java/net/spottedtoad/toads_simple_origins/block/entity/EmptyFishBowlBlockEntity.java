package net.spottedtoad.toads_simple_origins.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class EmptyFishBowlBlockEntity extends BlockEntity {
    public EmptyFishBowlBlockEntity(BlockPos pos, BlockState state) {
        //Implements block entity
        super(ModBlockEntities.EMPTY_FISH_BOWL_BLOCK_ENTITY, pos, state);
    }
}
