package de.manimax3.listener;

import java.util.ArrayList;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import de.manimax3.ModularArmor;
import de.manimax3.armor.ArmorType;
import de.manimax3.armor.ModularArmorPart;
import de.manimax3.bases.BaseArmorItem;
import de.manimax3.util.MessageManager;
import de.manimax3.util.MessageManager.MessageType;

public class MACreationListener implements Listener {

	// private static Random rand = new Random();
	private FileConfiguration config;
	private static MessageManager msgmgr = ModularArmor.msgmgr;

	private ModularArmor plugin;
	private ArrayList<Item> items;
	private ArrayList<Player> creatingPlayers;

	public MACreationListener(ModularArmor plugin) {
		this.plugin = plugin;
		config = ModularArmor.cfgmgr.getConfig();
		items = new ArrayList<Item>();
		creatingPlayers = new ArrayList<Player>();
	}

	@EventHandler
	public void onPlayerDamageEntity(EntityDamageByEntityEvent event) {
		if (!(event.getDamager() instanceof Player))
			return;
	}

	@EventHandler
	public void onModularArmorCreate(PlayerInteractEvent e) {

		if (!(e.getAction() == Action.RIGHT_CLICK_BLOCK))
			return;
		if (!(e.getClickedBlock().getType() == Material.DIAMOND_BLOCK))
			return;

		final Player p = e.getPlayer();
		final Location loc = e.getClickedBlock().getLocation();

		if (!(p.hasPermission("modulararmor.create"))) {
			msgmgr.msgPlayer(p, MessageType.BAD, "PermissionMissing");
			return;
		}

		for (Entity ent : p.getWorld().getEntities()) {
			if (!(ent instanceof Item))
				continue;
			if (!(ent.getLocation().getY() == loc.getY() + 1))
				continue;

			final Item item = (Item) ent;
			ItemStack itemStack = item.getItemStack().clone();
			final Material mat = itemStack.getType();

			if (!(ArmorType.isArmorType(mat)))
				continue;

			if ((itemStack.getDurability() > 0)
					&& !ModularArmorPart.isModular(itemStack)) {
				msgmgr.msgPlayer(p, MessageType.INFO,
						"ModularArmorCreationNoDamage");
				continue;
			}

			if (creatingPlayers.contains(p)) {
				msgmgr.msgPlayer(p, MessageType.INFO,
						"ModularArmorCreationAlreadyCreating");
				continue;
			}

			if (ModularArmorPart.isModular(itemStack)) {
				msgmgr.msgPlayer(p, MessageType.INFO,
						"ModularArmorCreationAlreadyModular");
				continue;
			}

			msgmgr.msgPlayer(p, MessageType.INFO, "ModularArmorCreationStart");
			creatingPlayers.add(p);
			items.add(item);

			BukkitRunnable spawn = new BukkitRunnable() {
				@Override
				public void run() {
					items.remove(item);
					creatingPlayers.remove(p);
					if (loc.distance(item.getLocation()) > 1.5D) {
						msgmgr.msgPlayer(p, MessageType.INFO,
								"ModularArmorCreationCancel");
						return;
					}
					item.getLocation().getWorld()
							.playEffect(item.getLocation(), Effect.CRIT, null);
					if (config.getInt("DiamondBlockLossChance") > Math.random() * 100 + 1) {
						loc.getBlock().setType(Material.AIR);
						loc.getWorld().createExplosion(loc.getX(), loc.getY(),
								loc.getZ(), 5F, false, false);
						msgmgr.msgPlayer(p, MessageType.BAD,
								"ModularArmorCreationFail");
					} else {
						item.remove();
						p.getWorld().dropItemNaturally(
								loc.clone().add(0, 1, 0),
								BaseArmorItem.generateArmor(mat));
						msgmgr.msgPlayer(p, MessageType.GOOD,
								"ModularArmorCreationWin");
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1f, 1f);
					}
				}
			};

			spawn.runTaskLater(plugin, config.getInt("CreationWaitTime") * 20);

		}

	}

	@EventHandler
	public void onPlayerPickUp(PlayerPickupItemEvent e) {
		if (items.contains(e.getItem()))
			e.setCancelled(true);
		return;
	}

}
