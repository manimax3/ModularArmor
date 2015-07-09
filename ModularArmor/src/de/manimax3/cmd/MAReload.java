package de.manimax3.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.manimax3.ModularArmor;
import de.manimax3.util.MessageManager;
import de.manimax3.util.MessageManager.MessageType;

public class MAReload implements CommandExecutor {

	private static MessageManager msgmgr = ModularArmor.msgmgr;

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (!p.hasPermission("modulararmor.reload")) {
				msgmgr.msgPlayer(p, MessageType.BAD, "PermissionMissing");
			} else {

				ModularArmor.cfgmgr.save();
				ModularArmor.cfgmgr.load();
				msgmgr.msgPlayer(p, MessageType.INFO, "ConfigReload");
			}
		} else {
			ModularArmor.cfgmgr.save();
			ModularArmor.cfgmgr.load();
			String s = ModularArmor.cfgmgr.getLocalization().getString(
					"ConfigReload");
			ModularArmor.console.sendMessage(ModularArmor.PREFIX + s);
		}
		return true;
	}

}
