package gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class AgentMap extends JPanel {
	
	private final int cellSize = 5;

	public AgentMap() {
		super();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		//TODO get grid and fill in the grid thing 
	}
	
	public void paintCoordinate(Graphics g, int x, int y, Color color) {
		g.setColor(color);
		g.drawRect(x, y, cellSize, cellSize);
		g.setColor(Color.WHITE);
	}
	
}
