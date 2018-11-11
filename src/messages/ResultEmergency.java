package messages;

import java.io.Serializable;

import emergency.EmergencyResult;

public final class ResultEmergency implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final EmergencyResult result;
	
	private final int severity;
	
	private final double totalTime;
	
	public ResultEmergency(EmergencyResult result, int severity, double totalTime) {
		this.result = result;
		this.severity = severity;
		this.totalTime = totalTime;
	}
	
	public EmergencyResult getResult() {
		return result;
	}
	
	public String toString() {
		return "EmergencyResult: "+result+"\nSeverity: "+Integer.toString(severity)+"\nTotal Time: "+Double.toString(totalTime);
	}
	
	public int getSeverity() {
		return severity;
	}
	
	public double getTotalTime() {
		return totalTime;
	}

}
