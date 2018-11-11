package messages;

import java.io.Serializable;

public final class ArrivalEmergency implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final double arrivalTime;
	
	private final double totalTime;
	
	private final int stationID;
	
	private final String vehicleID;
	
	/*private final CallEmergency callEmer;*/
	
	public ArrivalEmergency(double arrivalTime2, int stationID, double totalTime, String vehicleID/*, CallEmergency callEmer*/) {
		this.arrivalTime = arrivalTime2;
		this.stationID = stationID;
		this.totalTime = totalTime;
		this.vehicleID = vehicleID;
		/*this.callEmer = callEmer;*/
	}
	
	public double getArrivalTime() {
		return arrivalTime;
	}
	
	public int getStationID() {
		return stationID;
	}
	
	public double getTotalTime() {
		return totalTime;
	}
	
	/*public CallEmergency getCallEmer() {
		return callEmer;
	}*/
	
	public String getVehicleID() {
		return vehicleID;
	}
	
	public String toString() {
		return "Arrival Time: "+arrivalTime+"\nTotal Time: "+totalTime+"\nVehicle ID: "+vehicleID;
	}

}
