package de.manimax3.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.manimax3.ModularArmor;

public class ConfigManager {

	private ModularArmor plugin;

	public ConfigManager(ModularArmor plugin) {
		this.plugin = plugin;
	}

	private File fconfig, flocalization, fdata;
	private FileConfiguration config, localization, data;

	public void setup() {
		if (!plugin.getDataFolder().exists()) {
			plugin.getDataFolder().mkdirs();
		}
		plugin.saveResource("localization.yml", false);
		plugin.saveResource("config.yml", false);

		fconfig = new File(plugin.getDataFolder(), "config.yml");
		flocalization = new File(plugin.getDataFolder(), "localization.yml");
		fdata = new File(plugin.getDataFolder(), "data.yml");

		if (!fdata.exists())
			try {
				fdata.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}

		config = YamlConfiguration.loadConfiguration(fconfig);
		localization = YamlConfiguration.loadConfiguration(flocalization);
		data = YamlConfiguration.loadConfiguration(fdata);
	}

	public FileConfiguration getConfig() {
		return config;
	}

	public FileConfiguration getLocalization() {
		return localization;
	}
	public FileConfiguration getData() {
		return data;
	}
	

	public void save() {
		// localization.options().copyDefaults(true);
		// config.options().copyDefaults(true);
		try {
			data.save(fdata);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void load() {
		
		try {
			config.load(fconfig);
			localization.load(flocalization);
			data.load(fdata);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}

	}

}
