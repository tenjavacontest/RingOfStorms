package com.RingOfStorms.tenjava;

import org.bukkit.plugin.java.JavaPlugin;

import com.RingOfStorms.tenjava.commands.EntityPetsCommands;

public class EntityPets extends JavaPlugin {
	
	private EntityPetsCommands commands;
	
	public EntityPetsCommands getCommands ()	{return commands;	}
	
	@Override
	public void onDisable () {
		getServer().getScheduler().cancelTasks(this);
		return;
	}
	
	@Override
	public void onEnable () {
		commands = new EntityPetsCommands(this, "pet");
		
		return;
	}
}
/*
* Plan:
* 
* Pets>
* All entities can follow you around <
* Help player attack enemies <
* Level up pets ?
* Pet skills ?
* sit/stay mode, follow mode, ride mode (when available, possible skill?) <
* 
* anything else:
*/