package team11.pirategame;

import com.badlogic.gdx.Gdx; 
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
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
	
	/**
	 * Constructor for Ship
	 * @param collegeId			the integer id for the college this ship belongs to
	 * @param textures			the textures for this college's appearance
	 * @param x					x-coordinate to spawn the ship at
	 * @param y					y-coordinate to spawn the ship at
	 * @param maxHealth			the maximum and initial amount of health this ship has
	 * @param damage			the amount of damage cannonballs fired from this ship deal
	 * @param accel				acceleration of the ship in pixels^2/second
	 * @param decel				natural deceleration of the ship in pixels^2/second
	 * @param brakeDecel		deceleration of the ship while braking in pixels^2/second
	 * @param speedCap			maximum speed of the ship in pixels/second
	 * @param reverseSpeedCap 	maximum speed of the ship while moving backwards in pixels/second
	 * @param turnSpeed			how fast the ship turns in degrees/second
	 * @param cannonBallSpeed	how fast the cannonballs fired from this ship move horizontally from the ship
	 * @param fireRate			how fast the ship's cannons can fire
	 * */
	public Ship(int collegeId, Texture[] textures, double x, double y, double maxHealth, double damage, double accel, double decel,
			double brakeDecel, double speedCap, double reverseSpeedCap, double turnSpeed, double cannonBallSpeed, double fireRate) {
		this.x = x;
		this.y = y;
		health = maxHealth;
		this.maxHealth = maxHealth;
		this.damage = damage;
		hitbox = new Polygon(new float[]{5,0,91,0,91,95,48,128,5,95});	// Create the ship's hitbox
		hitbox.setOrigin(96/2, 128/2);
		hitbox.setPosition((float) x, (float) y);
		// Make texture regions for each of the textures and place them in the animFrames array
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
	
	/**
	 * Returns cannonballs fired from each side of the ship if the cannons aren't on cooldown
	 * @param gameTime the current gameTime
	 * @return 
	 * */
	public Cannonball[] fire(float gameTime) {
		if(gameTime > lastShot + (1/fireRate)) {	// Make sure the cannons are only fired whilst not on cooldown
			lastShot = gameTime;
			// Create a cannonball to fire from each side of the ship. Calculate speed so it moves horizontally relative to the ship at cannonBallSpeed and retains the speed of the ship moving forwards
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

	/**
	 * Get the image to display for the current ship condition
	 * @return a texture region for the current stage in the animation
	 * */
	public TextureRegion getTextureRegion() {
		if(Gdx.input.isKeyPressed(Input.Keys.W)) { // If the ship is accelerating, animate the rows moving
			lastFrameChange ++;
			if(lastFrameChange > 50) {
				lastFrameChange = 0;
				animIndex++;
			}
		} else if(Gdx.input.isKeyPressed(Input.Keys.S)) {	// If the ship is decelerating, animate the rows moving backwards
			lastFrameChange++;
			if(lastFrameChange > 50) {
				lastFrameChange = 0;
				animIndex--;
			}
		}
		// Loop the animation
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
