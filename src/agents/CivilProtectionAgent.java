package agents;

import java.util.ArrayList;

import jade.core.Agent;

public class CivilProtectionAgent extends Agent{
	private final ArrayList<Integer> coordinates;
	private final ArrayList<CivilProtectionAgent> civilProtectionStations;
	private final int id;
	
	public CivilProtectionAgent(ArrayList<Integer> coordinates, ArrayList<CivilProtectionAgent> civilProtectionStations, int id){
		this.coordinates = coordinates;
		this.civilProtectionStations = civilProtectionStations;
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public ArrayList<Integer> getCoordinates() {
		return coordinates;
	}
}