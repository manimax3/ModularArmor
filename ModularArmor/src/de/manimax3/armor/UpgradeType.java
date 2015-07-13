package de.manimax3.armor;

import org.bukkit.configuration.file.FileConfiguration;

import de.manimax3.ModularArmor;

public enum UpgradeType {

	DamageReduction(5),
	FireAbsorption(2),
	Unbreakable(1),
	PoisonAbsorption(2);
	
	public int maxLevel;
	
	private UpgradeType(int maxLevel) {
		this.maxLevel = maxLevel;
	}
	
	public static void updateMaxLevels(){
		FileConfiguration cfg = ModularArmor.cfgmgr.getConfig();
		for (UpgradeType type : UpgradeType.values()){
			int level = cfg.getInt("Upgrades." + type.toString() + ".maxLevel");
			if (level == 0) continue;
			type.maxLevel = level;
		}
	}
	
}
