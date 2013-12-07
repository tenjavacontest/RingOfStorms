package com.RingOfStorms.tenjava.listeners;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_6_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import com.RingOfStorms.tenjava.EntityPets;
import com.RingOfStorms.tenjava.entities.PetEntity;

public class EntityPetListener implements Listener {
	
	private final EntityPets plugin;
	
	public EntityPetListener (EntityPets plugin) {
		this.plugin = plugin;
	}
	
	public EntityPets getPlugin () {
		return plugin;
	}
	
	@EventHandler
	public void damage (EntityDamageByEntityEvent e) {
		if(e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			if(e.getEntity().hasMetadata(p.getName()+"_petEntity0"))
				e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void sit (PlayerInteractEntityEvent e) {
		Player p = e.getPlayer();
		Entity ce = e.getRightClicked();
		if(((CraftEntity)ce).getHandle() instanceof PetEntity) {
			PetEntity pe = (PetEntity) ((CraftEntity)ce).getHandle();
			pe.setSitting(!pe.isSitting());
			pe.setNameTag((pe.isSitting() ? ChatColor.GOLD : ChatColor.GREEN)+ChatColor.stripColor(pe.getNameTag()));
		}
	}
}
