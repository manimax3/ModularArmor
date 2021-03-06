package de.manimax3.armor;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import de.manimax3.ErrorCode;
import de.manimax3.ModularArmor;
import de.tr7zw.itemnbtapi.ma.Itemnbtapi;

public class ModularArmorPart {
	private final int ID;
	private ArmorType type;
	private HashMap<UpgradeType, Integer> upgrades;

	private static FileConfiguration data = null;
	private static FileConfiguration config = null;

	public ModularArmorPart(ArmorType type) {
		if (data == null)
			data = ModularArmor.cfgmgr.getData();
		if (config == null)
			config = ModularArmor.cfgmgr.getConfig();
		this.type = type;
		ID = manageID();
		if (upgrades == null)
			upgrades = new HashMap<UpgradeType, Integer>();
		this.serialize();
	}

	private ModularArmorPart(int id, ArmorType type) {
		this.type = type;
		this.ID = id;
		if (data == null)
			data = ModularArmor.cfgmgr.getData();
		if (config == null)
			config = ModularArmor.cfgmgr.getConfig();
		if (upgrades == null)
			upgrades = new HashMap<UpgradeType, Integer>();
		this.serialize();
	}

	public String serialize() {

		String strg = "";

		for (Entry<UpgradeType, Integer> ent : upgrades.entrySet()) {
			strg = strg + ent.getKey().toString() + ":" + ent.getValue() + ";";
		}

		data.set("IDs." + this.type.toString() + "." + ID, strg);

		return strg;
	}

	public static ModularArmorPart deserialize(int id, ArmorType type) {
		if (data == null)
			data = ModularArmor.cfgmgr.getData();

		try {
			String strg;

			strg = data.getString("IDs." + type.toString() + "." + id);
			String[] parts = strg.split(";");

			ModularArmorPart armor = new ModularArmorPart(id, type);

			for (String s : parts) {

				if (s == "" || s == null)
					continue;
				try {
					String[] sub = s.split(":");
					armor.addUpgrade(UpgradeType.valueOf(sub[0]),
							Integer.parseInt(sub[1]));
				} catch (Exception e) {
					continue;
				}

			}

			return armor;
		} catch (Exception e) {
			ErrorCode.DESERIALIZATION_Fail.out();
			return null;
		}
	}

	private int manageID() {
		int ids = 0;
		ids = data.getInt("IDs." + type.toString() + ".count") + 1;
		data.set("IDs." + type.toString() + ".count", ids);
		return ids;
	}

	public int getID() {
		return ID;
	}

	public void addUpgrade(UpgradeType type, int level) {
		if (upgrades.containsKey(type)) {
			int curLevel = upgrades.get(type);
			upgrades.put(type, curLevel + level);
		} else {
			upgrades.put(type, level);
		}
		this.serialize();
	}

	public int getUpgradeLevel(UpgradeType type) {
		if (upgrades.containsKey(type))
			return upgrades.get(type);
		return 0;
	}

	public static boolean isModular(ItemStack item) {

		if (item == null) {
			return false;
		}
		if (item.getType() == null) {
			return false;
		}

		for (ArmorType type : ArmorType.values()) {
			if (type.getMaterial() == item.getType()) {
				if (Itemnbtapi.getNBTItem(item).hasKey("ID")) {
					return true;
				}
			}
		}
		return false;

	}

	public static int getID(ItemStack item) {
		int id = 0;
		if (isModular(item)) {
			id = Itemnbtapi.getNBTItem(item).getInteger("ID");
		}
		return id;
	}

	public HashMap<UpgradeType, Integer> getUpgrades() {
		return upgrades;
	}

	public boolean hasUpgrade(UpgradeType type) {
		if (upgrades.containsKey(type) && upgrades.get(type) > 0)
			return true;
		else
			return false;
	}

	public double getPrizeToUpgrade(UpgradeType type) {
		
		if (type == null) return 0;
		
		double basePrize = config.getDouble("Upgrades." + type.toString()
				+ ".BasePrize");
		double raisingConst = config.getDouble("Upgrades." + type.toString()
				+ ".RaisingConstant");
//		System.out.println(type.toString() + " BasePrize: " + basePrize
//				+ " Raising: " + raisingConst);
		double price = Math.round(basePrize
				* Math.pow(raisingConst, this.getUpgradeLevel(type)));
		return price;
	}

}
