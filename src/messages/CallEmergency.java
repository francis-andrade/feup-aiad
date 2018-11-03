package messages;

import java.util.ArrayList;

import emergency.Emergency;
import jade.util.leap.Serializable;

public class CallEmergency implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final ArrayList<Integer> invalidIDs;
	
	private final ArrayList<Emergency> emergencies;
	
	private final int risk;
	private final ArrayList<Integer> coordinates;
	
	public CallEmergency(ArrayList<Integer> invalidIDs, ArrayList<Emergency> emergencies, int risk, ArrayList<Integer> coordinates) {
		this.invalidIDs = invalidIDs;
		this.emergencies = emergencies;
		this.risk = risk;
		this.coordinates = coordinates;
	}
	
	public ArrayList<Integer> getInvalidIDs(){
		return invalidIDs;
	}
	
	public ArrayList<Emergency> getEmergency() {
		return emergencies;
	}
	
	public int getRisk() {
		return risk;
	}
	
	public ArrayList<Integer> getCoordinates(){
		return coordinates;
	}

}
