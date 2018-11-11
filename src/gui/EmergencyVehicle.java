package gui;

import java.util.ArrayList;

import emergency.EmergencyUnit;

public class EmergencyVehicle {
	private ArrayList<Integer> startCoordinates;	//pixel coordinates
	private ArrayList<Integer> endCoordinates;		//pixel coordinates
	private ArrayList<Integer> currentCoordinates;	//pixel coordinates
	private EmergencyUnit type;
	private double deltaX;				//pixels
	private double deltaY;				//pixels
	private final long startTime;	//milliseconds
	private boolean stop;

	//start and end coordinates are in grid coordinates
	public EmergencyVehicle(int startX, int startY, int endX, int endY, EmergencyUnit type, int speed) {
		this.startTime = AgentsWindow.getTime();
	
		startCoordinates = new ArrayList<Integer>(2);
		endCoordinates = new ArrayList<Integer>(2);
		currentCoordinates = new ArrayList<Integer>(2);
		
		int realStartX = startX * AgentMap.getCellSize() + 3;
		int realStartY = startY * AgentMap.getCellSize() + 3;
		int realEndX = endX * AgentMap.getCellSize() + 3;
		int realEndY = endY * AgentMap.getCellSize() + 3;
		
		setStartCoordinates(realStartX, realStartY);
		setEndCoordinates(realEndX, realEndY);
		setCurrentCoordinates(realStartX, realStartY);
		
		setSpeed(speed * AgentMap.getCellSize());	//speed in pixels
		stop = false;
		this.setType(type);
	}

	private void setSpeed(int pixelsPerSecond) {
		int distX = endCoordinates.get(0) - startCoordinates.get(0);
		int distY = endCoordinates.get(1) - startCoordinates.get(1);
		double distance = Math.sqrt(distX*distX + distY*distY);
		
		deltaX = pixelsPerSecond * (distX/distance);
		deltaY = pixelsPerSecond * (distY/distance);
	}

	public void setCurrentCoordinates(int x, int y) {
		currentCoordinates.set(0, x);
		currentCoordinates.set(1, y);
	}

	public void setStartCoordinates(int x, int y) {
		startCoordinates.set(0, x);
		startCoordinates.set(1, y);
	}

	public void setEndCoordinates(int x, int y) {
		endCoordinates.set(0, x);
		endCoordinates.set(1, y);
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
		if (stop) return;
		long t = currentTime - startTime;
		int x = (int) (startCoordinates.get(0) + deltaX * t);
		int y = (int) (startCoordinates.get(1) + deltaY * t);
		setCurrentCoordinates(x, y);
		if (x == endCoordinates.get(0) && y == endCoordinates.get(1))
			stop = true;
	}

}
