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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AgentsWindow {

	private JFrame frame;
	private JLabel statusLabel;
	
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
		AgentMap map = new AgentMap();
		statusLabel = new JLabel("New label");
		status.setLayout(new GridLayout(0, 1, 0, 0));
		status.add(statusLabel);
		
		frame = new JFrame();
		frame.setTitle("Civil protection agents");
		frame.setBounds(100, 100, 300, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(status, BorderLayout.SOUTH);
		frame.getContentPane().add(map, BorderLayout.CENTER);
		frame.pack();
		
		setStatusText("Check console for additional details.");
	}
	
	public void setStatusText(String text) {
		statusLabel.setText(text);
	}
	
	public String getStatusText() {
		return statusLabel.getText();
	}

}
