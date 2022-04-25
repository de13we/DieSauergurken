package de.fhdw.chitter;

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

	public ReceiverGUI() {
		this.setTitle("Client GUI");
		this.setSize(1000, 620);
		// this.setResizable(false);
		this.setLocation(150, 50);
		this.setVisible(true);

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		getContentPane().setLayout(new java.awt.BorderLayout());

		JPanel topPanel = new JPanel(new FlowLayout());
		topPanel.add(new JLabel("Topic:"));
		txtTopic = new JTextField(20);
		txtTopic.setText("Sport");

		topPanel.add(txtTopic);
		btnRegister = new javax.swing.JButton("Register");
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
		String topic = txtTopic.getText();

		switch (topic) {
		case "Sport":
			Newssystem.instance.registerSportReceiver(new Receiver() {
				public void receiveSportMessage(Newsmessage msg) {
					txtText.append("####### BEGIN ##################\n");
					txtText.append(msg.headline + "[" + msg.topic + "]\n" + msg.text + "\n(" + msg.author + ","
							+ msg.date + ")\n");
					txtText.append("####### END ####################\n");

				}
			});
			txtText.append("Topic Sport wurde registriert\n");
			break;
		case "Politik":
			Newssystem.instance.registerPolitikReceiver(new Receiver() {
				public void receivePolitikMessage(Newsmessage msg) {
					txtText.append("####### BEGIN ##################\n");
					txtText.append(msg.headline + "[" + msg.topic + "]\n" + msg.text + "\n(" + msg.author + ","
							+ msg.date + ")\n");
					txtText.append("####### END ####################\n");

				}
			});
			txtText.append("Topic Politik wurde registriert\n");
			break;
		case "Wirtschaft":
			Newssystem.instance.registerWirtschaftReceiver(new Receiver() {
				public void receiveWirtschaftMessage(Newsmessage msg) {
					txtText.append("####### BEGIN ##################\n");
					txtText.append(msg.headline + "[" + msg.topic + "]\n" + msg.text + "\n(" + msg.author + ","
							+ msg.date + ")\n");
					txtText.append("####### END ####################\n");

				}
			});

			txtText.append("Topic Wirtschaft wurde registriert\n");
			break;

		default:
			txtText.append("Kein topic mit dem Namen " + topic + " verfügbar");
		}

		String[] files = new File("data").list();

		for (String f : files) {
			Newsmessage msg = new Newsmessage();
			msg.readFromFile("data/" + f);

			if (msg.topic.equals(topic)) {
				txtText.append("####### BEGIN ##################\n");
				txtText.append(msg.headline + "[" + msg.topic + "]\n" + msg.text + "\n(" + msg.author + "," + msg.date
						+ ")\n");
				txtText.append("####### END ####################\n");
				
			}

		}

	}
}
