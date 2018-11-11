package reinforcementlearning;

public class State {
	private int availableAmbulance;
	private int availableFirefighter;
	private int availablePolice;
	
	public State(int availableAmbulance, int availableFirefighter, int availablePolice) {
		this.availableAmbulance = availableAmbulance;
		this.availableFirefighter = availableFirefighter;
		this.availablePolice = availablePolice;
		
	}
	
	public int getAvailableAmbulance() {
		return availableAmbulance;
	}
	
	public int getAvailableFirefighter() {
		return availableFirefighter;
	}
	
	public int getAvailablePolice() {
		return availablePolice;
	}
}
