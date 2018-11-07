package agents;

import java.util.ArrayList;


public abstract class StationAgent extends MainAgent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<CivilProtectionAgent> civilProtectionStations;
	
	
	
	public void setCivilProtectionStations(ArrayList<CivilProtectionAgent> civilProtectionStations) {
		this.civilProtectionStations = civilProtectionStations;
	}
	
	protected double calculateDistance(ArrayList<Integer> coordinates1, ArrayList<Integer> coordinates2) {
		double diffX = coordinates1.get(0) - coordinates2.get(0);
		double diffY = coordinates1.get(1) - coordinates2.get(1);
		return Math.sqrt(diffX*diffX+diffY*diffY);
	}
	
	public int getClosestStation(int x, int y, ArrayList<Integer> invalidIDs) {
		int min_squares = Integer.MAX_VALUE;
		int min_index = -1;
		for(int i=0; i < civilProtectionStations.size();i++) {
			if(!invalidIDs.contains(civilProtectionStations.get(i).getId())) {
				int dist_x = civilProtectionStations.get(i).getCoordinates().get(0)-x;
				int dist_y = civilProtectionStations.get(i).getCoordinates().get(1)-y;
				int new_squares = dist_x*dist_x+dist_y*dist_y;
				if(new_squares < min_squares) {
					min_index = civilProtectionStations.get(i).getId();
				}
			}
		}
		
		return min_index;
	}

}
