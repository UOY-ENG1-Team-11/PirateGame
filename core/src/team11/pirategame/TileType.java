package team11.pirategame;

import com.badlogic.gdx.Gdx; 
import com.badlogic.gdx.graphics.Texture;

public enum TileType {
	Ocean("0", "Ocean", "water.png", false),
	Land("L", "Land", "land.png", true),
	Land_Left("]", "Left Land", "left land.png", true, new float[]{0,0,16,0,16,32,0,32}),
	Land_Right("[", "Right Land", "right land.png", true, new float[]{16,0,32,0,32,32,16,32}),
	Land_Top("-", "Top Land", "top land.png", true, new float[]{0,16,32,16,32,32,0,32}),
	Land_Bottom("_", "Bottom Land", "bottom land.png", true, new float[]{0,0,32,0,32,16,0,16}),
	Land_Bottom_Left_Inner("1", "Bottom Left Inner Corner", "inner bottom left corner.png", true, new float[]{0,0,32,0,32,16,16,16,16,32,0,32}),
	Land_Bottom_Right_Inner("2", "Bottom Right Inner Corner", "inner bottom right corner.png", true, new float[]{0,0,32,0,32,32,16,32,16,16,0,16}),
	Land_Top_Left_Inner("3", "Top Left Inner Corner", "inner top left corner.png", true, new float[]{0,0,16,0,16,16,32,16,32,32,0,32}),
	Land_Top_Right_Inner("4", "Top Right Inner Corner", "inner top right corner.png", true, new float[]{0,16,16,16,16,0,32,0,32,32,0,32}),
	Land_Bottom_Left_Outer("5", "Bottom Left Outer Corner", "outer bottom left corner.png", true, new float[]{16,16,32,16,32,32,16,32}),
	Land_Bottom_Right_Outer("6", "Bottom Right Outer Corner", "outer bottom right corner.png", true, new float[]{0,16,16,16,16,32,0,32}),
	Land_Top_Left_Outer("7", "Top Left Outer Corner", "outer top left corner.png", true, new float[]{16,0,32,0,32,16,16,16}),
	Land_Top_Right_Outer("8", "Top Right Outer Corner", "outer top right corner.png", true, new float[]{0,0,16,0,16,16,0,16});
	
	TileType(String id, String name, String fileName, boolean collision) {
		this.id = id;
		this.name = name;
		texture = new Texture(Gdx.files.internal("tiles/" + fileName));
		this.collision = collision;
		hitbox = new float[]{0,0,32,0,32,32,0,32};
	}
	
	TileType(String id, String name, String fileName, boolean collision, float[] hitbox) {
		this.id = id;
		this.name = name;
		texture = new Texture(Gdx.files.internal("tiles/" + fileName));
		this.collision = collision;
		this.hitbox = hitbox;
	}
	
	private String id;
	private String name;
	private Texture texture;
	private boolean collision;
	private float[] hitbox;
	
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

	public float[] getHitbox() {
		return hitbox;
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
