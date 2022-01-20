package team11.pirategame;

import com.badlogic.gdx.graphics.Color;

public enum TileType {
	Ocean(0, "Ocean", false, Color.CYAN),
	Land(1, "Land", true, Color.GREEN);
	
	TileType(int id, String name, boolean collision, Color color) {
		this.id = id;
		this.name = name;
		this.collision = collision;
		this.color = color;
	}
	
	private int id;
	private String name;
	private boolean collision;
	private Color color;
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public boolean hasCollision() {
		return collision;
	}

	public Color getColor() {
		return color;
	}
}
