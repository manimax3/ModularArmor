package de.tr7zw.itemnbtapi.ma;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Itemnbtapi {
	private static Boolean works = true;

	public void onEnable() {

		/*
		 * 
		 * This API is a copy from https://www.spigotmc.org/resources/item-nbt-api.7939/
		 * Give this Guy a Vote for this nice API
		 * 
		 */
		
		System.out.println("Checking if reflections are working!");
		try {
			ItemStack item = new ItemStack(Material.STONE, 1);
			NBTItem nbti = new NBTItem(item);
			nbti.setString("Stringtest", "Teststring");
			nbti.setInteger("Inttest", 42);
			nbti.setDouble("Doubletest", 1.5d);
			nbti.setBoolean("Booleantest", true);
			nbti.getItem();
			if (!nbti.hasKey("Stringtest")) {
				works = false;
				System.out
						.println("Exeption while testing! Assuming reflections are broken! Check for a plugin update!");

				return;
			}
			if (!nbti.getString("Stringtest").equals("Teststring")) {
				works = false;
				System.out
						.println("Exeption while testing! Assuming reflections are broken! Check for a plugin update!");

				return;
			}
			if (nbti.getInteger("Inttest") != 42) {
				works = false;
				System.out
						.println("Exeption while testing! Assuming reflections are broken! Check for a plugin update!");

				return;
			}
			if (nbti.getDouble("Doubletest") != 1.5d) {
				works = false;
				System.out
						.println("Exeption while testing! Assuming reflections are broken! Check for a plugin update!");

				return;
			}
			if (!nbti.getBoolean("Booleantest")) {
				works = false;
				System.out
						.println("Exeption while testing! Assuming reflections are broken! Check for a plugin update!");

				return;
			}
		} catch (Exception ex) {
			works = false;
			System.out
					.println("Exeption while testing! Assuming reflections are broken! Check for a plugin update!");

			return;
		}
		System.out.println("Everything seems to work!");
	}

	public static NBTItem getNBTItem(ItemStack item) {
		if (!ispluginisworking())
			return null;
		return new NBTItem(item);
	}

	public static Boolean ispluginisworking() {
		return works;
	}

	// private void initmcstats(){
	// try {
	// Metrics metrics = new Metrics(this);
	// metrics.start();
	// } catch (IOException e) {
	//
	// }
	// }

}
