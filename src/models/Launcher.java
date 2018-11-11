package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import agents.CitizenAgent;
import agents.CivilProtectionAgent;
import agents.DispatcherAgent;
import emergency.Emergency;
import emergency.EmergencyList;
import emergency.EmergencyResult;
import gui.AgentsWindow;
import gui.EmergencyVehicle;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.StaleProxyException;
import utils.Log;
import jade.core.Runtime;

public class Launcher {

	private static AgentContainer mainContainer;
	private static ArrayList<CivilProtectionAgent> stations;
	private static ArrayList<CitizenAgent> citizens;
	private static DispatcherAgent dispatcher;
	private static HashMap<String, EmergencyVehicle> vehicles;
	private static int fineCitizens = 0;
	private static int injuredCitizens = 0;
	private static int deadCitizens = 0;
	
	public static void main(String[] args) {
		vehicles = new HashMap<String, EmergencyVehicle>();
		setUpJADE();
		AgentsWindow.launch();
		runModel1();
		launchAgents();
	
		while((fineCitizens + injuredCitizens + deadCitizens) != citizens.size())
			try {
				Thread.sleep(5*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		printStatistics();
	}

	public static ArrayList<CivilProtectionAgent> getStations() {
		return stations;
	}

	public static ArrayList<CitizenAgent> getCitizens() {
		return citizens;
	}

	public static DispatcherAgent getDispatcher() {
		return dispatcher;
	}

	private static void setUpJADE() {
		// Get a hold on JADE runtime
		Runtime rt = Runtime.instance();

		// Exit the JVM when there are no more containers around
		rt.setCloseVM(true);
		System.out.print("runtime created\n");

		// Create a default profile
		Profile profile = new ProfileImpl(null, 1203, null);
		System.out.print("profile created\n");

		System.out.println("Launching a whole in-process platform..." + profile);
		mainContainer = rt.createMainContainer(profile);

	}

	public static void launchAgents() {
		try {
			Log.log("Model running -----------");
			for (int i = 0; i < stations.size(); i++)
				mainContainer.acceptNewAgent("station-" + Integer.toString(i + 1), stations.get(i)).start();
			for (int i = 0; i < citizens.size(); i++)
				mainContainer.acceptNewAgent("citizen-" + Integer.toString(i + 1), citizens.get(i)).start();
			mainContainer.acceptNewAgent("dispatcher", dispatcher).start();

		} catch (StaleProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void runModel1() {
		ArrayList<Integer> coordinatesc1 = new ArrayList<Integer>(Arrays.asList(5, 5));
		ArrayList<Emergency> emergencies1 = new ArrayList<Emergency>();
		emergencies1.add(EmergencyList.getEmergencies().get(1));
		CitizenAgent citizen1 = new CitizenAgent(coordinatesc1, emergencies1, 1, 3, 1);

		ArrayList<Integer> coordinatess1 = new ArrayList<Integer>(Arrays.asList(10, 10));
		CivilProtectionAgent station1 = new CivilProtectionAgent(coordinatess1, 1, 2, 2, 2);

		ArrayList<CivilProtectionAgent> civilProtectionList = new ArrayList<CivilProtectionAgent>(
				Arrays.asList(station1));
		DispatcherAgent dispatcher = new DispatcherAgent(civilProtectionList);

		station1.setCivilProtectionStations(civilProtectionList);

		Launcher.citizens = new ArrayList<CitizenAgent>(Arrays.asList(citizen1));
		Launcher.stations = civilProtectionList;
		Launcher.dispatcher = dispatcher;

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

		ArrayList<Integer> coordinatess2 = new ArrayList<Integer>(Arrays.asList(19, 19));
		CivilProtectionAgent station2 = new CivilProtectionAgent(coordinatess2, 2, 2, 2, 2);

		ArrayList<CivilProtectionAgent> civilProtectionList = new ArrayList<CivilProtectionAgent>(
				Arrays.asList(station1, station2));
		DispatcherAgent dispatcher = new DispatcherAgent(civilProtectionList);

		station1.setCivilProtectionStations(civilProtectionList);
		station2.setCivilProtectionStations(civilProtectionList);

		Launcher.citizens = new ArrayList<CitizenAgent>(Arrays.asList(citizen1, citizen2, citizen3));
		Launcher.stations = civilProtectionList;
		Launcher.dispatcher = dispatcher;
	}

	public static void runModel3() {
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
		CivilProtectionAgent station1 = new CivilProtectionAgent(coordinatess1, 1, 1, 1, 1);

		ArrayList<Integer> coordinatess2 = new ArrayList<Integer>(Arrays.asList(19, 19));
		CivilProtectionAgent station2 = new CivilProtectionAgent(coordinatess2, 2, 1, 1, 1);

		ArrayList<CivilProtectionAgent> civilProtectionList = new ArrayList<CivilProtectionAgent>(
				Arrays.asList(station1, station2));
		DispatcherAgent dispatcher = new DispatcherAgent(civilProtectionList);

		station1.setCivilProtectionStations(civilProtectionList);
		station2.setCivilProtectionStations(civilProtectionList);

		Launcher.citizens = new ArrayList<CitizenAgent>(Arrays.asList(citizen1, citizen2, citizen3));
		Launcher.stations = civilProtectionList;
		Launcher.dispatcher = dispatcher;
	}

	public static HashMap<String, EmergencyVehicle> getVehicles() {
		return vehicles;
	}

	public static void setVehicles(HashMap<String, EmergencyVehicle> vehicles) {
		Launcher.vehicles = vehicles;
	}

	public static void addVehicle(String id, EmergencyVehicle vehicle) {
		vehicles.put(id, vehicle);
	}
	
	public static void incrementStatisticsCounter(EmergencyResult result) {
		if(result == EmergencyResult.DEAD)
			deadCitizens++;
		else if(result == EmergencyResult.INJURED)
			injuredCitizens++;
		else if(result == EmergencyResult.FINE)
			fineCitizens++;
	}
	
	public static void printStatistics() {
		System.out.println("\n-------------------------------");
		System.out.println("Statistics----------------------");
		System.out.println("Number of No Injured Citizens: "+Integer.toString(fineCitizens));
		System.out.println("Number of Injured Citizens: "+Integer.toString(injuredCitizens));
		System.out.println("Number of Dead Citizens: "+Integer.toString(deadCitizens));
	}
}
