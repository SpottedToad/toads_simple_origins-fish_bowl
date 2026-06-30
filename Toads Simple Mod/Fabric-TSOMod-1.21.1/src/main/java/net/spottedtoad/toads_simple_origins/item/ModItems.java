package net.spottedtoad.toads_simple_origins.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;
import net.spottedtoad.toads_simple_origins.TSOMod;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item MISERABLE_CREATURE = registerItem("miserable_creature", new Item(new Item.Settings()));



    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, Identifier.of(TSOMod.MOD_ID, name), item);
    }

    public static void registerModItems(){
        TSOMod.LOGGER.info("Registering Mod Items for " + TSOMod.MOD_ID);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(MISERABLE_CREATURE);
        });
    }
}
