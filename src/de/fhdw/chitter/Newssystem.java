package de.fhdw.chitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.atmosphere.websocket.WebSocket;

import de.fhdw.chitter.extern.newsticker.WebSocketServer;

import static java.util.Map.entry;

public class Newssystem implements Publisher {


	// Map, die die Receiver den jeweiligen Ressorts zuordnet
	public Map<String, ArrayList<Receiver>> resortsReceivers = Map.ofEntries(
			entry("Sport", new ArrayList<>()),
			entry("Politik", new ArrayList<>()),
			entry("Wirtschaft", new ArrayList<>())
			);

	// Liste für die Mitarbeiter (Staff)
	public ArrayList<Staff> staffList = new ArrayList();

	// Konstruktor
	private Newssystem()
	{
		staffList.add(new Staff("Max", ""));
		staffList.add(new Staff("Hans", "12345"));
		staffList.add(new Staff("John", "wer?"));
	}

	// Singleton Implementation
	private static Newssystem instance = new Newssystem();
	public static Newssystem getInstance(){
		return instance;
	}


	// Markdown Parser fürs Umwandeln des Texts mit typischer Markdown Syntax
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

	// Nachricht wird an den Newsticker weitergeleitet
	public void publishMessageForTicker(Newsmessage msg)
	{
		String msgtext = "<h2>" + msg.topic + "</h2><br><h3>" + msg.headline + "</h3><br>" + "\n" 
				+ msg.text + "<br><br><hr>";

		WebSocket connection = WebSocketServer.getInstance().lastClient;
		
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


	// Methode fürs "Registrieren" des jeweiligen Ressorts
	//@Override
	public void subscribe(Receiver receiver, String resort) {
		if (!isSubscribed(receiver, resort)) {
			resortsReceivers.get(resort).add(receiver);
			System.out.println(receiver + " hat sich fuer " + resort + " registriert.");
		}
	}

	@Override
	public void unsubscribe(Receiver receiver, String resort) {
		if (isSubscribed(receiver, resort)){
			resortsReceivers.get(resort).remove(receiver);
			System.out.println("Benutzer ist nicht länger fuer " + resort + "registriert.");
		}
	}

	// Senden der Nachricht an alle Receiver, die für das Topic angemeldet sind
	@Override
	public void notifyObserver(Newsmessage msg) {
		msg = markdownParser(msg);
		System.out.println("Was kommt hier raus: " + msg.topic);
		System.out.println(resortsReceivers.get(msg.topic));

		for (Receiver receiver: resortsReceivers.get(msg.topic)) {
			System.out.println("DIESER BENUTZER KRIEGT DIE NACHRICHT: " + receiver);
			receiver.update(msg);

		}

		publishMessageForTicker(msg);
	}

	// Überprüft, ob der Receiver das Ressort abonniert hat
	private boolean containsReceiver(Receiver receiver, String resort){
		return resortsReceivers.get(resort).contains(receiver);
	}

	// Überprüft, ob das eingegebene Ressort eingetragen ist
	private boolean existsResort(String resort){
		return resortsReceivers.containsKey(resort);
	}

	private boolean isSubscribed(Receiver receiver, String resort){
		if (existsResort(resort)){
			if (containsReceiver(receiver, resort)){
				System.out.println("Benutzer ist bereits registriert.");
				return true;
			}
		}
		else {
			System.out.println("Ressort existiert nicht.");
		}
		return false;
	}
}


