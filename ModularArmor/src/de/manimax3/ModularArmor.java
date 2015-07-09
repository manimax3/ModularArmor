package de.manimax3;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import de.manimax3.cmd.MAReload;
import de.manimax3.cmd.Test;
import de.manimax3.listener.MACombatListener;
import de.manimax3.listener.MACreationListener;
import de.manimax3.listener.MAGuiOpenListener;
import de.manimax3.listener.MAGuiUseListener;
import de.manimax3.util.ConfigManager;
import de.manimax3.util.MessageManager;
import de.tr7zw.itemnbtapi.Itemnbtapi;

public class ModularArmor extends JavaPlugin {

	public static final String PREFIX = "[§1Modular§6Armor§r] ";

	public static Economy economy = null;
	
	public static ConfigManager cfgmgr;
	public static MessageManager msgmgr;
	public static ConsoleCommandSender console;
	private PluginManager pm;

	@Override
	public void onEnable() {
		pm = this.getServer().getPluginManager();
		setupEconomy();
		cfgmgr = new ConfigManager(this);
		msgmgr = new MessageManager();
		cfgmgr.setup();
		registerEvents();
		registerCommand();

		console = this.getServer().getConsoleSender();

		if (!Itemnbtapi.ispluginisworking())
			console.sendMessage(PREFIX + "§4"
					+ cfgmgr.getLocalization().getString("ApiNotWorking"));
		else
			console.sendMessage(PREFIX + cfgmgr.getLocalization().getString("PluginEnabled"));
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
		this.getCommand("matest").setExecutor(new Test());
	}
	
	private boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }
	
}
