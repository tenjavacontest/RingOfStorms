package com.RingOfStorms.tenjava.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_6_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_6_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import com.RingOfStorms.tenjava.EntityPets;
import com.RingOfStorms.tenjava.entities.PetEntity;
import com.RingOfStorms.tenjava.entities.PetZombie;
import com.RingOfStorms.tenjava.utils.CustomMobUtil;
import com.RingOfStorms.tenjava.utils.ItemStackUtil;

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
			if(ce.hasMetadata(p.getName()+"_petEntity0")) {
				ItemStack i = p.getItemInHand();
				if(p.isSneaking()) {
					p.getInventory().addItem(ItemStackUtil.setName(new ItemStack(Material.SNOW_BALL), p.getName()+"'s Pet "+entityName(ce.getType())));
					e.setCancelled(true);
					ce.remove();
				}else if(i.getType() == Material.SADDLE) {
					if(p.getVehicle() != null)
						p.getVehicle().eject();
					ce.setPassenger(p);
					e.setCancelled(true);
				}else{
					pe.setSitting(!pe.isSitting());
					pe.setNameTag((pe.isSitting() ? ChatColor.GOLD : ChatColor.GREEN)+ChatColor.stripColor(pe.getNameTag()));
					e.setCancelled(true);
				}
			}
		}
	}
	
	private String entityName (EntityType t) {
		return (t.toString().toUpperCase().charAt(0)+t.toString().toLowerCase().substring(1)).replace("_", " ");
	}
	
	private EntityType reverseName (String name) {
		try {
			return EntityType.valueOf(name.toUpperCase().replace(" ", "_"));
		}catch (Exception e) {
			return null;
		}
	}
	
	@EventHandler
	public void eggHit (ProjectileHitEvent e) {
		Projectile pro = e.getEntity();
		if(pro.getShooter() instanceof Player) {
			Player p = (Player) pro.getShooter();
			if(pro.hasMetadata(p.getName()+"'s_Pet")) {
				EntityType eType = reverseName(pro.getMetadata(p.getName()+"'s_Pet").get(0).asString());
				if(eType != null) {
					Location t = pro.getLocation().add(0, 0.1, 0);
					pro.remove();
					PetZombie z = new PetZombie(((CraftWorld)t.getWorld()).getHandle(), getPlugin(), p.getName());
					CustomMobUtil.spawnCustomMob(t, z);
					z.setNameTag(ChatColor.GREEN+p.getName()+"'s Pet Zombie");
					z.setOwner(p);
				}
			}
		}
	}
	
	@EventHandler
	public void throwEgg (ProjectileLaunchEvent e) {
		LivingEntity le = e.getEntity().getShooter();
		if(le instanceof Player) {
			Player p = (Player) le;
			ItemStack i = p.getItemInHand();
			if(i != null && i.getItemMeta() != null && i.getItemMeta().getDisplayName() != null) {
				String[] args = i.getItemMeta().getDisplayName().split(" ");
				if(args.length == 3) {
					EntityType eType = reverseName(args[2]);
					if(eType != null) {
						e.getEntity().setMetadata(args[0]+"_Pet", new FixedMetadataValue(getPlugin(), eType.toString()));
					}
				}
			}
		}
	}
}
