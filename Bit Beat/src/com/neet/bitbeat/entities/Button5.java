package com.neet.bitbeat.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.neet.bitbeat.main.Game;

public class Button5 extends B2DSprite {
	
	public Button5(Body body) {
		
		super(body);
		
		Texture tex = Game.res.geTexture("button5");
		TextureRegion[] sprites = TextureRegion.split(tex, 95, 24)[0];
		
		setAnimation(sprites, 0f);
	}
}