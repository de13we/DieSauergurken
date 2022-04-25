package de.fhdw.chitter;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class StaffGUI extends JFrame implements ActionListener {

	private JTextField txtTopic;
	private JButton btnSend;
	private JTextField txtHeadline;
	private JTextArea txtText;
	private JTextField txtStaff;
	private JTextField txtPassword;
	private JButton btnLogin;
	
	private JLabel lblUsermsg;

	boolean isLoggedin = false;
	
	public StaffGUI() {
		this.setTitle("Staff GUI");
		this.setSize(1000, 620);

		this.setLocation(50, 50);
		this.setVisible(true);
		
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		
		getContentPane().setLayout(new java.awt.BorderLayout());
		 
		
		JPanel topPanel = new JPanel(new FlowLayout());
		topPanel.add(new JLabel("Name:"));
		txtStaff = new JTextField(10);
		txtStaff.setText("Max");
		
		topPanel.add(txtStaff);
		topPanel.add(new JLabel("Passwort:"));
		txtPassword = new JPasswordField(10);
		topPanel.add(txtPassword);
		
		btnLogin = new javax.swing.JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {

				
				Staff[] stafflist = Newssystem.instance.stafflist;
				
				for(int i=0;i<stafflist.length;i++)
				{
					Staff s = stafflist[i];
					if(s!=null)
					{
						if(s.name.equals(txtStaff.getText()))
						{
							if(s.passwort.equals(txtPassword.getText()))
							{
								isLoggedin = true;
								lblUsermsg.setText("Benutzer ist eingeloggt.");
								
								txtStaff.setEnabled(false);
								txtPassword.setEnabled(false);
								
								btnSend.setEnabled(true);
								txtText.setEnabled(true);
								
								btnLogin.setEnabled(false);
								
								break;
							}
						}
					}
				}
				
				if(isLoggedin == false)
				{
					lblUsermsg.setText("Benutzername bzw. Passwort falsch.");
					
					txtStaff.setEnabled(true);
					txtPassword.setEnabled(true);
					
					btnSend.setEnabled(false);
					txtText.setEnabled(false);
					
				}
			}
		});
		
		topPanel.add(btnLogin);
		
		
		add(topPanel, BorderLayout.PAGE_START);
        
		
		
		
		
		JPanel bottomPanel = new JPanel(new FlowLayout());
		
		lblUsermsg = new JLabel("keine neuen Nachrichten");
		bottomPanel.add(lblUsermsg);
		
		btnSend = new javax.swing.JButton("Senden");
		btnSend.addActionListener(this);
		bottomPanel.add(btnSend);
		
		
		
		add(bottomPanel, BorderLayout.PAGE_END);
		
		
		
		
		
		JPanel basePanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		basePanel.add(new JLabel("Topic:"), c);
		
		txtTopic = new JTextField();
		c.gridx = 1;
		c.weightx = 1;
		c.gridwidth = 2;
		basePanel.add(txtTopic, c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		basePanel.add(new JLabel("Headline:"), c);
		
		txtHeadline = new JTextField();
		c.gridx = 1;
		c.weightx = 1;
		c.gridwidth = 2;
		basePanel.add(txtHeadline, c);
		
		
		
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		basePanel.add(new JLabel("Text:"), c);
		
		txtText = new JTextArea();
		c.gridx = 1;
		c.weightx = 1;
		c.weighty = 1;
		
		c.gridwidth = 2;
		c.gridheight = 2;
		basePanel.add(txtText, c);
		
		
		add(basePanel, BorderLayout.CENTER);
		
		
		txtStaff.setEnabled(true);
		txtPassword.setEnabled(true);
		
		btnSend.setEnabled(false);
		txtText.setEnabled(false);
		
        pack();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if(isLoggedin == false)
		{
			lblUsermsg.setText("Benutzer ist nicht eingeloggt.");
			return;
		}
		
		String staff = txtStaff.getText();
		String topic = txtTopic.getText();
		String headline = txtHeadline.getText();
		String text = txtText.getText();
		
		SimpleDateFormat sdf_message = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		
		Newsmessage m = new Newsmessage();
		m.date = sdf_message.format(System.currentTimeMillis());
		m.author = staff;
		m.topic = topic;
		m.headline = headline;
		m.text = text;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");
  
		String filename = "data/msg_" + sdf.format(System.currentTimeMillis()) + ".txt";
		
		
		switch(topic)
		{
		case "Sport":
			Newssystem.instance.publishSportNews(m);
			
			lblUsermsg.setText("Nachricht wurde versendet");
			
			m.writeToFile(filename);
			break;
		case "Politik":
			Newssystem.instance.publishPolitikNews(m);
			
			lblUsermsg.setText("Nachricht wurde versendet");
			
			m.writeToFile(filename);
			break;
		case "Wirtschaft":
			Newssystem.instance.publishWirtschaftNews(m);
			
			lblUsermsg.setText("Nachricht wurde versendet");
			
			m.writeToFile(filename);
			break;
		
		default:
			lblUsermsg.setText("Kein topic mit dem Namen " + topic + " verfügbar");
		}
		
	}
}
