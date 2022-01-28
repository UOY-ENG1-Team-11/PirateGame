package team11.pirategame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class College {
	
	private Sprite sprite;
	private double x, y;
	private double damage;
	private double health, maxHealth;
	private double fireRate;
	private double range;
	private boolean playerAlly;
	
	public College(Texture tex, double x, double y, double damage, double maxHealth, double fireRate, double range, boolean playerAlly) {
		this.x = x;
		this.y = y;
		health = maxHealth;
		this.maxHealth = maxHealth;
		this.damage = damage;
		sprite = new Sprite(tex);
		sprite.setOrigin(32/2, 32/2);
		sprite.setPosition((float) x*32, (float) y*32);
		this.fireRate = fireRate;
		this.setRange(range);
		this.playerAlly = playerAlly;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getDamage() {
		return damage;
	}

	public void setDamage(double damage) {
		this.damage = damage;
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

	public boolean isPlayerAlly() {
		return playerAlly;
	}

	public void setPlayerAlly(boolean playerAlly) {
		this.playerAlly = playerAlly;
	}
}
