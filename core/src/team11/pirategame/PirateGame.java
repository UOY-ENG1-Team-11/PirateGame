package team11.pirategame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.ScreenUtils;

public class PirateGame extends ApplicationAdapter {
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private BitmapFont font;
	private ShapeRenderer shapeRenderer;
	//if these values are changed, change in desktop launcher too
	private int screenWidth = 1920;
	private int screenHeight = 1080;
	
	private Polygon player;
	private double playerX;
	private double playerY;
	private double playerRotation;
	private int playerWidth = 64;
	private int playerHeight = 128;
	//these values are in pixels/second
	private double playerSpeed = 0;
	private double playerAccel = 100;
	private double playerNaturalDecel = 50;
	private double playerBrakeDecel = 250;
	private double playerSpeedCap = 250;
	private double playerMinusSpeedCap = -50;
	private double playerRotSpeed = 120;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		font = new BitmapFont();
		shapeRenderer = new ShapeRenderer();
		camera.setToOrtho(false, screenWidth, screenHeight);
		player = new Polygon(new float[]{0,0,playerWidth,0,playerWidth,playerHeight,0,playerHeight});
		player.setOrigin(64/2, 128/2);
		player.setPosition(screenWidth/2 - playerWidth/2, screenHeight/2 - playerHeight/2);
		playerX = screenWidth/2 - playerWidth/2;
		playerY = screenHeight/2 - playerHeight/2;
		playerRotation = 0;
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0.4f, 0.6f, 1);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeType.Line);
        shapeRenderer.setColor(Color.BROWN);
        shapeRenderer.polygon(player.getTransformedVertices());
        shapeRenderer.end();
		batch.begin();
		font.draw(batch, "SPEED: " + playerSpeed, 20, 20);
		// IMAGE PROCESSING HERE
		batch.end();
		playerMovement();
		cameraMovement();
	}
	
	private void playerMovement() {
		if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			if(playerSpeed < playerSpeedCap) {
				playerSpeed += playerAccel * Gdx.graphics.getDeltaTime();
				if(playerSpeed > playerSpeedCap) {
					playerSpeed = playerSpeedCap;
				}
			}
		} else if(Gdx.input.isKeyPressed(Input.Keys.S)) {
			playerSpeed -= playerBrakeDecel * Gdx.graphics.getDeltaTime();
			if(playerSpeed < playerMinusSpeedCap) {
				playerSpeed = playerMinusSpeedCap;
			}
		} else if(playerSpeed > 0){
				playerSpeed -= playerNaturalDecel * Gdx.graphics.getDeltaTime();
				if(playerSpeed < 0) {
					playerSpeed = 0;
				}
		} else if(playerSpeed < 0) {
			playerSpeed += playerNaturalDecel * Gdx.graphics.getDeltaTime();
			if(playerSpeed > 0) {
				playerSpeed = 0;
			}
		}
		if(playerSpeed != 0) {
			playerX += playerSpeed * Math.sin(Math.toRadians(-player.getRotation())) * Gdx.graphics.getDeltaTime();
			playerY += playerSpeed * Math.cos(Math.toRadians(-player.getRotation())) * Gdx.graphics.getDeltaTime();
			player.setPosition(Math.round(playerX), Math.round(playerY));
			if(Gdx.input.isKeyPressed(Input.Keys.A)) player.rotate((float)(playerRotSpeed * Math.abs(playerSpeed/playerSpeedCap) * Gdx.graphics.getDeltaTime()));
			if(Gdx.input.isKeyPressed(Input.Keys.D)) player.rotate((float)(-playerRotSpeed * Math.abs(playerSpeed/playerSpeedCap) * Gdx.graphics.getDeltaTime()));
		}
	}
	
	private void cameraMovement() {
		
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
		shapeRenderer.dispose();
	}
}
