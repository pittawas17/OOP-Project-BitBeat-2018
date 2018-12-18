package com.neet.bitbeat.handlers;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;

public class MyContactListener implements ContactListener{
	
	private int numFootContacts;
	private Array<Body> bodiesToRemove;
	private Array<Body> canToRemove;
	private Array<Body> diamondToRemove;
	
	public MyContactListener() {
		
		super();
		bodiesToRemove = new Array<Body>();
		canToRemove = new Array<Body>();
		diamondToRemove = new Array<Body>();
	}
	
	// called when two fixtures start to collide
	public void beginContact(Contact c) {
		
		Fixture fa = c.getFixtureA();
		Fixture fb = c.getFixtureB();
		
		if (fa== null || fb == null) {
			return;
		}
		if (fa.getUserData() != null && fa.getUserData().equals("foot")) {
			numFootContacts++;
		}
		if (fb.getUserData() != null && fb.getUserData().equals("foot")) {
			numFootContacts++;
		}
		if (fa.getUserData() != null && fa.getUserData().equals("coin")) {
			bodiesToRemove.add(fa.getBody());
		}
		if (fb.getUserData() != null && fb.getUserData().equals("coin")) {
			bodiesToRemove.add(fb.getBody());
		}
		if (fa.getUserData() != null && fa.getUserData().equals("can")) {
			canToRemove.add(fa.getBody());
		}
		if (fb.getUserData() != null && fb.getUserData().equals("can")) {
			canToRemove.add(fb.getBody());
		}
		if (fa.getUserData() != null && fa.getUserData().equals("diamond")) {
			diamondToRemove.add(fa.getBody());
		}
		if (fb.getUserData() != null && fb.getUserData().equals("diamond")) {
			diamondToRemove.add(fb.getBody());
		} 
		
	}
	
	// called when two fixtures no longer collide
	public void endContact(Contact c) {

		Fixture fa = c.getFixtureA();
		Fixture fb = c.getFixtureB();
		
		if (fa.getUserData() != null && fa.getUserData().equals("foot")) {
			numFootContacts--;
		}
		if (fb.getUserData() != null && fb.getUserData().equals("foot")) {
			numFootContacts--;
		}
		
	}
	
	public boolean isPlayerOnGround() { return numFootContacts > 0; }
	public Array<Body> getBodiesToRemove() { return bodiesToRemove; }
	public Array<Body> getCanToRemove() { return canToRemove; }
	public Array<Body> getDiamondToRemove() { return diamondToRemove; }
	
	public void preSolve(Contact c, Manifold m) {}
	public void postSolve(Contact c, ContactImpulse ci) {}

}
