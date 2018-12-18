package com.neet.bitbeat.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.neet.bitbeat.main.Game;

public class TrackHardButton3 extends B2DSprite {
	
	public TrackHardButton3(Body body) {
		
		super(body);
		
		Texture tex = Game.res.geTexture("hardT3");
		TextureRegion[] sprites = TextureRegion.split(tex, 483, 36)[0];
		
		setAnimation(sprites, 1 / 14f);
	}
}
