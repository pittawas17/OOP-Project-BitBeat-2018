package com.neet.bitbeat.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.neet.bitbeat.main.Game;

public class Start extends B2DSprite {
	
	public Start(Body body) {
		
		super(body);
		
		Texture tex = Game.res.geTexture("start");
		TextureRegion[] sprites = TextureRegion.split(tex, 216, 10)[0];
		
		setAnimation(sprites, 1 / 14f);
	}
}
