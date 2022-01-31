package team11.pirategame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;

public class Ship {
	
	private TextureRegion[] animFrames;
	private int animIndex;
	private float lastFrameChange;
	
	private int collegeId;
	private double x, y;
	private double health, maxHealth;
	private double damage;
	private Polygon hitbox;
	private double speed, accel, decel, brakeDecel;
	private double speedCap, reverseSpeedCap;
	private double turnSpeed;
	private double cannonBallSpeed;
	private double fireRate;
	private double lastShot = 0;
	
	public Ship(int collegeId, Texture[] textures, double x, double y, double maxHealth, double damage, double accel, double decel,
			double brakeDecel, double speedCap, double reverseSpeedCap, double turnSpeed, double cannonBallSpeed, double fireRate) {
		this.x = x;
		this.y = y;
		health = maxHealth;
		this.maxHealth = maxHealth;
		this.damage = damage;
		hitbox = new Polygon(new float[]{5,0,91,0,91,95,48,128,5,95});
		hitbox.setOrigin(96/2, 128/2);
		hitbox.setPosition((float) x, (float) y);
		animFrames = new TextureRegion[textures.length];
		for(int i = 0; i < textures.length; i++) {
			animFrames[i] = new TextureRegion(textures[i]);
		}
		speed = 0;
		this.accel = accel;
		this.decel = decel;
		this.brakeDecel = brakeDecel;
		this.speedCap = speedCap;
		this.reverseSpeedCap = reverseSpeedCap;
		this.turnSpeed = turnSpeed;
		this.cannonBallSpeed = cannonBallSpeed;
		this.fireRate = fireRate;
	}
	
	public Cannonball[] fire(float gameTime) {
		if(gameTime > lastShot + (1/fireRate)) {
			lastShot = gameTime;
			Cannonball cLeft = new Cannonball(collegeId, x+48, y+64, damage, Math.sqrt(Math.pow(cannonBallSpeed, 2) + Math.pow(speed, 2)), getRotation() + 90 - Math.toDegrees(Math.atan(speed/500)), gameTime);
			Cannonball cRight = new Cannonball(collegeId, x+48, y+64, damage, Math.sqrt(Math.pow(cannonBallSpeed, 2) + Math.pow(speed, 2)), getRotation() + 270 + Math.toDegrees(Math.atan(speed/500)), gameTime);
			Cannonball[] balls = {cLeft, cRight};
			return balls;
		} else return null;
	}
	
	public int getCollegeId() {
		return collegeId;
	}

	public void setCollegeId(int collegeId) {
		this.collegeId = collegeId;
	}

	public TextureRegion getTextureRegion() {
		if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			lastFrameChange ++;
			if(lastFrameChange > 50) {
				lastFrameChange = 0;
				animIndex++;
			}
		} else if(Gdx.input.isKeyPressed(Input.Keys.S)) {
			lastFrameChange++;
			if(lastFrameChange > 50) {
				lastFrameChange = 0;
				animIndex--;
			}
		}
		if(animIndex >= animFrames.length) {
			animIndex = 0;
		} else if(animIndex < 0) {
			animIndex = animFrames.length - 1;
		}
		return animFrames[animIndex];
	}

	public double getX() {
		return x;
	}
	
	public double getCenterX() {
		return x + 48;
	}

	public void setX(double x) {
		setPosition(x,y);
	}
	
	public double getY() {
		return y;
	}
	
	public double getCenterY() {
		return y + 64;
	}

	public void setY(double y) {
		setPosition(x,y);
	}
	
	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
		hitbox.setPosition(Math.round(x), Math.round(y));
	}
	
	public double getHealth() {
		return health;
	}
	
	public void setHealth(double health) {
		this.health = health;
		if(health <= 0) {
			this.health = 0;
		}
	}
	public double getMaxHealth() {
		return maxHealth;
	}
	
	public void setMaxHealth(double maxHealth) {
		this.maxHealth = maxHealth;
	}
	
	public double getDamage() {
		return damage;
	}
	
	public void setDamage(double damage) {
		this.damage = damage;
	}
	
	public void damage(double damage) {
		setHealth(health -= damage);
	}
	
	public Polygon getHitbox() {
		return hitbox;
	}
	
	public float getRotation() {
		return hitbox.getRotation();
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	public double getAccel() {
		return accel;
	}

	public void setAccel(double accel) {
		this.accel = accel;
	}

	public double getDecel() {
		return decel;
	}

	public void setDecel(double decel) {
		this.decel = decel;
	}

	public double getBrakeDecel() {
		return brakeDecel;
	}

	public void setBrakeDecel(double brakeDecel) {
		this.brakeDecel = brakeDecel;
	}

	public double getSpeedCap() {
		return speedCap;
	}

	public void setSpeedCap(double speedCap) {
		this.speedCap = speedCap;
	}

	public double getReverseSpeedCap() {
		return reverseSpeedCap;
	}

	public void setReverseSpeedCap(double reverseSpeedCap) {
		this.reverseSpeedCap = reverseSpeedCap;
	}

	public double getTurnSpeed() {
		return turnSpeed;
	}

	public void setTurnSpeed(double turnSpeed) {
		this.turnSpeed = turnSpeed;
	}
	
	public void rotate(float rotation) {
		hitbox.rotate(rotation);
	}
	
	public double getCannonBallSpeed() {
		return cannonBallSpeed;
	}

	public void setCannonBallSpeed(double cannonBallSpeed) {
		this.cannonBallSpeed = cannonBallSpeed;
	}

	public double getFireRate() {
		return fireRate;
	}

	public void setFireRate(double fireRate) {
		this.fireRate = fireRate;
	}
}
