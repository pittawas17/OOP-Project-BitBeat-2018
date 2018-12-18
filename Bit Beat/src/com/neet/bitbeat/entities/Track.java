package com.neet.bitbeat.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.neet.bitbeat.main.Game;

public class Track extends B2DSprite {
	
	public Track(Body body) {
		
		super(body);
		
		Texture tex = Game.res.geTexture("track");
		TextureRegion[] sprites = TextureRegion.split(tex, 89, 21)[0];
		
		setAnimation(sprites, 1 / 9f);
	}

}
