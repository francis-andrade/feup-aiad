package agents;


import java.util.ArrayList;

import emergency.Emergency;
import emergency.EmergencyResult;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import messages.ArrivalEmergency;
import messages.CallEmergency;
import messages.ResultEmergency;
import utils.Log;

public class CitizenAgent extends MainAgent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final CallEmergency callEmergency;
	private final double probability;
	private final int emergencyTime; //seconds
	private final int id;
	
	public CitizenAgent(ArrayList<Integer> coordinates, ArrayList<Emergency> emergencies, double probability, int emergencyTime, int id) {
		this.id = id;
		this.callEmergency = new CallEmergency(emergencies, coordinates, this.id);
		this.probability = probability;
		this.emergencyTime = emergencyTime;
	}
	
	protected void setup() {
		addBehaviour(new OneShotBehaviour(this) {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			@Override
			public void action() {
				callEmergency();
			}
		});
		
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
						ArrivalEmergency arrival = (ArrivalEmergency) msg.getContentObject();
						Log.handleMessage("citizen-"+Integer.toString(id), arrival, true);
						EmergencyResult result;
						double probDying = callEmergency.getProbabilityDying(arrival.getArrivalTime());
						double randomDying = Math.random();
						if(randomDying < probDying) {
							result = EmergencyResult.DEAD;
						}
						else {
							double probInjured = callEmergency.getProbabilityInjured(arrival.getArrivalTime());
							double randomInjured = Math.random();
							if(randomInjured < probInjured)
								result = EmergencyResult.INJURED;
							else
								result = EmergencyResult.FINE;
								
						}
						
						ResultEmergency resultEmergency = new ResultEmergency(result);
						int stationID = arrival.getStationID();
						
						try {
							Thread.sleep((long) (2*arrival.getArrivalTime()+callEmergency.getTimeDisposed())*1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						sendMessage("station-"+Integer.toString(stationID), resultEmergency);
						Log.handleMessage("citizen-"+Integer.toString(id), resultEmergency, false);
						doDelete();
						
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
	
	private void callEmergency() {
		if(Math.random() < this.probability) {
			System.out.println("citizen-"+Integer.toString(id)+" started wait of "+Integer.toString((int) emergencyTime)+ " s to call emergency...");
			try {
				Thread.sleep(emergencyTime*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			callEmergency.setCallTime();
			sendMessage("dispatcher", callEmergency);
			
			Log.handleMessage("citizen-"+Integer.toString(id), callEmergency, false);
		}
	}
	
	
}