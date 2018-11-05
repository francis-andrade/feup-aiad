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
	
	Emergency(String name, int severity, EmergencyUnit unit) {
		this.name = name;
		this.severity = severity;
		this.vehicles = new ArrayList<EmergencyUnit>();
		vehicles.add(unit);
	}
	
	Emergency(String name, int severity, EmergencyUnit unit1, EmergencyUnit unit2) {
		this.name = name;
		this.severity = severity;
		this.vehicles = new ArrayList<EmergencyUnit>();
		vehicles.add(unit1);
		vehicles.add(unit2);
	}
	
	Emergency(String name, int severity, EmergencyUnit unit1, EmergencyUnit unit2, EmergencyUnit unit3) {
		this.name = name;
		this.severity = severity;
		this.vehicles = new ArrayList<EmergencyUnit>();
		vehicles.add(unit1);
		vehicles.add(unit2);
		vehicles.add(unit3);
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
