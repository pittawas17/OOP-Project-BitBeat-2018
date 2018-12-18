package com.neet.bitbeat.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.neet.bitbeat.main.Game;

public class TrackNormalButton3 extends B2DSprite {
	
	public TrackNormalButton3(Body body) {
		
		super(body);
		
		Texture tex = Game.res.geTexture("normalT3");
		TextureRegion[] sprites = TextureRegion.split(tex, 618, 36)[0];
		
		setAnimation(sprites, 1 / 14f);
	}
}
