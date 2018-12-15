package models;

public class DataSet {
	private int civilStations;
	private double avgAmbulances;
	private double avgPolice;
	private double avgFirefighter;
	private double avgEmergenciesPerMinute;
	private double avgSeverity;
	private boolean reinforcementLearningUsed;
	private int numberOfEmergencies;
	
	private double savedLives;
	private int dead;
	private int injured;
	
	
	DataSet (int civilStations, double avgAmbulances, double avgPolice, double avgFirefighter,
			double avgEmergencies, double avgSeverity, float savedLives, boolean reinforcement,
			int dead, int injured, int numberOfEmergencies){
		this.civilStations = civilStations;
		this.avgAmbulances = avgAmbulances;
		this.avgPolice = avgPolice;
		this.avgFirefighter = avgFirefighter;
		this.avgEmergenciesPerMinute = avgEmergencies;
		this.avgSeverity = avgSeverity;
		this.savedLives = savedLives;
		this.reinforcementLearningUsed = reinforcement;
		this.dead = dead;
		this.injured = injured;
		this.numberOfEmergencies = numberOfEmergencies;
	}
	
	
	public int getCivilStations() {
		return civilStations;
	}
	
	public void setCivilStations(int civilStations) {
		this.civilStations = civilStations;
	}
	
	public double getAvgAmbulances() {
		return avgAmbulances;
	}
	
	public void setAvgAmbulances(double avgAmbulances) {
		this.avgAmbulances = avgAmbulances;
	}
	
	public double getAvgPolice() {
		return avgPolice;
	}
	
	public void setAvgPolice(double avgPolice) {
		this.avgPolice = avgPolice;
	}
	
	public double getAvgFirefighter() {
		return avgFirefighter;
	}
	
	public void setAvgFirefighter(double avgFirefighter) {
		this.avgFirefighter = avgFirefighter;
	}
	
	public double getAvgEmergenciesPerMinute() {
		return avgEmergenciesPerMinute;
	}
	
	public void setAvgEmergenciesPerMinute(double avgEmergenciesPerMinute) {
		this.avgEmergenciesPerMinute = avgEmergenciesPerMinute;
	}
	public double getAvgSeverity() {
		return avgSeverity;
	}
	
	public void setAvgSeverity(double avgSeverity) {
		this.avgSeverity = avgSeverity;
	}
	
	public double getSavedLives() {
		return savedLives;
	}
	
	public void setSavedLives(double savedLives) {
		this.savedLives = savedLives;
	}
	
	public boolean isReinforcementLearningUsed() {
		return reinforcementLearningUsed;
	}
	
	public void setReinforcementLearningUsed(boolean reinforcementLearningUsed) {
		this.reinforcementLearningUsed = reinforcementLearningUsed;
	}
	
	public int getDead() {
		return dead;
	}
	
	public void setDead(int dead) {
		this.dead = dead;
	}
	
	public int getInjured() {
		return injured;
	}
	
	public void setInjured(int injured) {
		this.injured = injured;
	}
	
	public int getNumberOfEmergencies() {
		return numberOfEmergencies;
	}
	
	public void setNumberOfEmergencies(int emergencies) {
		this.numberOfEmergencies = emergencies;
	}
}
