package emergency;

import java.util.ArrayList;

public class Emergency {
	private String name;
	private int severity;
	private ArrayList<Vehicle> vehicles;
	
	Emergency(String name, ArrayList<Vehicle> vehicles) {
		this.name = name;
		this.severity = 0;
		this.vehicles = vehicles;
	}
	
	Emergency(String name, ArrayList<Vehicle> vehicles, int severity) {
		this.name = name;
		this.severity = severity;
		this.vehicles = vehicles;
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

	public ArrayList<Vehicle> getVehicles() {
		return vehicles;
	}
	
	public void setVehicles(ArrayList<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}
	
	public void addVehicle(Vehicle vehicle) {
		this.vehicles.add(vehicle);
	}
	
}
