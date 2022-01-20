package team11.pirategame;

import com.badlogic.gdx.math.Polygon;

public class Ship {
	
	private double x, y;
	private int health, maxHealth;
	private int damage;
	private Polygon poly;
	private double speed, accel, decel, brakeDecel;
	private double speedCap, reverseSpeedCap;
	private double turnSpeed;
	
	public Ship(double x, double y, int maxHealth, int damage, double accel, double decel,
			double brakeDecel, double speedCap, double reverseSpeedCap, double turnSpeed) {
		this.x = x;
		this.y = y;
		health = maxHealth;
		this.maxHealth = maxHealth;
		this.damage = damage;
		poly = new Polygon(new float[]{0,0,64,0,64,128,0,128});
		poly.setOrigin(64/2, 128/2);
		poly.setPosition((float) x, (float) y);
		speed = 0;
		this.accel = accel;
		this.decel = decel;
		this.brakeDecel = brakeDecel;
		this.speedCap = speedCap;
		this.reverseSpeedCap = reverseSpeedCap;
		this.turnSpeed = turnSpeed;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
		setPosition(x,y);
	}
	
	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
		setPosition(x,y);
	}
	
	public void setPosition(double x, double y) {
		poly.setPosition(Math.round(x), Math.round(y));
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
	}
}
