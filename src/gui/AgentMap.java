package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;
import javax.swing.JPanel;

import agents.CitizenAgent;
import agents.CivilProtectionAgent;
import emergency.EmergencyVehicle;
import models.Launcher;

public class AgentMap extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final static int cellSize = 15;
	private final int mapWidth = 20;
	private final int mapHeight = 20;
	private final static int timerInterval = 10;
	private Timer timer;
	private long startTime;

	public AgentMap() {
		super();
		timer = new Timer(getTimerInterval(), this);
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
		startTime = System.currentTimeMillis();
		timer.start();
	}

	private void paintEmergencyVehicles(Graphics g) {
		for (int i = 0; i < Launcher.getVehicles().size(); i++) {
			drawEmergencyVehicle(g, Launcher.getVehicles().get(i));
		}
		/*
		g.setColor(Color.BLACK);
		g.drawOval(3 * cellSize + 1 + cellSize / 4, 3 * cellSize + 1 + cellSize / 4, cellSize / 2, cellSize / 2);
		g.setColor(Color.CYAN);
		g.fillOval(3 * cellSize + 1 + cellSize / 4, 3 * cellSize + 1 + cellSize / 4, cellSize / 2, cellSize / 2);
		*/
		g.setColor(Color.WHITE);
	}

	private void drawEmergencyVehicle(Graphics g, EmergencyVehicle v) {
		g.setColor(Color.BLACK);
		int x = v.getCurrentCoordinates().get(0);
		int y = v.getCurrentCoordinates().get(1);
		g.setColor(getVehicleColor(v));
		g.drawOval(x, y, cellSize / 2, cellSize / 2);
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
		updateVehicles();
		repaint();
	}

	private void updateVehicles() {
		for (int i = 0; i < Launcher.getVehicles().size(); i++) {
			EmergencyVehicle current = Launcher.getVehicles().get(i);
			if (!current.getCurrentCoordinates().equals(current.getEndCoordinates())) {
				int x = current.getCurrentCoordinates().get(0) + current.getDeltaX();
				int y = current.getCurrentCoordinates().get(1) + current.getDeltaY();
				current.setCurrentCoordinates(x, y);
			}
		}
	}

	public long getTime() {
		return System.currentTimeMillis() - startTime;
	}

	public static int getTimerInterval() {
		return timerInterval;
	}

}
