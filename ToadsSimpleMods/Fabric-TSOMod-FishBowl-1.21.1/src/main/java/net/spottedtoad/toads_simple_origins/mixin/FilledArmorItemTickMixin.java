package net.spottedtoad.toads_simple_origins.mixin;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.FluidTags;
import net.spottedtoad.toads_simple_origins.item.custom.FilledFishBowlArmorItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.spottedtoad.toads_simple_origins.ModConfig.maxOxygen;

@Mixin(LivingEntity.class)
public abstract class FilledArmorItemTickMixin {
    @Shadow
    public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    //Add tick event
    @Inject(method = "tick", at = @At("HEAD"))
    private void tickCustomArmor(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        //Tick on the server
        if (entity.getWorld().isClient() || !(entity instanceof PlayerEntity player)) return;

        //Check head slot for filled fish bowl armor item
        ItemStack headStack = this.getEquippedStack(EquipmentSlot.HEAD);
        if (headStack.getItem() instanceof FilledFishBowlArmorItem) {
            //Get or create nbt data
            NbtComponent nbtComponent = headStack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT);
            NbtCompound nbt = nbtComponent.copyNbt();
            //Initialize nbt data
            if (!nbt.contains("oxygenLevel")) {
                nbt.putInt("oxygenLevel", maxOxygen);
            }
            int currentOxygen = nbt.contains("oxygenLevel") ? nbt.getInt("oxygenLevel") : maxOxygen;
            //Ensure oxygen is reset to the maximum value when above that value (useful for setting maxOxygen to low values in config)
            if (currentOxygen > maxOxygen) {
                currentOxygen = maxOxygen;
            }

            //Check player for gills, hide hud if no gills
            boolean hasGills = player.getCommandTags().contains("has_gills");
            nbt.putBoolean("showGillsHud", hasGills);
            if (hasGills) {
                //Check for rain or water
                if (player.isSubmergedIn(FluidTags.WATER) || (player.getWorld().isRaining() && player.getWorld().isSkyVisible(player.getBlockPos()))) {
                    currentOxygen = maxOxygen;
                }
                //Tick when exposed to air
                else {
                    if (currentOxygen > 0) {
                        currentOxygen--;
                    }
                }
            }
            //Modify nbt data of item
            nbt.putInt("oxygenLevel", currentOxygen);
            headStack.set(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(nbt));
            player.getInventory().markDirty();
        }
    }
}