package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import javax.swing.JToolBar;
import javax.swing.JLabel;
import java.awt.GridLayout;

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
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel status = new JPanel();
		frame.getContentPane().add(status, BorderLayout.SOUTH);
		status.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel statusLabel = new JLabel("New label");
		status.add(statusLabel);
		
		AgentMap map = new AgentMap();
		frame.getContentPane().add(map, BorderLayout.CENTER);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		frame.getContentPane().add(toolBar, BorderLayout.NORTH);
		
		JButton btnStart = new JButton("Start");
		toolBar.add(btnStart);
		
		JButton btnStop = new JButton("Stop");
		toolBar.add(btnStop);
	}

}
