package agents;

import java.util.ArrayList;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;


public class DispatcherAgent extends Agent{
	private final ArrayList<CivilProtectionAgent> civilProtectionStations;
	
	public DispatcherAgent(ArrayList<CivilProtectionAgent> civilProtectionStations) {
		this.civilProtectionStations = civilProtectionStations;
	}
	
	public void init() {
		addBehaviour(new CyclicBehaviour(this){
			
			@Override
			public void action() {
				ACLMessage msg = myAgent.receive();
				if(msg != null) {
					
					
				}
				else {
					block();
				}
			}
		});
	}
	
	private double calculateDistance(ArrayList<Integer> coordinates1, ArrayList<Integer> coordinates2) {
		double diffX = coordinates1.get(0) - coordinates2.get(0);
		double diffY = coordinates1.get(1) - coordinates2.get(1);
		return Math.sqrt(diffX*diffX+diffY*diffY);
	}

}