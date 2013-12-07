package com.RingOfStorms.tenjava.pathfinders;

import net.minecraft.server.v1_6_R3.Entity;
import net.minecraft.server.v1_6_R3.EntityInsentient;
import net.minecraft.server.v1_6_R3.EntityLiving;
import net.minecraft.server.v1_6_R3.PathEntity;
import net.minecraft.server.v1_6_R3.PathfinderGoal;
import net.minecraft.server.v1_6_R3.World;

import org.bukkit.entity.Player;

import com.RingOfStorms.tenjava.entities.PetEntity;

public class PetMeleeAttack extends PathfinderGoal {
	
	World a;
	EntityInsentient b;
	int c;
	double d;
	boolean e;
	PathEntity f;
	@SuppressWarnings("rawtypes")
	Class g;
	private int h;
	
	public PetMeleeAttack (EntityInsentient entity, @SuppressWarnings("rawtypes") Class oclass, double d0, boolean flag) {
		this(entity, d0, flag);
		this.g = oclass;
	}
	
	public PetMeleeAttack (EntityInsentient entity, double d0, boolean flag) {
		this.b = entity;
		this.a = b.world;
		this.d = d0;
		this.e = flag;
		this.a(3);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean a() {
		EntityLiving el = this.b.getGoalTarget();
		
		if(el == null) {
			return false;
		}else if(!el.isAlive()) {
			return false;
		}else if(el.getBukkitEntity() instanceof Player && b instanceof PetEntity && ((PetEntity)b).getOwnerName().equals(((Player)el.getBukkitEntity()).getName())) {
			return false;
		}else if(b instanceof PetEntity && ((PetEntity)b).isSitting()) {
			return false;
		}else if(this.g != null && !this.g.isAssignableFrom(el.getClass())) {
			return true;
		}else{
			this.f = this.b.getNavigation().a(el);
			return this.f != null;
		}
	}
	
	public boolean b () {
		EntityLiving el = this.b.getGoalTarget();
		return el == null ? false : (!el.isAlive() ? false : (!this.e ? !this.b.getNavigation().g() : false));
	}
	
	public void c () {
		this.b.getNavigation().a(this.f, this.d);
		this.h = 0;
	}
	
	public void d () {
		this.b.getNavigation().h();
	}
	
	public void e () {
		EntityLiving el = this.b.getGoalTarget();
		this.b.getControllerLook().a(el, 30.0F, 30.0F);
		if((this.e || this.b.getEntitySenses().canSee(el)) && --this.h <= 0) {
			this.h = 4 + this.b.aD().nextInt(17);
			this.b.getNavigation().a((Entity) el, this.d);
		}
		
		this.c = Math.max(this.c - 1, 0);
		double d0 = (double) (this.b.width * 2.0F + this.b.width * 2.0F + el.width);
		
		if(this.b.e(el.locX, el.boundingBox.b, el.locZ) <= d0) {
			if(this.c <= 0) {
				this.c = 20;
				if(this.b.aZ() != null) {
					this.b.aV();
				}
				
				this.b.m(el);
			}
		}
	}
}
