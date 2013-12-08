package com.RingOfStorms.tenjava.packets;

import net.minecraft.server.v1_6_R3.EntityPlayer;
import net.minecraft.server.v1_6_R3.Packet27PlayerInput;
import net.minecraft.server.v1_6_R3.PlayerConnection;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_6_R3.CraftServer;
import org.bukkit.craftbukkit.v1_6_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_6_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.RingOfStorms.tenjava.entities.PetEntity;

public class PlayerDriver extends PlayerConnection {
	
	public PlayerDriver(EntityPlayer entityplayer) {
		super(((CraftServer)Bukkit.getServer()).getHandle().getServer(), entityplayer.playerConnection.networkManager, entityplayer);
	}
	
	/**
	 * Replace PlayerConnection with PlayerDriver
	 * @param player
	 */
	public static void track (Player player) {
		EntityPlayer ep = ((CraftPlayer)player).getHandle();
		if(!(ep.playerConnection instanceof PlayerDriver)) {
			ep.playerConnection = new PlayerDriver(ep);
		}
	}
	
	/**
	 * Replaces PlayerDriver with PlayerConnection
	 * @param player
	 */
	public static void untrack (Player player) {
		EntityPlayer ep = ((CraftPlayer)player).getHandle();
		if(ep.playerConnection instanceof PlayerDriver) {
			ep.playerConnection = new PlayerConnection(((CraftServer)Bukkit.getServer()).getHandle().getServer(), ep.playerConnection.networkManager, ep);
		}
	}
	
	/**
	 * Listeners for packet 27 player input for pet movement
	 */
	@Override
	public void a (Packet27PlayerInput packet) {
		super.a(packet);
		CraftPlayer p = this.getPlayer();
		if(p.getVehicle() != null) {
			CraftEntity vc = (CraftEntity) p.getVehicle();
			if(vc.getHandle() instanceof PetEntity) {
				PetEntity pe = (PetEntity) vc.getHandle();
				pe.driveInput(packet.g(), packet.h(), packet.f(), packet.d());
			}
		}
	}
}
