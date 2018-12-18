package com.neet.bitbeat.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.neet.bitbeat.main.Game;

public class TrackHardButton2 extends B2DSprite {
	
	public TrackHardButton2(Body body) {
		
		super(body);
		
		Texture tex = Game.res.geTexture("hardT2");
		TextureRegion[] sprites = TextureRegion.split(tex, 608, 30)[0];
		
		setAnimation(sprites, 1 / 14f);
	}
}
