package team11.pirategame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public enum TileType {
	Ocean("0", "Ocean", "water.png", false),
	Land("L", "Land", "land.png", true),
	Land_Left("]", "Left Land", "left land.png", true),
	Land_Right("[", "Right Land", "right land.png", true),
	Land_Top("-", "Top Land", "top land.png", true),
	Land_Bottom("_", "Bottom Land", "bottom land.png", true),
	Land_Bottom_Left_Inner("1", "Bottom Left Inner Corner", "inner bottom left corner.png", true),
	Land_Bottom_Right_Inner("2", "Bottom Right Inner Corner", "inner bottom right corner.png", true),
	Land_Top_Left_Inner("3", "Top Left Inner Corner", "inner top left corner.png", true),
	Land_Top_Right_Inner("4", "Top Right Inner Corner", "inner top right corner.png", true),
	Land_Bottom_Left_Outer("5", "Bottom Left Outer Corner", "outer bottom left corner.png", true),
	Land_Bottom_Right_Outer("6", "Bottom Right Outer Corner", "outer bottom right corner.png", true),
	Land_Top_Left_Outer("7", "Top Left Outer Corner", "outer top left corner.png", true),
	Land_Top_Right_Outer("8", "Top Right Outer Corner", "outer top right corner.png", true);
	
	TileType(String id, String name, String fileName, boolean collision) {
		this.id = id;
		this.name = name;
		texture = new Texture(Gdx.files.internal("tiles/" + fileName));
		this.collision = collision;
	}
	
	private String id;
	private String name;
	private Texture texture;
	private boolean collision;
	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public Texture getTexture() {
		return texture;
	}

	public boolean hasCollision() {
		return collision;
	}

	public static TileType getTileType(String id) {
		for(TileType t : TileType.values()) {
			if(t.getId().equals(id)) {
				return t;
			}
		}
		return null;
	}
	
	public void dispose() {
		texture.dispose();
	}
}
