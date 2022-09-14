package de.fhdw.chitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.atmosphere.websocket.WebSocket;

import de.fhdw.chitter.extern.newsticker.WebSocketServer;

import static java.util.Map.entry;

public class Newssystem {
	public Map<String, ArrayList<Receiver>> resortsReceivers = Map.ofEntries(
			entry("Sport", new ArrayList<>()),
			entry("Politik", new ArrayList<>()),
			entry("Wirtschaft", new ArrayList<>())
			);
	public ArrayList<Staff> staffList = new ArrayList();
	private Newssystem()
	{
		staffList.add(new Staff("Max", "passwort"));
		staffList.add(new Staff("Hans", "12345"));
		staffList.add(new Staff("John", "wer?"));
	}

	static Newssystem instance = new Newssystem();

	public void registerReceiver(Receiver receiver, String resort) {
		if(resortsReceivers.containsKey(resort)) {
			resortsReceivers.get(resort).add(receiver);
		}
		else {
			// wie fangen wir sowas ab oder bauen wir vorher ein System ein, damit diese Fehler gar nicht erst entstehen
		}
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
	public void publishNews(Newsmessage msg, String resort) {
		msg = markdownParser(msg);

		for (Receiver receiver: resortsReceivers.get(resort)) {
			receiver.receiveMessage(msg);
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


