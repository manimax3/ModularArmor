package de.manimax3;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import de.manimax3.cmd.MAReload;
import de.manimax3.cmd.MAUpdate;
import de.manimax3.listener.MACombatListener;
import de.manimax3.listener.MACreationListener;
import de.manimax3.listener.MAGuiOpenListener;
import de.manimax3.listener.MAGuiUseListener;
import de.manimax3.util.ConfigManager;
import de.manimax3.util.MessageManager;
import de.manimax3.util.MessageManager.MessageType;
import de.tr7zw.itemnbtapi.ma.Itemnbtapi;

public class ModularArmor extends JavaPlugin {

	public static final String PREFIX = "[§1Modular§6Armor§r] ";

	public static ModularArmor plugin;
	
	private Object economy;
	public boolean vaultEnabled;

	public static ConfigManager cfgmgr;
	public static MessageManager msgmgr;
	public static ConsoleCommandSender console;
	private PluginManager pm;

	@Override
	public void onEnable() {
		Itemnbtapi nbtApi = new Itemnbtapi();
		nbtApi.onEnable();

		pm = this.getServer().getPluginManager();
		console = this.getServer().getConsoleSender();
		cfgmgr = new ConfigManager(this);
		msgmgr = new MessageManager();
		cfgmgr.setup();
		ModularArmor.plugin = this;
		
		vaultEnabled = pm.isPluginEnabled("Vault");
		if(vaultEnabled)
			setupEconomy();
		
		registerEvents();
		registerCommand();
		checkOlderVersions();

		if (!Itemnbtapi.ispluginisworking())
			msgmgr.msgConsole(MessageType.BAD, "ApiNotWorking");
		else
			msgmgr.msgConsole(MessageType.INFO, "PluginEnabled");
	}

	@Override
	public void onDisable() {
		cfgmgr.save();
	}

	private void registerEvents() {
		pm.registerEvents(new MACreationListener(this), this);
		pm.registerEvents(new MAGuiUseListener(), this);
		pm.registerEvents(new MACombatListener(this), this);
		pm.registerEvents(new MAGuiOpenListener(), this);
	}

	private void registerCommand() {
		this.getCommand("mareload").setExecutor(new MAReload());
		this.getCommand("maupdate").setExecutor(new MAUpdate());
	}

	private boolean setupEconomy() {
		RegisteredServiceProvider<net.milkbowl.vault.economy.Economy> economyProvider = getServer()
				.getServicesManager().getRegistration(
						net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			economy = economyProvider.getProvider();
		}
		return (economy != null);
	}
	
	public Object getEconomy(){
		if (!vaultEnabled) return null;
		return (net.milkbowl.vault.economy.Economy) economy;
	}

	private void checkOlderVersions() {
		String version = this.getDescription().getVersion();

		if (!(cfgmgr.getConfigVersion().equalsIgnoreCase(version))
				|| cfgmgr.getConfigVersion() == null) {
			msgmgr.msgConsole(MessageType.INFO, "ConfigUpdate");
		}
		if (!(cfgmgr.getLocalizationVersion().equalsIgnoreCase(version))
				|| cfgmgr.getLocalizationVersion() == null) {
			msgmgr.msgConsole(MessageType.INFO, "LocalizationUpdate");
		}
	}
	
}
