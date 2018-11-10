package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import agents.CitizenAgent;
import agents.CivilProtectionAgent;
import emergency.EmergencyResult;
import models.Launcher;

public class AgentMap extends JPanel {

	private static final long serialVersionUID = 1L;
	private final int cellSize = 15;
	private final int mapWidth = 20;
	private final int mapHeight = 20;

	public AgentMap() {
		super();
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

	private Color getCitizenColor(CitizenAgent current) {
		EmergencyResult status = current.getEmergencyStatus();
		switch (status) {
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

	public int getCellSize() {
		return cellSize;
	}

	public int getMapWidth() {
		return mapWidth;
	}

	public int getMapHeight() {
		return mapHeight;
	}

}
