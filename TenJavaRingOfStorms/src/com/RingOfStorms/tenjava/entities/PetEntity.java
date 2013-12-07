package com.RingOfStorms.tenjava.entities;

import net.minecraft.server.v1_6_R3.EntityInsentient;
import net.minecraft.server.v1_6_R3.PathfinderGoalSelector;

import org.bukkit.entity.Player;

import com.RingOfStorms.tenjava.EntityPets;
import com.RingOfStorms.tenjava.pathfinders.PetGoalFollowOwner;

public interface PetEntity {

//	private final EntityPets plugin;
//	private final PetGoalFollowOwner follow;
	
//	public PetEntity(World arg0, EntityPets plugin) {
//		super(arg0);
//		
//		this.plugin = plugin;
//		
//		follow = new PetGoalFollowOwner(this, 1.0D, 10.0F, 2.0F, null);
//		
//		try {
//			Field lists = goalSelector.getClass().getDeclaredField("a");
//			lists.setAccessible(true);
//			List<?> list = (List<?>) lists.get(goalSelector);
//			list.clear();
//			
//			lists = goalSelector.getClass().getDeclaredField("b");
//			lists.setAccessible(true);
//			list = (List<?>) lists.get(goalSelector);
//			list.clear();
//			
//			lists = targetSelector.getClass().getDeclaredField("a");
//			lists.setAccessible(true);
//			list = (List<?>) lists.get(targetSelector);
//			list.clear();
//			
//			lists = targetSelector.getClass().getDeclaredField("b");
//			lists.setAccessible(true);
//			list = (List<?>) lists.get(targetSelector);
//			list.clear();
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		goalSelector.a(0, new PathfinderGoalFloat(this));
//		goalSelector.a(1, follow);
//		goalSelector.a(2, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 10.0F));
//		goalSelector.a(3, new PathfinderGoalRandomLookaround(this));
//	}		
	
	public EntityPets getPlugin ();
	
	public EntityInsentient getThis ();
	
//	private String ownerName;
//	private boolean isSitting;
//	private String nameTag;
	
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
