package net.spottedtoad.toads_simple_origins.item.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.spottedtoad.toads_simple_origins.block.ModBlocks;
import net.spottedtoad.toads_simple_origins.block.entity.EmptyFishBowlBlockEntity;
import net.spottedtoad.toads_simple_origins.block.entity.FilledFishBowlBlockEntity;

public class FilledFishBowlArmorItem extends ArmorItem {
    public FilledFishBowlArmorItem(RegistryEntry<ArmorMaterial> material, Type type, Settings settings) {
        super(material, type, settings);
    }

    //Define action on item use
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        //Define action when sneaking as block raycast
        if (user.isSneaking()) {
            BlockHitResult hitResult = (BlockHitResult) user.raycast(5.0D, 0.0F, false);
            //Define action when hitting a block with raycast as defining targeted block position
            if (hitResult.getType() == HitResult.Type.BLOCK) {
                BlockPos targetPos = hitResult.getBlockPos();
                Direction side = hitResult.getSide();
                BlockPos placePos = targetPos.offset(side);
                //Define action on server as placing filled fish bowl block
                if (!world.isClient()) {
                    boolean isTargetWater = world.getFluidState(placePos).getFluid() == Fluids.WATER;
                    //Place filled if not in water, otherwise waterlogged
                    BlockState blockToPlace;
                    if (isTargetWater) {
                        blockToPlace = ModBlocks.FILLED_FISH_BOWL_BLOCK.getDefaultState().with(Properties.WATERLOGGED, true);
                    } else {
                        blockToPlace = ModBlocks.FILLED_FISH_BOWL_BLOCK.getDefaultState();
                    }
                    if (world.getBlockState(placePos).canReplace(new ItemPlacementContext(user, hand, itemStack, hitResult))) {
                        world.setBlockState(placePos, blockToPlace);
                        //Get durability data
                        int currentDamage = itemStack.getDamage();
                        //Apply durability data
                        BlockEntity placedEntity = world.getBlockEntity(placePos);
                        if (placedEntity instanceof FilledFishBowlBlockEntity filledEntity) {
                            filledEntity.setSavedDamage(currentDamage);
                        } else if (placedEntity instanceof FilledFishBowlBlockEntity filledEntity) {
                            filledEntity.setSavedDamage(currentDamage);
                        }
                        //Remove one item if not in creative
                        if (!user.getAbilities().creativeMode) {
                            itemStack.decrement(1);
                        }
                    }
                }
                //Swing hand
                return TypedActionResult.success(itemStack, world.isClient());
            }
        }
        //Define action when not sneaking as default armor item use
        return super.use(world, user, hand);
    }
}