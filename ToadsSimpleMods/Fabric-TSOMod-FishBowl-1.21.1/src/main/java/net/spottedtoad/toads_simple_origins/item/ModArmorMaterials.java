package net.spottedtoad.toads_simple_origins.item;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.spottedtoad.toads_simple_origins.TSOMod;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class ModArmorMaterials {

    //Define values for glass armor material
    public static final RegistryEntry<ArmorMaterial> GLASS_ARMOR_MATERIAL = registerArmorMaterial("glass",
            () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS, 1);
                map.put(ArmorItem.Type.LEGGINGS, 2);
                map.put(ArmorItem.Type.CHESTPLATE, 2);
                map.put(ArmorItem.Type.HELMET, 1);
                map.put(ArmorItem.Type.BODY, 3);
            }), 0, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, () -> Ingredient.ofItems(Items.GLASS),
                    List.of(new ArmorMaterial.Layer(Identifier.of(TSOMod.MOD_ID, "glass"))), 0, 0));

    //Define values for unused reinforced glass armor material
    public static final RegistryEntry<ArmorMaterial> REINFORCED_GLASS_ARMOR_MATERIAL = registerArmorMaterial("reinforced_glass",
            () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS, 3);
                map.put(ArmorItem.Type.LEGGINGS, 4);
                map.put(ArmorItem.Type.CHESTPLATE, 5);
                map.put(ArmorItem.Type.HELMET, 3);
                map.put(ArmorItem.Type.BODY, 6);
            }), 0, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, () -> Ingredient.ofItems(Items.GLASS),
                    List.of(new ArmorMaterial.Layer(Identifier.of(TSOMod.MOD_ID, "reinforced_glass"))), 0, 0));


    //Register armor materials
    public static RegistryEntry<ArmorMaterial> registerArmorMaterial(String name, Supplier<ArmorMaterial> material){
        return Registry.registerReference(Registries.ARMOR_MATERIAL, Identifier.of(TSOMod.MOD_ID, name), material.get());
    }
}
