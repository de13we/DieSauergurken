package de.fhdw.chitter;

import de.fhdw.chitter.exceptions.ResortDoesNotExistException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ReceiverGUI extends JFrame implements ActionListener {

	private JTextField txtTopic;
	private JButton btnRegister;
	private JTextArea txtText;
	private Receiver receiver;
	public ReceiverGUI() {
		this.setTitle("Client GUI");
		this.setSize(1000, 620);
		this.setLocation(150, 50);
		this.setVisible(true);
		receiver = new Receiver(this);

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		getContentPane().setLayout(new java.awt.BorderLayout());

		JPanel topPanel = new JPanel(new FlowLayout());
		topPanel.add(new JLabel("Topic:"));
		txtTopic = new JTextField(20);
		txtTopic.setText("Sport");

		topPanel.add(txtTopic);
		btnRegister = new javax.swing.JButton("Register/Unsubscribe");
		btnRegister.addActionListener(this);
		topPanel.add(btnRegister);

		add(topPanel, BorderLayout.PAGE_START);

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

	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			txtText.append(subscriptionManager());
		} catch (ResortDoesNotExistException e) {
			System.out.println(e.getMessage());
		}
	}

	private String subscriptionManager() throws ResortDoesNotExistException {
		String topic = txtTopic.getText();
		StringBuilder resultBuilder = new StringBuilder();

		Newssystem newssystem = Newssystem.getInstance();
		try {
			if(newssystem.isSubscribed(receiver, topic)) {
				// Abmelden vom Thema
				newssystem.unsubscribe(receiver, topic);
				resultBuilder.append("Topic " + topic + " wurde abgemeldet\n");
			}
		else {
			// Registrieren fürs Thema
				newssystem.subscribe(receiver, topic);
				resultBuilder.append("Topic " + topic + " wurde registriert\n") ;

				String[] files = new File("data").list();

				for (String f : files) {
					Newsmessage msg = new Newsmessage("data/" + f);

					if (msg.getTopics().contains(topic)) {
						printNewsmessage(msg);
					}
				}
			}
		} catch (ResortDoesNotExistException e) {
			printCustommessage(e.getMessage());
		}
		return resultBuilder.toString();
	}

	// Audrucken der Nachricht
	public void printNewsmessage(Newsmessage msg) {
		txtText.append("####### BEGIN ##################\n");
		txtText.append(msg.getHeadline() + "[Hauptthema: " + msg.getMainTopic() + "] {Alle Themen: " + msg.getTopics() + "}" + "\n" + msg.getText() + "\n(" + msg.getAuthor() + ","
				+ msg.getDate() + ")\n");
		txtText.append("####### END ####################\n");
	}

	private void printCustommessage(String msg){
		txtText.append(msg + "\n");
	}
}
