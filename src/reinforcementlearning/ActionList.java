package reinforcementlearning;

import java.util.ArrayList;

public class ActionList {
	
	private static boolean listMade = false;
	private static ArrayList<Action> actionList;
	
	public static ArrayList<Action> getActionList(){
		if(listMade == false) {
			actionList = new ArrayList<Action>();
			for(int indAmb=0; indAmb <= 1; indAmb++) {
				for(int indFire=0; indFire <= 1; indFire++) {
					for(int indPol=0; indPol <=1; indPol++) {
						boolean[] emergencyUnits = new boolean[3];
						emergencyUnits[0] = intToBoolean(indAmb);
						emergencyUnits[1] = intToBoolean(indFire);
						emergencyUnits[2] = intToBoolean(indPol);
						if(emergencyUnits[0] == false && emergencyUnits[1] == false && emergencyUnits[2] == false) {
							actionList.add(new Action(emergencyUnits, -1));
						}
						else {
							for(int totalTimeLevel = 0; totalTimeLevel <= 5; totalTimeLevel++) {
								actionList.add(new Action(emergencyUnits, totalTimeLevel));
							}
						}
					}
				}
			}
			listMade = true;
			return actionList;
		}
		else {
			return actionList;			
		}
	}
	
	public static int ActionToIndex(Action action) {
		if(action.getAmbulance() == false && action.getFirefighter() == false && action.getPolice() == false)
			return 0;  
		else
			return booleanToInt(action.getAmbulance())*2*2*6+booleanToInt(action.getFirefighter())*2*6+booleanToInt(action.getPolice())*6+action.getTotalTimeLevel() - 5;
	}
	
	private static boolean intToBoolean(int i) {
		if(i==1)
			return true;
		else
			return false;
	}
	
	private static int booleanToInt(boolean bol) {
		if(bol)
			return 1;
		else
			return 0;
	}
}
