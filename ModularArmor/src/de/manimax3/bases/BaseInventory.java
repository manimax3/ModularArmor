package de.manimax3.bases;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.manimax3.ModularArmor;
import de.manimax3.armor.ArmorType;
import de.manimax3.armor.ModularArmorPart;
import de.manimax3.armor.UpgradeType;
import de.manimax3.listener.MAGuiOpenListener;
import de.tr7zw.itemnbtapi.NBTItem;

public class BaseInventory {

	@SuppressWarnings("deprecation")
	public static Inventory createInventory(Player p) {
		Inventory inv = Bukkit.createInventory(null, InventoryType.CHEST,
				"Modular Armor Interface");
		for (int i = 0; i < inv.getSize(); i++) {
			inv.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE, 1,
					DyeColor.GRAY.getData()));
		}

		ModularArmorPart armor = ModularArmorPart.deserialize(ModularArmorPart
				.getID(MAGuiOpenListener.playerInMAGui.get(p)), ArmorType
				.getArmorTypeByMat((MAGuiOpenListener.playerInMAGui.get(p))
						.getType()));

		inv.setItem(10, cIro(armor));
		inv.setItem(11, cGol(armor));
		inv.setItem(12, cDia(armor));
		inv.setItem(13, cBrick(armor));
		inv.setItem(14, cNBrick(armor));
		inv.setItem(15, cEmer(armor));
		inv.setItem(16, cStar(armor));

		return inv;
	}

	public static void update(Player p) {
		if (!MAGuiOpenListener.playerInMAGui.containsKey(p))
			return;
		InventoryView invV = p.getOpenInventory();
		ModularArmorPart armor = ModularArmorPart.deserialize(ModularArmorPart
				.getID(MAGuiOpenListener.playerInMAGui.get(p)), ArmorType
				.getArmorTypeByMat((MAGuiOpenListener.playerInMAGui.get(p))
						.getType()));
		for (int i = 0; i < invV.countSlots(); i++) {
			if (invV.getItem(i).getType() == Material.DIAMOND) {
				invV.setItem(i, cDia(armor));
				continue;
			}
			if (invV.getItem(i).getType() == Material.IRON_INGOT) {
				invV.setItem(i, cIro(armor));
				continue;
			}
			if (invV.getItem(i).getType() == Material.GOLD_INGOT) {
				invV.setItem(i, cGol(armor));
				continue;
			}
			if (invV.getItem(i).getType() == Material.BRICK) {
				invV.setItem(i, cBrick(armor));
				continue;
			}
			if (invV.getItem(i).getType() == Material.NETHER_BRICK_ITEM) {
				invV.setItem(i, cNBrick(armor));
				continue;
			}
			if (invV.getItem(i).getType() == Material.EMERALD) {
				invV.setItem(i, cEmer(armor));
				continue;
			}
			if (invV.getItem(i).getType() == Material.NETHER_STAR) {
				invV.setItem(i, cStar(armor));
				break;
			}
		}
	}

	private static ItemStack cDia(ModularArmorPart armor) {
		ItemStack item = new ItemStack(Material.DIAMOND, 1);
		ItemMeta meta = item.getItemMeta();

		final UpgradeType type = UpgradeType.DamageReduction;

		List<String> lore = new ArrayList<String>();
		if (armor.getUpgradeLevel(type) < type.maxLevel) {
			lore.add("§fPrice: "
					+ "§6"
					+ ModularArmor.economy.format(armor.getPrizeToUpgrade(type)));
		}else lore.add("§7Already at Max Level");
		meta.setLore(lore);

		meta.setDisplayName("Damage Reduction");
		meta.addEnchant(Enchantment.ARROW_DAMAGE, 0, false);

		item.setItemMeta(meta);
		item.removeEnchantment(Enchantment.ARROW_DAMAGE);

		NBTItem nbtItem = new NBTItem(item);
		nbtItem.setString("UpgradeType", type.toString());
		nbtItem.setDouble("Price", armor.getPrizeToUpgrade(type));
		item = nbtItem.getItem();

		return item;
	}

	// Marked as useless
	private static ItemStack cIro(ModularArmorPart armor) {
		ItemStack item = new ItemStack(Material.IRON_INGOT, 1);
		ItemMeta meta = item.getItemMeta();

		meta.setDisplayName("Protection Tier I");

		item.setItemMeta(meta);
		return item;
	}

	// Marked as useless
	private static ItemStack cGol(ModularArmorPart armor) {
		ItemStack item = new ItemStack(Material.GOLD_INGOT, 1);
		ItemMeta meta = item.getItemMeta();

		meta.setDisplayName("Protection Tier II");

		item.setItemMeta(meta);
		return item;
	}

	private static ItemStack cBrick(ModularArmorPart armor) {
		ItemStack item = new ItemStack(Material.BRICK, 1);
		ItemMeta meta = item.getItemMeta();

		final UpgradeType type = UpgradeType.Unbreakable;

		List<String> lore = new ArrayList<String>();
		if (armor.getUpgradeLevel(type) < type.maxLevel) {
			lore.add("§fPrice: "
					+ "§6"
					+ ModularArmor.economy.format(armor.getPrizeToUpgrade(type)));
		}else lore.add("§7Already at Max Level");
		meta.setLore(lore);

		meta.setDisplayName("Ubreakable");
		meta.addEnchant(Enchantment.ARROW_DAMAGE, 0, false);

		item.setItemMeta(meta);
		item.removeEnchantment(Enchantment.ARROW_DAMAGE);

		NBTItem nbtItem = new NBTItem(item);
		nbtItem.setString("UpgradeType", type.toString());
		nbtItem.setDouble("Price", armor.getPrizeToUpgrade(type));
		item = nbtItem.getItem();

		return item;
	}

	// Marked as useless
	private static ItemStack cNBrick(ModularArmorPart armor) {
		ItemStack item = new ItemStack(Material.NETHER_BRICK_ITEM, 1);
		ItemMeta meta = item.getItemMeta();

		meta.setDisplayName("Useless");

		item.setItemMeta(meta);
		return item;
	}

	private static ItemStack cEmer(ModularArmorPart armor) {
		ItemStack item = new ItemStack(Material.EMERALD, 1);
		ItemMeta meta = item.getItemMeta();

		final UpgradeType type = UpgradeType.FireAbsorption;

		List<String> lore = new ArrayList<String>();
		if (armor.getUpgradeLevel(type) < type.maxLevel) {
			lore.add("§fPrize: "
					+ "§6"
					+ ModularArmor.economy.format(armor.getPrizeToUpgrade(type)));
		}else lore.add("§7Already at Max Level");
		meta.setLore(lore);

		meta.setDisplayName("Fire Absorption");
		meta.addEnchant(Enchantment.ARROW_DAMAGE, 0, false);

		item.setItemMeta(meta);
		item.removeEnchantment(Enchantment.ARROW_DAMAGE);

		NBTItem nbtItem = new NBTItem(item);
		nbtItem.setString("UpgradeType", type.toString());
		nbtItem.setDouble("Price", armor.getPrizeToUpgrade(type));
		item = nbtItem.getItem();

		return item;
	}

	// Marked as useless
	private static ItemStack cStar(ModularArmorPart armor) {
		ItemStack item = new ItemStack(Material.NETHER_STAR, 1);
		ItemMeta meta = item.getItemMeta();

		meta.setDisplayName("Useless");

		item.setItemMeta(meta);
		return item;
	}

}
