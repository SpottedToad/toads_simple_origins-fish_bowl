package net.spottedtoad.toads_simple_origins.item.custom;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.spottedtoad.toads_simple_origins.block.ModBlocks;

public class EmptyFishBowlArmorItem extends ArmorItem {
    public EmptyFishBowlArmorItem(RegistryEntry<ArmorMaterial> material, Type type, Settings settings) {
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
                //Define action on server as placing empty fish bowl block
                if (!world.isClient()) {
                 BlockState blockToPlace = ModBlocks.EMPTY_FISH_BOWL_BLOCK.getDefaultState();
                    //Perform action on server when position matches conditions to be placeable
                    if (world.getBlockState(placePos).canReplace(new ItemPlacementContext(user, hand, itemStack, hitResult))) {
                        world.setBlockState(placePos, blockToPlace);
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
