package reinforcementlearning;

public class Action {
	private boolean ambulance;
	private boolean firefighter;
	private boolean police;
	private int totalTimeLevel;
	
	public Action(boolean[] emergencyUnits, double totalTime) {
		ambulance = emergencyUnits[0];
		firefighter = emergencyUnits[1];
		police = emergencyUnits[2];
		if(totalTime >= 75)
			totalTime = 74;
		this.totalTimeLevel = (int) (totalTime/15+0.5);
	}
	
	public boolean getAmbulance() {
		return ambulance;
	}
	
	public boolean getFirefighter() {
		return firefighter;
	}
	
	public boolean getPolice() {
		return police;
	}
	
	public int getTotalTimeLevel() {
		return totalTimeLevel;
	}
}
