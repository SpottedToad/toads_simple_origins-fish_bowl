package net.spottedtoad.toads_simple_origins;


import net.fabricmc.api.ModInitializer;

import net.spottedtoad.toads_simple_origins.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TSOMod implements ModInitializer {
	public static final String MOD_ID = "toads_simple_origins";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
	}
}
