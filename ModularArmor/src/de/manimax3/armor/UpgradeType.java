package de.manimax3.armor;

public enum UpgradeType {

	DamageReduction(5),
	FireAbsorption(2),
	Unbreakable(1),
	PoisonAbsorption(2);
	
	public int maxLevel;
	
	private UpgradeType(int maxLevel) {
		this.maxLevel = maxLevel;
	}
}
