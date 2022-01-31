package team11.pirategame;

public class Objective {

	private String name;
	private String description;
	private boolean mainObjective;
	private boolean active, completed;
	private int reward;
	
	public Objective(String name, String description, boolean main, boolean active, int reward) {
		this.name = name;
		this.description = description;
		mainObjective = main;
		this.active = active;
		this.reward = reward;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public boolean isMainObjective() {
		return mainObjective;
	}

	public void setMainObjective(boolean mainObjective) {
		this.mainObjective = mainObjective;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isCompleted() {
		return completed;
	}
	
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	
	public int complete() {
		if(!completed) {
			setCompleted(true);
			return reward;
		}
		return 0;
	}
	
	public int getReward() {
		return reward;
	}
	
	public void setReward(int reward) {
		this.reward = reward;
	}
}
