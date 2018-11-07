package agents;

import java.util.ArrayList;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import messages.CallEmergency;
import utils.Pair;

public class CivilProtectionAgent extends StationAgent{
	private final ArrayList<Integer> coordinates;
	private final int id;
	private int availableAmbulance;
	private int availablePolice;
	private int availableFirefighter;
	private ArrayList<Pair> unlockResources;
	private ArrayList<CallEmergency> waitingEmergencies;
	
	public CivilProtectionAgent(ArrayList<Integer> coordinates, ArrayList<CivilProtectionAgent> civilProtectionStations, int id,int ...available){
		super(civilProtectionStations);
		this.coordinates = coordinates;
		this.id = id;
		availableAmbulance = available[0];
		availablePolice = available[2];
		availableFirefighter = available[1];
		unlockResources = new ArrayList<Pair>();
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
						simpleProtocol(callMsg);
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
			
			public void action() {
				if(unlockResources.size() > 0) {
					double secondsTime = ((double) System.nanoTime())/1000000000;
					
					
					while(secondsTime > ((double) unlockResources.get(0).getKey())) {
						addResources((boolean[]) unlockResources.get(0).getValue());
						unlockResources.remove(0);
					}
					
					for(int i=0; i < waitingEmergencies.size(); i++) {
						if(handleEmergency(waitingEmergencies.get(i)))
							waitingEmergencies.remove(i);
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
	
	private boolean handleResources(boolean[] emergencyUnits) {
		
		if(! hasResources(emergencyUnits))
			return false;
		
		if(emergencyUnits[0])
			availableAmbulance --;
		if(emergencyUnits[1])
			availableFirefighter --;
		if(emergencyUnits[2])
			availablePolice--;
		
		return true;
	}
	
	private void addResources(boolean[] emergencyUnits) {
		if(emergencyUnits[0])
			availableAmbulance++;
		if(emergencyUnits[1])
			availableFirefighter++;
		if(emergencyUnits[2])
			availablePolice++;
	}
	
	private boolean handleEmergency(CallEmergency callMsg) {
		boolean[] emergencyUnits = callMsg.getEmergencyUnitsRequired();
		
		if(handleResources(emergencyUnits)) {
			double totalTime = callMsg.getTimeDisposed() +2*super.calculateDistance(callMsg.getCoordinates(), coordinates)/40;
			double secondsTime = ((double) System.nanoTime())/1000000000;
			double unlockTime = secondsTime + totalTime;
			Pair resources = new Pair(unlockTime, emergencyUnits);
			boolean addedResources = false;
			for(int i = unlockResources.size()-1; i >= 0 && addedResources == false; i--)
				if(unlockTime > ((double) unlockResources.get(i).getKey())) {
					unlockResources.add(i+1, resources);
					addedResources = true;
				}
			
			if(addedResources == false)
				unlockResources.add(0, resources);
			
			return true;			
		}
		else
			return false;
	}
	
	private void simpleProtocol(CallEmergency callMsg) {
		if(!handleEmergency(callMsg)) {
			if(callMsg.getPassedAllStations()) {
				waitingEmergencies.add(callMsg);
			}
			else {
				callMsg.addInvalidID(id);
				int stationID = getClosestStation(callMsg.getCoordinates().get(0), callMsg.getCoordinates().get(1), callMsg.getInvalidIDs());
				if(stationID != -1)
					sendMessage("station-"+Integer.toString(stationID), callMsg);
				else {
					callMsg.clearInvalidIDs();
					callMsg.setTruePassedAllStations();
					int stationIDSecondPass = getClosestStation(callMsg.getCoordinates().get(0), callMsg.getCoordinates().get(1), callMsg.getInvalidIDs());
					if(stationIDSecondPass == id) {
						waitingEmergencies.add(callMsg);
					}
					else if(stationIDSecondPass != -1)
						sendMessage("station-"+Integer.toString(stationIDSecondPass), callMsg);
				}
			}
		}
	}
}