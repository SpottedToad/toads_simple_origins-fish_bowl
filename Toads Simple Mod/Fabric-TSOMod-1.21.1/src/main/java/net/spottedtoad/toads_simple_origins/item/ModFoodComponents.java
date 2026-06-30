package net.spottedtoad.toads_simple_origins.item;

import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;

public class ModFoodComponents {
    public static final FoodComponent MISERABLE_CREATURE = new FoodComponent.Builder().nutrition(1).saturationModifier(0.3f).usingConvertsTo(Items.BONE)
            .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 300, 0), 1f).build();

    public static final FoodComponent COOKED_MISERABLE_CREATURE = new FoodComponent.Builder().nutrition(4).saturationModifier(0.8f).usingConvertsTo(Items.BONE)
            .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 150, 0), 0.69f).build();
}
