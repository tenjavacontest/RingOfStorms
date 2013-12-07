package com.RingOfStorms.tenjava.entities;

import net.minecraft.server.v1_6_R3.EntityInsentient;
import net.minecraft.server.v1_6_R3.PathfinderGoalSelector;

import org.bukkit.entity.Player;

import com.RingOfStorms.tenjava.EntityPets;
import com.RingOfStorms.tenjava.pathfinders.PetGoalFollowOwner;

public interface PetEntity {
	
	public EntityPets getPlugin ();
	
	public EntityInsentient getThis ();
	
	public String getOwnerName ();
	
	public void setOwner (Player player);
	
	public void setOwner (String player);
	
	public boolean isSitting ();
	
	public void setSitting (boolean isSitting);
	
	public String getNameTag ();
	
	public void setNameTag (String nameTag);
	
	public void updateFollowGoal ();
	
	public void updateAttribs ();
	
	public PathfinderGoalSelector getGoalSelector ();
	
	public PathfinderGoalSelector getTargetSelector ();
	
	public PetGoalFollowOwner getFollow ();
	
	public void setFollow (PetGoalFollowOwner follow);
}
