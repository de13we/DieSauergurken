package de.fhdw.chitter;

import java.io.IOException;

import org.atmosphere.websocket.WebSocket;

import de.fhdw.chitter.extern.newsticker.WebSocketServer;

public class Newssystem {

	
	public Receiver[] receiversSport = new Receiver[1000];
	public Receiver[] receiversPolitik = new Receiver[1000];
	public Receiver[] receiversWirtschaft = new Receiver[1000];
	
	public int receiverSportPointer = 0;
	public int receiverPolitikPointer = 0;
	public int receiverWirtschaftPointer = 0;
	
	public Staff[] stafflist = new Staff[10];
	
	public String[] resorts = {"Sport","Politik","Wirtschaft"};
	
	
	private Newssystem()
	{
		Staff s1 = new Staff();
		s1.name = "Max";
		s1.passwort = "passwort";
		
		stafflist[0] = s1;
		
		stafflist[1] = new Staff();
		stafflist[1].name = "Hans";
		stafflist[1].passwort = "12345";
		
		stafflist[2] = new Staff();
		stafflist[2].name = "John";
		stafflist[2].passwort = "wer?";
		
	}
	
	static Newssystem instance = new Newssystem();
	
	public void registerSportReceiver(Receiver receiver)
	{
		receiversSport[receiverSportPointer++] = receiver;
	}
	public void registerPolitikReceiver(Receiver receiver)
	{
		receiversPolitik[receiverPolitikPointer++] = receiver;
	}
	public void registerWirtschaftReceiver(Receiver receiver)
	{
		receiversWirtschaft[receiverWirtschaftPointer++] = receiver;
	}
	
	
	private Newsmessage markdownParser(Newsmessage msg)
	{
		msg.text = msg.text.replaceAll("(?m)^#(?!#)(.*)", "<h1>$1</h1>");
		msg.text = msg.text.replaceAll("(?m)^#{2}(?!#)(.*)", "<h2>$1</h2>");
		msg.text = msg.text.replaceAll("(?m)^#{2}(?!#)(.*)", "<h3>$1</h3>");
		
		
		msg.text = msg.text.replaceAll("\\*(.*)\\*", "<em>$1</em>");
		msg.text = msg.text.replaceAll("\\*\\*(.*)\\*\\*", "<b>$1</b>");
		
		msg.text = msg.text.replaceAll("(?m)^\\* (.*)$", "<li> $1 </li>");
		
		return msg;
	}
	
	public void publishSportNews(Newsmessage msg)
	{
		
		msg = markdownParser(msg);
		
		for(int i=0; i<receiverSportPointer; i++)
		{
			receiversSport[i].receiveSportMessage(msg);
		}
		
		publishMessageForTicker(msg);
	}
	public void publishPolitikNews(Newsmessage msg)
	{
		msg = markdownParser(msg);
		
		
		for(int i=0; i<receiverPolitikPointer; i++)
		{
			receiversPolitik[i].receivePolitikMessage(msg);
		}
		
		publishMessageForTicker(msg);
		
	}
	public void publishWirtschaftNews(Newsmessage msg)
	{
		
		msg = markdownParser(msg);
		
		
		for(int i=0; i<receiverWirtschaftPointer; i++)
		{
			receiversWirtschaft[i].receiveWirtschaftMessage(msg);
		}
		
		publishMessageForTicker(msg);
	}
	
	public void publishMessageForTicker(Newsmessage msg)
	{
		String msgtext = "<h2>" + msg.topic + "</h2><br><h3>" + msg.headline + "</h3><br>" + "\n" 
				+ msg.text + "<br><br><hr>";
		
		
		WebSocket connection = WebSocketServer.instance.lastClient;
		
		if(connection == null)
		{
			return;
		}
		
		try {
			connection.write(msgtext);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}


