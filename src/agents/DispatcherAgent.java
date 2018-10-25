package agents;

import java.util.ArrayList;

import jade.core.Agent;

public class DispatcherAgent extends Agent{
	private final ArrayList<CivilProtectionAgent> civilProtectionStations;
	
	public DispatcherAgent(ArrayList<CivilProtectionAgent> civilProtectionStations) {
		this.civilProtectionStations = civilProtectionStations;
	}

}