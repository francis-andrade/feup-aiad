package gui;

import java.util.ArrayList;
import java.util.Arrays;

import emergency.EmergencyUnit;

public class EmergencyVehicle {
	private ArrayList<Integer> startCoordinates;	//pixel coordinates
	private ArrayList<Integer> endCoordinates;		//pixel coordinates
	private ArrayList<Integer> currentCoordinates;	//pixel coordinates
	private EmergencyUnit type;
	private double deltaX;				//pixels
	private double deltaY;				//pixels
	private long startTime;	//milliseconds
	private VehicleStatus status;
	private String name;

	//start and end coordinates are in grid coordinates
	public EmergencyVehicle(String name, int startX, int startY, int endX, int endY, EmergencyUnit type, int speed) {
		this.name = name;
		this.startTime = AgentsWindow.getTime();
	
		startCoordinates = new ArrayList<Integer>(2);
		endCoordinates = new ArrayList<Integer>(2);
		currentCoordinates = new ArrayList<Integer>(2);
		
		int realStartX = startX * AgentMap.getCellSize() + 3;
		int realStartY = startY * AgentMap.getCellSize() + 3;
		int realEndX = endX * AgentMap.getCellSize() + 3;
		int realEndY = endY * AgentMap.getCellSize() + 3;
		
		setStartCoordinates(new ArrayList<Integer>(Arrays.asList(realStartX, realStartY)));
		setEndCoordinates(new ArrayList<Integer>(Arrays.asList(realEndX, realEndY)));
		setCurrentCoordinates(new ArrayList<Integer>(Arrays.asList(realStartX, realStartY)));
		
		setSpeed(speed * AgentMap.getCellSize());	//speed in pixels
		setStatus(VehicleStatus.GOING);
		this.setType(type);
	}

	private void setSpeed(int pixelsPerSecond) {
		int distX = endCoordinates.get(0) - startCoordinates.get(0);
		int distY = endCoordinates.get(1) - startCoordinates.get(1);
		double distance = Math.sqrt(distX*distX + distY*distY);
		
		deltaX = pixelsPerSecond * (distX/distance);
		deltaY = pixelsPerSecond * (distY/distance);
	}

	public void setCurrentCoordinates(ArrayList<Integer> coords) {
		currentCoordinates = coords;
	}

	public void setStartCoordinates(ArrayList<Integer> coords) {
		startCoordinates = coords;
	}

	public void setEndCoordinates(ArrayList<Integer> coords) {
		endCoordinates = coords;
	}

	public ArrayList<Integer> getStartCoordinates() {
		return startCoordinates;
	}

	public ArrayList<Integer> getEndCoordinates() {
		return endCoordinates;
	}

	public ArrayList<Integer> getCurrentCoordinates() {
		return currentCoordinates;
	}

	public EmergencyUnit getType() {
		return type;
	}

	public void setType(EmergencyUnit type) {
		this.type = type;
	}

	public long getStartTime() {
		return startTime;
	}

	public double getDeltaX() {
		return deltaX;
	}

	public void setDeltas(double deltaX, double deltaY) {
		this.deltaX = deltaX;
		this.deltaY = deltaY;
	}

	public double getDeltaY() {
		return deltaY;
	}

	public void updatePosition(long currentTime) {
		switch (getStatus()) {
		case GOING:
			updatePositionGoing(currentTime);
			break;
		case RETURNING:
			updatePositionReturning(currentTime);
			break;
		default:
			break;
		}
		
	}

	private void updatePositionReturning(long currentTime) {
		long t = currentTime - startTime;
		int x = (int) (endCoordinates.get(0) - deltaX * t / 1000);
		int y = (int) (endCoordinates.get(1) - deltaY * t / 1000);
		setCurrentCoordinates(new ArrayList<Integer>(Arrays.asList(x, y)));
		if (x == startCoordinates.get(0) && y == startCoordinates.get(1))
			setStatus(VehicleStatus.FINISHED);		
	}

	private void updatePositionGoing(long currentTime) {
		long t = currentTime - startTime;
		int x = (int) (startCoordinates.get(0) + deltaX * t / 1000);
		int y = (int) (startCoordinates.get(1) + deltaY * t / 1000);
		setCurrentCoordinates(new ArrayList<Integer>(Arrays.asList(x, y)));
		if (x == endCoordinates.get(0) && y == endCoordinates.get(1))
			setStatus(VehicleStatus.STOPPED);
	}
	
	public void sendHome() {
		setStatus(VehicleStatus.RETURNING);
		this.startTime = AgentsWindow.getTime();
	}

	public VehicleStatus getStatus() {
		return status;
	}

	public void setStatus(VehicleStatus status) {
		this.status = status;
	}
}
