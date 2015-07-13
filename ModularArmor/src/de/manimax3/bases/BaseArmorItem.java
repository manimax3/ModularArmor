package de.manimax3.bases;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.manimax3.ModularArmor;
import de.manimax3.armor.ArmorType;
import de.manimax3.armor.ModularArmorPart;
import de.manimax3.armor.UpgradeType;
import de.tr7zw.itemnbtapi.ma.Itemnbtapi;
import de.tr7zw.itemnbtapi.ma.NBTItem;

public class BaseArmorItem {

	public static FileConfiguration localization;

	public static ItemStack generateArmor(Material mat) {
		localization = ModularArmor.cfgmgr.getLocalization();
		ItemStack item = new ItemStack(mat);
		ItemMeta meta = item.getItemMeta();

		ArmorType type = ArmorType.getArmorTypeByMat(mat);
		String matName = type.toString();
		switch (matName) {
		case "CHESTPLATE":
			matName = localization.getString("ChestPlate Name");
			break;
		case "BOOTS":
			matName = localization.getString("Boots Name");
			break;
		case "HELMET":
			matName = localization.getString("Helmet Name");
			break;
		case "LEGGINGS":
			matName = localization.getString("Leggings Name");
			break;
		}

		String displayName = "§l§4Modular " + matName;
		ChatColor.translateAlternateColorCodes('&', displayName);

		ModularArmorPart armor = new ModularArmorPart(type);

		// List<String> lore = new ArrayList<String>();
		// lore.add("ID: "+ armor.getID());
		// meta.setLore(lore);

		meta.setDisplayName(displayName);
		item.setItemMeta(meta);

		NBTItem nbtItem = new NBTItem(item);
		nbtItem.setInteger("ID", armor.getID());
		item = nbtItem.getItem();

		return item;
	}

	public static ItemStack updateArmorItem(ItemStack item, Player p) {
		ModularArmorPart armor = ModularArmorPart.deserialize(
				ModularArmorPart.getID(item),
				ArmorType.getArmorTypeByMat(item.getType()));

		if (armor == null)
			return null;

		ItemMeta itemMeta = item.getItemMeta();
		List<String> lore = new ArrayList<String>();
		lore.add("§3Upgrades on this Armor");
		int itemID = armor.getID();
		
		if (armor.hasUpgrade(UpgradeType.Unbreakable)) {
			item.setDurability((short) 0);
			itemMeta.spigot().setUnbreakable(true);
		}

		for (Entry<UpgradeType, Integer> ent : armor.getUpgrades().entrySet()) {
		if(ent.getKey() == UpgradeType.Unbreakable) continue;
			lore.add("§6" + ent.getKey().toString() + ": §2" + ent.getValue());
		}
		itemMeta.setLore(lore);
		item.setItemMeta(itemMeta);

		if (p != null) {
			int i = 0;
			boolean found = false;
			for (ItemStack cont : p.getInventory().getContents()) {
				if (itemID == ModularArmorPart.getID(cont)) {
					p.getInventory().setItem(i, item);
					found = true;
					break;
				}
				i++;
			}
			if (!found) {
				ItemStack[] contents = p.getInventory().getArmorContents();
				for (int j = 0; j < contents.length; j++) {
					if (itemID == ModularArmorPart.getID(contents[j])) {
						contents[j] = item;
						p.getInventory().setArmorContents(contents);
						break;
					}
				}
			}
			p.updateInventory();
		}
		Itemnbtapi.getNBTItem(item).setInteger("ID", itemID);
		return item;
	}

}
