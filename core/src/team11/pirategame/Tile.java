package team11.pirategame;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;

public class Tile {
	private TileType type;
	private int x, y;
	private Polygon hitbox;
	private Sprite sprite;
	
	/**
	 * Constructor for Tile
	 * @param x x-coordinate for the tile in the map structure
	 * @param y y-coordinate for the tile in the map structure
	 * @param type the TileType that represents which type of tile this is
	 * */
	public Tile(int x, int y, TileType type) {
		this.x = x;
		this.y = y;
		this.type = type;
		hitbox = new Polygon(type.getHitbox());
		hitbox.setPosition((float) x*32, (float) y*32);
		sprite = new Sprite(type.getTexture());
		sprite.setPosition(x*32, y*32);
	}
	
	public TileType getType() {
		return type;
	}
	public void setType(TileType type) {
		this.type = type;
		sprite.setTexture(type.getTexture());
	}
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Polygon getHitbox() {
		return hitbox;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
}
