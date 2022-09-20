package de.fhdw.chitter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class AdminGUI extends JFrame {

	private JButton btnNewStaff;
	private JButton btnNewReceiver;
	private JButton btnExit;
	
	public AdminGUI() {

		// Initialisieren des Fensters
		this.setTitle("Admin GUI");
		this.setSize(1000, 620);
		this.setLocation(50, 50);
		this.setVisible(true);
		
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		
		getContentPane().setLayout(new java.awt.BorderLayout());

		JPanel topPanel = new JPanel(new FlowLayout());


		// Button für Redakteur
		ActionListener newStaffListener = method -> new StaffGUI();
		btnNewStaff = createButton("Neuer Redakteur", newStaffListener);
		topPanel.add(btnNewStaff);

		// Button für neuen Receiver
		ActionListener newReaderListener = method -> new ReceiverGUI();
		btnNewReceiver = createButton("Neuer Leser", newReaderListener);
		topPanel.add(btnNewReceiver);

		// Button Beenden
		ActionListener endActionListener = method -> System.exit(0);
		btnExit = createButton("Beenden", endActionListener);
		topPanel.add(btnExit);
		

        // Alles auf die GUI packen
		add(topPanel, BorderLayout.PAGE_START);
        pack();
	}

	public JButton createButton(String title, ActionListener actionListener) {
		JButton button = new JButton(title);
		button.addActionListener(actionListener);
		return button;
	}
}
