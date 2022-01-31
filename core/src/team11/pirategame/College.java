package team11.pirategame;

import com.badlogic.gdx.graphics.Texture; 
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;

public class College {
	
	private int id;
	private TextureRegion img, imgDead;
	private double x, y;
	private Polygon hitbox;
	private double damage;
	private double cannonLocs[];
	private double health, maxHealth;
	private double fireRate, cannonBallSpeed;
	private double lastShot = 0;
	private double range;
	private float rotation;
	private boolean playerAlly;
	private boolean defeated;
	
	public College(int id, Texture tex, Texture deadTex, double x, double y, double maxHealth, double damage, double cannonLocs[], double fireRate, double cannonBallSpeed, double range, float rotation, boolean playerAlly) {
		this.id = id;
		this.x = x;
		this.y = y;
		hitbox = new Polygon(new float[] {0, 0, 128, 0, 128, 128, 0, 128});
		hitbox.setPosition((float) x*32, (float) y*32);
		health = maxHealth;
		this.maxHealth = maxHealth;
		this.damage = damage;
		this.setCannonLocs(cannonLocs);
		img = new TextureRegion(tex);
		imgDead = new TextureRegion(deadTex);
		this.fireRate = fireRate;
		this.cannonBallSpeed = cannonBallSpeed;
		this.setRange(range);
		this.rotation = rotation;
		this.playerAlly = playerAlly;
		setDefeated(false);
	}
	
	public Cannonball[] fire(double X, double Y, float gameTime) {
		if(!defeated) {
			if(gameTime > lastShot + (1/fireRate)) {
				lastShot = gameTime;
				Cannonball[] balls = new Cannonball[cannonLocs.length/2];
				for(int i = 0; i < cannonLocs.length; i+=2) {
					double theta = Math.toDegrees(Math.acos((Y-(y*32 + cannonLocs[i + 1]))/(Math.sqrt(Math.pow(X - (x*32 + cannonLocs[i]), 2) + Math.pow(Y - (y * 32 + cannonLocs[i+1]), 2)))));
					if(X > (x*32 + cannonLocs[i])) {
						theta = -theta;
					}
					balls[i/2] = new Cannonball(id, x*32 + cannonLocs[i], y*32 + cannonLocs[i + 1], damage, cannonBallSpeed, theta, gameTime);
				}
				return balls;
			} else return null;
		} else return null;
	}
	
	public int getId() {
		return id;
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

	public double[] getCannonLocs() {
		return cannonLocs;
	}

	public void setCannonLocs(double cannonLocs[]) {
		this.cannonLocs = cannonLocs;
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
