package com.RingOfStorms.tenjava.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.server.v1_6_R3.EntityCreature;
import net.minecraft.server.v1_6_R3.EntityHuman;
import net.minecraft.server.v1_6_R3.EntityInsentient;
import net.minecraft.server.v1_6_R3.EntityLiving;
import net.minecraft.server.v1_6_R3.EntityTypes;
import net.minecraft.server.v1_6_R3.PathfinderGoal;
import net.minecraft.server.v1_6_R3.PathfinderGoalFloat;
import net.minecraft.server.v1_6_R3.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_6_R3.PathfinderGoalRandomLookaround;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_6_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_6_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import com.RingOfStorms.tenjava.entities.PetEntity;
import com.RingOfStorms.tenjava.pathfinders.PetGoalFollowOwner;
import com.RingOfStorms.tenjava.pathfinders.PetMeleeAttack;
import com.RingOfStorms.tenjava.pathfinders.PetOwnerHurtByTarget;
import com.RingOfStorms.tenjava.pathfinders.PetOwnerHurtTarget;

public class CustomMobUtil {
	
	public static void defaultPet (PetEntity entity) {
		removeAllGoals(entity);
		
		entity.setFollow(new PetGoalFollowOwner(entity.getThis(), 1.5D, 10.0F, 2.0F, null));
		addPathGoal(entity, 0, new PathfinderGoalFloat(entity.getThis()));
		addPathGoal(entity, 1, new PetMeleeAttack(entity.getThis(), 1.8D, false));
		addPathGoal(entity, 2, entity.getFollow());
		addPathGoal(entity, 3, new PathfinderGoalLookAtPlayer(entity.getThis(), EntityHuman.class, 10.0F));
		addPathGoal(entity, 4, new PathfinderGoalRandomLookaround(entity.getThis()));
		
		Player p = Bukkit.getPlayerExact(entity.getOwnerName());
		if(p != null && entity.getThis() instanceof EntityCreature) {
			EntityLiving elp = ((CraftPlayer)p).getHandle();
			addTargetGoal(entity, 0, new PetOwnerHurtTarget((EntityCreature) entity.getThis(), elp));
			addTargetGoal(entity, 1, new PetOwnerHurtByTarget((EntityCreature) entity.getThis(), elp));
		}
		
		CraftEntity ce = entity.getThis().getBukkitEntity();
		ce.setMetadata("petEntity0", new FixedMetadataValue(entity.getPlugin(), entity.getOwnerName()));
	}
	
	public static void addPathGoal (PetEntity entity, int priority, PathfinderGoal goal) {
		entity.getGoalSelector().a(priority, goal);
	}
	
	public static void addTargetGoal (PetEntity entity, int priority, PathfinderGoal goal) {
		entity.getTargetSelector().a(priority, goal);
	}
	
	public static void removeAllGoals (PetEntity entity) {
		try {
			Field lists = entity.getGoalSelector().getClass().getDeclaredField("a");
			lists.setAccessible(true);
			List<?> list = (List<?>) lists.get(entity.getGoalSelector());
			list.clear();
			
			lists = entity.getGoalSelector().getClass().getDeclaredField("b");
			lists.setAccessible(true);
			list = (List<?>) lists.get(entity.getGoalSelector());
			list.clear();
			
			lists = entity.getTargetSelector().getClass().getDeclaredField("a");
			lists.setAccessible(true);
			list = (List<?>) lists.get(entity.getTargetSelector());
			list.clear();
			
			lists = entity.getTargetSelector().getClass().getDeclaredField("b");
			lists.setAccessible(true);
			list = (List<?>) lists.get(entity.getTargetSelector());
			list.clear();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void spawnCustomMob (Location loc, EntityInsentient mob) {
		try {
			mob.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getPitch(), loc.getYaw());
			
			Class<?> superClass = mob.getClass().getSuperclass();
			
			Field cField = EntityTypes.class.getDeclaredField("c");
			cField.setAccessible(true);
			Map c = (Map) cField.get(null);
			String name = null;
			for(Object en : c.entrySet()) {
				Entry<Class<?>, String> ent = (Entry<Class<?>, String>) en;
				if(ent.getKey().equals(superClass)) {
					name = ent.getValue();
					break;
				}
			}
			
			Field eField = EntityTypes.class.getDeclaredField("e");
			eField.setAccessible(true);
			Map e = (Map) eField.get(null);
			int id = -1;
			for(Object en : e.entrySet()) {
				Entry<Class<?>, Integer> ent = (Entry<Class<?>, Integer>) en;
				if(ent.getKey().equals(superClass)) {
					id = ent.getValue();
					break;
				}
			}
			
			Method a = EntityTypes.class.getDeclaredMethod("a", new Class[] {Class.class, String.class, int.class});
			a.setAccessible(true);
			a.invoke(null, mob.getClass(), name, id);
			mob.world.addEntity(mob);
			a.invoke(null, superClass, name, id);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
