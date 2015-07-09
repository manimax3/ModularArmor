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
import de.manimax3.util.MessageManager;
import de.manimax3.util.MessageManager.MessageType;
import de.tr7zw.itemnbtapi.Itemnbtapi;
import de.tr7zw.itemnbtapi.NBTItem;

public class MAGuiUseListener implements Listener {
	
	private MessageManager msgmgr = ModularArmor.msgmgr;

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (e.getClickedInventory() == null)
			return;
		if (!e.getClickedInventory().getName()
				.equals("Modular Armor Interface"))
			return;

		if (!MAGuiOpenListener.playerInInv.containsKey((Player) e
				.getWhoClicked())) {
			ErrorCode.WRONG_Player_In_Inventory.out();
			return;
		}

		e.setCancelled(true);

		ItemStack item = e.getCurrentItem();

		if (item == null || item.getType() == Material.STAINED_GLASS_PANE)
			return;

		NBTItem nbtItem = new NBTItem(item);

		if (!nbtItem.hasKey("UpgradeType"))
			return;
		UpgradeType type = UpgradeType
				.valueOf(nbtItem.getString("UpgradeType"));

		int id = Itemnbtapi.getNBTItem(
				MAGuiOpenListener.playerInInv.get((Player) e.getWhoClicked()))
				.getInteger("ID");

		ModularArmorPart armor = null;
		Material mat = MAGuiOpenListener.playerInInv.get(
				(Player) e.getWhoClicked()).getType();
		ArmorType arType = ArmorType.getArmorTypeByMat(mat);

		if (arType == null || type == null) {
			return;
		}

		armor = ModularArmorPart.deserialize(id, arType);
		
		if(armor.getUpgradeLevel(type) >= type.maxLevel){
			msgmgr.msgPlayer((Player) e.getWhoClicked(), MessageType.INFO, "AlreadyMaxLevel");
			return;
		}
		
		armor.addUpgrade(type, 1);

		BaseArmorItem.updateArmorItem(
				MAGuiOpenListener.playerInInv.get((Player) e.getWhoClicked()),
				(Player) e.getWhoClicked());
	}

}
