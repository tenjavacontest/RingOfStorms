package com.RingOfStorms.tenjava.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.RingOfStorms.tenjava.EntityPets;

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
}
