package com.neet.bitbeat.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.neet.bitbeat.main.Game;

public class Pointer extends B2DSprite {
	
	public Pointer(Body body) {
		
		super(body);
		
		Texture tex = Game.res.geTexture("pointer");
		TextureRegion[] sprites = TextureRegion.split(tex, 24, 24)[0];
		
		setAnimation(sprites, 0f);
	}
}
