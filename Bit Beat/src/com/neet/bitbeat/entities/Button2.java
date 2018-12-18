package com.neet.bitbeat.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.neet.bitbeat.main.Game;

public class Button2 extends B2DSprite {
	
	public Button2(Body body) {
		
		super(body);
		
		Texture tex = Game.res.geTexture("button2");
		TextureRegion[] sprites = TextureRegion.split(tex, 72, 24)[0];
		
		setAnimation(sprites, 1 / 14f);
	}
}
