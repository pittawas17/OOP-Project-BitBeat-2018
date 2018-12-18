package com.neet.bitbeat.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.neet.bitbeat.main.Game;

public class TrackEasyButton2 extends B2DSprite {
	
	public TrackEasyButton2(Body body) {
		
		super(body);
		
		Texture tex = Game.res.geTexture("easyT3");
		TextureRegion[] sprites = TextureRegion.split(tex, 524, 34)[0];
		
		setAnimation(sprites, 1 / 14f);
	}
}
