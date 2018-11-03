package agents;

import java.io.IOException;
import java.util.ArrayList;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import messages.CallEmergency;


public class DispatcherAgent extends MainAgent{
	
	
	public DispatcherAgent(ArrayList<CivilProtectionAgent> civilProtectionStations) {
		super(civilProtectionStations);
	}
	
	public void init() {
		addBehaviour(new CyclicBehaviour(this){
			
			@Override
			public void action() {
				ACLMessage msg = myAgent.receive();
				if(msg != null) {
					
					try {
						CallEmergency callMsg = (CallEmergency) msg.getContentObject();
						int stationID = getClosestStation(callMsg.getCoordinates().get(0), callMsg.getCoordinates().get(1), callMsg.getInvalidIDs());
						if(stationID != -1) {
							AID dest = new AID("station-"+Integer.toString(stationID), AID.ISLOCALNAME);
							ACLMessage forwardMsg = new ACLMessage(ACLMessage.INFORM);
							forwardMsg.setContentObject(callMsg);
							forwardMsg.addReceiver(dest);
							send(msg);
						}
					} catch (UnreadableException | IOException e) {
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