package messages;

import java.io.Serializable;

import emergency.EmergencyResult;

public final class ResultEmergency implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final EmergencyResult result;
	
	public ResultEmergency(EmergencyResult result) {
		this.result = result;
	}
	
	public EmergencyResult getResult() {
		return result;
	}

}
