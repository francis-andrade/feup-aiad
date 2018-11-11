package agents;


import java.util.ArrayList;

import emergency.Emergency;
import emergency.EmergencyResult;
import gui.AgentsWindow;
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
	private final ArrayList<Integer> coordinates;
	private final CallEmergency callEmergency;
	private final double probability;
	private final int emergencyTime; //seconds
	private final int id;
	private EmergencyResult emergencyStatus;
	
	public CitizenAgent(ArrayList<Integer> coordinates, ArrayList<Emergency> emergencies, double probability, int emergencyTime, int id) {
		this.coordinates = coordinates;
		this.id = id;
		this.callEmergency = new CallEmergency(emergencies, coordinates, this.getId());
		this.probability = probability;
		this.emergencyTime = emergencyTime;
		this.setEmergencyStatus(EmergencyResult.WAITING);
	}
	
	public ArrayList<Integer> getCoordinates(){
		return this.coordinates;
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
						if(arrival.getStationID() != -1) {
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
						double totalTime = arrival.getTotalTime();
						double totalEmergencyTime = 2*(totalTime - callEmergency.getTimeDisposed() - arrival.getArrivalTime()) + callEmergency.getTimeDisposed();
						ResultEmergency resultEmergency = new ResultEmergency(result, callEmergency.getSeverity(), totalTime);

						
						//setEmergencyStatus(result);

						int stationID = arrival.getStationID();
						
						try {
							Thread.sleep((long) (totalEmergencyTime)*1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						sendMessage("station-"+Integer.toString(stationID), resultEmergency);
						Log.handleMessage("citizen-"+Integer.toString(id), resultEmergency, false);
						}
						else {
							if(callEmergency.getSeverity() > 5)
								result = EmergencyResult.DEAD;
							else
								result = EmergencyResult.INJURED;
						}
						setEmergencyStatus(result);

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
			System.out.println("citizen-"+Integer.toString(getId())+" started wait of "+Integer.toString((int) emergencyTime)+ " s to call emergency...");
			try {
				Thread.sleep(emergencyTime*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			callEmergency.setCallTime();
			sendMessage("dispatcher", callEmergency);
			
			Log.handleMessage("citizen-"+Integer.toString(getId()), callEmergency, false);
		}
	}

	public EmergencyResult getEmergencyStatus() {
		return emergencyStatus;
	}

	public void setEmergencyStatus(EmergencyResult emergencyStatus) {
		this.emergencyStatus = emergencyStatus;
	}

	public int getId() {
		return id;
	}
	
	
}