package com.RingOfStorms.tenjava.entities;

import net.minecraft.server.v1_6_R3.EntityInsentient;
import net.minecraft.server.v1_6_R3.EntityVillager;
import net.minecraft.server.v1_6_R3.PathfinderGoalSelector;
import net.minecraft.server.v1_6_R3.World;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_6_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.RingOfStorms.tenjava.EntityPets;
import com.RingOfStorms.tenjava.pathfinders.PetGoalFollowOwner;
import com.RingOfStorms.tenjava.utils.CustomMobUtil;

public class PetVillager extends EntityVillager implements PetEntity {

	private final EntityPets plugin;

	public PetVillager(World arg0, EntityPets plugin) {
		this(arg0, plugin, null);
	}
	
	public PetVillager(World arg0, EntityPets plugin, String owner) {
		super(arg0);
		this.plugin = plugin;
		if(owner != null)
			setOwner(owner);
		CustomMobUtil.defaultPet(this);
	}	
	
	public EntityPets getPlugin () {
		return plugin;
	}
	
	public EntityInsentient getThis() {
		return this;
	}
	
	@Override
	public void l_ () {
		super.l_();
		Player p = Bukkit.getPlayerExact(ownerName);
		if(p != null) {
			if(p.getVehicle() != null && p.getVehicle().getEntityId() == id) {
				if(p.isBlocking()) {
					Vector dir = p.getEyeLocation().getDirection();
					dir.normalize();
					dir.setY(0);
					dir.multiply(0.8D);
					motX = dir.getX();
					motZ = dir.getZ();
				}
			}
		}
	}
	
	private PetGoalFollowOwner follow;
	private String ownerName;
	private boolean isSitting;
	private String nameTag;
	
	public PetGoalFollowOwner getFollow () {
		return follow;
	}
	
	public void setFollow (PetGoalFollowOwner follow) {
		this.follow = follow;
	}
	
	public String getOwnerName () {
		return ownerName;
	}
	
	public void setOwner (Player player) {
		setOwner(player.getName());
	}
	
	public void setOwner (String player) {
		this.ownerName = player;
		updateFollowGoal();
	}
	
	public boolean isSitting () {
		return isSitting;
	}
	
	public void setSitting (boolean isSitting) {
		this.isSitting = isSitting;
		setSneaking(isSitting());
		updateFollowGoal();
	}
	
	public String getNameTag () {
		return nameTag;
	}
	
	public void setNameTag (String nameTag) {
		this.nameTag = nameTag;
		updateAttribs();
	}
	
	public void updateFollowGoal () {
		Player p = Bukkit.getPlayerExact(ownerName);
		if(follow != null) {
			if(p != null)
				follow.setOwner(((CraftPlayer)p).getHandle());
			follow.setSitting(isSitting());
		}
	}
	
	public void updateAttribs () {
		setCustomName(getNameTag());
		setCustomNameVisible(true);
	}
	
	public PathfinderGoalSelector getGoalSelector () {
		return goalSelector;
	}
	
	public PathfinderGoalSelector getTargetSelector () {
		return targetSelector;
	}
}