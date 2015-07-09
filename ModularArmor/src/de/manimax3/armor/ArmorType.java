package de.manimax3.armor;

import org.bukkit.Material;

public enum ArmorType {

	HELMET(Material.DIAMOND_HELMET),
	LEGGEINGS(Material.DIAMOND_LEGGINGS),
	CHESTPLATE(	Material.DIAMOND_CHESTPLATE),
	BOOTS(Material.DIAMOND_BOOTS);

	private Material mat;

	private ArmorType(Material mat) {
		this.mat = mat;
	}

	public Material getMaterial() {
		return mat;
	}

	public static ArmorType getArmorTypeByMat(Material mat) {

		for (ArmorType type : ArmorType.values()) {
			if (type.getMaterial().equals(mat))
				return type;
		}

		return null;
	}
	public static boolean isArmorType(Material mat){
		for(ArmorType type : ArmorType.values()){
			if(type.getMaterial() == mat)
				return true;
		}
		return false;
	}
}
