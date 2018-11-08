package models;

import java.util.ArrayList;
import java.util.Arrays;

import agents.CitizenAgent;
import agents.CivilProtectionAgent;
import agents.DispatcherAgent;
import emergency.Emergency;
import emergency.EmergencyList;
import gui.AgentsWindow;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.StaleProxyException;
import utils.Log;
import jade.core.Runtime;

public class Launcher {
	
	public static AgentContainer mainContainer;
	
	public static void main(String[] args) {
		launchJADE();
		AgentsWindow.launch();
		runModel2();
	}
	
	
	private static void launchJADE() {
		// Get a hold on JADE runtime
		Runtime rt = Runtime.instance();

		// Exit the JVM when there are no more containers around
		rt.setCloseVM(true);
		System.out.print("runtime created\n");

		// Create a default profile
		Profile profile = new ProfileImpl(null, 1203, null);
		System.out.print("profile created\n");

		System.out.println("Launching a whole in-process platform..."+profile);
		mainContainer = rt.createMainContainer(profile);
		
		
	}
	
	public static void runModel1() {
		ArrayList<Integer> coordinatesc1 = new ArrayList<Integer>(Arrays.asList(5, 5));
		ArrayList<Emergency> emergencies1 = new ArrayList<Emergency>();
		emergencies1.add(EmergencyList.getEmergencies().get(1));
		CitizenAgent citizen1 = new CitizenAgent(coordinatesc1, emergencies1, 1, 3, 1);
		
		ArrayList<Integer> coordinatess1 = new ArrayList<Integer>(Arrays.asList(10, 10));
		CivilProtectionAgent station1 = new CivilProtectionAgent(coordinatess1, 1, 2, 2, 2);
		
		ArrayList<CivilProtectionAgent> civilProtectionList = new ArrayList<CivilProtectionAgent>(Arrays.asList(station1));
		DispatcherAgent dispatcher = new DispatcherAgent(civilProtectionList);
		
		station1.setCivilProtectionStations(civilProtectionList);
		
		try {
			Log.log("Model 1 running -----------");
			mainContainer.acceptNewAgent("station-1", station1).start();
			mainContainer.acceptNewAgent("citizen-1", citizen1).start();
			mainContainer.acceptNewAgent("dispatcher", dispatcher).start();
			
		} catch (StaleProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void runModel2() {
		ArrayList<Integer> coordinatesc1 = new ArrayList<Integer>(Arrays.asList(1, 1));
		ArrayList<Emergency> emergencies1 = new ArrayList<Emergency>();
		emergencies1.add(EmergencyList.getEmergencies().get(3));
		CitizenAgent citizen1 = new CitizenAgent(coordinatesc1, emergencies1, 1, 3, 1);
		
		ArrayList<Integer> coordinatesc2 = new ArrayList<Integer>(Arrays.asList(2, 2));
		ArrayList<Emergency> emergencies2 = new ArrayList<Emergency>();
		emergencies2.add(EmergencyList.getEmergencies().get(15));
		CitizenAgent citizen2 = new CitizenAgent(coordinatesc2, emergencies2, 1, 4, 2);
		
		ArrayList<Integer> coordinatesc3 = new ArrayList<Integer>(Arrays.asList(3, 3));
		ArrayList<Emergency> emergencies3 = new ArrayList<Emergency>();
		emergencies3.add(EmergencyList.getEmergencies().get(3));
		CitizenAgent citizen3 = new CitizenAgent(coordinatesc3, emergencies3, 1, 5, 3);
		
		ArrayList<Integer> coordinatess1 = new ArrayList<Integer>(Arrays.asList(0, 0));
		CivilProtectionAgent station1 = new CivilProtectionAgent(coordinatess1, 1, 2, 2, 2);
		
		ArrayList<Integer> coordinatess2 = new ArrayList<Integer>(Arrays.asList(20, 20));
		CivilProtectionAgent station2 = new CivilProtectionAgent(coordinatess2, 2, 2, 2, 2);
		
		
		ArrayList<CivilProtectionAgent> civilProtectionList = new ArrayList<CivilProtectionAgent>(Arrays.asList(station1, station2));
		DispatcherAgent dispatcher = new DispatcherAgent(civilProtectionList);
		
		station1.setCivilProtectionStations(civilProtectionList);
		station2.setCivilProtectionStations(civilProtectionList);
		
		try {
			Log.log("Model 1 running -----------");
			mainContainer.acceptNewAgent("station-1", station1).start();
			mainContainer.acceptNewAgent("citizen-1", citizen1).start();
			mainContainer.acceptNewAgent("station-2", station2).start();
			mainContainer.acceptNewAgent("citizen-2", citizen2).start();
			mainContainer.acceptNewAgent("citizen-3", citizen3).start();
			mainContainer.acceptNewAgent("dispatcher", dispatcher).start();
			
		} catch (StaleProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
