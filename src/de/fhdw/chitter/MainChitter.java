package de.fhdw.chitter;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import de.fhdw.chitter.extern.RestAPIServer;
import de.fhdw.chitter.extern.newsticker.WebSocketServer;

public class MainChitter {

	public static void main(String[] args) {
		
		// Erstellt den Ordner "Data", falls dieser nicht existert
		new File("data").mkdirs();
		
		// Startet das Newssystem
		Newssystem newssystem = Newssystem.getInstance();

		// Startet den externen Restapi Server
		RestAPIServer server = new RestAPIServer("127.0.0.1", 8080);
		server.start();

		// Startet den Websocketserver
		WebSocketServer.getInstance().start();

		// Startet die verschiedenen GUIs
		new AdminGUI();
		new StaffGUI();
		new ReceiverGUI();

		// Programm läuft biss es mit Q in der Konsole oder anderweitig beendet wird
		BufferedReader readUConsole = new BufferedReader(new InputStreamReader(System.in));
		while(true)
		{
			System.out.println("Q zum beenden");
			
			try
			{
			String userinput = readUConsole.readLine();
			
				if(userinput.startsWith("q"))
				{
					System.exit(0);
				}
			} catch (Exception e) {
				System.out.println("Unknown execption " + e);
			}
			
		}
	}
}

