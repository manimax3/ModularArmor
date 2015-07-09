package de.manimax3.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.manimax3.armor.ArmorType;
import de.manimax3.armor.ModularArmorPart;
import de.manimax3.armor.UpgradeType;

public class Test implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command,	String label, String[] args) {
		Player p = (Player) sender;
		if(!p.isOp()) return true;
		
		if(args[0].equalsIgnoreCase("createArmorObj")){
			
			ArmorType type = ArmorType.valueOf(args[1]);
			ModularArmorPart armor = new ModularArmorPart(type);
			armor.addUpgrade(UpgradeType.DamageReduction, 10);
			armor.addUpgrade(UpgradeType.FireAbsorption, 2);
			
			armor.serialize();
			
			
			p.sendMessage(""+armor.getID());
		}
		
		if(args[0].equalsIgnoreCase("getArmorObj")){
			ModularArmorPart armor = null;
			try {
				armor = ModularArmorPart.deserialize(Integer.parseInt(args[1]), ArmorType.valueOf(args[2]));
			} catch (Exception e) {
				p.sendMessage("Knnte Armor nicht finden");
				return true;
			}
			p.sendMessage("" + armor.getID());
			p.sendMessage(""+ armor.getUpgradeLevel(UpgradeType.DamageReduction));
			
			for(UpgradeType u : armor.getUpgrades().keySet()){
				if (u == null) continue;
				p.sendMessage(u.toString() + " at level " + armor.getUpgradeLevel(u));
			}
		}
		
		return true;
	}

}
