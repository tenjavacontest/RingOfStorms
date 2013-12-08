package com.RingOfStorms.tenjava.pathfinders;

import net.minecraft.server.v1_6_R3.EntityInsentient;
import net.minecraft.server.v1_6_R3.EntityLiving;
import net.minecraft.server.v1_6_R3.MathHelper;
import net.minecraft.server.v1_6_R3.Navigation;
import net.minecraft.server.v1_6_R3.PathfinderGoal;
import net.minecraft.server.v1_6_R3.World;

/**
 * Custom follow ower goal for pets
 * @author RingOfStorms
 *
 */
public class PetGoalFollowOwner extends PathfinderGoal {
	
	private EntityInsentient d;
	private EntityLiving e;
	World a;
	private double f;
	private Navigation g;
	private int h;
	float b;
	float c;
	boolean i;
	
	private EntityLiving owner;
	private boolean sitting;
	
	public PetGoalFollowOwner (EntityInsentient entity, double f, float f1, float f2, EntityLiving owner) {
		this.d = entity;
		this.a = this.d.world;
		this.f = f;
		this.g = this.d.getNavigation();
		this.c = f1;
		this.b = f2;
		this.a(3);
		this.owner = owner;
	}
	
	public void setOwner (EntityLiving owner) {
		this.owner = owner;
	}
	
	public EntityLiving getOwner () {
		return owner;
	}
	
	public boolean isSitting () {
		return sitting;
	}
	
	public void setSitting (boolean isSitting) {
		this.sitting = isSitting;
	}
	
	@Override
	public boolean a() {
		EntityLiving el = owner;
		
		if(el == null) {
			return false;
		}else if(isSitting()) {
			return false;
		}else if(this.d.e(owner) < (double) (this.c * this.c)) {
			return false;
		}else{
			this.e = el;
			return true;
		}
	}
	
	public boolean b () {
		return !this.g.g() && this.d.e(this.e) > (double) (this.b * this.b) && !isSitting();
	}
	
	public void c () {
		this.h = 0;
		this.g.g();
		this.g.a(this.i);
	}
	
	public void d () {
		this.e = null;
		this.g.g();
		this.g.a(this.i);
	}
	
	public void e () {
		this.d.getControllerLook().a(this.e, 10.0F, (float) this.d.bp());
		if(!isSitting()) {
			if(--this.h <= 0) {
				this.h = 10;
				if(!this.g.a(this.e, this.f)) {
					int i = MathHelper.floor(this.e.locX) - 2;
					int j = MathHelper.floor(this.e.locZ) - 2;
					int k = MathHelper.floor(this.e.boundingBox.b);
					
					for(int l = 0; l <= 4; l++) {
						for(int i1 = 0; i1 <= 4; i1++) {
							if(
								(l < 1 || i1 < 1 || l > 3 || i1 > 3) &&
								this.a.w(i + l,  k - 1, j + i1) &&
								!this.a.u(i + l, k, j + i1) &&
								!this.a.u(i + l,  k + 1,  j + i1)
								) {
								this.d.setPositionRotation((double) ((float) (i + l) + 0.5F), (double) k, (double) ((float) (j + i1) + 0.5F), this.d.yaw, this.d.pitch);
								this.g.h();
								return;
							}
						}
					}
				}
			}
		}
	}
}
