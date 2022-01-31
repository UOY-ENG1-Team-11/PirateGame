package team11.pirategame;

public class Cannonball {
	
	private int collegeId;
	private double x, y;
	private double damage;
	private double speed;
	private double direction;
	private float creationTime;
	
	public Cannonball(int collegeId, double x, double y, double damage, double speed, double direction, float gameTime) {
		this.collegeId = collegeId;
		this.x = x;
		this.y = y;
		this.damage = damage;
		this.speed = speed;
		this.direction = direction;
		creationTime = gameTime;
	}
	
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
	
	public float getCreationTime() {
		return creationTime;
	}
}
