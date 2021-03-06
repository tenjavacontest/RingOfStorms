package com.RingOfStorms.tenjava;

import org.bukkit.craftbukkit.v1_6_R3.CraftWorld;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.RingOfStorms.tenjava.commands.EntityPetsCommands;
import com.RingOfStorms.tenjava.entities.PetChicken;
import com.RingOfStorms.tenjava.entities.PetCow;
import com.RingOfStorms.tenjava.entities.PetCreeper;
import com.RingOfStorms.tenjava.entities.PetEntity;
import com.RingOfStorms.tenjava.entities.PetHorse;
import com.RingOfStorms.tenjava.entities.PetIronGolem;
import com.RingOfStorms.tenjava.entities.PetMushroomCow;
import com.RingOfStorms.tenjava.entities.PetOcelot;
import com.RingOfStorms.tenjava.entities.PetPig;
import com.RingOfStorms.tenjava.entities.PetPigZombie;
import com.RingOfStorms.tenjava.entities.PetSheep;
import com.RingOfStorms.tenjava.entities.PetSkeleton;
import com.RingOfStorms.tenjava.entities.PetSnowman;
import com.RingOfStorms.tenjava.entities.PetVillager;
import com.RingOfStorms.tenjava.entities.PetWitch;
import com.RingOfStorms.tenjava.entities.PetWolf;
import com.RingOfStorms.tenjava.entities.PetZombie;
import com.RingOfStorms.tenjava.listeners.EntityPetListener;
import com.RingOfStorms.tenjava.packets.PlayerDriver;

public class EntityPets extends JavaPlugin {
	
	/**
	 * Used to carry out commands in the plugin
	 */
	private EntityPetsCommands commands;
	public EntityPetsCommands getCommands ()	{return commands;	}
	
	/**
	 * Gets called when the plugin disables, cancels any tasks if there are any
	 */
	@Override
	public void onDisable () {
		getServer().getScheduler().cancelTasks(this);
		return;
	}
	
	/**
	 * Gets called when plugin gets enabled, initializes command executor and tracks players for the PlayerDriver
	 */
	@Override
	public void onEnable () {
		commands = new EntityPetsCommands(this, "pet");
		
		getServer().getPluginManager().registerEvents(new EntityPetListener(this), this);
		
		for(Player p : getServer().getOnlinePlayers())
			PlayerDriver.track(p);
		
		return;
	}
	
	/**
	 * Converts EntityType to a readable string
	 * @param t
	 * @return
	 */
	public String entityName (EntityType t) {
		return (t.toString().toUpperCase().charAt(0)+t.toString().toLowerCase().substring(1)).replace("_", " ");
	}
	
	/**
	 * Reverses the entityName (EntityType t) function back to an EntityType
	 * @param name
	 * @return
	 */
	public EntityType reverseName (String name) {
		try {
			return EntityType.valueOf(name.toUpperCase().replace(" ", "_"));
		}catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Creates a PetEntity based on a player and EntityType input
	 * @param et
	 * @param p
	 * @return
	 */
	public PetEntity generatePet (EntityType et, Player p) {
		PetEntity petEntity = null;
		
		switch(et) {
		case CHICKEN:
			petEntity = new PetChicken(((CraftWorld)p.getWorld()).getHandle(), this, p.getName());
			break;
		case COW:
			petEntity = new PetCow(((CraftWorld)p.getWorld()).getHandle(), this, p.getName());
			break;
		case CREEPER:
			petEntity = new PetCreeper(((CraftWorld)p.getWorld()).getHandle(), this, p.getName());
			break;
		case HORSE:
			petEntity = new PetHorse(((CraftWorld)p.getWorld()).getHandle(), this, p.getName());
			break;
		case IRON_GOLEM:
			petEntity = new PetIronGolem(((CraftWorld)p.getWorld()).getHandle(), this, p.getName());
			break;
		case MUSHROOM_COW:
			petEntity = new PetMushroomCow(((CraftWorld)p.getWorld()).getHandle(), this, p.getName());
			break;
		case OCELOT:
			petEntity = new PetOcelot(((CraftWorld)p.getWorld()).getHandle(), this, p.getName());
			break;
		case PIG:
			petEntity = new PetPig(((CraftWorld)p.getWorld()).getHandle(), this, p.getName());
			break;
		case PIG_ZOMBIE:
			petEntity = new PetPigZombie(((CraftWorld)p.getWorld()).getHandle(), this, p.getName());
			break;
		case SHEEP:
			petEntity = new PetSheep(((CraftWorld)p.getWorld()).getHandle(), this, p.getName());
			break;
		case SKELETON:
			petEntity = new PetSkeleton(((CraftWorld)p.getWorld()).getHandle(), this, p.getName());
			break;
		case SNOWMAN:
			petEntity = new PetSnowman(((CraftWorld)p.getWorld()).getHandle(), this, p.getName());
			break;
		case VILLAGER:
			petEntity = new PetVillager(((CraftWorld)p.getWorld()).getHandle(), this, p.getName());
			break;
		case WITCH:
			petEntity = new PetWitch(((CraftWorld)p.getWorld()).getHandle(), this, p.getName());
			break;
		case WOLF:
			petEntity = new PetWolf(((CraftWorld)p.getWorld()).getHandle(), this, p.getName());
			break;
		case ZOMBIE:
			petEntity = new PetZombie(((CraftWorld)p.getWorld()).getHandle(), this, p.getName());
			break;
		default:
			break;
		}
		return petEntity;
	}
}