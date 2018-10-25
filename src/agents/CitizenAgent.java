package agents;


import java.util.ArrayList;

import jade.core.Agent;

public class CitizenAgent extends Agent {
	private final int risk;
	private final ArrayList coordinates;
	
	public CitizenAgent(int risk, ArrayList coordinates) {
		this.risk = risk;
		this.coordinates = coordinates;
	}
}