package com.RingOfStorms.tenjava.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.RingOfStorms.tenjava.EntityPets;
import com.RingOfStorms.tenjava.entities.PetEntity;
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
	
	private List<EntityType> allow = new ArrayList<EntityType>();
	private String alloString = "";
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("pet")) {
			populateAllow();
			if(sender instanceof Player == false) {
				sender.sendMessage("Must be a player to use pets.");
				return true;
			}
			Player p = (Player) sender;
			
			if(args.length == 0) {
				p.sendMessage(ChatColor.GREEN+"Available Pets: "+ChatColor.DARK_AQUA+alloString);
			}else if(args.length >= 1) {
				EntityType et = null;
				try {
					et = EntityType.valueOf(args[0].toUpperCase());
				}catch(Exception e) {
					p.sendMessage("Invalid entity name, use \"/pet\" for a list that is supported.");
					return true;
				}
				if(et == null) {
					p.sendMessage("Invalid entity name, use \"/pet\" for a list that is supported.");
					return true;
				}
				
				PetEntity petEntity = getPlugin().generatePet(et, p);
				
				if(petEntity != null) {
					Location t = p.getTargetBlock(null, 120).getLocation().add(0.5, 1.1, 0.5);
					CustomMobUtil.spawnCustomMob(t, petEntity.getThis());
					petEntity.setNameTag(ChatColor.GREEN+p.getName()+"'s "+getPlugin().entityName(et));
					petEntity.setOwner(p);
				}
			}
		}
		return false;
	}
	
	private void populateAllow () {
		if(allow.isEmpty()) {
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
		
		if(alloString.isEmpty()) {
			String s = "";
			for(EntityType et : allow)
				s += et.toString().toLowerCase()+" ";
			s = s.substring(0, s.length() - 1);
			alloString = s;
		}
	}
}
