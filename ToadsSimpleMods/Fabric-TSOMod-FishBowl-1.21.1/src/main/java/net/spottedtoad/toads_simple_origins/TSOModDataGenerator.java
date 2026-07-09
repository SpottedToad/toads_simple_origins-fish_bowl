package net.spottedtoad.toads_simple_origins;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.spottedtoad.toads_simple_origins.datagen.ModModelProvider;

public class TSOModDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		//Register model provider
		pack.addProvider(ModModelProvider::new);
	}
}
