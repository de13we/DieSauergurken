package de.fhdw.chitter;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import de.fhdw.chitter.extern.RestAPIServer;
import de.fhdw.chitter.extern.newsticker.WebSocketServer;

public class MainChitter {

	public static void main(String[] args) {
		
		// run on deployment
		//new File("data").mkdirs();
		
		// start newsserver
		Newssystem newssystem = Newssystem.instance;
		
		
		// start external server api
		RestAPIServer server = new RestAPIServer("127.0.0.1", 8080);
		server.start();
		
		WebSocketServer.instance.start();
		
		
		// start gui
		new AdminGUI();
		new StaffGUI();
		new ReceiverGUI();
		
		
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

