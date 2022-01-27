package team11.pirategame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;

public class Ship {
	
	private Sprite sprite;
	
	private double x, y;
	private int health, maxHealth;
	private int damage;
	private Polygon poly;
	private double speed, accel, decel, brakeDecel;
	private double speedCap, reverseSpeedCap;
	private double turnSpeed;
	private double cannonBallSpeed;
	private double fireRate;
	private double lastShot = 0;
	
	public Ship(Texture tex, double x, double y, int maxHealth, int damage, double accel, double decel,
			double brakeDecel, double speedCap, double reverseSpeedCap, double turnSpeed, double cannonBallSpeed, double fireRate) {
		this.x = x;
		this.y = y;
		health = maxHealth;
		this.maxHealth = maxHealth;
		this.damage = damage;
		poly = new Polygon(new float[]{0,0,64,0,64,128,0,128});
		poly.setOrigin(64/2, 128/2);
		poly.setPosition((float) x, (float) y);
		sprite = new Sprite(tex);
		sprite.setOrigin(64/2, 128/2);
		sprite.setPosition((float) x, (float) y);
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
	
	public Cannonball[] fire() {
		if(System.currentTimeMillis() > lastShot + (1/fireRate)*1000) {
			lastShot = System.currentTimeMillis();
			Cannonball cLeft = new Cannonball(x+32, y+64, damage, Math.sqrt(Math.pow(cannonBallSpeed, 2) + Math.pow(speed, 2)), getRotation() + 90 - Math.toDegrees(Math.atan(speed/500)));
			Cannonball cRight = new Cannonball(x+32, y+64, damage, Math.sqrt(Math.pow(cannonBallSpeed, 2) + Math.pow(speed, 2)), getRotation() + 270 + Math.toDegrees(Math.atan(speed/500)));
			Cannonball[] balls = {cLeft, cRight};
			return balls;
		} else return null;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		setPosition(x,y);
	}
	
	public double getY() {
		return y;
	}

	public void setY(double y) {
		setPosition(x,y);
	}
	
	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
		poly.setPosition(Math.round(x), Math.round(y));
		sprite.setPosition(Math.round(x), Math.round(y));
	}
	
	public int getHealth() {
		return health;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
	public int getMaxHealth() {
		return maxHealth;
	}
	
	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	public Polygon getPoly() {
		return poly;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public float getRotation() {
		return poly.getRotation();
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
		poly.rotate(rotation);
		sprite.rotate(rotation);
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
