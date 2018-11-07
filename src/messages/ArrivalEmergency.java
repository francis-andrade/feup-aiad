package messages;

import java.io.Serializable;

public final class ArrivalEmergency implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final double arrivalTime;
	
	private final int stationID;
	
	public ArrivalEmergency(double arrivalTime2, int stationID) {
		this.arrivalTime = arrivalTime2;
		this.stationID = stationID;
	}
	
	public double getArrivalTime() {
		return arrivalTime;
	}
	
	public int getStationID() {
		return stationID;
	}
	
	public String toString() {
		return "arrival Time: "+arrivalTime;
	}

}
