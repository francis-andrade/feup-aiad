package agents;

import java.util.ArrayList;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import messages.CallEmergency;


public class DispatcherAgent extends StationAgent{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DispatcherAgent(ArrayList<CivilProtectionAgent> civilProtectionStations) {
		this.setCivilProtectionStations(civilProtectionStations);
	}
	
	protected void setup() {
		addBehaviour(new CyclicBehaviour(this){
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void action() {
				ACLMessage msg = myAgent.receive();
				if(msg != null) {
					
					try {
						CallEmergency callMsg = (CallEmergency) msg.getContentObject();
						int stationID = getClosestStation(callMsg.getCoordinates().get(0), callMsg.getCoordinates().get(1), callMsg.getInvalidIDs());
						if(stationID != -1) {
							sendMessage("station-"+Integer.toString(stationID), callMsg);
						}
					} catch (UnreadableException e) {
						e.printStackTrace();
					}
					
					
				}
				else {
					block();
				}
			}
		});
	}
	
	

}