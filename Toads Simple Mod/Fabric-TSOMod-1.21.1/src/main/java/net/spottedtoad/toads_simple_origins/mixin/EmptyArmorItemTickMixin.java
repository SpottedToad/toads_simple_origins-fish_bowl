package net.spottedtoad.toads_simple_origins.mixin;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.Identifier;
import net.spottedtoad.toads_simple_origins.item.custom.EmptyFishBowlArmorItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class EmptyArmorItemTickMixin {

    //Set tick threshold to switch to filled state
    @Unique
    private static final int RAIN_FILL_THRESHOLD = 1200;

    //Get equipped item
    @Shadow
    public abstract ItemStack getEquippedStack(EquipmentSlot slot);
    @Inject(method = "tick", at = @At("HEAD"))
    private void tickEmptyArmorConversion(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        //Track player on client
        if (entity.getWorld().isClient() || !(entity instanceof PlayerEntity player)) return;
        //Track head slot
        ItemStack headStack = this.getEquippedStack(EquipmentSlot.HEAD);
        if (headStack.isEmpty()) return;
        //Check for specific item
        if (headStack.getItem() instanceof EmptyFishBowlArmorItem) {
            //Define filled fish bowl item
            Item filledBowlItem = Registries.ITEM.get(Identifier.of("toads_simple_origins", "filled_fish_bowl"));
            if (filledBowlItem == null) return;
            //Switch to filled bowl when submerged in water while transfering data
            if (player.isSubmergedIn(FluidTags.WATER)) {
                ItemStack filledHelmet = new ItemStack(filledBowlItem);
                filledHelmet.set(DataComponentTypes.CUSTOM_DATA, headStack.get(DataComponentTypes.CUSTOM_DATA));
                player.equipStack(EquipmentSlot.HEAD, filledHelmet);
            }
            //Begin ticking towards water threshold
            else if (player.getWorld().isRaining() && player.getWorld().isSkyVisible(player.getBlockPos())) {
                //Don't tick in ultra-warm environment
                if (!player.getWorld().getDimension().ultrawarm()) {
                    NbtComponent nbtComponent = headStack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT);
                    NbtCompound nbt = nbtComponent.copyNbt();
                    int rainProgress = nbt.contains("rainTicks") ? nbt.getInt("rainTicks") : 0;
                    rainProgress++;
                    if (rainProgress >= RAIN_FILL_THRESHOLD) {
                        ItemStack filledHelmet = new ItemStack(filledBowlItem);
                        filledHelmet.set(DataComponentTypes.CUSTOM_DATA, headStack.get(DataComponentTypes.CUSTOM_DATA));
                        player.equipStack(EquipmentSlot.HEAD, filledHelmet);
                    } else {
                        nbt.putInt("rainTicks", rainProgress);
                        headStack.set(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(nbt));
                    }
                }
            }
        }
    }
}