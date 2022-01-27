package team11.pirategame;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;

public class Tile {
	private TileType type;
	private int x, y;
	private Polygon hitbox;
	private Sprite sprite;
	
	public Tile(int x, int y, TileType type) {
		this.x = x;
		this.y = y;
		this.type = type;
		hitbox = new Polygon(new float[]{0,0,32,0,32,32,0,32});
		hitbox.setOrigin(0, 0);
		hitbox.setPosition((float) x*32, (float) y*32);
		sprite = new Sprite(type.getTexture());
		sprite.setPosition(x*32, y*32);
	}
	
	public TileType getType() {
		return type;
	}
	public void setType(TileType type) {
		this.type = type;
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
