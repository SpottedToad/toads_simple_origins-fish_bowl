package net.spottedtoad.toads_simple_origins.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class EmptyFishBowlBlock extends Block {
    public EmptyFishBowlBlock(Settings settings) {
        super(settings);
    }


    //Define hitbox shape value
    private static final VoxelShape SHAPE =
            Block.createCuboidShape(3.2, 0, 3.2, 12.8, 9.6, 12.8);

    //Define visual hitbox with shape value
    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    //Define physical hitbox with shape value
    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }


    //Define block model
    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}