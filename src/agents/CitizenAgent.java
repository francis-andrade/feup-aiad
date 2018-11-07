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

public class CitizenAgent extends MainAgent {
	private final ArrayList<Integer> coordinates;
	private final CallEmergency emergency;
	private final double probability;
	private final long emergencyTime; //milliseconds
	
	public CitizenAgent(ArrayList<Integer> coordinates, CallEmergency emergency, double probability, long emergencyTime) {
		this.coordinates = coordinates;
		this.emergency = emergency;
		this.probability = probability;
		this.emergencyTime = emergencyTime;
	}
	
	protected void setup() {
		addBehaviour(new OneShotBehaviour(this) {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void action() {
				callEmergency();
			}
		});
	}
	
	private void callEmergency() {
		if(Math.random() < this.probability) {
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				
				public void run() {
					sendMessage("dispatcher", emergency);
				}
			}
					
					, emergencyTime);
		}
	}
	
	
}