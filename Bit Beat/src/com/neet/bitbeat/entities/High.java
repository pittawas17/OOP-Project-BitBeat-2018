package com.neet.bitbeat.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.neet.bitbeat.main.Game;

public class High extends B2DSprite {
	
	public High(Body body) {
		
		super(body);
		
		Texture tex = Game.res.geTexture("high");
		TextureRegion[] sprites = TextureRegion.split(tex, 212, 35)[0];
		
		setAnimation(sprites, 1 / 9f);
	}

}
