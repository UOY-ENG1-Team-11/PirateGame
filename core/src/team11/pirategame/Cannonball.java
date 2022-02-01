package team11.pirategame;

public class Cannonball {
	
	private int collegeId;
	private double x, y;
	private double damage;
	private double speed;
	private double direction;
	private float creationTime;
	
	/**
	 * Constructor for Cannonball
	 * @param collegeId the id of the college / college of the ship that fired this cannonball
	 * @param x			x-coordinate of the cannonball on spawn
	 * @param y			y-coordinate of the cannonball on spawn
	 * @param damage	how much damage the cannonball will deal on hitting a college/ship
	 * @param speed		the speed the cannonball should move at in pixels/second
	 * @param direction	the angle in degrees from north (0,1) the cannonball should move in
	 * @param gameTime	the game time when the cannonball was fired
	 * */
	public Cannonball(int collegeId, double x, double y, double damage, double speed, double direction, float gameTime) {
		this.collegeId = collegeId;
		this.x = x;
		this.y = y;
		this.damage = damage;
		this.speed = speed;
		this.direction = direction;
		creationTime = gameTime;
	}
	
	/**
	 * Returns the id of the college / college of the ship that fired this cannonball
	 * @return the id of the college / college of the ship that fired this cannonball
	 * */
	public int getCollegeId() {
		return collegeId;
	}

	public void setCollegeId(int collegeId) {
		this.collegeId = collegeId;
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
	
	public double getSpeed() {
		return speed;
	}
	
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	public double getDirection() {
		return direction;
	}
	
	public void setDirection(double direction) {
		this.direction = direction;
	}
	
	/**
	 * Returns the gameTime when the cannonball was created
	 * @return the value of gameTime from when the cannonball was created
	 * */
	public float getCreationTime() {
		return creationTime;
	}
}
