package com.neet.bitbeat.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.neet.bitbeat.main.Game;

public class Number extends B2DSprite {

	public Number(Body body, char i) {
		
		super(body);
		
		Texture tex = Game.res.geTexture("num"+i);
		TextureRegion[] sprites = TextureRegion.split(tex, 64, 64)[0];
		
		setAnimation(sprites, 1 / 5f);
		
	}
	
}
