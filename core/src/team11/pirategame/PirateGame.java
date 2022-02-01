package team11.pirategame;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class PirateGame extends ApplicationAdapter {
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private SpriteBatch hudBatch;
	private BitmapFont font;
	private ShapeRenderer shapeRenderer;
	
	private Stage buttonStage;
	private ImageButton restartBtnDead, restartBtnWin, restartBtnPause;
	
	private Tile[][] map;
	private int mapWidth = 60, mapHeight = 34; // Default values overwritten when loading map file.
	private float gameTime = 0;
	
	private int gold, points;
	
	private Random rand = new Random();
	
	private State state = State.RUNNING;
	
	private Ship player;
	// These values are in pixels/second and are to be used as default values when making a ship.
	private float shipAccel = 100;
	private float shipNaturalDecel = 50;
	private float shipBrakeDecel = 250;
	private float shipSpeedCap = 250;
	private float shipMinusSpeedCap = -50;
	private float shipRotSpeed = 120;
	
	private float cannonBallTimeout = 2f; // Time in seconds for cannonballs to dissapear
	
	private Texture[] playerTextures;
	private Texture[] collegeTextures;
	private Texture cannonBallTexture;
	private TextureRegion cannonBallTexR;
	private Texture[] hudTextures;
	private TextureRegion noticeBoard, menuBoard, tutorialPopUp, victoryScreen, deathScreen;
	private Drawable restartBtnRed, restartBtnBlue, restartBtnYel;

	private boolean tutorialShowing = false;
	private int tutorialInd;
	private String tutorial[] = new String[] {"PLAYER MOVEMENT\n"
			+ "Welcome to the game, here are the simple controls to move your ship!\n"
			+ "W moves your ship forwards\n"
			+ "A and D turn your ship left and right respectively while moving\n"
			+ "S brakes your ship and will make you reverse.\n"
			+ "SPACE fires the cannons on the side of your ship\n\n"
			+ "Your healthbar is located beneath your ship. Be careful if it runs out \nthe game will be over!",
			
			"OBJECTIVES\n"
			+ "You will notice in the top right corner of your screen there is an \nobjectives board.\n"
			+ "This board contains some objectvies for you to complete while playing.\n"
			+ "If you manage to beat the main objectives, you will win the game!\n"
			+ "Completing any objective will reward you with gold.\n"
			+ "Throughout the game, more objectives may be added so keep an \neye out!",
			
			"ENEMY COLLEGE!\n"
			+ "You've just come into range of an enemy college!\n"
			+ "Enemy colleges will fire at you with their cannons while you're in range.\n"
			+ "To take them down, fire your cannons so they hit the college and \nreduce their health to 0.\n"
			+ "When destroyed, colleges are reduced to piles of rubble and you will \ngain gold and points.",
			
			"ALLIED COLLEGE\n"
			+ "You've just approached your allied college!\n"
			+ "This college won't shoot at you and will fight your enemies with you.\n",
			
			"ENEMY COLLEGES\n"
			+ "As you explore the map you will come across some enemy colleges that \nare more powerful.\n"
			+ "While you gain greater rewards for beating these colleges, they are \nmuch harder to defeat!\n"
			+ "It is reccomended that you defeat easier colleges first in order to \nbecome more powerful to take on these harder colleges.",
			
			"CONGRATULATIONS\n"
			+ "You just beat your first enemy college!\n"
			+ "As a reward your ship's capabilities have been increased.\n"
			+ "Feel free to try out what your ship can do now!"};
	private ArrayList<Integer> shownTutorials = new ArrayList<Integer>();
	
	private College[] colleges;
	private ArrayList<Objective> objectives = new ArrayList<Objective>();
	private ArrayList<Cannonball> cannonballs = new ArrayList<Cannonball>();
	
	/**
	 * Method ran when the game is launched, loads in textures, drawing tools, camera, font and buttons
	 * */
	@Override
	public void create () {
		// Create essentials for the game
		batch = new SpriteBatch();
		hudBatch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.app.getGraphics().getWidth(), Gdx.app.getGraphics().getHeight());
		font = new BitmapFont();
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setAutoShapeType(true);
		// Load in all textures. Create texture regions too where needed
		playerTextures = new Texture[]{new Texture(Gdx.files.internal("ships/playership1.png")),
				new Texture(Gdx.files.internal("ships/playership2.png")),
				new Texture("ships/playership3.png")};
		collegeTextures = new Texture[] {new Texture(Gdx.files.internal("colleges/CollegeRubble.png")), 
				new Texture(Gdx.files.internal("colleges/HomeCollege.png")), 
				new Texture(Gdx.files.internal("colleges/Lvl1College.png")),
				new Texture(Gdx.files.internal("colleges/Lvl2College.png")),
				new Texture(Gdx.files.internal("colleges/Lvl3College.png")),
				new Texture(Gdx.files.internal("colleges/Lvl4College.png"))};
		cannonBallTexture = new Texture(Gdx.files.internal("CannonBall.png"));
		cannonBallTexR = new TextureRegion(cannonBallTexture);
		hudTextures = new Texture[8];
		hudTextures[0] = new Texture(Gdx.files.internal("ObjectivesBoard.png"));
		hudTextures[1] = new Texture(Gdx.files.internal("MenuBoard.png"));
		hudTextures[2] = new Texture(Gdx.files.internal("TutorialPopUp.png"));
		hudTextures[3] = new Texture(Gdx.files.internal("VictoryScreen.png"));
		hudTextures[4] = new Texture(Gdx.files.internal("DeathScreen.png"));
		hudTextures[5] = new Texture(Gdx.files.internal("RestartRedBtn.png"));
		hudTextures[6] = new Texture(Gdx.files.internal("RestartBlueBtn.png"));
		hudTextures[7] = new Texture(Gdx.files.internal("RestartYelBtn.png"));
		noticeBoard = new TextureRegion(hudTextures[0]);
		menuBoard = new TextureRegion(hudTextures[1]);
		tutorialPopUp = new TextureRegion(hudTextures[2]);
		victoryScreen = new TextureRegion(hudTextures[3]);
		deathScreen = new TextureRegion(hudTextures[4]);
		// Create restart buttons
		restartBtnRed = new TextureRegionDrawable(new TextureRegion(hudTextures[5]));
		restartBtnBlue = new TextureRegionDrawable(new TextureRegion(hudTextures[6]));
		restartBtnYel = new TextureRegionDrawable(new TextureRegion(hudTextures[7]));
		restartBtnWin = new ImageButton(restartBtnBlue);
		restartBtnWin.setPosition(Gdx.app.getGraphics().getWidth()/2 - 88, Gdx.app.getGraphics().getHeight()/2 - 18 - 200);
		restartBtnWin.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				restart();
			}
		});
		restartBtnPause = new ImageButton(restartBtnYel);
		restartBtnPause.setPosition(Gdx.app.getGraphics().getWidth()/2 - 88, Gdx.app.getGraphics().getHeight()/2 - 18);
		restartBtnPause.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				restart();
			}
		});
		restartBtnDead = new ImageButton(restartBtnRed);
		restartBtnDead.setPosition(Gdx.app.getGraphics().getWidth()/2 - 88, Gdx.app.getGraphics().getHeight()/2 - 18 - 300);
		restartBtnDead.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				restart();
			}
		});
		buttonStage = new Stage(new ScreenViewport());
		buttonStage.addActor(restartBtnWin);
		buttonStage.addActor(restartBtnPause);
		buttonStage.addActor(restartBtnDead);
		Gdx.input.setInputProcessor(buttonStage);
		restart();
	}

	/**
	 * Main game loop. LibGDX runs this every frame. All assets are drawn to the screen and movement/physics are updated for game entities.
	 * */
	@Override
	public void render () {
		if(state != State.DEATH) {
			// Move between running and pause menu screens when escape is pressed
			if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) && (state == State.RUNNING || state == State.PAUSED)) {
				if(tutorialShowing) { // If a tutorial is on screen, instead remove it / show next tutorial
					if(tutorialInd == 0) {
						showTutorial(1);
					} else {
						tutorialShowing = false;
					}
				} else {
					if(state == State.RUNNING) {
						state = State.PAUSED;
						restartBtnPause.setDisabled(false); // Make sure the restart button is only active when on the screen
					} else {
						state = State.RUNNING;
						restartBtnPause.setDisabled(true);
					}
				}
			}
			// Start drawing graphics to the screen
			ScreenUtils.clear(0.0157f, 0.6118f, 0.0157f, 1);
			camera.update();
			batch.setProjectionMatrix(camera.combined);
			shapeRenderer.setProjectionMatrix(camera.combined);
			batch.begin();
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(Color.RED);
			drawMap();
			batch.draw(player.getTextureRegion(),(float) player.getX(),(float) player.getY(), 96/2, 128/2, 96, 128, 1, 1, player.getRotation());   // Drawing the player
			drawCannonballs();
			batch.end();
			// Player healthbar
			shapeRenderer.setColor(Color.GREEN);
			float percentHealthPixels =  (float) (96 * (player.getHealth() / player.getMaxHealth()));
	        shapeRenderer.rect((float) player.getX(), (float) player.getY() - 15, percentHealthPixels, 8);
	        shapeRenderer.setColor(Color.RED);
	        shapeRenderer.rect((float) player.getX() + percentHealthPixels, (float) player.getY() - 15, 96 - percentHealthPixels, 8);
	        // College healthbars
	        for(College c : colleges) {
	        	if(!c.isDefeated()) {
		        	shapeRenderer.setColor(Color.GREEN);
		    		percentHealthPixels =  (float) (128 * (c.getHealth() / c.getMaxHealth()));
		            shapeRenderer.rect((float) c.getX() * 32, (float) (c.getY() * 32) - 15, percentHealthPixels, 8);
		            shapeRenderer.setColor(Color.RED);
		            shapeRenderer.rect((float) (c.getX() * 32) + percentHealthPixels, (float) (c.getY() * 32) - 15, 128 - percentHealthPixels, 8);
	        	}
	        }
			//shapeRenderer.setColor(Color.BROWN);
	        //shapeRenderer.polygon(player.getPoly().getTransformedVertices()); // These lines will display player hitbox
			shapeRenderer.end();
			hudBatch.begin();
			hudBatch.draw(noticeBoard, 1920-(128*3), 1080-(96*3), 0, 0, 128, 96, 3, 3, 0); // Draw the objective board
			int offsetY = 0;
			// Write the objectives onto the objective board
			for(Objective o : objectives) {
				if(o.isActive()) {
					String objString = "";
					if(o.isCompleted()) {
						objString += "COMPLETED: ";
						font.setColor(Color.GREEN);
					} else if(o.isMainObjective()){
						objString += "MAIN: ";
						font.setColor(Color.GOLD);
					} else {
						font.setColor(Color.WHITE);
					}
					font.draw(hudBatch, objString + o.getDescription(), 1920-360, 1035 - offsetY);
					offsetY += 25;
				}
			}
			font.setColor(Color.CYAN);
			font.draw(hudBatch, "Gold: " + gold + " \nPoints: " + points, 1820, 855);
			// If a tutorial should be showing, draw the box and the text within it
			if(tutorialShowing) {
				float xLoc = Gdx.app.getGraphics().getWidth()/4 - 128*4/2;
				float yLoc = Gdx.app.getGraphics().getHeight()/2 - 96*4/2;
				hudBatch.draw(tutorialPopUp, xLoc, yLoc, 0, 0, 128, 96, 4, 4, 0);
				font.setColor(Color.BLACK);
				font.draw(hudBatch, tutorial[tutorialInd],xLoc + 40, yLoc + 300);
				font.draw(hudBatch, "Press ESC to close.", xLoc + 190, yLoc + 35);
			}
			if(state == State.RUNNING) {
				// If the game isn't paused increment gameTime and do player movement
				gameTime += Gdx.graphics.getDeltaTime();
		        playerMovement();
		        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
		        	Cannonball[] balls = player.fire(gameTime);
		        	if(balls != null) {
			        	for(Cannonball c : balls) {
			        		cannonballs.add(c);
			        	}
		        	}
		        }
			} else if(state == State.PAUSED) {
				// Draw pause menu
				hudBatch.draw(menuBoard, Gdx.app.getGraphics().getWidth()/2 - 128*3/2, Gdx.app.getGraphics().getHeight()/2 - 96*3/2, 0, 0, 128, 96, 3, 3, 0);
				restartBtnPause.draw(hudBatch, 100);
			} else if(state == State.WIN) {
				// Draw victory screen over the game
				hudBatch.draw(victoryScreen, 0, 0, 0, 0, 192, 108, 10, 10, 0);
				restartBtnWin.draw(hudBatch, 100);
			}
			cameraMovement();
			hudBatch.end();
		} else {
			// If the game is lost draw the end screen only.
			ScreenUtils.clear(0.1f, 0f, 0f, 1);
			hudBatch.begin();
			hudBatch.draw(deathScreen, 0, 0, 0, 0, 192, 108, 10, 10, 0);
			restartBtnDead.draw(hudBatch, 100);
			hudBatch.end();
		}
	}
	
	/**
	 * Loads the map from the file map.txt located in the assets folder
	 * */
	private void initMap() {
		FileHandle file = Gdx.files.internal("map.txt"); // Load the map file
		String[] in = file.readString().split("\\r?\\n"); // Split the map file by every line
		String[] mapSize = in[0].split(","); // Split the first line in two and take the integer values to represent the size of the map
		mapWidth = Integer.parseInt(mapSize[0]);
		mapHeight = Integer.parseInt(mapSize[1]);
		map = new Tile[mapWidth][mapHeight];  // Initialise the map with the correct size
		for (int y=0;y<mapHeight;y++) {
			 String[] line = in[mapHeight-y].split(" ");
			 for(int x=0;x<mapWidth;x++) {
				 TileType t = TileType.getTileType(line[x]); 
				 map[x][y] = new Tile(x, y, t); // Create a tile in the corresponding location based off the id within the file
			 }
		}
		int plunderAmount = 5; // How much random plunder to spawn
		while(plunderAmount > 0) {
			// Choose a random tile and if it's ocean change it to plunder and decrement the plunder to spawn
			int x = rand.nextInt(mapWidth);
			int y = rand.nextInt(mapHeight);
			if(map[x][y].getType().equals(TileType.Ocean)) {
				map[x][y].setType(TileType.Plunder);
				plunderAmount--;
			}
		}
		/*CREATES EMPTY MAP WITH LAND BORDER:
		 * 
		 * for(int x = 0; x < mapWidth; x++) {
			for(int y = 0; y < mapHeight; y++) {
				TileType t = TileType.Ocean;
				if(x == 0 || y == 0 || x == mapWidth - 1 || y == mapHeight -1) t = TileType.Land;
				map[x][y] = new Tile(x, y, t);
			}
		}*/
	}
	
	/**
	 * Loads colleges into the colleges[] array
	 * */
	private void loadColleges() {
		colleges = new College[5];
		// Player college
		colleges[0] = new College(0, collegeTextures[1], collegeTextures[0], 27, 1, 500, 20,
				new double[] {}, 0.5, 350, 512, 180, true);
		// Enemy college Lv1
		colleges[1] = new College(1, collegeTextures[2], collegeTextures[0], 49, 39, 500, 20, 
				new double[] {24, 0, 104, 0}, 0.5, 350, 512, 0, false);
		// Allied college Lv2
		colleges[2] = new College(2, collegeTextures[3], collegeTextures[0], 88, 15, 1000, 20, 
				new double[] {24, 128, 104, 128}, 0.5, 350, 512, 180, true);
		// Enemy college Lv3
		colleges[3] = new College(3, collegeTextures[4], collegeTextures[0], 88, 92, 1000, 20, 
				new double[] {14, 0, 30, 0, 98, 0, 114, 0}, 0.5, 350, 700, 0, false);
		// Enemy college Lv4
		colleges[4] = new College(4, collegeTextures[5], collegeTextures[0], 21, 64, 2000, 14, 
				new double[] {14, 112, 30, 112, 40, 112, 88, 112, 98, 112, 114, 112}, 0.75, 350, 800, 180, false);
	}
	
	/**
	 * Creates objectives to put in the objectives ArrayList
	 * */
	private void loadObjectives() {
		objectives.add(new Objective("main1", "Destroy the level 4 college", true, true, 1000));
		objectives.add(new Objective("visitAlly", "Visit your allied college", false, true, 100));
		objectives.add(new Objective("killCollege", "Destroy any enemy college", false, true, 250));
	}
	
	/**
	 * Draws map tiles and colleges onto the screen. Also fires cannonballs from the colleges if the player is close enough.
	 * */
	private void drawMap() {
		for(int x = 0; x < mapWidth; x++) {
			for(int y = 0; y < mapHeight; y++) {
				map[x][y].getSprite().draw(batch); // Draw the tile
				//if(map[x][y].getType().hasCollision()) shapeRenderer.polygon(map[x][y].getHitbox().getTransformedVertices()); //Line to show hitboxes, very buggy
			}
		}
		for(College c : colleges) {
			batch.draw(c.getImg(), (float) c.getX()*32, (float) c.getY()*32, 128/2, 128/2, 128, 128, 1, 1, c.getRotation()); // Draw in the college
			if(state == State.RUNNING) {
				// Calculate the distance from the centre of the college to the player
				double playerDist = Math.sqrt(Math.pow(player.getCenterX() - (c.getX()*32 + 64), 2) + Math.pow(player.getCenterY() - (c.getY() * 32 + 64), 2));
				if(!c.isPlayerAlly() &&  playerDist <= c.getRange()) {
					showTutorial(2); // Show the enemy college tutorial if not shown
					Cannonball[] balls = c.fire(player.getCenterX(), player.getCenterY(), gameTime); // Gets two cannonballs that this college can fire
					if(balls != null) { // If the college is on cooldown from shooting, null is returned
			        	for(Cannonball ball : balls) {
			        		cannonballs.add(ball); // Add the cannonballs to the game's list
			        	}
		        	}
				} else if(c.isPlayerAlly() && c.getId() > 0) {
					if(playerDist <= c.getRange()) {
						// Handles showing the 'Allied College' tutorial and side objective
						showTutorial(3);
						if(playerDist <= 160) {
							addGold(getObjective("visitAlly").complete());
						}
					}
				}
			}
		}
	}
	
	/**
	 * Draws cannonballs to the screen and does movement/collision for cannonballs.
	 * Removes cannonballs when they time out.
	 * */
	private void drawCannonballs() {
        shapeRenderer.setColor(Color.GRAY);
        ArrayList<Cannonball> toRemove = new ArrayList<Cannonball>();
        for(Cannonball c : cannonballs) {
        	if(state == State.RUNNING) {
        		// Move the cannonballs for this frame
	        	c.setX(c.getX() + c.getSpeed() * Math.sin(Math.toRadians(-c.getDirection())) * Gdx.graphics.getDeltaTime());
				c.setY(c.getY() + c.getSpeed() * Math.cos(Math.toRadians(-c.getDirection())) * Gdx.graphics.getDeltaTime());
				// Detect whether the tile at the centre of the cannonball has collision
				Tile t = map[(int) Math.floor(c.getX()/32)][(int) Math.floor(c.getY()/32)];
				if(t.getType().hasCollision() && t.getHitbox().contains((float) c.getX(), (float) c.getY())) {
					toRemove.add(c);	// If it does, kill the cannonball
				}
				for(College col : colleges) {
					if(!col.isDefeated() && col.getHitbox().contains((float) c.getX(), (float) c.getY())) {	// Check each college to check whether the cannonball is colliding with them
						if(col.getId() != c.getCollegeId() && colleges[c.getCollegeId()].isPlayerAlly() != col.isPlayerAlly()) { // Check if the cannonball wasn't fired from the same team as the cannonball
							col.damage(c.getDamage());
							if(col.getHealth() <= 0 && c.getCollegeId() == 0) { // Handles if the player killed the college
								addPoints(100);
								addGold(50);
								// Upgrade player ship
								player.setFireRate(player.getFireRate() * 2);
								player.setMaxHealth(player.getMaxHealth()*2);
								player.setHealth(player.getMaxHealth());
								player.setDamage(player.getDamage()*2);
								player.setSpeedCap(player.getSpeedCap() + 100);
								if(!getObjective("killCollege").isCompleted()) {	// Complete the objective if uncompleted and show tutorial
									addGold(getObjective("killCollege").complete());
									showTutorial(5);
								}
								if(col.getId() == 4 && !getObjective("main1").isCompleted()) {	// If killed the lvl4 enemy college, complete this objective and win the game
									addGold(getObjective("main1").complete());
									playerWin();
								}
							}
							toRemove.add(c);
						}
					}
				}
				if(player.getHitbox().contains((float) c.getX(), (float) c.getY())) {	// Check if the cannonball has hit the player ship
					if(c.getCollegeId() != 0 && !colleges[c.getCollegeId()].isPlayerAlly()) {	// If it has and it was fired by an enemy, damage the player and remove the cannonball
						player.damage(c.getDamage());
						toRemove.add(c);
					}
				}
        	}
        	batch.draw(cannonBallTexR, (float) c.getX() - 8, (float) c.getY() - 8);	// Draw the cannonball
        	if(gameTime > c.getCreationTime() + cannonBallTimeout) {
        		toRemove.add(c);
        	}
        }
        for(Cannonball c : toRemove) {
        	cannonballs.remove(c);	// Remove any cannonballs that collided with anything
        }
	}
	
	/**
	 * Handles moving the player's ship based on player input and collision with map objects.
	 * */
	private void playerMovement() {
		if(player.getHealth() <= 0) {	// Lose the game if the player has no health left
			playerDeath();
		}
		if(player.getY() > 32*60) {	// If the player is above a certain point on the map, show the tutorial about stronger enemy colleges
			showTutorial(4);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W)) {	// Handle player forward acceleration when pressing W
			if(player.getSpeed() < player.getSpeedCap()) {
				player.setSpeed(player.getSpeed() + player.getAccel() * Gdx.graphics.getDeltaTime());
				if(player.getSpeed() > player.getSpeedCap()) {
					player.setSpeed(player.getSpeedCap());
				}
			}
		} else if(Gdx.input.isKeyPressed(Input.Keys.S)) { // Handle player braking when pressing S
			player.setSpeed(player.getSpeed() - player.getBrakeDecel() * Gdx.graphics.getDeltaTime());
			if(player.getSpeed() < player.getReverseSpeedCap()) {
				player.setSpeed(player.getReverseSpeedCap());
			}
		} else if(player.getSpeed() > 0){	// On no inputs decelerate if moving forwards
				player.setSpeed(player.getSpeed() - player.getDecel() * Gdx.graphics.getDeltaTime());
				if(player.getSpeed() < 0) {
					player.setSpeed(0);
				}
		} else if(player.getSpeed() < 0) {	// On no inputs accelerate if moving backwards
			player.setSpeed(player.getSpeed() + player.getDecel() * Gdx.graphics.getDeltaTime());
			if(player.getSpeed() > 0) {
				player.setSpeed(0);
			}
		}
		if(player.getSpeed() != 0) {	// Handle moving the player
			// Store the player's original position
			double oldX = player.getX(), oldY = player.getY();
			float oldRot = player.getRotation();
			// Move the player based on their speed
			player.setX(player.getX() + player.getSpeed() * Math.sin(Math.toRadians(-player.getRotation())) * Gdx.graphics.getDeltaTime());
			player.setY(player.getY() + player.getSpeed() * Math.cos(Math.toRadians(-player.getRotation())) * Gdx.graphics.getDeltaTime());
			// If holding A / D, turn the player
			if(Gdx.input.isKeyPressed(Input.Keys.A)) player.rotate((float)(player.getTurnSpeed() * Math.abs(player.getSpeed()/player.getSpeedCap()) * Gdx.graphics.getDeltaTime()));
			if(Gdx.input.isKeyPressed(Input.Keys.D)) player.rotate((float)(-player.getTurnSpeed() * Math.abs(player.getSpeed()/player.getSpeedCap()) * Gdx.graphics.getDeltaTime()));
			// Find the mininum and maximum x and y for each point on the player's hitbox
			float[] vertices = player.getHitbox().getTransformedVertices();
			int minX = mapWidth + 1, minY = mapHeight + 1, maxX = -1, maxY = -1;
			for(int i = 0; i < vertices.length - 1; i +=2) {
				int x = (int) Math.floor(vertices[i]/32);
				int y = (int) Math.floor(vertices[i+1]/32);
				if(x < minX) minX = x;
				if(x > maxX) maxX = x;
				if(y < minY) minY = y;
				if(y > maxY) maxY = y;
			}
			// Check every tile that can contain the player to check if they are colliding
			for(int x = minX; x <= maxX; x++) {
				for(int y = minY; y <= maxY; y++) {
					Tile t = map[x][y];
					if(Intersector.overlapConvexPolygons(player.getHitbox(), t.getHitbox())) {
						if(t.getType().hasCollision()) {
							// If they are colliding revert any changes to the player's position and set their speed to 0
							player.setX(oldX);
							player.setY(oldY);
							player.getHitbox().setRotation(oldRot);
							player.setSpeed(0);
						} else if(t.getType().equals(TileType.Plunder)) {
							t.setType(TileType.Ocean); // If on a plunder tile remove it
							addGold(10 + rand.nextInt(16)); //adds a random amount of gold between 10 - 25 inclusive
						}
					}
				}
			}
		}
	}
	
	/**
	 * Moves the camera to be centred on the player's current position
	 * */
	private void cameraMovement() {
		camera.position.set((float) player.getX() + 48, (float) player.getY() + 64, 0);
		camera.update();
	}
	
	/**
	 * Pauses the game and shows a tutorial to the player.
	 * @param tutorialNo the index of the tutorial to be shown
	 * */
	private void showTutorial(int tutorialNo) {
		if(!shownTutorials.contains(tutorialNo)) {
			shownTutorials.add(tutorialNo);
			tutorialInd = tutorialNo;
			tutorialShowing = true;
		}
	}
	
	private void setPoints(int points) {
		this.points = points;
	}
	
	private void addPoints(int points) {
		setPoints(this.points += points);
	}
	
	private void setGold(int gold) {
		this.gold = gold;
	}
	
	private void addGold(int gold) {
		setGold(this.gold += gold);
	}
	
	/**
	 * Returns an objective with the same name as input
	 * @param name the name of the objective to be found
	 * @return an Objective with this name or null
	 * */
	public Objective getObjective(String name) {
		for(Objective o : objectives) {
			if(o.getName().equals(name)) {
				return o;
			}
		}
		return null;
	}
	
	/**
	 * Handles player death
	 **/
	public void playerDeath() {
		state = State.DEATH;
		restartBtnDead.setDisabled(false);
	}
	
	/**
	 * Handles the player winning the game
	 * */
	public void playerWin() {
		state = State.WIN;
		restartBtnWin.setDisabled(false);
	}
	
	/**
	 * Restarts the game by resetting all variables to the initial state and overwriting ships/colleges with new ones as when the game was created.
	 * */
	public void restart() {
		objectives.clear();
		cannonballs.clear();
		shownTutorials.clear();
		gameTime = 0;
		gold = 0;
		points = 0;
		player = new Ship(0, playerTextures, 27.5*32, 6*32, 200, 50, shipAccel, shipNaturalDecel, shipBrakeDecel, shipSpeedCap, shipMinusSpeedCap, shipRotSpeed, 500, 1);
		loadColleges();
		initMap();
		loadColleges();
		loadObjectives();
		showTutorial(0);
		state = State.RUNNING;
		restartBtnWin.setDisabled(true);
		restartBtnPause.setDisabled(true);
		restartBtnDead.setDisabled(true);
	}
	
	/**
	 * LibGDX function that handles the window being resized
	 * */
	@Override
	public void resize(int width, int height) {
		camera.setToOrtho(false, Gdx.app.getGraphics().getWidth(), Gdx.app.getGraphics().getHeight());
	}
	
	/**
	 * LibGDX function called when the game closes, disposes of all textures and sprite batches created.
	 * */
	@Override
	public void dispose () { 
		batch.dispose();
		hudBatch.dispose();
		font.dispose();
		shapeRenderer.dispose();
		cannonBallTexture.dispose();
		for(Texture t : hudTextures) {
			t.dispose();
		}
		for(Texture t : playerTextures) {
			t.dispose();
		}
		for(Texture t : collegeTextures) {
			t.dispose();
		}
		for(TileType t : TileType.values()) {
			t.dispose();
		}
	}
	
	/**
	 * Enum that contains different states the game can be in whilst running.
	 * */
	public enum State {
		RUNNING,
		PAUSED,
		DEATH,
		WIN;
	}
}
