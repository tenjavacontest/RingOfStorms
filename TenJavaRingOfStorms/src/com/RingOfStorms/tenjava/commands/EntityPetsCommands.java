package com.RingOfStorms.tenjava.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_6_R3.CraftWorld;
import org.bukkit.entity.Player;

import com.RingOfStorms.tenjava.EntityPets;
import com.RingOfStorms.tenjava.entities.PetZombie;
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
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("pet")) {
			Player p = null;
			if(sender instanceof Player)
				p = (Player) sender;
			if(args.length == 0) {
				if(p != null) {
					Location t = p.getTargetBlock(null, 120).getLocation().add(0.5, 1.1, 0.5);
					PetZombie z = new PetZombie(((CraftWorld)t.getWorld()).getHandle(), getPlugin());
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

}
