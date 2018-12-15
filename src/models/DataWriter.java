package models;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DataWriter {
	private String filename;
	private File file;

	public DataWriter(String filename) {
		this.filename = filename;
		this.file = new File(filename);
	}

	public void writeToFile(String str, boolean newLine) throws IOException {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(this.filename, true));
			if (newLine) 
				writer.newLine();
			writer.write(str);
			writer.close();
		} catch (IOException e) {
			System.out.println("Error writing to file" + this.filename);
		}
	}

	public void createHeadings() throws IOException {
		String str = "civilStations,avgAmbulances,avgPolice;avgFirefighter,"
				+ "avgEmergencies,avgSeverity,savedLives,reinforcement,"
				+ "dead,injured,emergencies";
		writeToFile(str, false);
	}

	public void writeToFile(DataSet data) throws IOException {
		String str = data.getCivilStations() + "," + data.getAvgAmbulances() + "," 
				+ data.getAvgPolice() + "," + data.getAvgFirefighter() + "," 
				+ data.getAvgEmergenciesPerMinute() + "," + data.getAvgSeverity() + ","
				+ data.getSavedLives() + "," + data.isReinforcementLearningUsed() + "," 
				+ data.getDead() + "," + data.getInjured() + "," + data.getNumberOfEmergencies();
		writeToFile(str, true);
	}

}
