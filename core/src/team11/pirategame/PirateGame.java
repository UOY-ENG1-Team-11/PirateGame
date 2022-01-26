package team11.pirategame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.ScreenUtils;

public class PirateGame extends ApplicationAdapter {
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private BitmapFont font;
	private ShapeRenderer shapeRenderer;
	//if these values are changed, change in desktop launcher too
	private int screenWidth = 1920;
	private int screenHeight = 1080;
	
	private int tileWidth = 32, tileHeight = 32;
	
	private Tile[][] map;
	private int mapWidth = 60, mapHeight = 34; //Default values overwritten when loading map file.
	
	private Ship player;
	//these values are in pixels/second and are to be used as default values when making a ship.
	private float shipAccel = 100;
	private float shipNaturalDecel = 50;
	private float shipBrakeDecel = 250;
	private float shipSpeedCap = 250;
	private float shipMinusSpeedCap = -50;
	private float shipRotSpeed = 120;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		font = new BitmapFont();
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setAutoShapeType(true);
		camera.setToOrtho(false, screenWidth, screenHeight);
		player = new Ship(screenWidth/2 - 64/2, screenHeight/2 - 128/2, 100, 1, shipAccel, shipNaturalDecel, shipBrakeDecel, shipSpeedCap, shipMinusSpeedCap, shipRotSpeed);
		initMap();
	}

	@Override
	public void render () {
		ScreenUtils.clear(1f, 1f, 1f, 1);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeType.Filled);
		drawMap();
        shapeRenderer.setColor(Color.BROWN);
        shapeRenderer.polygon(player.getPoly().getTransformedVertices());
        playerMovement();
		cameraMovement();
        shapeRenderer.end();
		batch.begin();
		font.draw(batch, "SPEED: " + player.getSpeed(), 20, 20);
		// IMAGE PROCESSING HERE
		batch.end();
		
	}
	
	private void initMap() {
		FileHandle file = Gdx.files.local("map.txt");
		String[] in = file.readString().split("\\r?\\n");
		String[] mapSize = in[0].split(",");
		mapWidth = Integer.parseInt(mapSize[0]);
		mapHeight = Integer.parseInt(mapSize[1]);
		map = new Tile[mapWidth][mapHeight];
		for (int y=0;y<mapHeight;y++) {
			 String[] line = in[mapHeight-y].split(" ");
			 for(int x=0;x<mapWidth;x++) {
				 TileType t = TileType.Ocean;
				 if(line[x].equals("L")) {
					 t = TileType.Land;
				 } 
				 map[x][y] = new Tile(x, y, t);
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
	
	private void drawMap() {
		for(int x = 0; x < mapWidth; x++) {
			for(int y = 0; y < mapHeight; y++) {
				shapeRenderer.setColor(map[x][y].getType().getColor());
				shapeRenderer.rect(x*tileWidth, y*tileHeight, tileWidth, tileHeight);
				
			}
		}
	}
	
	private void playerMovement() {
		if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			if(player.getSpeed() < player.getSpeedCap()) {
				player.setSpeed(player.getSpeed() + player.getAccel() * Gdx.graphics.getDeltaTime());
				if(player.getSpeed() > player.getSpeedCap()) {
					player.setSpeed(player.getSpeedCap());
				}
			}
		} else if(Gdx.input.isKeyPressed(Input.Keys.S)) {
			player.setSpeed(player.getSpeed() - player.getBrakeDecel() * Gdx.graphics.getDeltaTime());
			if(player.getSpeed() < player.getReverseSpeedCap()) {
				player.setSpeed(player.getReverseSpeedCap());
			}
		} else if(player.getSpeed() > 0){
				player.setSpeed(player.getSpeed() - player.getDecel() * Gdx.graphics.getDeltaTime());
				if(player.getSpeed() < 0) {
					player.setSpeed(0);
				}
		} else if(player.getSpeed() < 0) {
			player.setSpeed(player.getSpeed() + player.getDecel() * Gdx.graphics.getDeltaTime());
			if(player.getSpeed() > 0) {
				player.setSpeed(0);
			}
		}
		if(player.getSpeed() != 0) {
			double oldX = player.getX(), oldY = player.getY();
			float oldRot = player.getRotation();
			player.setX(player.getX() + player.getSpeed() * Math.sin(Math.toRadians(-player.getRotation())) * Gdx.graphics.getDeltaTime());
			player.setY(player.getY() + player.getSpeed() * Math.cos(Math.toRadians(-player.getRotation())) * Gdx.graphics.getDeltaTime());
			if(Gdx.input.isKeyPressed(Input.Keys.A)) player.rotate((float)(player.getTurnSpeed() * Math.abs(player.getSpeed()/player.getSpeedCap()) * Gdx.graphics.getDeltaTime()));
			if(Gdx.input.isKeyPressed(Input.Keys.D)) player.rotate((float)(-player.getTurnSpeed() * Math.abs(player.getSpeed()/player.getSpeedCap()) * Gdx.graphics.getDeltaTime()));
			float[] vertices = player.getPoly().getTransformedVertices();
			int minX = mapWidth + 1, minY = mapHeight + 1, maxX = -1, maxY = -1;
			for(int i = 0; i < 7; i +=2) {
				int x = (int) Math.floor(vertices[i]/32);
				int y = (int) Math.floor(vertices[i+1]/32);
				if(x < minX) minX = x;
				if(x > maxX) maxX = x;
				if(y < minY) minY = y;
				if(y > maxY) maxY = y;
			}
			for(int x = minX; x <= maxX; x++) {
				for(int y = minY; y <= maxY; y++) {
					Tile t = map[x][y];
					if(t.getType().hasCollision() && Intersector.overlapConvexPolygons(player.getPoly(), t.getHitbox())) {
						System.out.println("AA");
						player.setX(oldX);
						player.setY(oldY);
						player.getPoly().setRotation(oldRot);
						player.setSpeed(0);
					}
				}
			}
		}
	}
	
	private void cameraMovement() {
		camera.position.set((float) player.getX(), (float) player.getY(), 0);
		camera.update();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
		shapeRenderer.dispose();
	}
}
