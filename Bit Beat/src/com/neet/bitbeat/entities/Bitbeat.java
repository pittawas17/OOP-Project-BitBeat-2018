package com.neet.bitbeat.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.neet.bitbeat.main.Game;

public class Bitbeat extends B2DSprite {
	
	public Bitbeat(Body body) {
		
		super(body);
		
		Texture tex = Game.res.geTexture("bitbeat");
		TextureRegion[] sprites = TextureRegion.split(tex, 430, 77)[0];
		
		setAnimation(sprites, 0f);
	}
}
