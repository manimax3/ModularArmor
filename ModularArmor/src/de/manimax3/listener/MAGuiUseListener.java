package de.manimax3.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import de.manimax3.ErrorCode;
import de.manimax3.ModularArmor;
import de.manimax3.armor.ArmorType;
import de.manimax3.armor.ModularArmorPart;
import de.manimax3.armor.UpgradeType;
import de.manimax3.bases.BaseArmorItem;
import de.manimax3.bases.BaseInventory;
import de.manimax3.util.MessageManager;
import de.manimax3.util.MessageManager.MessageType;
import de.tr7zw.itemnbtapi.ma.Itemnbtapi;
import de.tr7zw.itemnbtapi.ma.NBTItem;

public class MAGuiUseListener implements Listener {

	private MessageManager msgmgr = ModularArmor.msgmgr;

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (e.getClickedInventory() == null)
			return;
		if (!e.getClickedInventory().getName()
				.equals("Modular Armor Interface"))
			return;

		if (!MAGuiOpenListener.playerInMAGui.containsKey((Player) e
				.getWhoClicked())) {
			ErrorCode.Wrong_Player_In_Inventory.out();
			return;
		}
		Player p = (Player) e.getWhoClicked();
		e.setCancelled(true);

		ItemStack item = e.getCurrentItem();

		if (item == null || item.getType() == Material.STAINED_GLASS_PANE)
			return;

		NBTItem nbtItem = new NBTItem(item);

		if (!nbtItem.hasKey("UpgradeType"))
			return;
		UpgradeType type = UpgradeType
				.valueOf(nbtItem.getString("UpgradeType"));

		int id = Itemnbtapi.getNBTItem(MAGuiOpenListener.playerInMAGui.get(p))
				.getInteger("ID"); 
		double price = nbtItem.getDouble("Price");

		if (ModularArmor.plugin.vaultEnabled) {
			if (!((net.milkbowl.vault.economy.Economy) ModularArmor.plugin.getEconomy()).has(p, price)) {
				msgmgr.msgPlayer(p, MessageType.INFO, "NotEnoughMoney");
				return;
			}
		}

		ModularArmorPart armor = null;

		Material mat = MAGuiOpenListener.playerInMAGui.get(p).getType();

		ArmorType arType = ArmorType.getArmorTypeByMat(mat);

		if (arType == null || type == null) {
			return;
		}

		armor = ModularArmorPart.deserialize(id, arType);

		if (armor.getUpgradeLevel(type) >= type.maxLevel) {
			msgmgr.msgPlayer(p, MessageType.INFO, "AlreadyMaxLevel");
			return;
		}
		if (ModularArmor.plugin.vaultEnabled) {
			((net.milkbowl.vault.economy.Economy) ModularArmor.plugin.getEconomy()).withdrawPlayer(p, price);
			msgmgr.msgPlayer(p, MessageType.GOOD, "UpgradeBuySuc");
		}else{
			msgmgr.msgPlayer(p, MessageType.GOOD, "UpgradeAddSuc");
		}
		armor.addUpgrade(type, 1);
		BaseArmorItem
				.updateArmorItem(MAGuiOpenListener.playerInMAGui.get(p), p);
		BaseInventory.update(p);
	}

}
