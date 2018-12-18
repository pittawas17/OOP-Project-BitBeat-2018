package com.neet.bitbeat.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.neet.bitbeat.main.Game;

public class Can extends B2DSprite {

	public Can(Body body) {
		
		super(body);
		
		Texture tex = Game.res.geTexture("can");
		TextureRegion[] sprites = TextureRegion.split(tex, 32, 32)[0];
		
		setAnimation(sprites, 1 / 5f);
		
	}
	
}
