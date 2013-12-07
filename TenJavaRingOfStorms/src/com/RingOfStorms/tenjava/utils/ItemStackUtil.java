package com.RingOfStorms.tenjava.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemStackUtil {

	public static ItemStack setName (ItemStack itemStack, String name) {
		if(itemStack == null || name.isEmpty())
			return null;
		ItemStack ret = itemStack;
		ItemMeta m = ret.getItemMeta();
		m.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		ret.setItemMeta(m);
		return ret;
	}
	
	public static boolean itemStacksMatch (ItemStack item, ItemStack item2) {
		if(item == null && item2 == null)
			return true;
		if(item == null || item2 == null)
			return false;
		ItemMeta im = item.getItemMeta();
		ItemMeta im2 = item2.getItemMeta();
		if(im != null && im2 != null) {
			if(im.hasDisplayName() && im.getDisplayName() != null
					&& im2.hasDisplayName() && im2.getDisplayName() != null) {
				if(im.getDisplayName().equals(im2.getDisplayName())) {
					return true;
				}
			}else{
				if(item.getType() == item2.getType()) {
					return true;
				}
			}
		}else{
			if(item.getType() == item2.getType()) {
				return true;
			}
		}
		return false;
	}
	
	public static void removeItems (Player player, ItemStack itemToRemove) {
		int amount = itemToRemove.getAmount();
		ItemStack[] items = player.getInventory().getContents();
		for(int i=0; i<items.length; i++) {
			ItemStack item = items[i];
			if(item != null && item.getType() != Material.AIR) {
				if(itemStacksMatch(itemToRemove, item)) {
					int n = item.getAmount() - amount;
					if(n > 0) {
						item.setAmount(n);
						break;
					}else{
						items[i] = new ItemStack(Material.AIR);
						amount = -1*n;
						if(amount == 0) break;
					}
				}
			}
		}
		player.getInventory().setContents(items);
		player.updateInventory();
	}
	
	public static void removeItems (Player player, Material material, int amount) {
		ItemStack[] items = player.getInventory().getContents();
		for(int i=0; i<items.length; i++) {
			ItemStack item = items[i];
			if(item != null && item.getType() != Material.AIR) {
				if(item.getType() == material) {
					int n = item.getAmount() - amount;
					if(n > 0) {
						item.setAmount(n);
						break;
					}else{
						items[i] = new ItemStack(Material.AIR);
						amount = -1*n;
						if(amount == 0) break;
					}
				}
			}
		}
		player.getInventory().setContents(items);
		player.updateInventory();
	}
	
	public static void removeItems (Player player, int id, int amount) {
		ItemStack[] items = player.getInventory().getContents();
		for(int i=0; i<items.length; i++) {
			ItemStack item = items[i];
			if(item != null && item.getType() != Material.AIR) {
				if(item.getTypeId() == id) {
					int n = item.getAmount() - amount;
					if(n > 0) {
						item.setAmount(n);
						break;
					}else{
						items[i] = new ItemStack(Material.AIR);
						amount = -1*n;
						if(amount == 0) break;
					}
				}
			}
		}
		player.getInventory().setContents(items);
		player.updateInventory();
	}
}
