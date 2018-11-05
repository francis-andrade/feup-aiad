package agents;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import emergency.Emergency;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import messages.CallEmergency;

public class CitizenAgent extends Agent {
	private final int risk;
	private final ArrayList<Integer> coordinates;
	private final CallEmergency emergency;
	private final double probability;
	private final long emergencyTime; //milliseconds
	
	public CitizenAgent(int risk, ArrayList<Integer> coordinates, CallEmergency emergency, double probability, long emergencyTime) {
		this.risk = risk;
		this.coordinates = coordinates;
		this.emergency = emergency;
		this.probability = probability;
		this.emergencyTime = emergencyTime;
	}
	
	protected void setup() {
		addBehaviour(new OneShotBehaviour(this) {
			
			public void action() {
				
			}
		});
	}
	
	private void callEmergency() {
		if(Math.random() < this.probability) {
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				
				public void run() {
					ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
					try {
						msg.setContentObject(emergency);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					@SuppressWarnings("deprecation")
					AID dest = new AID("dispatcher", AID.ISLOCALNAME);
					msg.addReceiver(dest);
					send(msg);
				}
			}
					
					, emergencyTime);
		}
	}
	
	
}