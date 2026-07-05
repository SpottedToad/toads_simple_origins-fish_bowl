package net.spottedtoad.toads_simple_origins.mixin;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.spottedtoad.toads_simple_origins.item.custom.FilledFishBowlArmorItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class ArmorItemTickMixin {
    @Shadow
    public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    //Set max oxygen; 6000 ticks = 300 seconds = 5 minutes
    @Unique
    private static final int MAX_OXYGEN = 1200;

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
                nbt.putInt("oxygenLevel", 1200);
            }
            int currentOxygen = nbt.getInt("oxygenLevel");
            //Check for rain or water
            if (player.isTouchingWater() || (player.getWorld().isRaining() && player.getWorld().isSkyVisible(player.getBlockPos()))) {
                currentOxygen = MAX_OXYGEN;
            }
            //Tick when exposed to air
            else {
                if (currentOxygen > 0) {
                    currentOxygen--;
                }
            }
            //Modify nbt data of item
            nbt.putInt("oxygenLevel", currentOxygen);
            headStack.set(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(nbt));
        }
    }
}