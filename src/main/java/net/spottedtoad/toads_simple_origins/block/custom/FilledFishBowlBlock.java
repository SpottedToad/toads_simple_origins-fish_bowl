package net.spottedtoad.toads_simple_origins.block.custom;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.nbt.NbtCompound;
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

public class FilledFishBowlBlock extends TranslucentBlock implements Waterloggable, BlockEntityProvider {
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

    //Set state to waterlogged when placed in water
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        boolean isWater = fluidState.getFluid() == Fluids.WATER;
        return this.getDefaultState().with(Properties.WATERLOGGED, isWater);
    }

    //Update logic
    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(Properties.WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }


    //Define action on item use
    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        //Define action when held item is a bucket
        if (stack.isOf(Items.BUCKET)) {
            //Define action on server as play sound, swap block with empty fish bowl block, and waterlog if block was waterlogged
            if (!world.isClient()) {
                //Get durability data
                int currentDamage = 0;
                if (world.getBlockEntity(pos) instanceof FilledFishBowlBlockEntity filledEntity) {
                    currentDamage = filledEntity.getSavedDamage();
                }
                world.playSound(null, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                boolean isWaterlogged = state.contains(Properties.WATERLOGGED) && state.get(Properties.WATERLOGGED);
                //Define action when block is waterlogged as replacing with a not waterlogged filled fish bowl
                if (isWaterlogged) {
                    world.setBlockState(pos, ModBlocks.FILLED_FISH_BOWL_BLOCK.getDefaultState()
                            .with(Properties.WATERLOGGED, false), 3);
                }
                //Define action when block is not waterlogged as replacing with a not waterlogged empty fish bowl
                else {
                    world.setBlockState(pos, ModBlocks.EMPTY_FISH_BOWL_BLOCK.getDefaultState()
                            .with(Properties.WATERLOGGED, false), 3);
                }
                //Apply durability data
                if (world.getBlockEntity(pos) instanceof EmptyFishBowlBlockEntity emptyEntity) {
                    emptyEntity.setSavedDamage(currentDamage);
                }
                //Remove one item when not in creative, then place water bucket into their hand or dropped onto the ground with full inventory
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
            //Swing hand
            return ItemActionResult.SUCCESS;
        }
        //Define action when held item is not a bucket as default
        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }


    //Implement system to copy oxygen level to dropped item
    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder) {
        //Get drop from loot table
        List<ItemStack> drops = super.getDroppedStacks(state, builder);

        //get block data on block break
        BlockEntity blockEntity = builder.getOptional(LootContextParameters.BLOCK_ENTITY);
        if (blockEntity instanceof FilledFishBowlBlockEntity bowlEntity) {
            //Get oxygen level data
            int savedOxygen = bowlEntity.getOxygenLevel();
            //Get dropped fish bowl helmet
            for (ItemStack drop : drops) {
                if (drop.getRegistryEntry().getKey().map(key -> key.getValue().toString()).orElse("").equals("toads_simple_origins:filled_fish_bowl")) {
                    //Add block data to dropped helmet item
                    NbtCompound nbt = new NbtCompound();
                    nbt.putInt("oxygenLevel", savedOxygen);
                    drop.set(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(nbt));
                }
            }
        }
        return drops;
    }


    //Trigger animation
    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
       if (world.getBlockEntity(pos) instanceof FilledFishBowlBlockEntity fishBowlBe) {
           if (state.get(Properties.WATERLOGGED)) {
               fishBowlBe.dispatcher.waterlog(fishBowlBe);
           }
           else {
               fishBowlBe.dispatcher.unwaterlog(fishBowlBe);
           }
        }
    }

    //Control animations by block state
    @Override
    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            super.onStateReplaced(state, world, pos, newState, moved);
            return;
        }
        if (!world.isClient() && state.get(Properties.WATERLOGGED) && !newState.get(Properties.WATERLOGGED)) {
            world.updateListeners(pos, state, newState, 3);
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }


    //Implements block entity
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FilledFishBowlBlockEntity(pos, state);
    }


    //Apply data to dropped item
    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient() && !player.getAbilities().creativeMode) {
            //Get durability and oxygen data
            int currentDamage = 0;
            int currentOxygen = 0;
            if (world.getBlockEntity(pos) instanceof FilledFishBowlBlockEntity filledEntity) {
                currentDamage = filledEntity.getSavedDamage();
                currentOxygen = filledEntity.getOxygenLevel();
            }
            //Drop item with durability data
            ItemStack droppedStack = new ItemStack(ModItems.FILLED_FISH_BOWL);
            droppedStack.set(DataComponentTypes.DAMAGE, currentDamage);
            NbtCompound customData = new NbtCompound();
            customData.putInt("oxygenLevel", currentOxygen);
            droppedStack.set(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(customData));
            Block.dropStack(world, pos, droppedStack);
        }
        return super.onBreak(world, pos, state, player);
    }
}