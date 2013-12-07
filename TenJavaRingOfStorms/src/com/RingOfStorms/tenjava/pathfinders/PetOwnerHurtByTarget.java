package com.RingOfStorms.tenjava.pathfinders;

import net.minecraft.server.v1_6_R3.EntityCreature;
import net.minecraft.server.v1_6_R3.EntityLiving;
import net.minecraft.server.v1_6_R3.PathfinderGoalTarget;

public class PetOwnerHurtByTarget extends PathfinderGoalTarget {
	
	EntityLiving a;
	EntityLiving b;
	private int e;
	
	EntityLiving owner;
	
	public PetOwnerHurtByTarget(EntityCreature entitycreature, EntityLiving owner) {
		super(entitycreature, false);
		this.a = entitycreature;
		this.a(1);
		
		this.owner = owner;
	}

	@Override
	public boolean a() {
		EntityLiving el = owner;
		
		if(el == null) {
			return false;
		}else{
			this.b = el.getLastDamager();
			int i = el.aF();
			
			return i != this.e && this.a(this.b, false);
		}
	}
	
	@Override
	public void c () {
		this.c.setGoalTarget(this.b);
		if(owner != null)
			this.e = owner.aF();
		super.c();
	}
}
