package de.manimax3.listener;

import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

import de.manimax3.ModularArmor;
import de.manimax3.armor.ArmorType;
import de.manimax3.armor.ModularArmorPart;
import de.manimax3.armor.UpgradeType;

public class MACombatListener implements Listener {

	@SuppressWarnings("unused")
	private ModularArmor plugin;

	public MACombatListener(ModularArmor plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerDamageEntity(EntityDamageByEntityEvent e) {
		if (!(e.getEntity() instanceof Player))
			return;
		Player damaged = (Player) e.getEntity();
		ItemStack[] armorContents = damaged.getInventory().getArmorContents();
		int damageReduction = 0;
		for (ItemStack item : armorContents) {
			if (item == null)
				continue;
			if (!ModularArmorPart.isModular(item))
				continue;
			ModularArmorPart armor = ModularArmorPart.deserialize(
					ModularArmorPart.getID(item),
					ArmorType.getArmorTypeByMat(item.getType()));
			if(!armor.hasUpgrade(UpgradeType.DamageReduction)) continue;
			damageReduction += armor
					.getUpgradeLevel(UpgradeType.DamageReduction);
		}
		if (e.getDamage() - damageReduction >= 0) {
			e.setDamage(e.getDamage() - damageReduction);
		}
		else e.setDamage(0);
	}
	@EventHandler
	public void onEntityDamage(EntityDamageEvent e){
		if(!(e.getCause() == DamageCause.FIRE_TICK)) return;
		if (!(e.getEntity() instanceof Player))
			return;
		Player p = (Player) e.getEntity();
		ItemStack[] armorContents = p.getInventory().getArmorContents();
		int fireAbsorption = 0;
		for (ItemStack item : armorContents){
			if (item == null)
				continue;
			if (!ModularArmorPart.isModular(item))
				continue;
			ModularArmorPart armor = ModularArmorPart.deserialize(
					ModularArmorPart.getID(item),
					ArmorType.getArmorTypeByMat(item.getType()));
			if(!armor.hasUpgrade(UpgradeType.FireAbsorption)) continue;
//			if(armor.hasUpgrade(UpgradeType.Unbreakable)){
//				ItemMeta meta = item.getItemMeta();
//				meta.spigot().setUnbreakable(true);
//				item.setItemMeta(meta);	
//			}

			fireAbsorption += armor.getUpgradeLevel(UpgradeType.FireAbsorption);
		}
		
		if(fireAbsorption > 0){
			Random rand = new Random();
			if(rand.nextInt(10) < fireAbsorption){
				double health = p.getHealth() + e.getFinalDamage() * 2;
				if(health > 20) health = 20;
				p.setHealth(health);
			}
		}
		
	}

}
