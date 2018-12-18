package com.neet.bitbeat.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.neet.bitbeat.handlers.Content;
import com.neet.bitbeat.handlers.GameStateManager;
import com.neet.bitbeat.handlers.MyInput;
import com.neet.bitbeat.handlers.MyInputProcessor;

public class Game implements ApplicationListener{
	
	public static final String TITLE = "Bit Beat";
	public static final int V_WIDTH = 640;
	public static final int V_HEIGHT = 480;
	public static final int SCALE = 2;
	
	public static final float STEP = 1 / 120f;
	private float accum;
	
	private SpriteBatch sb;
	private OrthographicCamera cam;
	private OrthographicCamera hudCam;
	
	private GameStateManager gsm;
	
	public static Content res;
	
	public void create() {
		
		Gdx.graphics.setVSync(true);
		Gdx.input.setInputProcessor(new MyInputProcessor());
		
		res = new Content();
		res.loadTexture("res/character/easy1.png", "player6");
		res.loadTexture("res/character/normal1.png", "player7");
		res.loadTexture("res/character/hard1.png", "player8");
		res.loadTexture("res/character/easy2.png", "player9");
		res.loadTexture("res/character/normal2.png", "player10");
		res.loadTexture("res/character/hard2.png", "player11");
		res.loadTexture("res/character/easy3.png", "player12");
		res.loadTexture("res/character/normal3.png", "player13");
		res.loadTexture("res/character/hard3.png", "player14");
		res.loadTexture("res/object/coin.png", "coin");
		res.loadTexture("res/object/can3.png", "can");
		res.loadTexture("res/object/gem.png", "diamond");
		res.loadTexture("res/object/heart2.png", "heart");
		res.loadTexture("res/map/start.png", "start");
		res.loadTexture("res/map/lives.png", "lives");
		res.loadTexture("res/map/tracks.png", "track");
		res.loadTexture("res/map/op2.png", "bitbeat");
		res.loadTexture("res/map/high.png", "high");
		res.loadTexture("res/map/new.png", "button1");
		res.loadTexture("res/map/rank.png", "button2");
		res.loadTexture("res/map/credits.png", "button3");
		res.loadTexture("res/map/exit.png", "button4");
		res.loadTexture("res/map/easy.png", "button5");
		res.loadTexture("res/map/normal.png", "button6");
		res.loadTexture("res/map/hard.png", "button7");
		res.loadTexture("res/map/easyT1.png", "easyT1");
		res.loadTexture("res/map/easyT2.png", "easyT2");
		res.loadTexture("res/map/easyT3.png", "easyT3");
		res.loadTexture("res/map/hardT1.png", "hardT1");
		res.loadTexture("res/map/hardT2.png", "hardT2");
		res.loadTexture("res/map/hardT3.png", "hardT3");
		res.loadTexture("res/map/normalT1.png", "normalT1");
		res.loadTexture("res/map/normalT2.png", "normalT2");
		res.loadTexture("res/map/normalT3.png", "normalT3");
		res.loadTexture("res/object/pointer.png", "pointer");
		res.loadTexture("res/object/pointer2.png", "pointer2");
		res.loadTexture("res/object/0-b.png", "num0");
		res.loadTexture("res/object/1-b.png", "num1");
		res.loadTexture("res/object/2-b.png", "num2");
		res.loadTexture("res/object/3-b.png", "num3");
		res.loadTexture("res/object/4-b.png", "num4");
		res.loadTexture("res/object/5-b.png", "num5");
		res.loadTexture("res/object/6-b.png", "num6");
		res.loadTexture("res/object/7-b.png", "num7");
		res.loadTexture("res/object/8-b.png", "num8");
		res.loadTexture("res/object/9-b.png", "num9");

		sb = new SpriteBatch();
		cam = new OrthographicCamera();
		cam.setToOrtho(false, V_WIDTH, V_HEIGHT);
		hudCam = new OrthographicCamera();
		hudCam.setToOrtho(false, V_WIDTH, V_HEIGHT);
		
		gsm = new GameStateManager(this);
		
	}
	
	public void render() {
		
		accum += Gdx.graphics.getDeltaTime();
		while(accum >= STEP) {
			accum -= STEP;
			gsm.update(STEP);
			gsm.render();
			MyInput.update();
		}
		
	}
	
	public void dispose() {
		
	}
	
	public SpriteBatch getSpriteBatch() { return sb; }
	public OrthographicCamera getCamera() { return cam; }
	public OrthographicCamera getHUDCamera() { return hudCam; }
	
	public void resize(int w, int h) {}
	public void pause() {}
	public void resume() {}

}
