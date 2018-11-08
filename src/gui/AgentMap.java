package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class AgentMap extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private final int cellSize = 15;
	private final int mapWidth = 20;
	private final int mapHeight = 20;

	public AgentMap() {
		super();
		this.setPreferredSize(new Dimension(mapWidth * cellSize+3, mapHeight * cellSize+2));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		for (int y = 0; y < getMapHeight(); y++) {
			for (int x = 0; x <getMapWidth(); x++) {
				paintCoordinate(g, x*getCellSize()+1, y*getCellSize(), Color.WHITE);
			}
		}
	}
	
	public void paintCoordinate(Graphics g, int x, int y, Color color) {
		g.setColor(color);
		g.fillRect(x, y, getCellSize(), getCellSize());
		g.setColor(Color.BLACK);
		g.drawRect(x, y, getCellSize(), getCellSize());
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
