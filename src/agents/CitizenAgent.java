package agents;


import java.util.ArrayList;

import jade.core.Agent;

public class CitizenAgent extends Agent {
	private final int risk;
	private final ArrayList<Integer> coordinates;
	private final DispatcherAgent dispatcher;
	
	public CitizenAgent(int risk, ArrayList<Integer> coordinates, DispatcherAgent dispatcher) {
		this.risk = risk;
		this.coordinates = coordinates;
		this.dispatcher = dispatcher;
	}
	
	
}