package me.mathiaseklund.stylenames;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	private static Main main;

	File configurationConfig;
	public FileConfiguration config;

	public static Main getMain() {
		return main;
	}

	public void onEnable() {
		main = this;
		configurationConfig = new File(getDataFolder(), "config.yml");
		config = YamlConfiguration.loadConfiguration(configurationConfig);
		if (!configurationConfig.exists()) {
			loadConfig();
		}
		getCommand("style").setExecutor(new StyleCommand());
	}

	public void savec() {
		try {
			config.save(configurationConfig);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadConfig() {

		config.addDefault("message.command.notarget", "There is no target by that name.");
		config.addDefault("message.command.changed.self", "You've changed your displayname to: %name.");
		config.addDefault("message.command.changed.other", "You've changed %player's displayname to: %name.");
		config.addDefault("message.command.noperm.color", "You are not allowed to use color in your name.");
		config.addDefault("message.command.noperm.other", "You are only allowed to change your own displayname.");
		config.addDefault("message.command.noperm.format", "You are not allowed to use formatting in your name.");
		config.addDefault("message.command.illegal", "Illegal name argument. You need to keep your original name.");
		config.addDefault("message.command.noperm.use", "You are not allowed to use that command.");
		config.addDefault("message.command.usage", "Usage: /style <name> [player]");
		config.options().copyDefaults(true);
		savec();

	}

}
