package team11.pirategame;

public class Tile {
	private TileType type;
	private int x, y;
	
	public Tile(int x, int y, TileType type) {
		this.x = x;
		this.y = y;
		this.type = type;
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
}
