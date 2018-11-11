package emergency;

import java.util.ArrayList;

import gui.AgentMap;
import gui.AgentsWindow;

public class EmergencyVehicle {
	private ArrayList<Integer> startCoordinates;	//pixel coordinates
	private ArrayList<Integer> endCoordinates;		//pixel coordinates
	private ArrayList<Integer> currentCoordinates;	//pixel coordinates
	private EmergencyUnit type;
	private int deltaX;				//pixels
	private int deltaY;				//pixels
	private final long startTime;	//milliseconds

	//start and end coordinates are in grid coordinates
	public EmergencyVehicle(int startX, int startY, int endX, int endY, EmergencyUnit type, int speed) {
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
		setSpeed(speed * AgentMap.getCellSize());
		this.setType(type);
		this.startTime = AgentsWindow.getTime();
	}

	private void setSpeed(int pixelsPerSecond) {
		int diffX = endCoordinates.get(0) - startCoordinates.get(0);
		int diffY = endCoordinates.get(1) - startCoordinates.get(1);
		double distance = Math.sqrt(diffX*diffX + diffY*diffY);
		double totalTime = distance/pixelsPerSecond;
		int nIncrements = (int) (totalTime*1000/AgentMap.getTimerInterval());
		deltaX = diffX/nIncrements;
		deltaY = diffY/nIncrements;
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

	public int getDeltaX() {
		return deltaX;
	}

	public void setDeltas(int deltaX, int deltaY) {
		this.deltaX = deltaX;
		this.deltaY = deltaY;
	}

	public int getDeltaY() {
		return deltaY;
	}

}
