package de.fhdw.chitter.extern.newsticker;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.concurrent.CompletionStage;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class NewstickerGUI extends JFrame {

	public JTextArea txtText;
	
	public NewstickerGUI() {
				
		this.setTitle("Client GUI");
		this.setSize(1000, 620);
		this.setLocation(150, 50);
		this.setVisible(true);
		
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		
		getContentPane().setLayout(new java.awt.BorderLayout());
		
		JPanel basePanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		
	
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		basePanel.add(new JLabel("Nachrichten:"), c);
		
		txtText = new JTextArea();
		c.gridx = 1;
		c.weightx = 1;
		c.weighty = 1;
		
		c.gridwidth = 2;
		c.gridheight = 2;
		basePanel.add(txtText, c);
		
		
		add(basePanel, BorderLayout.CENTER);
	
		pack();
	}
	
	 
}
