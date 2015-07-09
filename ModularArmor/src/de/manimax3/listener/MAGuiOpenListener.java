package de.manimax3.listener;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import de.manimax3.armor.ModularArmorPart;
import de.manimax3.bases.BaseInventory;

public class MAGuiOpenListener implements Listener {
	
	public static HashMap<Player, ItemStack> playerInMAGui;
	
	public MAGuiOpenListener() {
		playerInMAGui = new HashMap<Player, ItemStack>();
	}
	
	@EventHandler
	public void onModularArmorOpenGui(PlayerInteractEvent e) {
		if (!(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK))
			return;
		if (!ModularArmorPart.isModular(e.getItem()))
			return;

		Player p = e.getPlayer();
		
		p.updateInventory();
		
		playerInMAGui.put(p, e.getItem());
		p.openInventory(BaseInventory.createInventory(p));
		e.setCancelled(true);
	}
	@EventHandler
	public void onInvenoryClose(InventoryCloseEvent e){
		if(playerInMAGui.containsKey(e.getPlayer())){
			playerInMAGui.remove(e.getPlayer());
		}
	}
	@EventHandler
	public void onPlayerDc(PlayerQuitEvent e){
		if(playerInMAGui.containsKey(e.getPlayer())){
			playerInMAGui.remove(e.getPlayer());
		}
	}
}
