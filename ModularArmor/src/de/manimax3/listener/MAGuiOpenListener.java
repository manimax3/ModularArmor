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
	
	protected static HashMap<Player, ItemStack> playerInInv;
	
	public MAGuiOpenListener() {
		playerInInv = new HashMap<Player, ItemStack>();
	}
	
	@EventHandler
	public void onModularArmorOpenGui(PlayerInteractEvent e) {
		if (!(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK))
			return;
		if (!ModularArmorPart.isModular(e.getItem()))
			return;

		Player p = e.getPlayer();
		
		p.updateInventory();
		
		playerInInv.put(p, e.getItem());
		p.openInventory(BaseInventory.createInventory());
		e.setCancelled(true);
	}
	@EventHandler
	public void onInvenoryClose(InventoryCloseEvent e){
		if(playerInInv.containsKey(e.getPlayer())){
			playerInInv.remove(e.getPlayer());
		}
	}
	@EventHandler
	public void onPlayerDc(PlayerQuitEvent e){
		if(playerInInv.containsKey(e.getPlayer())){
			playerInInv.remove(e.getPlayer());
		}
	}
}
