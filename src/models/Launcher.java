package models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import agents.CitizenAgent;
import agents.CivilProtectionAgent;
import agents.DispatcherAgent;
import emergency.Emergency;
import emergency.EmergencyList;
import emergency.EmergencyResult;
import emergency.EmergencyUnit;
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
	private volatile static int fineCitizens = 0;
	private volatile static int injuredCitizens = 0;
	private volatile static int deadCitizens = 0;
	private final static String filename = "data.csv";
	private final static boolean useGui = false; 
	private static DataWriter writer; 
	private final static double timeDivider = 100;
		
	public static void main(String[] args) throws IOException {
		writer = new DataWriter(filename);
		//writer.createHeadings();
		vehicles = new HashMap<String, EmergencyVehicle>();
		setUpJADE();
		
		final int noIter = 2000;
		
		for(int i = 0; i < noIter; i++)
			runModel();
	}
	
	private static void runModel() {
		vehicles = new HashMap<String, EmergencyVehicle>();
		setUpJADE();
		
		DataSet currentData = new DataSet();
		if(useGui)
			AgentsWindow.launch();
		runRandomModel(currentData);
		launchAgents();
	
		while((fineCitizens + injuredCitizens + deadCitizens) != citizens.size())
			try {
				Thread.sleep(5*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		updateDataSet(currentData);
		try {
			writer.writeToFile(currentData);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		printStatistics();
		
		try {
			mainContainer.kill();
		} catch (StaleProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		resetStatistics();
	}
	
	private static void resetStatistics() {
		fineCitizens = 0;
		injuredCitizens = 0;
		deadCitizens = 0;
	}

	private static void updateDataSet(DataSet currentData) {
		System.out.println(currentData);
		System.out.println(fineCitizens);
		currentData.setSavedLives(fineCitizens);
		currentData.setDead(deadCitizens);
		currentData.setInjured(injuredCitizens);
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
	
	public static boolean getUseGui() {
		return useGui;
	}
	
	public static double getTimeDivider() {
		return timeDivider;
	}

	private static void setUpJADE() {
		// Get a hold on JADE runtime
		Runtime rt = Runtime.instance();

		// Exit the JVM when there are no more containers around
		rt.setCloseVM(true);
		System.out.print("runtime created\n");

		// Create a default profile
		Profile profile = new ProfileImpl(null, 1200, null);
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
	
	public static void runRandomModel(DataSet data) {
		Launcher.citizens = new ArrayList<CitizenAgent>();
		Launcher.stations = new ArrayList<CivilProtectionAgent>();
		Random r = new Random();
		int numberOfStations = r.nextInt(8) + 1;
		int numberOfCitizens = r.nextInt(9) + 1;
		//int numberOfCitizens = 10;
		ArrayList<ArrayList<Integer>> coordinates = generateRandomCoordinates(r, numberOfCitizens + numberOfStations);
		createRandomAgents(numberOfStations, numberOfCitizens, r, coordinates);
		createDataSet(data);
	}

	private static void createDataSet(DataSet data) {
		double avgAmbulances = getAverageVehicles(EmergencyUnit.AMBULANCE);
		double avgPolice = getAverageVehicles(EmergencyUnit.POLICE);
		double avgFirefighter = getAverageVehicles(EmergencyUnit.FIREFIGHTER);
		double avgSeverity = getAverageSeverity();
		double maxWaitTime = getMaxWaitTime();
		double avgEmergencies = Launcher.citizens.size()/(maxWaitTime+1);
		
		data.setCivilStations(Launcher.stations.size());
		data.setAvgAmbulances(avgAmbulances);
		data.setAvgPolice(avgPolice);
		data.setAvgFirefighter(avgFirefighter);
		data.setAvgSeverity(avgSeverity);
		data.setAvgEmergenciesPerMinute(avgEmergencies);


	}
	
	private static int getMaxWaitTime() {
		int maxWaitTime = 0;
		for(int i=0; i < Launcher.citizens.size(); i++) {
			if(Launcher.citizens.get(i).getEmergencyTime() > maxWaitTime)
				maxWaitTime = Launcher.citizens.get(i).getEmergencyTime();
		}
		
		return maxWaitTime;
	}

	
	private static double getAverageSeverity() {
		double sum = 0;
		for (int i = 0; i < Launcher.getCitizens().size(); i++) {
			sum += Launcher.getCitizens().get(i).getEmergencies().get(0).getSeverity();
		}
		return sum/Launcher.getCitizens().size();
	}

	private static double getAverageVehicles(EmergencyUnit type) {
		double sum = 0;
		switch(type) {
		case AMBULANCE:
			for (int i = 0; i < Launcher.getStations().size(); i++) {
				sum += Launcher.getStations().get(i).getAvailableAmbulance();
			}
			break;
		case POLICE:
			for (int i = 0; i < Launcher.getStations().size(); i++) {
				sum += Launcher.getStations().get(i).getAvailablePolice();
			}
			break;
		default:
			for (int i = 0; i < Launcher.getStations().size(); i++) {
				sum += Launcher.getStations().get(i).getAvailableFirefighter();
			}
			break;
		}
		return sum/Launcher.getStations().size();
	}

	private static void createRandomAgents(int numberOfStations, int numberOfCitizens, Random r, ArrayList<ArrayList<Integer>> coordinates) {
				
		for (int i = 0; i < numberOfStations; i++) {
			CivilProtectionAgent newStation = generateStation(r, coordinates.get(i));
			Launcher.stations.add(newStation);
		}
		
		for (int i = 0; i < numberOfCitizens; i++) {
			CitizenAgent newAgent = generateCitizen(r, coordinates.get(i+numberOfStations));
			Launcher.citizens.add(newAgent);
		}
		
		Launcher.dispatcher = new DispatcherAgent(Launcher.stations);	
		
		for(int i = 0; i < Launcher.stations.size(); i++) {
			Launcher.stations.get(i).setCivilProtectionStations(Launcher.stations);
		}
	}

	private static CitizenAgent generateCitizen(Random r, ArrayList<Integer> coordinates) {
		int bound = EmergencyList.getEmergencies().size();
		Emergency emergency = EmergencyList.getEmergencies().get(r.nextInt(bound));
		ArrayList<Emergency> emergencies = new ArrayList<Emergency>();
		emergencies.add(emergency);
		
		CitizenAgent res = new CitizenAgent(coordinates, emergencies, 1, r.nextInt(5), Launcher.citizens.size() + 1);
		
		return res;
	}

	private static CivilProtectionAgent generateStation(Random r, ArrayList<Integer> coordinates) {
		return new CivilProtectionAgent(coordinates, Launcher.stations.size()+1, r.nextInt(4)+1, r.nextInt(4)+1, r.nextInt(4)+1);
	}

	private static ArrayList<ArrayList<Integer>> generateRandomCoordinates(Random r, int n) {
		ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < n; i++) {
			ArrayList<Integer> current = generatePair(r);
			res.add(current);	
		}
		
		return res;
	}
	
	private static ArrayList<Integer> generatePair(Random r){
		ArrayList<Integer> current = new ArrayList<Integer>();
		int x = r.nextInt(20);
		int y = r.nextInt(20);
		current.add(x);
		current.add(y);
		return current;
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
	
	public static void runModel4() {
		ArrayList<Integer> coordinatesc1 = new ArrayList<Integer>(Arrays.asList(0, 0));
		ArrayList<Emergency> emergencies1 = new ArrayList<Emergency>();
		emergencies1.add(EmergencyList.getEmergencies().get(3));

		ArrayList<Integer> coordinatesc2 = new ArrayList<Integer>(Arrays.asList(0, 0));
		ArrayList<Emergency> emergencies2 = new ArrayList<Emergency>();
		emergencies2.add(EmergencyList.getEmergencies().get(15));
		Launcher.citizens = new ArrayList<CitizenAgent>();
		for(int i = 1; i <=5; i++) {
			Launcher.citizens.add( new CitizenAgent(coordinatesc2, emergencies2, 1, i*4, 2*i-1));
			Launcher.citizens.add( new CitizenAgent(coordinatesc1, emergencies1, 1, i*4+2, 2*i));
		}
		
		ArrayList<Integer> coordinatess1 = new ArrayList<Integer>(Arrays.asList(0, 0));
		CivilProtectionAgent station1 = new CivilProtectionAgent(coordinatess1, 1, 1, 1, 1);

		ArrayList<CivilProtectionAgent> civilProtectionList = new ArrayList<CivilProtectionAgent>(
				Arrays.asList(station1));
		DispatcherAgent dispatcher = new DispatcherAgent(civilProtectionList);

		station1.setCivilProtectionStations(civilProtectionList);
		
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
		if(useGui)
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
