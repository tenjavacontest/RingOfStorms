package com.RingOfStorms.tenjava.entities;

import net.minecraft.server.v1_6_R3.EntityChicken;
import net.minecraft.server.v1_6_R3.EntityInsentient;
import net.minecraft.server.v1_6_R3.PathfinderGoalSelector;
import net.minecraft.server.v1_6_R3.World;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_6_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.RingOfStorms.tenjava.EntityPets;
import com.RingOfStorms.tenjava.pathfinders.PetGoalFollowOwner;
import com.RingOfStorms.tenjava.utils.CustomMobUtil;

public class PetChicken extends EntityChicken implements PetEntity {

	private final EntityPets plugin;

	public PetChicken(World arg0, EntityPets plugin) {
		this(arg0, plugin, null);
	}
	
	public PetChicken(World arg0, EntityPets plugin, String owner) {
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
	
	public void driveInput (boolean jump, boolean shift, double forward, double sideways) {
		if(!shift) {
			f = forward;
			s = sideways;
			j = jump;
		}else{
			f = 0;
			s = 0;
			j = false;
		}
	}
	
	private double f = 0;
	private double s = 0;
	private boolean j = false;
	private boolean cj = true;
	
	@Override
	public void l_ () {
		super.l_();
		if(this.onGround)
			cj = true;
		if(this.passenger != null) {
			Player p = Bukkit.getPlayerExact(ownerName);
			if(p != null) {
				this.yaw = this.passenger.yaw;
				double speed = 0.6;
				Vector v = this.passenger.getBukkitEntity().getLocation().getDirection().setY(0).normalize();
				Vector vs = this.passenger.getBukkitEntity().getLocation().getDirection().setY(0).normalize().crossProduct(new Vector(0, 1, 0));
				if(f < 0)
					v.multiply(-1);
				else if(f == 0)
					v.multiply(0);
				
				if(s > 0)
					v.add(vs.multiply(-1));
				else if(s < 0)
					v.add(vs);
				
				if(j && cj) {
					v.setY(2);
					cj = false;
				}
				v.multiply(speed);
				this.move(v.getX(), v.getY(), v.getZ());
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