package de.fhdw.chitter;

import de.fhdw.chitter.exceptions.ResortDoesNotExistException;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ReceiverGUI extends JFrame implements ActionListener {

	private JTextField txtTopic;
	private JButton btnRegister;
	private JTextArea txtText;
	private Receiver receiver;
	public ReceiverGUI() {
		this.setTitle("Client GUI");
		this.setSize(1000, 620);
		// this.setResizable(false);
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
			txtText.append(extracted());
		} catch (ResortDoesNotExistException e) {
			System.out.println(e.getMessage());
		}
	}

	private String extracted() throws ResortDoesNotExistException { //TODO umbenennen
		String topic = txtTopic.getText();
		String result = "";

		Newssystem newssystem = Newssystem.getInstance();

		if(newssystem.isSubscribed(receiver, topic)) {
			//unsubscribe
			newssystem.unsubscribe(receiver, topic);
			result += "Topic " + topic + " wurde abgemeldet";
		}
		else {
			//subscribe
			try {
				newssystem.subscribe(receiver, topic);
				result += "Topic " + topic + " wurde registriert\n";

				String[] files = new File("data").list();

				for (String f : files) {
					Newsmessage msg = new Newsmessage();
					msg.readFromFile("data/" + f);

					if (msg.getTopics().contains(topic)) {
						receiveMessage(msg);
					}
				}
			} catch (ResortDoesNotExistException e) {
				customMessage(e.getMessage());
			}
		}
		return result;
	}

	public void receiveMessage(Newsmessage msg) {
		txtText.append("####### BEGIN ##################\n");
		txtText.append(msg.headline + "[" + msg.topic + "]\n" + msg.text + "\n(" + msg.author + ","
				+ msg.date + ")\n");
		txtText.append("####### END ####################\n");
	}
}
