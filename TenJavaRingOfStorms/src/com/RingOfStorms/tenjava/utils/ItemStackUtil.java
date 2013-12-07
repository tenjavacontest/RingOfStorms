package com.RingOfStorms.tenjava.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.server.v1_6_R3.Packet103SetSlot;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_6_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_6_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

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
	
	public static ItemStack setLore (ItemStack itemStack, String... lore) {
		if(itemStack == null || lore.length == 0)
			return null;
		ItemStack ret = itemStack;
		ItemMeta m = ret.getItemMeta();
		List<String> l = new ArrayList<String>();
		for(String s : lore)
			l.add(ChatColor.translateAlternateColorCodes('&', s));
		m.setLore(l);
		ret.setItemMeta(m);
		return ret;
	}
	
	public static ItemStack setLore (ItemStack itemStack, List<String> lore) {
		if(itemStack == null || lore.size() == 0)
			return null;
		ItemStack ret = itemStack;
		ItemMeta m = ret.getItemMeta();
		m.setLore(lore);
		ret.setItemMeta(m);
		return ret;
	}
	
	public static ItemStack setDura (ItemStack itemStack, short dura) {
		if(itemStack == null)
			return null;
		ItemStack ret = itemStack;
		ret.setDurability(dura);
		return ret;
	}
	
	public static ItemStack setNameLore (ItemStack itemStack, String name, String... lore) {
		return setLore(setName(itemStack, name), lore);
	}
	
	public static ItemStack setNameDataLore (ItemStack itemStack, String name, short dura, String... lore) {
		return setDura(setLore(setName(itemStack, name), lore), dura);
	}
	
	public static ItemStack setEnchantment (ItemStack itemStack, Enchantment ench, int lvl) {
		ItemStack ret = itemStack;
		ret.addUnsafeEnchantment(ench, lvl);
		return itemStack; 
	}
	
	public static ItemStack setLeatherColor (ItemStack itemStack, Color color) {
		ItemStack ret = itemStack == null ? null : itemStack.clone();
		
		if(ret != null) {
			ItemMeta im = ret.getItemMeta();
			if(im instanceof LeatherArmorMeta) {
				((LeatherArmorMeta) im).setColor(color);
			}
			
			ret.setItemMeta(im);
		}
		
		return ret;
	}
	
	public static ItemStack getCleanVersion (ItemStack item) {
		ItemStack itemR = new ItemStack(item.getType());
			itemR.setAmount(item.getAmount());
			itemR.setDurability(item.getDurability());
			itemR.setData(item.getData());
		return itemR;
	}
	
	public static ItemStack[] cloneItemStackArray (ItemStack[] itemstacks) {
		ItemStack[] ret = new ItemStack[itemstacks.length];
		for(int c=0; c<itemstacks.length; c++)
			if(itemstacks[c] != null)
				ret[c] = itemstacks[c].clone();
		return ret;
	}
	
	public static ItemStack[] itemListToArray (List<ItemStack> itemstacks) {
		ItemStack[] ret = new ItemStack[itemstacks.size()];
		for(int c=0; c<itemstacks.size(); c++)
			if(itemstacks.get(c) != null)
				ret[c] = itemstacks.get(c).clone();
		return ret;
	}
	
	public static ItemStack[] extendItemArray (ItemStack[] itemstacks, int extend) {
		ItemStack[] ret = new ItemStack[itemstacks.length + extend];
		for(int c=0; c<(itemstacks.length+extend); c++)
			if(c+1 > itemstacks.length)
				ret[c] = null;
			else if(itemstacks[c] == null || itemstacks[c].getType() == Material.AIR)
				ret[c] = null;
			else
				ret[c] = itemstacks[c].clone();
		return ret;
	}
	
	public static ItemStack[] addItemArrays (ItemStack[]... is) {
		List<ItemStack> ou = new ArrayList<ItemStack>();
		for(ItemStack[] i : is)
			for(ItemStack item : i)
				ou.add(item);
		return itemListToArray(ou);
	}
	
	public static void sendSlotPacket (Player player, ItemStack item, int slot, int window) {
		/*
		 * -1 unkown | 0 player | 1 bench | 2 furnace | 3 chest | 4 doublechest | 5 dispenser
		 */
		if(player == null || item == null || window > 5 || window < 0)
			return;
		((CraftPlayer)player).getHandle().playerConnection.sendPacket(new Packet103SetSlot(slot, window, CraftItemStack.asNMSCopy(item)));
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
	
	public static void writeItemStack (FileConfiguration fc, String path, ItemStack item) {
		if(item == null)
			fc.set(path, "null");
		else{
			Map<String, Object> s = item.serialize();
			for(String k : s.keySet())
				fc.set(path+"."+k, s.get(k));
		}
	}
	
	public static ItemStack readItemStack (FileConfiguration fc, String path) {
		if(!fc.isConfigurationSection(path))
			return new ItemStack(Material.AIR);
		ConfigurationSection cs = fc.getConfigurationSection(path);
		Map<String, Object> s = new HashMap<String, Object>();
		for(String k : cs.getKeys(false))
			s.put(k, cs.get(k));
		ItemStack ret = ItemStack.deserialize(s);
		if(ret == null)
			ret = new ItemStack(Material.AIR);
		return ret;
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
