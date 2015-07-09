package de.manimax3.bases;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.manimax3.armor.UpgradeType;
import de.tr7zw.itemnbtapi.NBTItem;

public class BaseInventory {
	@SuppressWarnings("deprecation")
	public static Inventory createInventory() {
		Inventory inv = Bukkit.createInventory(null, InventoryType.CHEST,
				"Modular Armor Interface");
		for (int i = 0; i < inv.getSize(); i++){
			inv.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.GRAY
				.getData()));
		}
		
		inv.setItem(10, cIro());
		inv.setItem(11, cGol());
		inv.setItem(12, cDia());
		inv.setItem(13, cBrick());
		inv.setItem(14, cNBrick());
		inv.setItem(15, cEmer());
		inv.setItem(16, cStar());


		return inv;
	}

	private static ItemStack cDia() {
		ItemStack item = new ItemStack(Material.DIAMOND, 1);
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName("Damage Reduction");
		meta.addEnchant(Enchantment.ARROW_DAMAGE, 0, false);
		
		item.setItemMeta(meta);
		item.removeEnchantment(Enchantment.ARROW_DAMAGE);
		
		NBTItem nbtItem = new NBTItem(item);
		nbtItem.setString("UpgradeType", UpgradeType.DamageReduction.toString());
		item = nbtItem.getItem();
		
		return item;
	}

	private static ItemStack cIro() {
		ItemStack item = new ItemStack(Material.IRON_INGOT, 1);
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName("Protection Tier I");
		
		item.setItemMeta(meta); 		
		return item;
	}

	private static ItemStack cGol() {
		ItemStack item = new ItemStack(Material.GOLD_INGOT, 1);
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName("Protection Tier II");

		item.setItemMeta(meta);
		return item;
	}

	private static ItemStack cBrick() {
		ItemStack item = new ItemStack(Material.BRICK, 1);
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName("Ubreakable");
		meta.addEnchant(Enchantment.ARROW_DAMAGE, 0, false);
		
		item.setItemMeta(meta);
		item.removeEnchantment(Enchantment.ARROW_DAMAGE);
		
		NBTItem nbtItem = new NBTItem(item);
		nbtItem.setString("UpgradeType", UpgradeType.Unbreakable.toString());
		item = nbtItem.getItem();

		return item;
	}

	private static ItemStack cNBrick() {
		ItemStack item = new ItemStack(Material.NETHER_BRICK_ITEM, 1);
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName("Useless");
		
		item.setItemMeta(meta);
		return item;
	}

	private static ItemStack cEmer() {
		ItemStack item = new ItemStack(Material.EMERALD, 1);
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName("Fire Absorption");
		meta.addEnchant(Enchantment.ARROW_DAMAGE, 0, false);
		
		item.setItemMeta(meta);
		item.removeEnchantment(Enchantment.ARROW_DAMAGE);
		
		NBTItem nbtItem = new NBTItem(item);
		nbtItem.setString("UpgradeType", UpgradeType.FireAbsorption.toString());
		item = nbtItem.getItem();

		return item;
	}

	private static ItemStack cStar() {
		ItemStack item = new ItemStack(Material.NETHER_STAR, 1);
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName("Useless");
		
		item.setItemMeta(meta);
		return item;
	}

}
