package com.neet.bitbeat.states;

import static com.neet.bitbeat.handlers.B2DVars.PPM;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.neet.bitbeat.entities.Bitbeat;
import com.neet.bitbeat.entities.Button1;
import com.neet.bitbeat.entities.Button2;
import com.neet.bitbeat.entities.Button3;
import com.neet.bitbeat.entities.Button4;
import com.neet.bitbeat.entities.Button5;
import com.neet.bitbeat.entities.Button6;
import com.neet.bitbeat.entities.Button7;
import com.neet.bitbeat.entities.Number;
import com.neet.bitbeat.entities.Can;
import com.neet.bitbeat.entities.Coin;
import com.neet.bitbeat.entities.Diamond;
import com.neet.bitbeat.entities.Heart;
import com.neet.bitbeat.entities.High;
import com.neet.bitbeat.entities.Lives;
import com.neet.bitbeat.entities.Player;
import com.neet.bitbeat.entities.Pointer;
import com.neet.bitbeat.entities.Pointer2;
import com.neet.bitbeat.entities.Start;
import com.neet.bitbeat.entities.Track;
import com.neet.bitbeat.entities.TrackEasyButton1;
import com.neet.bitbeat.entities.TrackEasyButton2;
import com.neet.bitbeat.entities.TrackEasyButton3;
import com.neet.bitbeat.entities.TrackHardButton1;
import com.neet.bitbeat.entities.TrackHardButton2;
import com.neet.bitbeat.entities.TrackHardButton3;
import com.neet.bitbeat.entities.TrackNormalButton1;
import com.neet.bitbeat.entities.TrackNormalButton2;
import com.neet.bitbeat.entities.TrackNormalButton3;
import com.neet.bitbeat.handlers.B2DVars;
import com.neet.bitbeat.handlers.GameStateManager;
import com.neet.bitbeat.handlers.MyContactListener;
import com.neet.bitbeat.handlers.MyInput;
import com.neet.bitbeat.main.Game;

public class Play extends GameState {
	
	public Game game = new Game();
	private boolean debug = false, run = true;
	private float tileSize, songPosit, checkPointX = 0, checkPointY = 425 / PPM;
	private double lastx;
	private int status = 2, status2 = -1, status3 = 6, status4 = 7, status5 = 8, lives = 3, point = 0, highscore = 0;
	private char[] chars;
	
	private World world;
	private Box2DDebugRenderer b2dr;
	
	private OrthographicCamera b2dCam;
	
	private MyContactListener cl;
	
	private TiledMapTileLayer layer;
	private OrthogonalTiledMapRenderer tmr;
	
	private Pointer pointer;
	private Pointer2 pointer2;
	private Heart heart, heart2, heart3;
	private TrackEasyButton1 bTrack;
	private TrackEasyButton2 bTrack2;
	private TrackEasyButton3 bTrack3;
	private TrackNormalButton1 bTrack4;
	private TrackNormalButton2 bTrack5;
	private TrackNormalButton3 bTrack6;
	private TrackHardButton1 bTrack7;
	private TrackHardButton2 bTrack8;
	private TrackHardButton3 bTrack9;
	private Button1 bt1;
	private Button2 bt2;
	private Button3 bt3;
	private Button4 bt4;
	private Button5 bt5;
	private Button6 bt6;
	private Button7 bt7;
	private High high;
	private Number num1, num2, num3, num4, num5;
	private Player player;
	private Array<Coin> coins;
	private Array<Can> can;
	private Array<Diamond> diamond;
	private Start start;
	private Track track;
	private Lives livesT;
	private Bitbeat bitbeat;
	
	public Play(GameStateManager gsm) {
		
		super(gsm);

		//set up box2d stuff
		world = new World(new Vector2(0, 0f), true);
		cl = new MyContactListener();
		world.setContactListener(cl);
		b2dr = new Box2DDebugRenderer();
		
		//create start
		createStart();
		
		//create bitbeat
		createBitbeat();
		
		levelSong.setVolume(0.5f);
		levelSong.setLooping(true);
		playMusic();
		
		//set up box2d camera
		b2dCam = new OrthographicCamera();
		b2dCam.setToOrtho(false, Game.V_WIDTH / PPM, Game.V_HEIGHT / PPM);
			
	}
	
	public void handleInput(GameStateManager gsm) {
		if (gsm.PLAY == 0) {			//title input
			//push to start
			if(MyInput.isPressed(MyInput.BUTTON1)) {
				gsm.PLAY = 1;
				Play(gsm);
			}
		} else if (gsm.PLAY > 5) {		//state input
			//player jump
			if(MyInput.isPressed(MyInput.BUTTON1) && run == true) {
				if (cl.isPlayerOnGround()) {
					player.getBody().applyForceToCenter(0, 420, true);
				}
			} else if(MyInput.isPressed(MyInput.BUTTON1) && run == false) {
				player.getBody().setLinearVelocity(1.7f, 0);
				levelSong.setPosition(songPosit);
				run = true;
			}
		} else if (gsm.PLAY == 1) {		//main menu input
			if(MyInput.isPressed(MyInput.BUTTON1)) {
				if (status != 5) {
					gsm.PLAY = status;
					setState();
					Play(gsm);
				} else Gdx.app.exit();
			} else if(MyInput.isPressed(MyInput.BUTTON2)) {
				if (pointer.getBody().getPosition().y != 250 / PPM) {
					pointer.getBody().setTransform(new Vector2(
							pointer.getBody().getPosition().x, 
							pointer.getBody().getPosition().y + 50 / PPM), 
							0
						);
					status--;
				} else {
					pointer.getBody().setTransform(new Vector2(
							pointer.getBody().getPosition().x, 
							100 / PPM), 
							0
						);
					status = 5;
				}
			} else if(MyInput.isPressed(MyInput.BUTTON3)) {
				if (pointer.getBody().getPosition().y != 100 / PPM) {
					pointer.getBody().setTransform(new Vector2(
							pointer.getBody().getPosition().x, 
							pointer.getBody().getPosition().y - 50 / PPM), 
							0
						);
					status++;
				} else {
					pointer.getBody().setTransform(new Vector2(
							pointer.getBody().getPosition().x, 
							250 / PPM), 
							0
						);
					status = 2;
				}
			} else if(MyInput.isPressed(MyInput.BUTTON4)) {
				gsm.PLAY = 0;
				setState();
				Play(gsm);
			}
		} else if (gsm.PLAY == 2) {		//select input
			if(MyInput.isPressed(MyInput.BUTTON1)) {
				gsm.PLAY = status2;
				setState();
				Play(gsm);
			} else if(MyInput.isPressed(MyInput.BUTTON2)) {
				if (pointer2.getBody().getPosition().y != 350 / PPM) {
					pointer2.getBody().setTransform(new Vector2(
							pointer2.getBody().getPosition().x, 
							pointer2.getBody().getPosition().y + 100 / PPM), 
							0
						);
					status2++;
				} else {
					pointer2.getBody().setTransform(new Vector2(
							pointer2.getBody().getPosition().x, 
							150 / PPM), 
							0
						);
					status2 = -3;
				}
			} else if(MyInput.isPressed(MyInput.BUTTON3)) {
				if (pointer2.getBody().getPosition().y != 150 / PPM) {
					pointer2.getBody().setTransform(new Vector2(
							pointer2.getBody().getPosition().x, 
							pointer2.getBody().getPosition().y - 100 / PPM), 
							0
						);
					status2--;
				} else {
					pointer2.getBody().setTransform(new Vector2(
							pointer2.getBody().getPosition().x, 
							350 / PPM), 
							0
						);
					status2 = -1;
				}
			} else if(MyInput.isPressed(MyInput.BUTTON4)) {
				gsm.PLAY = 1;
				setState();
				Play(gsm);
			}
		} else if (gsm.PLAY == 3) {
			if(MyInput.isPressed(MyInput.BUTTON4)) {
				gsm.PLAY = 1;
				setState();
				Play(gsm);
			}
		} else if (gsm.PLAY == 4) {
			if(MyInput.isPressed(MyInput.BUTTON4)) {
				gsm.PLAY = 1;
				setState();
				Play(gsm);
			}
		} else if (gsm.PLAY == -1) {		//select input
			if(MyInput.isPressed(MyInput.BUTTON1)) {
				gsm.PLAY = status3;
				setState();
				setSong(gsm.PLAY);
				Play(gsm);
			} else if(MyInput.isPressed(MyInput.BUTTON2)) {
				if (pointer2.getBody().getPosition().y != 350 / PPM) {
					pointer2.getBody().setTransform(new Vector2(
							pointer2.getBody().getPosition().x, 
							pointer2.getBody().getPosition().y + 100 / PPM), 
							0
						);
					status3 -= 3;
				} else {
					pointer2.getBody().setTransform(new Vector2(
							pointer2.getBody().getPosition().x, 
							150 / PPM), 
							0
						);
					status3 = 12;
				}
			} else if(MyInput.isPressed(MyInput.BUTTON3)) {
				if (pointer2.getBody().getPosition().y != 150 / PPM) {
					pointer2.getBody().setTransform(new Vector2(
							pointer2.getBody().getPosition().x, 
							pointer2.getBody().getPosition().y - 100 / PPM), 
							0
						);
					status3 += 3;
				} else {
					pointer2.getBody().setTransform(new Vector2(
							pointer2.getBody().getPosition().x, 
							350 / PPM), 
							0
						);
					status3 = 6;
				}
			} else if(MyInput.isPressed(MyInput.BUTTON4)) {
				gsm.PLAY = 2;
				setState();
				Play(gsm);
			}
		} else if (gsm.PLAY == -2) {		//select input
			if(MyInput.isPressed(MyInput.BUTTON1)) {
				gsm.PLAY = status4;
				setState();
				setSong(gsm.PLAY);
				Play(gsm);
			} else if(MyInput.isPressed(MyInput.BUTTON2)) {
				if (pointer2.getBody().getPosition().y != 350 / PPM) {
					pointer2.getBody().setTransform(new Vector2(
							pointer2.getBody().getPosition().x, 
							pointer2.getBody().getPosition().y + 100 / PPM), 
							0
						);
					status4 -= 3;
				} else {
					pointer2.getBody().setTransform(new Vector2(
							pointer2.getBody().getPosition().x, 
							150 / PPM), 
							0
						);
					status4 = 13;
				}
			} else if(MyInput.isPressed(MyInput.BUTTON3)) {
				if (pointer2.getBody().getPosition().y != 150 / PPM) {
					pointer2.getBody().setTransform(new Vector2(
							pointer2.getBody().getPosition().x, 
							pointer2.getBody().getPosition().y - 100 / PPM), 
							0
						);
					status4 += 3;
				} else {
					pointer2.getBody().setTransform(new Vector2(
							pointer2.getBody().getPosition().x, 
							350 / PPM), 
							0
						);
					status4 = 7;
				}
			} else if(MyInput.isPressed(MyInput.BUTTON4)) {
				gsm.PLAY = 2;
				setState();
				Play(gsm);
			}
		}  else if (gsm.PLAY == -3) {		//select input
			if(MyInput.isPressed(MyInput.BUTTON1)) {
				gsm.PLAY = status5;
				setState();
				setSong(gsm.PLAY);
				Play(gsm);
			} else if(MyInput.isPressed(MyInput.BUTTON2)) {
				if (pointer2.getBody().getPosition().y != 350 / PPM) {
					pointer2.getBody().setTransform(new Vector2(
							pointer2.getBody().getPosition().x, 
							pointer2.getBody().getPosition().y + 100 / PPM), 
							0
						);
					status5 -= 3;
				} else {
					pointer2.getBody().setTransform(new Vector2(
							pointer2.getBody().getPosition().x, 
							150 / PPM), 
							0
						);
					status5 = 14;
				}
			} else if(MyInput.isPressed(MyInput.BUTTON3)) {
				if (pointer2.getBody().getPosition().y != 150 / PPM) {
					pointer2.getBody().setTransform(new Vector2(
							pointer2.getBody().getPosition().x, 
							pointer2.getBody().getPosition().y - 100 / PPM), 
							0
						);
					status5 += 3;
				} else {
					pointer2.getBody().setTransform(new Vector2(
							pointer2.getBody().getPosition().x, 
							350 / PPM), 
							0
						);
					status5 = 8;
				}
			} else if(MyInput.isPressed(MyInput.BUTTON4)) {
				gsm.PLAY = 2;
				setState();
				Play(gsm);
			}
		}
	}
	
	private void Play(GameStateManager gsm) {
		
		//set up box2d stuff
		if (gsm.PLAY > 5) {
			world = new World(new Vector2(0, -20f), true);
		} else world = new World(new Vector2(0, 0f), true);
		
		cl = new MyContactListener();
		world.setContactListener(cl);
		b2dr = new Box2DDebugRenderer();
		
		if (gsm.PLAY < 6) {
			
			if (levelSong.isPlaying() == false) {
				setSong(gsm.PLAY);
				levelSong.setLooping(true);
				playMusic();
			}
			switch (gsm.PLAY) {
			case 1:
				status = 2;
				
				//create Button
				createButton(1);
				
				//create Pointer
				createPointer(1);
				break;

			case 2:
				status2 = -1;
				
				//create Button
				createButton(2);
				
				//create Pointer
				createPointer(2);
				break;
			case 3:
				//create Highscore
				createHighScore();
				break;
			case 4:
				break;
			case -1:
				status3 = 6;
				
				//create Track
				createTrack();
				
				//create Button
				createButton(-1);
				
				//create Pointer
				createPointer(3);
				break;
			case -2:
				status4 = 7;
				
				//create Track
				createTrack();
				
				//create Button
				createButton(-2);
				
				//create Pointer
				createPointer(3);
				break;
			case -3:	
				status5 = 8;
				
				//create Track
				createTrack();
				
				//create Button
				createButton(-3);
				
				//create Pointer
				createPointer(3);
				break;
			}
			
		} else if (gsm.PLAY > 5) {
			//create lives
			createLives();
			
			//create heart
			createHeart();
		
			//create player
			createPlayer();
		
			//create tiles
			createTiles();
		
			//create coins
			createCoins();
			
			//create cans
			createCan();
			
			//create diamonds
			createDiamond();
			
			playMusic();

		}
		
		//set up box2d camera
		b2dCam = new OrthographicCamera();
		b2dCam.setToOrtho(false, Game.V_WIDTH / PPM, Game.V_HEIGHT / PPM);
		
	}

	public void update(float dt) {
		
		//check input
		handleInput(gsm);
		
		//update box2d
		world.step(dt, 6, 2);
		
		if (gsm.PLAY == 0) {
			
			start.update(dt);
			
		} else if (gsm.PLAY > 5) {
			livesT.getBody().setTransform(new Vector2((cam.position.x - 250)/ PPM, livesT.getPosition().y), 0);
			
			// remove coins
			Array<Body> bodies = cl.getBodiesToRemove();
			for (int i = 0; i < bodies.size; i++) {
				Body b = bodies.get(i);
				coins.removeValue((Coin) b.getUserData(), true);
				world.destroyBody(b);
				point += 10;
				chars = ("" + point).toCharArray();
			}
			bodies.clear();
			
			for (int i = 0; i < coins.size; i++) {
				coins.get(i).update(dt);
			}
			
			// remove can
			Array<Body> bodies2 = cl.getCanToRemove();
			for (int i = 0; i < bodies2.size; i++) {
				Body b2 = bodies2.get(i);
				can.removeValue((Can) b2.getUserData(), true);
				world.destroyBody(b2);
				songPosit = levelSong.getPosition() + 1;
				checkPointX = player.getPosition().x + 40 / PPM;
			}
			bodies2.clear();
			
			for (int i = 0; i < can.size; i++) {
				can.get(i).update(dt);
			}
			
			// remove diamond
			Array<Body> bodies3 = cl.getDiamondToRemove();
			for (int i = 0; i < bodies3.size; i++) {
				Body b3 = bodies3.get(i);
				diamond.removeValue((Diamond) b3.getUserData(), true);
				world.destroyBody(b3);
				point += 50;
				chars = ("" + point).toCharArray();
			}
			bodies3.clear();
						
			for (int i = 0; i < diamond.size; i++) {
				diamond.get(i).update(dt);
			}
			
			heart.update(dt);
			heart2.update(dt);
			heart3.update(dt);
			player.update(dt);
			
			// game over
			if ((player.getPosition().y < 0 || player.getPosition().x == lastx || player.getPosition().x > 960 * 32) && run == true) {
				if (lives == 0) {
					gsm.PLAY = 1;
					setSong(gsm.PLAY);
					setState();
					status = 2;
					lives = 3;
					if (point > highscore) {
						highscore = point;
					}
					point = 0;
					Play(gsm);
					run = true;
				} else if (lives != 0){
					player.getBody().setTransform(new Vector2(
							checkPointX, 
							checkPointY),
							0
						);
					player.getBody().setLinearVelocity(0, 0);
					run = false;
					lives --;
					switch (lives) {
					case 2:
						for (float i = heart3.getPosition().y; i <= 480; i++) {
							heart3.getBody().setTransform(new Vector2((cam.position.x - 230 )/ PPM, i), 0);
						}
						break;
					case 1:
						for (float i = heart2.getPosition().y; i <= 480; i++) {
							heart2.getBody().setTransform(new Vector2((cam.position.x - 230 )/ PPM, i), 0);
						}
						break;
					case 0:
						for (float i = heart.getPosition().y; i <= 480; i++) {
							heart.getBody().setTransform(new Vector2((cam.position.x - 230 )/ PPM, i), 0);
						}
						break;
					default:
						break;
					}
				}
			} else {
				lastx = player.getPosition().x;
				heart.getBody().setTransform(new Vector2((cam.position.x - 150 )/ PPM, heart.getPosition().y), 0);
				heart2.getBody().setTransform(new Vector2((cam.position.x - 120 )/ PPM, heart2.getPosition().y), 0);
				heart3.getBody().setTransform(new Vector2((cam.position.x - 90 )/ PPM, heart3.getPosition().y), 0);
			}
			
			if (run == true) {
				levelSong.play();
				
			} else {
				levelSong.pause();
			}
		}
		
	}
	
	public void render() {
		
		// clear screen
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if (gsm.PLAY <= 5) {
			//draw background
			sb.begin();
			sb.draw(background, 0, 0, Game.V_WIDTH, Game.V_HEIGHT);
			sb.end();
			
			//set camera
			cam.position.set(Game.V_WIDTH / 2, Game.V_HEIGHT / 2, 0);
			cam.update();
			
			switch (gsm.PLAY) {
			case 0:
				//draw start
				sb.setProjectionMatrix(cam.combined);
				start.render(sb);
				
				//draw bitbeat
				sb.setProjectionMatrix(cam.combined);
				bitbeat.render(sb);
				break;

			case 1:
				//draw bitbeat
				sb.setProjectionMatrix(cam.combined);
				bitbeat.render(sb);
				
				//draw button1
				sb.setProjectionMatrix(cam.combined);
				bt1.render(sb);
				
				//draw button2
				sb.setProjectionMatrix(cam.combined);
				bt2.render(sb);
				
				//draw button3
				sb.setProjectionMatrix(cam.combined);
				bt3.render(sb);
				
				//draw button4
				sb.setProjectionMatrix(cam.combined);
				bt4.render(sb);
				
				//draw pointer
				sb.setProjectionMatrix(cam.combined);
				pointer.render(sb);
				break;
			case 2:
				
				//draw button5
				sb.setProjectionMatrix(cam.combined);
				bt5.render(sb);
				
				//draw button6
				sb.setProjectionMatrix(cam.combined);
				bt6.render(sb);
				
				//draw button7
				sb.setProjectionMatrix(cam.combined);
				bt7.render(sb);
				
				//draw pointer
				sb.setProjectionMatrix(cam.combined);
				pointer2.render(sb);
				break;
			case 3:
				//draw button5
				sb.setProjectionMatrix(cam.combined);
				high.render(sb);
				num1.render(sb);
				num2.render(sb);
				num3.render(sb);
				num4.render(sb);
				num5.render(sb);
				break;
			case -1:
				track.render(sb);
				bTrack.render(sb);
				bTrack2.render(sb);
				bTrack3.render(sb);
				pointer2.render(sb);
				break;
			case -2:
				track.render(sb);
				bTrack4.render(sb);
				bTrack5.render(sb);
				bTrack6.render(sb);
				pointer2.render(sb);
				break;
			case -3:
				track.render(sb);
				bTrack7.render(sb);
				bTrack8.render(sb);
				bTrack9.render(sb);
				pointer2.render(sb);
				break;
			}
			
		} else if (gsm.PLAY > 5) {
			
			if (player.getPosition().x * PPM < Game.V_WIDTH / 4) {
				//camera follow player
				cam.position.set(
						Game.V_WIDTH /2, 
						Game.V_HEIGHT / 2,
						0
				);
			} else {
				//camera follow player
				cam.position.set(
						player.getPosition().x * PPM + Game.V_WIDTH / 4, 
						Game.V_HEIGHT / 2,
						0
				);
			}
			cam.update();
			
			//draw background
			sb.begin();
			for (int i = 0; i <= layer.getWidth() / (Game.V_WIDTH / PPM) ;) {
				for (int j = 0; j < 3; j++) {
					sb.draw(background, Game.V_WIDTH * i, 0, Game.V_WIDTH, Game.V_HEIGHT);
					i++;
					sb.draw(background2, Game.V_WIDTH * i, 0, Game.V_WIDTH, Game.V_HEIGHT);
					i++;
					sb.draw(background3, Game.V_WIDTH * i, 0, Game.V_WIDTH, Game.V_HEIGHT);
					i++;
				}
			}
			sb.end();
			
			//draw tiled map
			tmr.setView(cam);
			tmr.render();
			
			//draw Lives Title
			livesT.render(sb);
			
			//draw player
			sb.setProjectionMatrix(cam.combined);
			player.render(sb);
			
			//draw coins
			for (int i = 0; i < coins.size; i++) {
				coins.get(i).render(sb);
			}
			
			//draw can
			for (int i = 0; i < can.size; i++) {
				can.get(i).render(sb);
			}
			
			//draw diamond
			for (int i = 0; i < diamond.size; i++) {
				diamond.get(i).render(sb);
			}
			
			//draw heart
			heart.render(sb);
			heart2.render(sb);
			heart3.render(sb);
			
		}
		
		//draw box2d (object simulator)
		if(debug) {
			b2dr.render(world, b2dCam.combined);
			
		}
		
	}
	
	public void dispose() {}
	
	public void createPointer(int i) {
		if (i == 1) {
			BodyDef bdef = new BodyDef();
			FixtureDef fdef = new FixtureDef();
			PolygonShape shape = new PolygonShape();
			
			bdef.position.set(210 / PPM, 250 / PPM);
			bdef.type = BodyType.DynamicBody;
			Body body = world.createBody(bdef);
			
			shape.setAsBox(24 / PPM, 24 / PPM , new Vector2(0 , -100 / PPM), 0);
			fdef.shape = shape;
			body.createFixture(fdef).setUserData("pointer");
			pointer = new Pointer(body);
		} else if (i == 2) {
			BodyDef bdef = new BodyDef();
			FixtureDef fdef = new FixtureDef();
			PolygonShape shape = new PolygonShape();
			
			bdef.position.set(210 / PPM, 350 / PPM);
			bdef.type = BodyType.DynamicBody;
			Body body = world.createBody(bdef);
			
			shape.setAsBox(24 / PPM, 24 / PPM , new Vector2(0 , -100 / PPM), 0);
			fdef.shape = shape;
			body.createFixture(fdef).setUserData("pointer2");
			pointer2 = new Pointer2(body);
		} else if (i == 3) {
			BodyDef bdef = new BodyDef();
			FixtureDef fdef = new FixtureDef();
			PolygonShape shape = new PolygonShape();
			
			bdef.position.set(15 / PPM, 350 / PPM);
			bdef.type = BodyType.DynamicBody;
			Body body = world.createBody(bdef);
			
			shape.setAsBox(24 / PPM, 24 / PPM , new Vector2(0 , -100 / PPM), 0);
			fdef.shape = shape;
			body.createFixture(fdef).setUserData("pointer2");
			pointer2 = new Pointer2(body);
			
		}
		
	}
	public void createStart() {
		
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		
		bdef.position.set(320 / PPM, 180 / PPM);
		bdef.type = BodyType.DynamicBody;
		Body body = world.createBody(bdef);
			
		shape.setAsBox(216 / PPM, 10 / PPM , new Vector2(0 , -100 / PPM), 0);
		fdef.shape = shape;
		body.createFixture(fdef).setUserData("start");
		start = new Start(body);
		
	}
	
	public void createButton(int i) {
		
		switch (i) {
		case 1:
			BodyDef bdef = new BodyDef();
			FixtureDef fdef = new FixtureDef();
			PolygonShape shape = new PolygonShape();
			
			bdef.position.set(320 / PPM, 250 / PPM);
			bdef.type = BodyType.DynamicBody;
			Body body = world.createBody(bdef);
				
			shape.setAsBox(80 / PPM, 24 / PPM , new Vector2(0 , -100 / PPM), 0);
			fdef.shape = shape;
			body.createFixture(fdef).setUserData("button1");
			bt1 = new Button1(body);
			
			BodyDef bdef1 = new BodyDef();
			FixtureDef fdef1 = new FixtureDef();
			PolygonShape shape1 = new PolygonShape();
			
			bdef1.position.set(320 / PPM, 200 / PPM);
			bdef1.type = BodyType.DynamicBody;
			Body body1 = world.createBody(bdef1);
				
			shape1.setAsBox(80 / PPM, 24 / PPM , new Vector2(0 , -100 / PPM), 0);
			fdef1.shape = shape1;
			body1.createFixture(fdef1).setUserData("button2");
			bt2 = new Button2(body1);
			
			BodyDef bdef2 = new BodyDef();
			FixtureDef fdef2 = new FixtureDef();
			PolygonShape shape2 = new PolygonShape();
			
			bdef2.position.set(320 / PPM, 150 / PPM);
			bdef2.type = BodyType.DynamicBody;
			Body body2 = world.createBody(bdef2);
				
			shape2.setAsBox(80 / PPM, 24 / PPM , new Vector2(0 , -100 / PPM), 0);
			fdef2.shape = shape2;
			body2.createFixture(fdef2).setUserData("button3");
			bt3 = new Button3(body2);
			
			BodyDef bdef3 = new BodyDef();
			FixtureDef fdef3 = new FixtureDef();
			PolygonShape shape3 = new PolygonShape();
			
			bdef3.position.set(320 / PPM, 100 / PPM);
			bdef3.type = BodyType.DynamicBody;
			Body body3 = world.createBody(bdef3);
				
			shape3.setAsBox(80 / PPM, 24 / PPM , new Vector2(0 , -100 / PPM), 0);
			fdef3.shape = shape;
			body3.createFixture(fdef3).setUserData("button4");
			bt4 = new Button4(body3);
			break;

		case 2:
			BodyDef bdef4 = new BodyDef();
			FixtureDef fdef4 = new FixtureDef();
			PolygonShape shape4 = new PolygonShape();
			
			bdef4.position.set(320 / PPM, 350 / PPM);
			bdef4.type = BodyType.DynamicBody;
			Body body4 = world.createBody(bdef4);
				
			shape4.setAsBox(80 / PPM, 24 / PPM , new Vector2(0 , -100 / PPM), 0);
			fdef4.shape = shape4;
			body4.createFixture(fdef4).setUserData("button5");
			bt5 = new Button5(body4);
			
			BodyDef bdef5 = new BodyDef();
			FixtureDef fdef5 = new FixtureDef();
			PolygonShape shape5 = new PolygonShape();
			
			bdef5.position.set(320 / PPM, 250 / PPM);
			bdef5.type = BodyType.DynamicBody;
			Body body5 = world.createBody(bdef5);
				
			shape5.setAsBox(80 / PPM, 24 / PPM , new Vector2(0 , -100 / PPM), 0);
			fdef5.shape = shape5;
			body5.createFixture(fdef5).setUserData("button6");
			bt6 = new Button6(body5);
			
			BodyDef bdef6 = new BodyDef();
			FixtureDef fdef6 = new FixtureDef();
			PolygonShape shape6 = new PolygonShape();
			
			bdef6.position.set(320 / PPM, 150 / PPM);
			bdef6.type = BodyType.DynamicBody;
			Body body6 = world.createBody(bdef6);
				
			shape6.setAsBox(80 / PPM, 24 / PPM , new Vector2(0 , -100 / PPM), 0);
			fdef6.shape = shape6;
			body6.createFixture(fdef6).setUserData("button7");
			bt7 = new Button7(body6);
			break;
		case -1:
			BodyDef bdef8 = new BodyDef();
			FixtureDef fdef8 = new FixtureDef();
			PolygonShape shape8 = new PolygonShape();
			
			bdef8.position.set(320 / PPM, 350 / PPM);
			bdef8.type = BodyType.DynamicBody;
			Body body8 = world.createBody(bdef8);
				
			shape8.setAsBox(80 / PPM, 24 / PPM , new Vector2(0 , -100 / PPM), 0);
			fdef8.shape = shape8;
			body8.createFixture(fdef8).setUserData("et1");
			bTrack = new TrackEasyButton1(body8);
			
			BodyDef bdef9 = new BodyDef();
			FixtureDef fdef9 = new FixtureDef();
			PolygonShape shape9 = new PolygonShape();
			
			bdef9.position.set(320 / PPM, 250 / PPM);
			bdef9.type = BodyType.DynamicBody;
			Body body9 = world.createBody(bdef9);
				
			shape9.setAsBox(80 / PPM, 24 / PPM , new Vector2(0 , -100 / PPM), 0);
			fdef9.shape = shape9;
			body9.createFixture(fdef8).setUserData("et2");
			bTrack2 = new TrackEasyButton2(body9);
			
			BodyDef bdef10 = new BodyDef();
			FixtureDef fdef10 = new FixtureDef();
			PolygonShape shape10 = new PolygonShape();
			
			bdef10.position.set(320 / PPM, 150 / PPM);
			bdef10.type = BodyType.DynamicBody;
			Body body10 = world.createBody(bdef10);
				
			shape10.setAsBox(80 / PPM, 24 / PPM , new Vector2(0 , -100 / PPM), 0);
			fdef10.shape = shape10;
			body10.createFixture(fdef10).setUserData("et3");
			bTrack3 = new TrackEasyButton3(body10);
			break;
		case -2:
			BodyDef bdef14 = new BodyDef();
			FixtureDef fdef14 = new FixtureDef();
			PolygonShape shape14 = new PolygonShape();
			
			bdef14.position.set(320 / PPM, 350 / PPM);
			bdef14.type = BodyType.DynamicBody;
			Body body14 = world.createBody(bdef14);
				
			shape14.setAsBox(80 / PPM, 24 / PPM , new Vector2(0 , -100 / PPM), 0);
			fdef14.shape = shape14;
			body14.createFixture(fdef14).setUserData("nt1");
			bTrack4 = new TrackNormalButton1(body14);
			
			BodyDef bdef15 = new BodyDef();
			FixtureDef fdef15 = new FixtureDef();
			PolygonShape shape15 = new PolygonShape();
			
			bdef15.position.set(320 / PPM, 250 / PPM);
			bdef15.type = BodyType.DynamicBody;
			Body body15 = world.createBody(bdef15);
				
			shape15.setAsBox(80 / PPM, 24 / PPM , new Vector2(0 , -100 / PPM), 0);
			fdef15.shape = shape15;
			body15.createFixture(fdef15).setUserData("nt2");
			bTrack5 = new TrackNormalButton2(body15);
			
			BodyDef bdef16 = new BodyDef();
			FixtureDef fdef16 = new FixtureDef();
			PolygonShape shape16 = new PolygonShape();
			
			bdef16.position.set(320 / PPM, 150 / PPM);
			bdef16.type = BodyType.DynamicBody;
			Body body16 = world.createBody(bdef16);
				
			shape16.setAsBox(80 / PPM, 24 / PPM , new Vector2(0 , -100 / PPM), 0);
			fdef16.shape = shape16;
			body16.createFixture(fdef16).setUserData("nt3");
			bTrack6 = new TrackNormalButton3(body16);
			break;
		case -3:
			BodyDef bdef11 = new BodyDef();
			FixtureDef fdef11 = new FixtureDef();
			PolygonShape shape11 = new PolygonShape();
			
			bdef11.position.set(320 / PPM, 350 / PPM);
			bdef11.type = BodyType.DynamicBody;
			Body body11 = world.createBody(bdef11);
				
			shape11.setAsBox(80 / PPM, 24 / PPM , new Vector2(0 , -100 / PPM), 0);
			fdef11.shape = shape11;
			body11.createFixture(fdef11).setUserData("ht1");
			bTrack7 = new TrackHardButton1(body11);
			
			BodyDef bdef12 = new BodyDef();
			FixtureDef fdef12 = new FixtureDef();
			PolygonShape shape12 = new PolygonShape();
			
			bdef12.position.set(320 / PPM, 250 / PPM);
			bdef12.type = BodyType.DynamicBody;
			Body body12 = world.createBody(bdef12);
				
			shape12.setAsBox(80 / PPM, 24 / PPM , new Vector2(0 , -100 / PPM), 0);
			fdef12.shape = shape12;
			body12.createFixture(fdef12).setUserData("ht2");
			bTrack8 = new TrackHardButton2(body12);
			
			BodyDef bdef13 = new BodyDef();
			FixtureDef fdef13 = new FixtureDef();
			PolygonShape shape13 = new PolygonShape();
			
			bdef13.position.set(320 / PPM, 150 / PPM);
			bdef13.type = BodyType.DynamicBody;
			Body body13 = world.createBody(bdef13);
				
			shape13.setAsBox(80 / PPM, 24 / PPM , new Vector2(0 , -100 / PPM), 0);
			fdef13.shape = shape13;
			body13.createFixture(fdef13).setUserData("ht3");
			bTrack9 = new TrackHardButton3(body13);
			break;
		}
		
	}
	
	public void createBitbeat() {
		
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		
		bdef.position.set(320 / PPM, 380 / PPM);
		bdef.type = BodyType.DynamicBody;
		Body body = world.createBody(bdef);
			
		shape.setAsBox(430/ PPM, 77 / PPM , new Vector2(0 , -100 / PPM), 0);
		fdef.shape = shape;
		body.createFixture(fdef).setUserData("bitbeat");
		bitbeat = new Bitbeat(body);
		
	}
	
	public void createPlayer() {
		
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		
		// create player
		bdef.position.set(0 / PPM, 300 / PPM);

		//Velocity -- walk speed
		bdef.linearVelocity.set(1.7f, 0);
		bdef.type = BodyType.DynamicBody;
		Body body = world.createBody(bdef);
			
		shape.setAsBox(12 / PPM, 16 / PPM);
		fdef.shape = shape;
		fdef.filter.categoryBits = B2DVars.BIT_BODY;
		fdef.filter.maskBits = B2DVars.BIT_LINE | B2DVars.BIT_COIN | B2DVars.BIT_CAN | B2DVars.BIT_DIAMOND;
		body.createFixture(fdef).setUserData("player");
		
		//create foot
		shape.setAsBox(10 / PPM, 0 / PPM, new Vector2(0 , -13 / PPM), 0);
		fdef.shape = shape;
		fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
		fdef.filter.maskBits = B2DVars.BIT_LINE | B2DVars.BIT_COIN | B2DVars.BIT_CAN | B2DVars.BIT_DIAMOND;
		body.createFixture(fdef).setUserData("stand");

		//create foot sensor
		shape.setAsBox(8 / PPM, 2 / PPM, new Vector2(0 , -13 / PPM), 0);
		fdef.shape = shape;
		fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
		fdef.filter.maskBits = B2DVars.BIT_LINE;
		fdef.isSensor = true;
		body.createFixture(fdef).setUserData("foot");
		
		//create player
		player = new Player(body, gsm.PLAY);
		
	}
	
	public void createTiles() {
		
		//load tile map
		tmr = new OrthogonalTiledMapRenderer(tileMap);
		
		tileSize = (int) tileMap.getProperties().get("tilewidth");
		
		layer = (TiledMapTileLayer) tileMap.getLayers().get("line");
		createLayer(layer, B2DVars.BIT_LINE);
	}
	
	private void createLayer(TiledMapTileLayer layer, short bits) {
		
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		
		//go through all the cells in the layer
		//y
		for (int row = 0; row < layer.getHeight(); row++) {
			//x
			for (int col = 0; col < layer.getWidth( ); col++) {
				
				// get cell
				Cell cell = layer.getCell(col, row);
				
				//check if cell exists
				if(cell == null) continue;
				if (cell.getTile() == null) continue;
				
				//create body & fixture from cell
				bdef.type = BodyType.StaticBody;
				bdef.position.set(
						(col + 0.7f) * tileSize / PPM,
						(row) * 16 / PPM
				);
				
				ChainShape cs = new ChainShape();
				Vector2[] v = new Vector2[2];
				v[0] = new Vector2(tileSize / 2 / PPM, tileSize / 2 / PPM);
				v[1] = new Vector2(-tileSize / 2 / PPM, tileSize / 2 / PPM);
				cs.createChain(v);
				fdef.friction = 0;
				fdef.shape = cs;
				fdef.filter.categoryBits = bits;
				fdef.filter.maskBits = B2DVars.BIT_PLAYER;
				fdef.isSensor = false;
				world.createBody(bdef).createFixture(fdef);
			}
		}
	}
	
	private void createCoins() {
		
		coins = new Array<Coin>();
		
		MapLayer layer = tileMap.getLayers().get("coins");
		
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		
		for(MapObject mo : layer.getObjects()) {
			
			bdef.type = BodyType.StaticBody;
			
			float x = (float) mo.getProperties().get("x") / PPM;
			float y = (float) mo.getProperties().get("y") / PPM;
			
			bdef.position.set(x, y);
			
			CircleShape cshape = new CircleShape();
			cshape.setRadius(8 / PPM);
			
			fdef.shape = cshape;
			fdef.isSensor = true;
			fdef.filter.categoryBits = B2DVars.BIT_COIN;
			fdef.filter.maskBits = B2DVars.BIT_BODY;
			
			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("coin");
			
			Coin c = new Coin(body);
			coins.add(c);
			
			body.setUserData(c);
			
		}
		
	}
	
	private void createCan() {
		
		can = new Array<Can>();
		
		MapLayer layer = tileMap.getLayers().get("can");
		
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		
		for(MapObject mo : layer.getObjects()) {
			
			bdef.type = BodyType.StaticBody;
			
			float x = (float) mo.getProperties().get("x") / PPM;
			float y = (float) mo.getProperties().get("y") / PPM;
			
			bdef.position.set(x, y);
			
			PolygonShape shape = new PolygonShape();
			shape.setAsBox(16 / PPM, 16 / PPM);
			
			fdef.shape =  shape;
			fdef.isSensor = true;
			fdef.filter.categoryBits = B2DVars.BIT_CAN;
			fdef.filter.maskBits = B2DVars.BIT_BODY;
			
			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("can");
			
			Can ca = new Can(body);
			can.add(ca);
			
			body.setUserData(ca);
			
		}
		
	}
	
	public void createHighScore() {
		
		chars = ("" + highscore).toCharArray();
		int j = 0;
		
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		
		bdef.position.set(320 / PPM, 350 / PPM);
		bdef.type = BodyType.DynamicBody;
		Body body = world.createBody(bdef);
			
		shape.setAsBox(80 / PPM, 24 / PPM , new Vector2(0 , -100 / PPM), 0);
		fdef.shape = shape;
		body.createFixture(fdef).setUserData("high score");
		high = new High(body);
		
		//first
		BodyDef bdef1 = new BodyDef();
		FixtureDef fdef1 = new FixtureDef();
		PolygonShape shape1 = new PolygonShape();
		
		bdef1.position.set(192 / PPM, 250 / PPM);
		bdef1.type = BodyType.DynamicBody;
		Body body1 = world.createBody(bdef1);
			
		shape1.setAsBox(32 / PPM, 64 / PPM , new Vector2(0 , -100 / PPM), 0);
		fdef1.shape = shape1;
		body1.createFixture(fdef1).setUserData("num1");
		if (chars.length < 5) {
			num1 = new Number(body1, '0');
		} else {
			num1 = new Number(body1, chars[j]);
			j++;
		}
		
		//second
		BodyDef bdef2 = new BodyDef();
		FixtureDef fdef2 = new FixtureDef();
		PolygonShape shape2 = new PolygonShape();
		
		bdef2.position.set(256 / PPM, 250 / PPM);
		bdef2.type = BodyType.DynamicBody;
		Body body2 = world.createBody(bdef2);
			
		shape2.setAsBox(32 / PPM, 64 / PPM , new Vector2(0 , -100 / PPM), 0);
		fdef2.shape = shape2;
		body2.createFixture(fdef2).setUserData("num2");
		if (chars.length < 4) {
			num2 = new Number(body2,'0');
		} else {
			num2 = new Number(body2, chars[j]);
			j++;
		}
		
		//third
		BodyDef bdef3 = new BodyDef();
		FixtureDef fdef3 = new FixtureDef();
		PolygonShape shape3 = new PolygonShape();
		
		bdef3.position.set(320 / PPM, 250 / PPM);
		bdef3.type = BodyType.DynamicBody;
		Body body3 = world.createBody(bdef3);
			
		shape3.setAsBox(32 / PPM, 64 / PPM , new Vector2(0 , -100 / PPM), 0);
		fdef3.shape = shape3;
		body3.createFixture(fdef3).setUserData("num3");
		if (chars.length < 3) {
			num3 = new Number(body3,'0');
		} else {
			num3 = new Number(body3, chars[j]);
			j++;
		}
		
		//forth
		BodyDef bdef4 = new BodyDef();
		FixtureDef fdef4 = new FixtureDef();
		PolygonShape shape4 = new PolygonShape();
		
		bdef4.position.set(384 / PPM, 250 / PPM);
		bdef4.type = BodyType.DynamicBody;
		Body body4 = world.createBody(bdef4);
			
		shape4.setAsBox(32 / PPM, 64 / PPM , new Vector2(0 , -100 / PPM), 0);
		fdef4.shape = shape4;
		body4.createFixture(fdef4).setUserData("num4");
		if (chars.length < 2) {
			num4 = new Number(body4,'0');
		} else {
			num4 = new Number(body4, chars[j]);
			j++;
		}
		
		//fifth
		BodyDef bdef5 = new BodyDef();
		FixtureDef fdef5 = new FixtureDef();
		PolygonShape shape5 = new PolygonShape();
		
		bdef5.position.set(448 / PPM, 250 / PPM);
		bdef5.type = BodyType.DynamicBody;
		Body body5 = world.createBody(bdef5);
			
		shape5.setAsBox(32 / PPM, 64 / PPM , new Vector2(0 , -100 / PPM), 0);
		fdef5.shape = shape5;
		body5.createFixture(fdef5).setUserData("num5");
		num5 = new Number(body5, chars[j]);
	}
	public void createLives() {
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		
		bdef.position.set(58 / PPM, 460 / PPM);
		bdef.type = BodyType.KinematicBody;
		Body body = world.createBody(bdef);
			
		shape.setAsBox(16 / PPM, 16 / PPM , new Vector2(0 , -100 / PPM), 0);
		fdef.shape = shape;
		body.createFixture(fdef).setUserData("heart");
		livesT = new Lives(body);
	}
	public void createHeart() {
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		
		bdef.position.set(150 / PPM, 460 / PPM);
		bdef.type = BodyType.KinematicBody;
		Body body = world.createBody(bdef);
			
		shape.setAsBox(16 / PPM, 16 / PPM , new Vector2(0 , -100 / PPM), 0);
		fdef.shape = shape;
		body.createFixture(fdef).setUserData("heart");
		heart = new Heart(body);
		
		Body body2 = world.createBody(bdef);
		body2.createFixture(fdef).setUserData("heart");
		heart2 = new Heart(body2);
		
		Body body3 = world.createBody(bdef);
		body3.createFixture(fdef).setUserData("heart");
		heart3 = new Heart(body3);
	}
	public void createTrack() {
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		
		bdef.position.set(320 / PPM, 400 / PPM);
		bdef.type = BodyType.KinematicBody;
		Body body = world.createBody(bdef);
			
		shape.setAsBox(89 / PPM, 21 / PPM);
		fdef.shape = shape;
		body.createFixture(fdef).setUserData("track");
		track = new Track(body);
	}
	public void createDiamond() {
		
		diamond = new Array<Diamond>();
		
		MapLayer layer = tileMap.getLayers().get("diamond");
		
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		
		for(MapObject mo : layer.getObjects()) {
			
			bdef.type = BodyType.StaticBody;
			
			float x = (float) mo.getProperties().get("x") / PPM;
			float y = (float) mo.getProperties().get("y") / PPM;
			
			bdef.position.set(x, y);
			
			PolygonShape shape = new PolygonShape();
			shape.setAsBox(16 / PPM, 16 / PPM);
			
			fdef.shape =  shape;
			fdef.isSensor = true;
			fdef.filter.categoryBits = B2DVars.BIT_DIAMOND;
			fdef.filter.maskBits = B2DVars.BIT_BODY;
			
			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("diamond");
			
			Diamond dm = new Diamond(body);
			diamond.add(dm);
			
			body.setUserData(dm);
			
		}
		
	}

	@Override
	public void handleInput() {
		
	}
	
}
