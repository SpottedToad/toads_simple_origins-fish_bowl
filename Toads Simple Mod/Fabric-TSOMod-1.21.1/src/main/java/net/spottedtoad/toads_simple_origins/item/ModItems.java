package net.spottedtoad.toads_simple_origins.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemGroups;
import net.spottedtoad.toads_simple_origins.TSOMod;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.spottedtoad.toads_simple_origins.item.custom.EmptyFishBowlArmorItem;

import static net.minecraft.item.Items.DRIED_KELP;
import static net.minecraft.item.Items.LEATHER_HORSE_ARMOR;

public class ModItems {

    public static final Item MISERABLE_CREATURE = registerItem("miserable_creature",
            new Item(new Item.Settings().food(ModFoodComponents.MISERABLE_CREATURE)));
    public static final Item COOKED_MISERABLE_CREATURE = registerItem("cooked_miserable_creature",
            new Item(new Item.Settings().food(ModFoodComponents.COOKED_MISERABLE_CREATURE)));

    public static final Item EMPTY_FISH_BOWL = registerItem("empty_fish_bowl",
            new EmptyFishBowlArmorItem(ModArmorMaterials.GLASS_ARMOR_MATERIAL, ArmorItem.Type.HELMET, new Item.Settings()
                    .maxDamage(ArmorItem.Type.HELMET.getMaxDamage(12))));
    public static final Item REINFORCED_FISH_BOWL = registerItem("reinforced_fish_bowl",
            new ArmorItem(ModArmorMaterials.GLASS_ARMOR_MATERIAL, ArmorItem.Type.HELMET, new Item.Settings()
                    .maxDamage(ArmorItem.Type.HELMET.getMaxDamage(20))));

    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, Identifier.of(TSOMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        TSOMod.LOGGER.info("Registering Mod Items for " + TSOMod.MOD_ID);
            ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> {
                entries.addAfter(DRIED_KELP, MISERABLE_CREATURE);
                entries.addAfter(MISERABLE_CREATURE, COOKED_MISERABLE_CREATURE);
            });
            ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
                entries.addBefore(LEATHER_HORSE_ARMOR, EMPTY_FISH_BOWL);
                entries.addAfter(EMPTY_FISH_BOWL, REINFORCED_FISH_BOWL);
            });
    }
}
