package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.Rectangle;

public class AgentsWindow {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AgentsWindow window = new AgentsWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AgentsWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		JPanel status = new JPanel();
		JLabel statusLabel = new JLabel("New label");
		AgentMap map = new AgentMap();
		JToolBar toolBar = new JToolBar();
		JButton btnStart = new JButton("Start");
		JButton btnStop = new JButton("Stop");
		status.setLayout(new GridLayout(0, 1, 0, 0));
		status.add(statusLabel);
		toolBar.setFloatable(false);
		toolBar.add(btnStart);
		toolBar.add(btnStop);
		
		frame = new JFrame();
		frame.setBounds(100, 100, 300, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(status, BorderLayout.SOUTH);
		frame.getContentPane().add(map, BorderLayout.CENTER);
		frame.getContentPane().add(toolBar, BorderLayout.NORTH);
		frame.pack();
	}

}
