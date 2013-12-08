package com.RingOfStorms.tenjava.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_6_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityCombustByBlockEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import com.RingOfStorms.tenjava.EntityPets;
import com.RingOfStorms.tenjava.entities.PetEntity;
import com.RingOfStorms.tenjava.packets.PlayerDriver;
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
	
	/**
	 * Cance's friendly damage
	 * @param e
	 */
	@EventHandler
	public void damage (EntityDamageByEntityEvent e) {
		if(e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			if(e.getEntity().hasMetadata("petEntity0"))
				if(e.getEntity().getMetadata("petEntity0").get(0).asString().equals(p.getName())) {
					e.setCancelled(true);
					return;
				}
		}
		if(e.getEntity() instanceof LivingEntity && e.getDamager() instanceof LivingEntity) {
			LivingEntity le0 = (LivingEntity) e.getEntity();
			LivingEntity le1 = (LivingEntity) e.getDamager();
			if(le0.getCustomName() != null && le1.getCustomName() != null) {
				if(le0.getCustomName().equals(le1.getCustomName())) {
					e.setCancelled(true);
					return;
				}else if(le0.getCustomName().split(" ")[0].equals(le1.getCustomName().split(" ")[0])) {
					e.setCancelled(true);
					return;
				}
			}
		}
		if(e.getDamager() instanceof Projectile && e.getEntity() instanceof LivingEntity && ((Projectile)e.getDamager()).getShooter() instanceof LivingEntity) {
			LivingEntity le0 = (LivingEntity) e.getEntity();
			LivingEntity le1 = (LivingEntity) ((Projectile)e.getDamager()).getShooter();
			if(le0.getCustomName().equals(le1.getCustomName())) {
				e.setCancelled(true);
				return;
			}
		}
	}
	
	/**
	 * Handles right clicks with pets for sitting, riding, and returning
	 * @param e
	 */
	@EventHandler
	public void sit (PlayerInteractEntityEvent e) {
		Player p = e.getPlayer();
		Entity ce = e.getRightClicked();
		if(((CraftEntity)ce).getHandle() instanceof PetEntity) {
			e.setCancelled(true);
			PetEntity pe = (PetEntity) ((CraftEntity)ce).getHandle();
			if(ce.hasMetadata("petEntity0")) {
				if(ce.getMetadata("petEntity0").get(0).asString().equals(p.getName())) {
					ItemStack i = p.getItemInHand();
					if(p.isSneaking()) {
						p.getInventory().addItem(ItemStackUtil.setName(new ItemStack(Material.SNOW_BALL), p.getName()+"'s Pet "+getPlugin().entityName(ce.getType())));
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
	}
	
	/**
	 * Spawns a pet when pet ball lands
	 * @param e
	 */
	@EventHandler
	public void eggHit (ProjectileHitEvent e) {
		Projectile pro = e.getEntity();
		if(pro.getShooter() instanceof Player) {
			Player p = (Player) pro.getShooter();
			if(pro.hasMetadata(p.getName()+"'s_Pet")) {
				EntityType eType = getPlugin().reverseName(pro.getMetadata(p.getName()+"'s_Pet").get(0).asString());
				if(eType != null) {
					Location t = pro.getLocation().add(0, 0.1, 0);
					pro.remove();
					PetEntity petEntity = getPlugin().generatePet(eType, p);
					CustomMobUtil.spawnCustomMob(t, petEntity.getThis());
					petEntity.setNameTag(ChatColor.GREEN+p.getName()+"'s Pet Zombie");
					petEntity.setOwner(p);
				}
			}
		}
	}
	
	/**
	 * Tags launched pet ball for when it lands
	 * @param e
	 */
	@EventHandler
	public void throwEgg (ProjectileLaunchEvent e) {
		LivingEntity le = e.getEntity().getShooter();
		if(le instanceof Player) {
			Player p = (Player) le;
			ItemStack i = p.getItemInHand();
			if(i != null && i.getItemMeta() != null && i.getItemMeta().getDisplayName() != null) {
				String[] args = i.getItemMeta().getDisplayName().split(" ");
				if(args.length >= 3) {
					EntityType eType = getPlugin().reverseName(comb(2, args));
					if(eType != null) {
						e.getEntity().setMetadata(args[0]+"_Pet", new FixedMetadataValue(getPlugin(), eType.toString()));
					}
				}
			}
		}
	}
	
	/**
	 * Combines a part of a String array from an offset
	 * @param o
	 * @param args
	 * @return
	 */
	private String comb (int o, String[] args) {
		String ret = "";
		for(int i=o; i<args.length; i++)
			ret += args[i] + " ";
		return ret.substring(0, ret.length()-1);
	}
	
	/**
	 * Cancel some problematic events
	 * @param e
	 */
	@EventHandler
	public void cancel (EntityExplodeEvent e) {
		if(e.getEntity().hasMetadata("petEntity0")) {
			e.setCancelled(true);
		}
	}
	
	/**
	 * Cancel some problematic events
	 * @param e
	 */
	@EventHandler
	public void cancel (EntityChangeBlockEvent e) {
		if(e.getEntity().hasMetadata("petEntity0")) {
			e.setCancelled(true);
		}
	}
	
	/**
	 * Cancel some problematic events
	 * @param e
	 */
	@EventHandler
	public void cancel (EntityCombustByBlockEvent e) {
		if(e.getEntity().hasMetadata("petEntity0")) {
			e.setCancelled(true);
		}
	}
	
	/**
	 * Cancel some problematic events
	 * @param e
	 */
	@EventHandler
	public void cancel (EntityCombustEvent e) {
		if(e.getEntity().hasMetadata("petEntity0")) {
			e.setCancelled(true);
		}
	}
	
	/**
	 * Registers player to PlayerDriver when they join
	 * @param e
	 */
	@EventHandler
	public void register (PlayerJoinEvent e) {
		PlayerDriver.track(e.getPlayer());
	}
}
