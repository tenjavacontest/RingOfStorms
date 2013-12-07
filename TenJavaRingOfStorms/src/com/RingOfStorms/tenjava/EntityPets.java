package com.RingOfStorms.tenjava;

import org.bukkit.plugin.java.JavaPlugin;

import com.RingOfStorms.tenjava.commands.EntityPetsCommands;
import com.RingOfStorms.tenjava.listeners.EntityPetListener;

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
		
		getServer().getPluginManager().registerEvents(new EntityPetListener(this), this);
		
		return;
	}
}
/*
* Plan:
* 
* Pets>
* Level up pets ?
* Pet skills ?
* sit/stay mode, follow mode, ride mode (when available, possible skill?) <
* 
* anything else:
*/