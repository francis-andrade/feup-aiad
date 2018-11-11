package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.Timer;
import javax.swing.JPanel;

import agents.CitizenAgent;
import agents.CivilProtectionAgent;
import models.Launcher;

public class AgentMap extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final static int cellSize = 15;
	private final int mapWidth = 20;
	private final int mapHeight = 20;
	private final static int timerInterval = 10;
	private Timer timer;
	//private long startTime;
	private long currentTime;

	public AgentMap() {
		super();
		timer = new Timer(getTimerInterval(), this);
		currentTime = 0;
		this.setPreferredSize(new Dimension(mapWidth * cellSize + 3, mapHeight * cellSize + 3));
	}

	@Override
	public void paintComponent(Graphics g) {
		for (int y = 0; y < getMapHeight(); y++) {
			for (int x = 0; x < getMapWidth(); x++) {
				paintCoordinate(g, x, y, Color.WHITE);
			}
		}
		paintCitizens(g);
		paintCivilProtection(g);
		paintEmergencyVehicles(g);
		//startTime = System.currentTimeMillis();
		timer.start();
	}

	private void paintEmergencyVehicles(Graphics g) {
		ArrayList<String> toRemove = new ArrayList<String>();
		
		Launcher.getVehicles().forEach((k,v)->{
			if(v.getStatus() == VehicleStatus.FINISHED) {
				toRemove.add(k);
			} else {
				drawEmergencyVehicle(g, v);
			}
		});
		
		for (int i = 0; i < toRemove.size(); i++) {
			Launcher.getVehicles().remove(toRemove.get(i));
		}
		
		g.setColor(Color.WHITE);
	}

	private void drawEmergencyVehicle(Graphics g, EmergencyVehicle v) {
		g.setColor(Color.BLACK);
		int x = v.getCurrentCoordinates().get(0);
		int y = v.getCurrentCoordinates().get(1);
		g.drawOval(x, y, cellSize / 2, cellSize / 2);
		g.setColor(getVehicleColor(v));
		g.fillOval(x, y, cellSize / 2, cellSize / 2);
	}

	private void paintCitizens(Graphics g) {
		for (int i = 0; i < Launcher.getCitizens().size(); i++) {
			CitizenAgent current = Launcher.getCitizens().get(i);
			int x = current.getCoordinates().get(0);
			int y = current.getCoordinates().get(1);
			if (x < 0 || x >= mapWidth || y < 0 || y >= mapHeight) {
				throw new IllegalArgumentException();
			}
			Color agentColor = getCitizenColor(current);

			paintCoordinate(g, x, y, agentColor);
		}
	}

	private Color getVehicleColor(EmergencyVehicle v) {
		switch (v.getType()) {
		case AMBULANCE:
			return Color.WHITE;
		case FIREFIGHTER:
			return Color.RED;
		case POLICE:
			return Color.BLUE;
		default:
			return Color.BLACK;
		}
	}

	private Color getCitizenColor(CitizenAgent citizen) {
		switch (citizen.getEmergencyStatus()) {
		case FINE:
			return Color.GREEN;
		case INJURED:
			return Color.RED;
		case DEAD:
			return Color.BLACK;
		default:
			return Color.YELLOW;
		}
	}

	private void paintCivilProtection(Graphics g) {
		for (int i = 0; i < Launcher.getStations().size(); i++) {
			CivilProtectionAgent current = Launcher.getStations().get(i);
			int x = current.getCoordinates().get(0);
			int y = current.getCoordinates().get(1);
			if (x < 0 || x >= mapWidth || y < 0 || y >= mapHeight) {
				throw new IllegalArgumentException();
			}
			paintCoordinate(g, x, y, Color.BLUE);
		}
	}

	public void paintCoordinate(Graphics g, int x, int y, Color color) {
		g.setColor(color);
		g.fillRect(x * getCellSize() + 1, y * getCellSize() + 1, getCellSize(), getCellSize());
		g.setColor(Color.BLACK);
		g.drawRect(x * getCellSize() + 1, y * getCellSize() + 1, getCellSize(), getCellSize());
		g.setColor(Color.WHITE);
	}

	public static int getCellSize() {
		return cellSize;
	}

	public int getMapWidth() {
		return mapWidth;
	}

	public int getMapHeight() {
		return mapHeight;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		currentTime += timerInterval;
		updateVehicles();
		repaint();
	}

	private void updateVehicles() {
		Launcher.getVehicles().forEach((k,v)->v.updatePosition(currentTime));
	}

	public long getTime() {
		return currentTime;
	}

	public static int getTimerInterval() {
		return timerInterval;
	}

}
