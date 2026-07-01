package net.spottedtoad.toads_simple_origins.item.custom;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.registry.entry.RegistryEntry;

public class EmptyFishBowlArmorItem extends ArmorItem {
    public EmptyFishBowlArmorItem(RegistryEntry<ArmorMaterial> material, Type type, Settings settings) {
        super(material, type, settings);
    }
}
