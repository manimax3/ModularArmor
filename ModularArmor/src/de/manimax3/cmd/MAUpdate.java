package de.manimax3.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.manimax3.ModularArmor;
import de.manimax3.util.ConfigManager;
import de.manimax3.util.MessageManager;
import de.manimax3.util.MessageManager.MessageType;

public class MAUpdate implements CommandExecutor {

	private static MessageManager msgmgr = ModularArmor.msgmgr;
	private static ConfigManager cfgmgr = ModularArmor.cfgmgr;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {

		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (!p.hasPermission("modulararmor.update")) {
				msgmgr.msgPlayer(p, MessageType.BAD, "PermissionMissing");
			} else {

				if (args[0].equalsIgnoreCase("config")) {
					cfgmgr.forceUpdate(true, false);
					msgmgr.msgPlayer(p, MessageType.INFO, "DataUpdated");
				} else if (args[0].equalsIgnoreCase("localization")) {
					cfgmgr.forceUpdate(false, true);
					msgmgr.msgPlayer(p, MessageType.INFO, "DataUpdated");
				} else
					msgmgr.msgPlayer(p, MessageType.INFO, "WrongArguments");

			}
		} else {
			if (args[0].equalsIgnoreCase("config")) {
				msgmgr.msgConsole(MessageType.INFO, "DataUpdated");
				cfgmgr.forceUpdate(true, false);
			} else if (args[0].equalsIgnoreCase("localization")) {
				msgmgr.msgConsole(MessageType.INFO, "DataUpdated");
				cfgmgr.forceUpdate(false, true);
			} else
				msgmgr.msgConsole(MessageType.INFO, "WrongArguments");
		}

		return true;
	}

}
