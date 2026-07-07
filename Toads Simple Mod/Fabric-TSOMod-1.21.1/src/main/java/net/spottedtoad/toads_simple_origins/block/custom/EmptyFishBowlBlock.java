package net.spottedtoad.toads_simple_origins.block.custom;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.spottedtoad.toads_simple_origins.block.ModBlocks;
import net.spottedtoad.toads_simple_origins.block.entity.EmptyFishBowlBlockEntity;
import net.spottedtoad.toads_simple_origins.block.entity.FilledFishBowlBlockEntity;
import net.spottedtoad.toads_simple_origins.item.ModItems;

import java.util.List;

public class EmptyFishBowlBlock extends TranslucentBlock implements Waterloggable, BlockEntityProvider {
    public EmptyFishBowlBlock(Settings settings) {
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


    //Prevent self face culling
    @Override
    protected boolean isSideInvisible(BlockState state, BlockState neighborState, Direction direction) {
        return false;
    }

    //Prevent vanilla block rendering
    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
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

    //Set block to waterlogged filled fish bowl block when in the waterlogged state
    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        //Waterlogging ticks
        if (state.get(Properties.WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        //Get durability data
        if (!world.isClient() && world.getFluidState(pos).isIn(FluidTags.WATER)) {
            int currentDamage = 0;
            if (world.getBlockEntity(pos) instanceof EmptyFishBowlBlockEntity emptyEntity) {
                currentDamage = emptyEntity.getSavedDamage();
            }
            if (world instanceof World realWorld) {
                realWorld.setBlockState(pos, ModBlocks.FILLED_FISH_BOWL_BLOCK.getDefaultState()
                        .with(Properties.WATERLOGGED, true), 3);
                if (realWorld.getBlockEntity(pos) instanceof FilledFishBowlBlockEntity filledEntity) {
                    filledEntity.setSavedDamage(currentDamage);
                }
            }
            return ModBlocks.FILLED_FISH_BOWL_BLOCK.getDefaultState().with(Properties.WATERLOGGED, true);
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }


    //Define action on item use
    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        //Define action when held item is a water bucket
        if (stack.isOf(Items.WATER_BUCKET)) {
            //Define action on server as play sound, swap block with filled fish bowl block, and waterlog if block was waterlogged
            if (!world.isClient()) {
                //Get durability data
                int currentDamage = 0;
                if (world.getBlockEntity(pos) instanceof EmptyFishBowlBlockEntity emptyEntity) {
                    currentDamage = emptyEntity.getSavedDamage();
                }
                world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
                //Change block state
                world.setBlockState(pos, ModBlocks.FILLED_FISH_BOWL_BLOCK.getDefaultState()
                        .with(Properties.WATERLOGGED, false), 3);
                //Apply durability data
                if (world.getBlockEntity(pos) instanceof FilledFishBowlBlockEntity filledEntity) {
                    filledEntity.setSavedDamage(currentDamage);
                }
                //Remove one item when not in creative, then place water bucket into their hand or dropped onto the ground with full inventory
                if (!player.getAbilities().creativeMode) {
                    stack.decrement(1);
                    ItemStack bucket = new ItemStack(Items.BUCKET);
                    if (stack.isEmpty()) {
                        player.setStackInHand(hand, bucket);
                    } else if (!player.getInventory().insertStack(bucket)) {
                        player.dropItem(bucket, false);
                    }
                }
            }
            //Swing hand
            return ItemActionResult.SUCCESS;
        }
        //Define action when held item is not a bucket as default
        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }


    //Implement random ticks
    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    //Set chance to change block during rain when exposed to the sky
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.isRaining() && world.isSkyVisible(pos)) {
            this.transformBlock(state, world, pos);
        }
    }

    //Change block to filled fish bowl block
    protected void transformBlock(BlockState state, ServerWorld world, BlockPos pos) {
        //Get durability data
        int currentDamage = 0;
        if (world.getBlockEntity(pos) instanceof EmptyFishBowlBlockEntity emptyEntity) {
            currentDamage = emptyEntity.getSavedDamage();
        }
        //Change block
        world.setBlockState(pos, ModBlocks.FILLED_FISH_BOWL_BLOCK.getDefaultState(), 3);
        //Apply durability data
        if (world.getBlockEntity(pos) instanceof FilledFishBowlBlockEntity filledEntity) {
            filledEntity.setSavedDamage(currentDamage);
        }
    }


    //Apply durability data to dropped item
    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient() && !player.getAbilities().creativeMode) {
            //Get durability data
            int currentDamage = 0;
            if (world.getBlockEntity(pos) instanceof EmptyFishBowlBlockEntity emptyEntity) {
                currentDamage = emptyEntity.getSavedDamage();
            }
            //Drop item with durability data
            ItemStack droppedStack = new ItemStack(ModItems.EMPTY_FISH_BOWL);
            droppedStack.set(DataComponentTypes.DAMAGE, currentDamage);
            Block.dropStack(world, pos, droppedStack);
        }
        return super.onBreak(world, pos, state, player);
    }
    
    //Implements block entity
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new EmptyFishBowlBlockEntity(pos, state);
    }
}