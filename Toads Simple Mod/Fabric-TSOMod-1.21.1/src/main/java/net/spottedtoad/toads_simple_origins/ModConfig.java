package net.spottedtoad.toads_simple_origins;

import net.fabricmc.loader.api.FabricLoader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ModConfig {
    //Define maximum oxygen ticks; 6000 ticks = 5 minutes
    public static int maxOxygen = 6000;

    private static final File CONFIG_FILE = FabricLoader.getInstance()
            .getConfigDir()
            .resolve("toads_simple_origins.properties")
            .toFile();

    //Load data from the config, otherwise default to 6000 ticks
    public static void load() {
        Properties properties = new Properties();
        if (CONFIG_FILE.exists()) {
            try (FileInputStream in = new FileInputStream(CONFIG_FILE)) {
                properties.load(in);
                maxOxygen = Integer.parseInt(properties.getProperty("max_oxygen", "6000"));
            } catch (IOException | NumberFormatException e) {
                System.err.println("[ToadsSimpleOrigins] Failed to load config, using defaults.");
            }
        } else {
            //create config file
            properties.setProperty("max_oxygen", "6000");
            save(properties);
        }
    }
    private static void save(Properties properties) {
        try (FileOutputStream out = new FileOutputStream(CONFIG_FILE)) {
            properties.store(out,
                    " Toads Simple Origins Configuration File\n max_oxygen: The maximum oxygen controls the number of ticks that can pass " +
                    "before the Fish Bowl becomes empty (20 ticks = 1 second)" +
                    "The default value is equivalent to 5 minutes");
        } catch (IOException e) {
            System.err.println("[ToadsSimpleOrigins] Failed to save config file.");
        }
    }
}