package com.neet.bitbeat.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.neet.bitbeat.main.Game;

public class HUD {
	
	private Player player;
	
	private TextureRegion[] blocks;
	
	public HUD(Player player) {
		
		this.player = player;
		
		Texture tex = Game.res.geTexture("hud");
		
		blocks = new TextureRegion[3];
		for (int i = 0; i < blocks.length; i++) {
			blocks[i] = new TextureRegion(tex, 32 + i * 16, 0, 16, 16 );
		}
		
	}
	public void render(SpriteBatch sb) {
		
		short bits = player.getBody().getFixtureList()
				.first().getFilterData().maskBits;
		
	}
	
}
