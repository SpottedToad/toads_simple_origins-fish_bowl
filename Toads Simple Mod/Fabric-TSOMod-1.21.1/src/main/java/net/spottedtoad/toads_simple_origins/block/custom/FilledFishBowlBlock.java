package net.spottedtoad.toads_simple_origins.block.custom;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.spottedtoad.toads_simple_origins.block.ModBlocks;

public class FilledFishBowlBlock extends Block implements Waterloggable {
    public FilledFishBowlBlock(Settings settings) {
        super(settings);
        //Set default state to not be waterlogged
        this.setDefaultState(this.stateManager.getDefaultState().with(Properties.WATERLOGGED, false));
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


    //Create waterlogged state
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(Properties.WATERLOGGED);
    }

    //Fill with water in waterlogged state
    @Override
    protected FluidState getFluidState(BlockState state) {
        if (state.contains(Properties.WATERLOGGED) && state.get(Properties.WATERLOGGED)) {
            return Fluids.WATER.getStill(false);
        }
        return super.getFluidState(state);
    }

    //Set state to waterlogged when placed in water
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        boolean isWater = fluidState.getFluid() == Fluids.WATER;
        return this.getDefaultState().with(Properties.WATERLOGGED, isWater);
    }

    //Set state to waterlogged when updates occur around the block
    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(Properties.WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    // Define action on item use
    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, net.minecraft.util.hit.BlockHitResult hit) {
        // Define action when held item is a bucket
        if (stack.isOf(Items.BUCKET)) {
            // Define action on server as play sound, swap block with empty fish bowl block, and waterlog if block was waterlogged
            if (!world.isClient()) {
                world.playSound(null, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                boolean isWaterlogged = state.contains(Properties.WATERLOGGED) && state.get(Properties.WATERLOGGED);
                // Define action when block is waterlogged as replacing with a not waterlogged filled fish bowl
                if (isWaterlogged) {
                    world.setBlockState(pos, ModBlocks.FILLED_FISH_BOWL_BLOCK.getDefaultState()
                            .with(Properties.WATERLOGGED, false), 3);
                }
                //Define action when block is not waterlogged as replacing with a not waterlogged empty fish bowl
                else {
                    world.setBlockState(pos, ModBlocks.EMPTY_FISH_BOWL_BLOCK.getDefaultState()
                            .with(Properties.WATERLOGGED, false), 3);
                }
                // Remove one item when not in creative, then place water bucket into their hand or dropped onto the ground with full inventory
                if (!player.getAbilities().creativeMode) {
                    stack.decrement(1);
                    ItemStack waterBucket = new ItemStack(Items.WATER_BUCKET);
                    if (stack.isEmpty()) {
                        player.setStackInHand(hand, waterBucket);
                    } else if (!player.getInventory().insertStack(waterBucket)) {
                        player.dropItem(waterBucket, false);
                    }
                }
            }
            // Swing hand
            return ItemActionResult.SUCCESS;
        }
        // Define action when held item is not a bucket as default
        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }
}