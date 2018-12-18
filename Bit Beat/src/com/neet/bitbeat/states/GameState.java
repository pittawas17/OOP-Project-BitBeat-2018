package com.neet.bitbeat.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.neet.bitbeat.handlers.GameStateManager;
import com.neet.bitbeat.main.Game;

public abstract class GameState {
	
	protected Texture background, background2, background3;
	
	protected GameStateManager gsm;
	protected Game game;
	
	protected SpriteBatch sb;
	protected OrthographicCamera cam;
	protected OrthographicCamera hudCam;
	protected Music levelSong = Gdx.audio.newMusic(Gdx.files.internal("res/music/Intro.mp3"));
	protected int currentSong = 0;
	protected TiledMap tileMap;
	
	protected GameState(GameStateManager gsm) {
		
		setState();
		setSong(gsm.PLAY);
		
		this.gsm = gsm;
		game = gsm.game();
		sb = game.getSpriteBatch();
		cam = game.getCamera();
		hudCam = game.getHUDCamera();
	}

	public abstract void handleInput();
	public abstract void update(float dt);
	public abstract void render();
	public abstract void dispose();
	
	public void setSong(int i) {
        //nothing to do, the song we want to load is already loaded
		if (currentSong == i) return;
		if (levelSong != null && currentSong != i){ //a different song is loaded
	        levelSong.stop(); //stop it
	        levelSong.dispose();
	        levelSong = null;
		}

        //load the appropriate file
		switch (i){
			case 0:
				levelSong = Gdx.audio.newMusic(Gdx.files.internal("res/music/Intro.mp3"));
				break;
			case 1:
				levelSong = Gdx.audio.newMusic(Gdx.files.internal("res/music/Intro.mp3"));
				break;
			case 2:
				levelSong = Gdx.audio.newMusic(Gdx.files.internal("res/music/Intro.mp3"));
				break;
			case 5:
				levelSong = Gdx.audio.newMusic(Gdx.files.internal("res/music/Intro.mp3"));
				break;
			case 6:
				levelSong = Gdx.audio.newMusic(Gdx.files.internal("res/music/4walls.mp3"));
				break;
			case 7:
				levelSong = Gdx.audio.newMusic(Gdx.files.internal("res/music/Very.mp3"));
				break;
			case 8:
				levelSong = Gdx.audio.newMusic(Gdx.files.internal("res/music/Silence.mp3"));
				break;
			case 9:
				levelSong = Gdx.audio.newMusic(Gdx.files.internal("res/music/Chicken.mp3"));
				break;
			case 10:
				levelSong = Gdx.audio.newMusic(Gdx.files.internal("res/music/Mela.mp3"));
				break;
			case 11:
				levelSong = Gdx.audio.newMusic(Gdx.files.internal("res/music/Ninja.mp3"));
				break;
			case 12:
				levelSong = Gdx.audio.newMusic(Gdx.files.internal("res/music/Who.mp3"));
				break;
			case 13:
				levelSong = Gdx.audio.newMusic(Gdx.files.internal("res/music/Cherry.mp3"));
				break;
			case 14:
				levelSong = Gdx.audio.newMusic(Gdx.files.internal("res/music/Happy.mp3"));
				break;
			case -1:
				levelSong = Gdx.audio.newMusic(Gdx.files.internal("res/music/Intro.mp3"));
				break;
			case -2:
				levelSong = Gdx.audio.newMusic(Gdx.files.internal("res/music/Intro.mp3"));
				break;
			case -3:
				levelSong = Gdx.audio.newMusic(Gdx.files.internal("res/music/Intro.mp3"));
				break;
		}
		levelSong.setVolume(0.5f);

        //remember the song we loaded
		currentSong = i;
	}
	
	public void playMusic() {
		levelSong.play();
		
	}
	
	public void setState() {
		switch (gsm.PLAY){
			case 0:
				background = new Texture("res/map/scene/itf.jpg");
				break;
			case 1:
				background = new Texture("res/map/scene/itf.jpg");
				break;
			case 2:
				background = new Texture("res/map/scene/select.jpg");
				break;
			case 3:
				background = new Texture("res/map/scene/credits.jpg");
				break;
			case 4:
				background = new Texture("res/map/scene/credit2.jpg");
				break;
			case 5:
				background = new Texture("res/map/scene/credits.jpg");
				break;
			case -1:
				background = new Texture("res/map/scene/select.jpg");
				break;
			case -2:
				background = new Texture("res/map/scene/select.jpg");
				break;
			case -3:
				background = new Texture("res/map/scene/select.jpg");
				break;
			case 6:
				background = new Texture("res/map/scene/easy1-1.jpg");
				background2 = new Texture("res/map/scene/easy1-2.jpg");
				background3 = new Texture("res/map/scene/easy1-3.jpg");
				tileMap = new TmxMapLoader().load("res/map/easy1.tmx");
				break;
			case 7:
				background = new Texture("res/map/scene/normal1-1.jpg");
				background2 = new Texture("res/map/scene/normal1-2.jpg");
				background3 = new Texture("res/map/scene/normal1-3.jpg");
				tileMap = new TmxMapLoader().load("res/map/normal1.tmx");
				break;
			case 8:
				background = new Texture("res/map/scene/hard1-1.jpg");
				background2 = new Texture("res/map/scene/hard1-2.jpg");
				background3 = new Texture("res/map/scene/hard1-3.jpg");
				tileMap = new TmxMapLoader().load("res/map/hard1.tmx");
				break;
			case 9:
				background = new Texture("res/map/scene/easy2-1.jpg");
				background2 = new Texture("res/map/scene/easy2-2.jpg");
				background3 = new Texture("res/map/scene/easy2-3.jpg");
				tileMap = new TmxMapLoader().load("res/map/easy2.tmx");
				break;
			case 10:
				background = new Texture("res/map/scene/normal2-1.jpg");
				background2 = new Texture("res/map/scene/normal2-2.jpg");
				background3 = new Texture("res/map/scene/normal2-3.jpg");
				tileMap = new TmxMapLoader().load("res/map/normal2.tmx");
				break;
			case 11:
				background = new Texture("res/map/scene/hard2-1.jpg");
				background2 = new Texture("res/map/scene/hard2-2.jpg");
				background3 = new Texture("res/map/scene/hard2-3.jpg");
				tileMap = new TmxMapLoader().load("res/map/hard2.tmx");
				break;
			case 12:
				background = new Texture("res/map/scene/easy3-1.jpg");
				background2 = new Texture("res/map/scene/easy3-2.jpg");
				background3 = new Texture("res/map/scene/easy3-3.jpg");
				tileMap = new TmxMapLoader().load("res/map/easy3.tmx");
				break;
			case 13:
				background = new Texture("res/map/scene/normal3-1.jpg");
				background2 = new Texture("res/map/scene/normal3-2.jpg");
				background3 = new Texture("res/map/scene/normal3-3.jpg");
				tileMap = new TmxMapLoader().load("res/map/normal3.tmx");
				break;
			case 14:
				background = new Texture("res/map/scene/hard3-1.jpg");
				background2 = new Texture("res/map/scene/hard3-2.jpg");
				background3 = new Texture("res/map/scene/hard3-3.jpg");
				tileMap = new TmxMapLoader().load("res/map/hard3.tmx");
				break;
			default:
				background = new Texture("res/map/scene/itf.jpg");
				break;
		}
	}
	
}
