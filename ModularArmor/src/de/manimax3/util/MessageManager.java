package de.manimax3.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import de.manimax3.ModularArmor;

public class MessageManager {
	public void msgPlayerD(Player p, MessageType type, String... text) {
		for (String s : text) {
			p.sendMessage(ModularArmor.PREFIX + type.color + s);
		}
	}

	public void msgPlayer(Player p, MessageType type, String path) {
		p.sendMessage(ModularArmor.PREFIX + type.color
				+ ModularArmor.cfgmgr.getLocalization().getString(path));
	}
	
	public void msgConsole(MessageType type, String path){
		ModularArmor.console.sendMessage(ModularArmor.PREFIX + type.color + ModularArmor.cfgmgr.getLocalization().getString(path));
	}

	public enum MessageType {
		GOOD(ChatColor.GREEN), INFO(ChatColor.YELLOW), BAD(ChatColor.RED);
		public ChatColor color;

		private MessageType(ChatColor color) {
			this.color = color;
		}
	}
}
