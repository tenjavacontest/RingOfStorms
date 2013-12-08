package com.RingOfStorms.tenjava.entities;

import net.minecraft.server.v1_6_R3.EntityInsentient;
import net.minecraft.server.v1_6_R3.PathfinderGoalSelector;

import org.bukkit.entity.Player;

import com.RingOfStorms.tenjava.EntityPets;
import com.RingOfStorms.tenjava.pathfinders.PetGoalFollowOwner;

public interface PetEntity {
	
	/**
	 * Returns the plugin
	 * @return
	 */
	public EntityPets getPlugin ();
	
	/**
	 * Gets the NMS entity
	 * @return
	 */
	public EntityInsentient getThis ();
	
	/**
	 * Gets the owner's name
	 * @return
	 */
	public String getOwnerName ();
	
	/**
	 * Sets the owner's name
	 * @param player
	 */
	public void setOwner (Player player);
	
	/**
	 * Sets the owner's name
	 * @param player
	 */
	public void setOwner (String player);
	
	/**
	 * Returns true if the Pet is sitting
	 * @return
	 */
	public boolean isSitting ();
	
	/**
	 * Set if the pet is sitting
	 * @param isSitting
	 */
	public void setSitting (boolean isSitting);
	
	/**
	 * Gets the current display name above the pet head
	 * @return
	 */
	public String getNameTag ();
	
	/**
	 * Sets the display name above the head
	 * @param nameTag
	 */
	public void setNameTag (String nameTag);
	
	/**
	 * Updates the Following pathfinder goal with owner information
	 */
	public void updateFollowGoal ();
	
	/**
	 * Updates any affects and or names needed, such as the sitting effects
	 */
	public void updateAttribs ();
	
	/**
	 * returns the entity's goal selector
	 * @return
	 */
	public PathfinderGoalSelector getGoalSelector ();
	
	/**
	 * returns the entity's target selector
	 * @return
	 */
	public PathfinderGoalSelector getTargetSelector ();
	
	/**
	 * Gets the Follow goal for this pet
	 * @return
	 */
	public PetGoalFollowOwner getFollow ();
	
	/**
	 * Sets the follow goal for this pet
	 * should only be called once on pet creation
	 * or when a new owner is assigned
	 * @param follow
	 */
	public void setFollow (PetGoalFollowOwner follow);
	
	/**
	 * Input for entity movement when a passanger is on it
	 * @param jump
	 * @param shift
	 * @param forward
	 * @param sideways
	 */
	public void driveInput (boolean jump, boolean shift, double forward, double sideways);
}
