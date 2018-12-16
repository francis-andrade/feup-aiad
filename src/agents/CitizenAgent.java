package agents;


import java.util.ArrayList;

import emergency.Emergency;
import emergency.EmergencyResult;
import gui.AgentsWindow;
import gui.VehicleStatus;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import messages.ArrivalEmergency;
import messages.CallEmergency;
import messages.ResultEmergency;
import models.Launcher;
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
	private ArrayList<Emergency> emergencies; 

	public CitizenAgent(ArrayList<Integer> coordinates, ArrayList<Emergency> emergencies, double probability, int emergencyTime, int id) {
		this.coordinates = coordinates;
		this.id = id;
		this.callEmergency = new CallEmergency(emergencies, coordinates, this.getId());
		this.probability = probability;
		this.emergencyTime = emergencyTime;
		this.setEmergencies(emergencies);
		this.setEmergencyStatus(EmergencyResult.WAITING);
	}

	public ArrayList<Integer> getCoordinates(){
		return this.coordinates;
	}
	
	public int getEmergencyTime() {
		return this.emergencyTime;
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
								Thread.sleep((long) ((long) (totalEmergencyTime)*1000/Launcher.getTimeDivider()));
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							sendAmbulanceHome(arrival.getVehicleID());
							sendMessage("station-"+Integer.toString(stationID), resultEmergency);
							Log.handleMessage("citizen-"+Integer.toString(id), resultEmergency, false);
						}
						else {
							if(callEmergency.getSeverity() > 5)
								result = EmergencyResult.DEAD;
							else
								result = EmergencyResult.INJURED;
						}
						Launcher.incrementStatisticsCounter(result);
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

	protected void sendAmbulanceHome(String id) {
		System.out.println("sendAmbulanceHome: "+id);
		if(Launcher.getUseGui())
			Launcher.getVehicles().get(id).sendHome();
	}

	private void callEmergency() {
		if(Math.random() < this.probability) {
			System.out.println("citizen-"+Integer.toString(getId())+" started wait of "+Integer.toString((int) emergencyTime)+ " s to call emergency...");
			try {
				Thread.sleep((long) (emergencyTime*1000/Launcher.getTimeDivider()));
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

	public ArrayList<Emergency> getEmergencies() {
		return emergencies;
	}

	public void setEmergencies(ArrayList<Emergency> emergencies) {
		this.emergencies = emergencies;
	}


}