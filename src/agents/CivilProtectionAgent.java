package agents;

import java.util.ArrayList;

import jade.core.Agent;

public class CivilProtectionAgent extends Agent{
	private final ArrayList<Integer> coordinates;
	private final ArrayList<CivilProtectionAgent> civilProtectionStations;
	
	public CivilProtectionAgent(ArrayList<Integer> coordinates, ArrayList<CivilProtectionAgent> civilProtectionStations){
		this.coordinates = coordinates;
		this.civilProtectionStations = civilProtectionStations;
	}
}