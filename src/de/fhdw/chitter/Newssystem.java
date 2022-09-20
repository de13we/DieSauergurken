package de.fhdw.chitter;

import de.fhdw.chitter.exceptions.ResortDoesNotExistException;
import de.fhdw.chitter.extern.newsticker.WebSocketServer;
import org.atmosphere.websocket.WebSocket;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Newssystem implements Publisher {

	// Map, die die Receiver den jeweiligen Ressorts zuordnet
	public Map<String, ArrayList<Receiver>> resortsReceivers;

	// Liste für die Mitarbeiter (Staff)
	public ArrayList<Staff> staffList;

	// Konstruktor
	private Newssystem(DatabaseConnection databaseConnection) throws SQLException {
		staffList = (ArrayList<Staff>) databaseConnection.getStaff();
		List<String> resortsList = databaseConnection.getTopics();

		resortsReceivers = new HashMap<>();

		for (String topic: resortsList) {
			resortsReceivers.put(topic, new ArrayList<>());
		}
	}

	// Singleton Implementation
	private static Newssystem instance;
	public static Newssystem getInstance(){
		return instance;
	}

	static {
		try {
			instance = new Newssystem(new DatabaseConnection());
		} catch (SQLException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	// Markdown Parser fürs Umwandeln des Texts mit typischer Markdown Syntax
	private Newsmessage markdownParser(Newsmessage msg)
	{
		msg.setText(msg.getText().replaceAll("(?m)^#(?!#)(.*)", "<h1>$1</h1>"));
		msg.setText(msg.getText().replaceAll("(?m)^#{2}(?!#)(.*)", "<h2>$1</h2>"));
		msg.setText(msg.getText().replaceAll("(?m)^#{2}(?!#)(.*)", "<h3>$1</h3>"));
		
		
		msg.setText(msg.getText().replaceAll("\\*(.*)\\*", "<em>$1</em>"));
		msg.setText(msg.getText().replaceAll("\\*\\*(.*)\\*\\*", "<b>$1</b>"));
		
		msg.setText(msg.getText().replaceAll("(?m)^\\* (.*)$", "<li> $1 </li>"));
		
		return msg;
	}

	// Nachricht wird an den Newsticker weitergeleitet
	public void publishMessageForTicker(Newsmessage msg)
	{
		String msgtext = "<h2>" + msg.getTopics() + "</h2><br><h3>" + msg.getHeadline() + "</h3><br>" + "\n"
				+ msg.getText() + "<br><br><hr>";

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
	@Override
	public void subscribe(Receiver receiver, String resort) throws ResortDoesNotExistException {
		if (!isSubscribed(receiver, resort)) {
			resortsReceivers.get(resort).add(receiver);
			System.out.println(receiver + " hat sich fuer " + resort + " registriert.");
		}
	}

	// Methode fürs "Abmelden" des jeweiligen Ressorts
	@Override
	public void unsubscribe(Receiver receiver, String resort) throws ResortDoesNotExistException {
		if (isSubscribed(receiver, resort)){
			resortsReceivers.get(resort).remove(receiver);
			System.out.println("Benutzer ist nicht länger fuer " + resort + "registriert.");
		}
	}

	// Senden der Nachricht an alle Receiver, die für das Topic angemeldet sind
	@Override
	public void notifyObserver(Newsmessage msg) {
		msg = markdownParser(msg);

		for (String topic: msg.getTopics()) {
			for (Receiver receiver: resortsReceivers.get(topic)) {
				System.out.println("Dieser Benutzer erhält die Nachricht: " + receiver);
				receiver.update(msg);
			}
		}

		publishMessageForTicker(msg);
	}

	// Überprüft, ob der Receiver das Ressort abonniert hat
	private boolean containsReceiver(Receiver receiver, String resort){
		return resortsReceivers.get(resort).contains(receiver);
	}

	// Überprüft, ob das eingegebene Ressort existiert
	protected boolean existsResort(String resort){
		return resortsReceivers.containsKey(resort);
	}

	protected boolean isSubscribed(Receiver receiver, String resort) throws ResortDoesNotExistException {
		if (existsResort(resort)){
			if (containsReceiver(receiver, resort)){
				System.out.println("Benutzer ist bereits registriert.");
				return true;
			}
			return false;
		}
		else {
			throw new ResortDoesNotExistException();
		}
	}
}


