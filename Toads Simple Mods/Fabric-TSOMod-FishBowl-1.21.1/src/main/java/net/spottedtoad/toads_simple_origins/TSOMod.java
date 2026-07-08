package net.spottedtoad.toads_simple_origins;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.fabricmc.api.ModInitializer;
import net.spottedtoad.toads_simple_origins.block.ModBlocks;
import net.spottedtoad.toads_simple_origins.block.entity.ModBlockEntities;
import net.spottedtoad.toads_simple_origins.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TSOMod implements ModInitializer {
	public static final String MOD_ID = "toads_simple_origins";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		//Register mod config
		ModConfig.load();

		//Register mod items on initialize
		ModItems.registerModItems();

		//Register mod blocks on initialize
		ModBlocks.registerModBlocks();

		//Register mod block entities on initialize
		ModBlockEntities.registerModBlockEntities();

		//Allow config to reload with /reload command
		ResourceManagerHelper.get(ResourceType.SERVER_DATA)
				.registerReloadListener(new SimpleSynchronousResourceReloadListener() {
					@Override
					public Identifier getFabricId() {
						return Identifier.of("toads_simple_origins", "config_reloader");
					}

					@Override
					public void reload(ResourceManager manager) {
						ModConfig.load();
					}
				});
	}
}
