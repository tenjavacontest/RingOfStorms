package com.RingOfStorms.tenjava.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_6_R3.CraftWorld;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.RingOfStorms.tenjava.EntityPets;
import com.RingOfStorms.tenjava.entities.PetPig;
import com.RingOfStorms.tenjava.utils.CustomMobUtil;

public class EntityPetsCommands implements CommandExecutor {
	
	private final EntityPets plugin;
	public EntityPetsCommands (EntityPets plugin, String... commands) {
		this.plugin = plugin;
		for(String command : commands)
			getPlugin().getCommand(command).setExecutor(this);
	}
	
	public EntityPets getPlugin () { 
		return plugin;
	}
	
	List<EntityType> allow = new ArrayList<EntityType>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("pet")) {
			if(allow.isEmpty())
				populateAllow();
			
			Player p = null;
			if(sender instanceof Player)
				p = (Player) sender;
			if(args.length == 0) {
				if(p != null) {
					Location t = p.getTargetBlock(null, 120).getLocation().add(0.5, 1.1, 0.5);
					PetPig z = new PetPig(((CraftWorld)t.getWorld()).getHandle(), getPlugin(), p.getName());
					CustomMobUtil.spawnCustomMob(t, z);
					z.setNameTag(ChatColor.GREEN+p.getName()+"'s Pet Zombie");
					z.setOwner(p);
				}else{
					sender.sendMessage("Must be a player!");
				}
			}
		}
		return false;
	}
	
	private void populateAllow () {
		allow.add(EntityType.CHICKEN);
		allow.add(EntityType.COW);
		allow.add(EntityType.CREEPER);
		allow.add(EntityType.HORSE);
		allow.add(EntityType.IRON_GOLEM);
		allow.add(EntityType.MUSHROOM_COW);
		allow.add(EntityType.OCELOT);
		allow.add(EntityType.PIG);
		allow.add(EntityType.PIG_ZOMBIE);
		allow.add(EntityType.SHEEP);
		allow.add(EntityType.SKELETON);
		allow.add(EntityType.SNOWMAN);
		allow.add(EntityType.VILLAGER);
		allow.add(EntityType.WITCH);
		allow.add(EntityType.WOLF);
		allow.add(EntityType.ZOMBIE);
	}
}
