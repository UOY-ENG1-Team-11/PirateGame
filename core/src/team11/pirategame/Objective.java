package team11.pirategame;

public class Objective {

	private String name;
	private String description;
	private boolean mainObjective;
	private boolean active, completed;
	private int reward;
	
	/**
	 * Constructor for Objective
	 * @param name			unique name for the objective
	 * @param description	display text for the objective
	 * @param main			declares whether this is a main objective or side objective
	 * @param active		declares if this objective is active
	 * @param reward		amount of gold the player recieves for completing this objective
	 * */
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
	
	/**
	 * Completes the objective
	 * @return the gold reward for completing this objective
	 * */
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
