package agents;

import java.util.ArrayList;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import messages.CallEmergency;

public class CivilProtectionAgent extends Agent{
	private final ArrayList<Integer> coordinates;
	private final ArrayList<CivilProtectionAgent> civilProtectionStations;
	private final int id;
	private int availableAmbulance;
	private int availablePolice;
	private int availableFirefighter;
	
	public CivilProtectionAgent(ArrayList<Integer> coordinates, ArrayList<CivilProtectionAgent> civilProtectionStations, int id,int ...available){
		this.coordinates = coordinates;
		this.civilProtectionStations = civilProtectionStations;
		this.id = id;
		availableAmbulance = available[0];
		availablePolice = available[2];
		availableFirefighter = available[1];
	}
	
	public int getId() {
		return id;
	}
	
	public ArrayList<Integer> getCoordinates() {
		return coordinates;
	}
	
	public void setup() {
		addBehaviour(new CyclicBehaviour(this){

			@Override
			public void action() {
				ACLMessage msg = myAgent.receive();
				if(msg != null) {
					try {
						CallEmergency callMsg = (CallEmergency) msg.getContentObject();
						handleEmergency(callMsg);
					} catch (UnreadableException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else {
					block();
				}
				
			}
			
		});
	}
	
	private void handleEmergency(CallEmergency callMsg) {
		
	}
	
	private void simpleProtocol(CallEmergency callMsg) {
		
	}
}