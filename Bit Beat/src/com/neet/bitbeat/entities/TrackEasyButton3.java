package com.neet.bitbeat.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.neet.bitbeat.main.Game;

public class TrackEasyButton3 extends B2DSprite {
	
	public TrackEasyButton3(Body body) {
		
		super(body);
		
		Texture tex = Game.res.geTexture("easyT2");
		TextureRegion[] sprites = TextureRegion.split(tex, 558, 36)[0];
		
		setAnimation(sprites, 1 / 14f);
	}
}
