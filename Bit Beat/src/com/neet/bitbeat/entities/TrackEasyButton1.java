package com.neet.bitbeat.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.neet.bitbeat.main.Game;

public class TrackEasyButton1 extends B2DSprite {
	
	public TrackEasyButton1(Body body) {
		
		super(body);
		
		Texture tex = Game.res.geTexture("easyT1");
		TextureRegion[] sprites = TextureRegion.split(tex, 232, 38)[0];
		
		setAnimation(sprites, 1 / 14f);
	}
}
