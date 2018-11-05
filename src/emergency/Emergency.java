package emergency;

import java.io.Serializable;
import java.util.ArrayList;

public class Emergency implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private int severity;
	private ArrayList<EmergencyUnit> vehicles;
	private final int timeDisposed;
	
	Emergency(String name, int severity, int timeDisposed, EmergencyUnit... units) {
		this.name = name;
		this.severity = severity;
		this.timeDisposed = timeDisposed;
		this.vehicles = new ArrayList<EmergencyUnit>();
		for (EmergencyUnit unit:units)
			vehicles.add(unit);
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSeverity() {
		return severity;
	}

	public void setSeverity(int severity) {
		this.severity = severity;
	}

	public ArrayList<EmergencyUnit> getVehicles() {
		return vehicles;
	}
	
	public void setVehicles(ArrayList<EmergencyUnit> vehicles) {
		this.vehicles = vehicles;
	}
	
	public void addVehicle(EmergencyUnit vehicle) {
		this.vehicles.add(vehicle);
	}
	
}
