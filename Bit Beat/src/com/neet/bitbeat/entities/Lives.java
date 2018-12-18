package com.neet.bitbeat.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.neet.bitbeat.main.Game;

public class Lives extends B2DSprite {
	
	public Lives (Body body) {
		
		super(body);
		
		Texture tex = Game.res.geTexture("lives");
		TextureRegion[] sprites = TextureRegion.split(tex, 110, 29)[0];
		
		setAnimation(sprites, 1 / 14f);
	}
}
