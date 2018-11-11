package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

public class AgentsWindow {

	private JFrame frame;
	private JLabel statusLabel;
	private static AgentMap map;
	private JTextPane agentDesc;
	
	/**
	 * Launch the application.
	 */
	public static void launch() {
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
	
	public AgentsWindow getInstance() {
		return this;
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
		map = new AgentMap();
		statusLabel = new JLabel("New label");
		status.setLayout(new GridLayout(0, 1, 0, 0));
		status.add(statusLabel);
		agentDesc = new JTextPane();
		agentDesc.setEditable(false);
		frame = new JFrame();
		frame.setTitle("Civil protection agents");
		frame.setBounds(100, 100, 300, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(status, BorderLayout.SOUTH);
		frame.getContentPane().add(map, BorderLayout.CENTER);
		frame.getContentPane().add(agentDesc, BorderLayout.EAST);
		frame.pack();
		
		
		setStatusText("Check console for additional details.");
	}
	
	public void setStatusText(String text) {
		statusLabel.setText(text);
	}
	
	public String getStatusText() {
		return statusLabel.getText();
	}
	
	public static void repaintMap() {
		map.repaint();
	}
	
	public void setAgentDesc(String text) {
		agentDesc.setText(text);
	}
	
	public String getAgendDesc() {
		return agentDesc.getText();
	}

}
