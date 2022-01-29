package team11.pirategame;

import com.badlogic.gdx.graphics.Texture; 
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;

public class College {
	
	private TextureRegion img, imgDead;
	private double x, y;
	private Polygon hitbox;
	private double damage;
	private int cannonNo;
	private double health, maxHealth;
	private double fireRate;
	private double range;
	private float rotation;
	private boolean playerAlly;
	private boolean defeated;
	
	public College(Texture tex, Texture deadTex, double x, double y, double maxHealth, double damage, int cannonNo, double fireRate, double range, float rotation, boolean playerAlly) {
		this.x = x;
		this.y = y;
		hitbox = new Polygon(new float[] {0, 0, 128, 0, 128, 128, 0, 128});
		hitbox.setPosition((float) x*32, (float) y*32);
		health = maxHealth;
		this.maxHealth = maxHealth;
		this.damage = damage;
		this.setCannonNo(cannonNo);
		img = new TextureRegion(tex);
		imgDead = new TextureRegion(deadTex);
		this.fireRate = fireRate;
		this.setRange(range);
		this.rotation = rotation;
		this.playerAlly = playerAlly;
		setDefeated(false);
	}

	public TextureRegion getImg() {
		if(!defeated) return img;
		return imgDead;
	}
	
	public void setImg(Texture tex) {
		img = new TextureRegion(tex);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public Polygon getHitbox() {
		return hitbox;
	}

	public double getDamage() {
		return damage;
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}

	public int getCannonNo() {
		return cannonNo;
	}

	public void setCannonNo(int cannonNo) {
		this.cannonNo = cannonNo;
	}

	public double getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(double maxHealth) {
		this.maxHealth = maxHealth;
	}

	public double getHealth() {
		return health;
	}

	public void setHealth(double health) {
		this.health = health;
		if(health <= 0) {
			this.health = 0;
			setDefeated(true);
		}
	}
	
	public void damage(double damage) {
		setHealth(health -= damage);
	}

	public double getFireRate() {
		return fireRate;
	}

	public void setFireRate(double fireRate) {
		this.fireRate = fireRate;
	}
	
	public double getRange() {
		return range;
	}

	public void setRange(double range) {
		this.range = range;
	}

	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	public boolean isPlayerAlly() {
		return playerAlly;
	}

	public void setPlayerAlly(boolean playerAlly) {
		this.playerAlly = playerAlly;
	}

	public boolean isDefeated() {
		return defeated;
	}

	public void setDefeated(boolean defeated) {
		this.defeated = defeated;
	}
}
