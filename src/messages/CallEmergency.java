package messages;

import java.util.ArrayList;

import emergency.Emergency;
import emergency.EmergencyUnit;
import jade.util.leap.Serializable;
import utils.Utils;

public final class CallEmergency implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final ArrayList<Integer> invalidIDs;
	
	private final ArrayList<Emergency> emergencies;
	
	private final ArrayList<Integer> coordinates;
	
	private double callTime;
	
	private final int citizenID;
	
	private boolean passedAllStations;
	
	private int timeDisposed;
	
	
	
	public CallEmergency(ArrayList<Emergency> emergencies, ArrayList<Integer> coordinates, int citizenID) {
		this.invalidIDs = new ArrayList<Integer>();
		this.emergencies = emergencies;
		this.coordinates = coordinates;
		this.callTime = -1;
		this.citizenID = citizenID;
		this.passedAllStations = false;
		setTimeDisposed();
	}
	
	public ArrayList<Integer> getInvalidIDs(){
		return invalidIDs;
	}
	
	public ArrayList<Emergency> getEmergency() {
		return emergencies;
	}
		
	
	public ArrayList<Integer> getCoordinates(){
		return coordinates;
	}
	
	public int getTimeDisposed() {
		return timeDisposed;
	}
	
	public void addInvalidID(int id) {
		invalidIDs.add(id);
	}
	
	public void clearInvalidIDs() {
		invalidIDs.clear();
	}
	
	public boolean getPassedAllStations() {
		return passedAllStations;
	}
	
	public double getCallTime() {
		return callTime;
	}
	
	public int getCitizenID() {
		return citizenID;
	}
	
	public void setTruePassedAllStations() {
		this.passedAllStations = true;
	}
	
	public boolean[] getEmergencyUnitsRequired(){
		boolean[] emergencyUnits = new boolean[3];
		for(int i = 0; i < emergencyUnits.length; i++) 
			emergencyUnits[i] = false;
		
		
		for(Emergency emer:emergencies) {
			if(emer.getVehicles().contains(EmergencyUnit.AMBULANCE))
				emergencyUnits[0] = true;
			
			if(emer.getVehicles().contains(EmergencyUnit.FIREFIGHTER))
				emergencyUnits[1] = true;			
			
			if(emer.getVehicles().contains(EmergencyUnit.POLICE))
				emergencyUnits[2] = true;
		}
		
		return emergencyUnits;
	}
	
	public double getProbabilityInjured(double d) {
		double product = 1;
		
		for (Emergency emer: emergencies) 
			product *= (1 - emer.getProbabilityInjured(d));
		
		
		return 1 - product;
	}
	
	public double getProbabilityDying(double d) {
		double product = 1;
		
		for (Emergency emer: emergencies) 
			product *= (1 - emer.getProbabilityDying(d));
		
		
		return 1 - product;
	}
	
	public void setTimeDisposed() {
		int maxTimeDisposed = Integer.MIN_VALUE;
		for(Emergency emer:emergencies) 
			if(emer.getTimeDisposed() > maxTimeDisposed) 
				maxTimeDisposed = emer.getTimeDisposed();
			
		
		
		timeDisposed = maxTimeDisposed;
	}
	
	
	public void setCallTime() {
		this.callTime = Utils.currentTime();
	}
	
	
	public void print() {
		System.out.println("Call Emergency Informations: ");
		System.out.println("Coordinates: "+Integer.toString(coordinates.get(0))+", "+Integer.toString(coordinates.get(1)));
		System.out.println("Time Disposed: "+Integer.toString(timeDisposed));
		System.out.print("Emergencies: ");
		for(Emergency emer : emergencies)
			System.out.print(emer.getName()+", ");
		System.out.println("");
	}

}
