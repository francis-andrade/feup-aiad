package agents;

import java.util.ArrayList;

import emergency.EmergencyResult;
import emergency.EmergencyUnit;
import gui.EmergencyVehicle;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import messages.ArrivalEmergency;
import messages.CallEmergency;
import messages.ResultEmergency;
import models.Launcher;
import reinforcementlearning.Action;
import reinforcementlearning.ActionList;
import reinforcementlearning.State;
import utils.Log;
import utils.Pair;
import utils.Utils;

public class CivilProtectionAgent extends StationAgent{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int speed = 2;
	private final ArrayList<Integer> coordinates;
	private final int id;
	private final int totalAmbulance;
	private final int totalFirefighter;
	private final int totalPolice;
	private int availableAmbulance;
	private int availableFirefighter;
	private int availablePolice;
	private int vehicleCounter;
	private ArrayList<Pair> unlockResources;
	private ArrayList<CallEmergency> waitingEmergencies;
	private final static boolean useReinforcementLearning = true;
	private final static double learningRate = 0.1;
	private final static int learningTime=200;
	private ArrayList<Pair> valueTable; //(State, Value)
	private ArrayList<Pair> qTable; //((State, Action), (Value))
	private ArrayList<Pair> qChosen; //((State, Action), (Chosen))
	private ArrayList<Pair> qTimesTable; //((State, Action), time)
	
	public CivilProtectionAgent(ArrayList<Integer> coordinates,  int id,int ...available){
		this.coordinates = coordinates;
		this.vehicleCounter = 0;
		this.id = id;
		totalAmbulance = available[0];
		totalFirefighter = available[1];
		totalPolice = available[2];
		availableAmbulance = available[0];
		availableFirefighter = available[1];
		availablePolice = available[2];
		unlockResources = new ArrayList<Pair>();
		waitingEmergencies = new ArrayList<CallEmergency>();
		if(useReinforcementLearning)
			initializeValueTable();
	}
	
	public int getId() {
		return id;
	}
	
	public ArrayList<Integer> getCoordinates() {
		return coordinates;
	}
	
	
	
	public void setup() {
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
						Object msgObject = msg.getContentObject();
						Log.handleMessage("station-"+Integer.toString(id), msgObject, true);
						if(msgObject instanceof CallEmergency) {
							CallEmergency callMsg = (CallEmergency) msgObject;
							simpleProtocol(callMsg);
						}
						else if(msgObject instanceof ResultEmergency) {
							ResultEmergency resultMsg = (ResultEmergency) msgObject;
							if(useReinforcementLearning)
								updateTables(resultMsg);
						}
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
		
		addBehaviour(new CyclicBehaviour(this) {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void action() {
				if(unlockResources.size() > 0) {
					double currentTime = Utils.currentTime();
					
					
					while(unlockResources.size() > 0 && currentTime > ((double) unlockResources.get(0).getKey())) {
						addResources((boolean[]) unlockResources.get(0).getValue());
						unlockResources.remove(0);
						//Return Vehicless
					}
					
					for(int i=0; i < waitingEmergencies.size(); i++) {
						int ret = handleEmergency(waitingEmergencies.get(i));
						if(ret == 1) {
							waitingEmergencies.remove(i);
							i--;
						}
						else if(ret == -1) {
							
							ArrivalEmergency arrival = new ArrivalEmergency(-1, -1, -1, null);
							sendMessage("citizen-"+Integer.toString(waitingEmergencies.get(i).getCitizenID()), arrival);
							Log.handleMessage("station-"+Integer.toString(id), arrival, false);
							waitingEmergencies.remove(i);
							i--;
						}
					}
				}
			}
		});
	}
	
	
	private boolean hasResources(boolean[] emergencyUnits) {
		if(emergencyUnits[0] && availableAmbulance == 0)
			return false;
		else if(emergencyUnits[1] && availableFirefighter == 0)
			return false;
		else if(emergencyUnits[2] && availablePolice == 0)
			return false;
		else
			return true;
	}
	
	private int handleResources(boolean[] emergencyUnits, double totalTime) {
		Action action = new Action(emergencyUnits, totalTime);
		if(! hasResources(emergencyUnits))
			return 0;
		else if(!dispatchResources(action))
			return -1;
		
		if(emergencyUnits[0])
			availableAmbulance --;
		if(emergencyUnits[1])
			availableFirefighter --;
		if(emergencyUnits[2])
			availablePolice--;
		
		return 1;
	}
	
	private void addResources(boolean[] emergencyUnits) {
		if(emergencyUnits[0])
			availableAmbulance++;
		if(emergencyUnits[1])
			availableFirefighter++;
		if(emergencyUnits[2])
			availablePolice++;
	}
	
	private int handleEmergency(CallEmergency callMsg) {
		boolean[] emergencyUnits = callMsg.getEmergencyUnitsRequired();
		
		double currentTime = Utils.currentTime();
		double waitingTime = currentTime-callMsg.getCallTime();
		double arrivalTime = waitingTime+super.calculateDistance(callMsg.getCoordinates(), coordinates)/speed;
		double totalTime = arrivalTime + callMsg.getTimeDisposed()+ super.calculateDistance(callMsg.getCoordinates(), coordinates)/speed;
		
		int ret = handleResources(emergencyUnits, totalTime);
		if(ret==1) {
			String name = "" + this.id + "_" + this.vehicleCounter;
			ArrivalEmergency arrivalEmergency = new ArrivalEmergency(arrivalTime, id, totalTime, name);
			sendMessage("citizen-"+Integer.toString(callMsg.getCitizenID()), arrivalEmergency);
			Log.handleMessage("station-"+Integer.toString(id), arrivalEmergency, false);
			sendEmergencyUnits(emergencyUnits, callMsg.getCoordinates(), name);
			this.vehicleCounter++;
			double totalEmergencyTime = callMsg.getTimeDisposed() +2*super.calculateDistance(callMsg.getCoordinates(), coordinates)/speed;
			double unlockTime = currentTime + totalEmergencyTime;
			Pair resources = new Pair(unlockTime, emergencyUnits);
			boolean addedResources = false;
			for(int i = unlockResources.size()-1; i >= 0 && addedResources == false; i--)
				if(unlockTime > ((double) unlockResources.get(i).getKey())) {
					unlockResources.add(i+1, resources);
					addedResources = true;
				}
			
			if(addedResources == false)
				unlockResources.add(0, resources);
			
			return ret;			
		}
		return ret;
	}
	
	
	
	private void sendEmergencyUnits(boolean[] emergencyUnits, ArrayList<Integer> citizenCoords, String name) {
		int startX = this.coordinates.get(0);
		int startY = this.coordinates.get(1);
		int endX = citizenCoords.get(0);
		int endY = citizenCoords.get(1);
		if(emergencyUnits[0]) {
			EmergencyVehicle ambulance = new EmergencyVehicle(name, startX,startY,endX,endY,EmergencyUnit.AMBULANCE,this.speed);
			Launcher.addVehicle(name, ambulance);
		}
		if(emergencyUnits[1]) {
			EmergencyVehicle firefighter = new EmergencyVehicle(name, startX,startY,endX,endY,EmergencyUnit.FIREFIGHTER,this.speed);
			Launcher.addVehicle(name, firefighter);
		}	
		if(emergencyUnits[2]) {
			EmergencyVehicle police = new EmergencyVehicle(name, startX,startY,endX,endY,EmergencyUnit.POLICE,this.speed);
			Launcher.addVehicle(name, police);
		}
	}

	private void simpleProtocol(CallEmergency callMsg) {
		if(handleEmergency(callMsg) != 1) {
			if(callMsg.getPassedAllStations()) {
				waitingEmergencies.add(callMsg);
			}
			else {
				callMsg.addInvalidID(id);
				int stationID = getClosestStation(callMsg.getCoordinates().get(0), callMsg.getCoordinates().get(1), callMsg.getInvalidIDs());
				if(stationID != -1) {
					sendMessage("station-"+Integer.toString(stationID), callMsg);
					Log.handleMessage("station-"+Integer.toString(id), callMsg, false);
				}
				else {
					callMsg.clearInvalidIDs();
					callMsg.setTruePassedAllStations();
					int stationIDSecondPass = getClosestStation(callMsg.getCoordinates().get(0), callMsg.getCoordinates().get(1), callMsg.getInvalidIDs());
					if(stationIDSecondPass == id) {
						waitingEmergencies.add(callMsg);
					}
					else if(stationIDSecondPass != -1) {
						sendMessage("station-"+Integer.toString(stationIDSecondPass), callMsg);
						Log.handleMessage("station-"+Integer.toString(id), callMsg, false);
					}
				}
			}
		}
	}
	
	//Reinforcement Learning Functions
	private boolean dispatchResources(Action action2) {
		
		if(useReinforcementLearning == false)
			return true;
		else { 
			State state = new State(availableAmbulance, availableFirefighter, availablePolice);
			boolean[] emergencyUnits = new boolean[3];
			emergencyUnits[0]=false;
			emergencyUnits[1]=false;
			emergencyUnits[2]=false;
			Action action1 = new Action(emergencyUnits, -1);
			int index1 = StateActionToIndex(state, action1);
			int actionChosen1 = (int) qChosen.get(index1).getValue();
			double avgReward1 = (double) qTable.get(index1).getValue();
			int index2 = StateActionToIndex(state, action2);
			int actionChosen2 = (int) qChosen.get(index2).getValue();
			double avgReward2 = (double) qTable.get(index2).getValue();
			if(monteCarloChoice(avgReward1, actionChosen1, avgReward2, actionChosen2)) {
				qChosen.get(index2).setValue((int) qChosen.get(index2).getValue()+1);
				return true;
			}
			else {
				qChosen.get(index1).setValue((int) qChosen.get(index1).getValue()+1);
				return false;
			}
		}
	}
	
	private void updateTables(ResultEmergency resultMsg) {
		double currentTime = Utils.currentTime();
		int reward = calculateReward(resultMsg);
		int i = qTimesTable.size()-1;
		while(i >= 0 && (currentTime - learningTime) < (double) qTimesTable.get(i).getValue()) {
			int index = StateActionToIndex((State) ((Pair) qTimesTable.get(i).getKey()).getKey(), (Action) ((Pair) qTimesTable.get(i).getKey()).getValue());
			qTable.get(index).setValue((1-learningRate) * ((double) qTable.get(index).getValue())+learningRate*reward);
			i--;
		}
	}
	
	private void initializeValueTable() {
		valueTable = new ArrayList<Pair>();
		qTable = new ArrayList<Pair>();
		qChosen = new ArrayList<Pair>();
		ArrayList<Action> actionList = ActionList.getActionList();
		for(int iAmb=0; iAmb <= totalAmbulance; iAmb++) {
			for(int iFire=0; iFire <= totalFirefighter; iFire++) {
				for(int iPol=0; iPol <= totalPolice; iPol++ ) {
					valueTable.add(new Pair(new State(iAmb, iFire, iPol), 0));
					for(Action action : actionList) {
						qTable.add(new Pair(new Pair(new State(iAmb, iFire, iPol), action), 0.0));
						qChosen.add(new Pair(new Pair(new State(iAmb, iFire, iPol), action), 0));
					}
				}
			}
		}
		
		qTimesTable = new ArrayList<Pair>();
	}
	
	private int calculateReward(ResultEmergency resultEmer) {
		if(resultEmer.getResult() == EmergencyResult.DEAD)		
			return 0;
		else if(resultEmer.getResult() == EmergencyResult.INJURED)
			return resultEmer.getSeverity()*resultEmer.getSeverity()*resultEmer.getSeverity()*resultEmer.getSeverity();
		else
			return 3*resultEmer.getSeverity()*resultEmer.getSeverity()*resultEmer.getSeverity()*resultEmer.getSeverity();
	}
	
	private int StateActionToIndex(State state, Action action) {
		return state.getAvailableAmbulance()*(totalFirefighter+1)*(totalPolice+1)*ActionList.getActionList().size()+
				state.getAvailableFirefighter()*(totalPolice+1)*ActionList.getActionList().size()+
				state.getAvailablePolice()*ActionList.getActionList().size()+ActionList.ActionToIndex(action);
	}
	
	private boolean monteCarloChoice(double avgReward1, int actionChosen1, double avgReward2, int actionChosen2) {
		if(actionChosen1 == 0 && actionChosen2==0)
			return true;
		else if(actionChosen1 == 0)
			return false;
		else if(actionChosen2 == 0)
			return true;
		else {
			double shift = 9*9*9*9;
			double value1 = avgReward1*(shift+Math.log(actionChosen1));
			double value2 = avgReward2*(shift+Math.log(actionChosen2));
			if(value1 >= value2)
				return false;
			else 
				return true;
		}
	}
		
}