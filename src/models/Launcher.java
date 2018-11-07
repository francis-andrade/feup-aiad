package models;

import java.util.ArrayList;
import java.util.Arrays;

import agents.CitizenAgent;
import agents.CivilProtectionAgent;
import agents.DispatcherAgent;
import emergency.Emergency;
import emergency.EmergencyList;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.StaleProxyException;
import jade.core.Runtime;

public class Launcher {
	
	public static AgentContainer mainContainer;
	
	public static void main(String[] args) {
		launchJADE();
		runModel1();
	}
	
	
	private static void launchJADE() {
		// Get a hold on JADE runtime
		Runtime rt = Runtime.instance();

		// Exit the JVM when there are no more containers around
		rt.setCloseVM(true);
		System.out.print("runtime created\n");

		// Create a default profile
		Profile profile = new ProfileImpl(null, 1200, null);
		System.out.print("profile created\n");

		System.out.println("Launching a whole in-process platform..."+profile);
		mainContainer = rt.createMainContainer(profile);
		
		
	}
	
	public static void runModel1() {
		ArrayList<Integer> coordinatesc1 = new ArrayList<Integer>(Arrays.asList(5, 5));
		ArrayList<Emergency> emergencies1 = new ArrayList<Emergency>();
		emergencies1.add(EmergencyList.getEmergencies().get(1));
		CitizenAgent citizen1 = new CitizenAgent(coordinatesc1, emergencies1, 1, 10, 1);
		
		ArrayList<Integer> coordinatess1 = new ArrayList<Integer>(Arrays.asList(10, 10));
		CivilProtectionAgent station1 = new CivilProtectionAgent(coordinatess1, 1, 2, 2, 2);
		
		ArrayList<CivilProtectionAgent> civilProtectionList = new ArrayList<CivilProtectionAgent>(Arrays.asList(station1));
		DispatcherAgent dispatcher = new DispatcherAgent(civilProtectionList);
		
		station1.setCivilProtectionStations(civilProtectionList);
		
		try {
			mainContainer.acceptNewAgent("station-1", station1).start();
			mainContainer.acceptNewAgent("citizen-1", citizen1).start();
			mainContainer.acceptNewAgent("dispatcher", dispatcher).start();
			System.out.println("Model 1 running -----------");
		} catch (StaleProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
