package de.fhdw.chitter;

import java.util.ArrayList;

public class Staff {

	final private String name;
	final private String password;

	// Konstruktor
	public Staff(String name, String password){
		this.name = name;
		this.password = password;
	}

	// Getter und Setter
	public String getName() {
		return name;
	}
	public String getPassword() {
		return password;
	}

	// Überprüfen des Passworts
	protected static boolean checkCredentials(String username, String password){
		ArrayList<Staff> stafflist = Newssystem.getInstance().staffList;

		for (Staff s : stafflist) {
			if (s != null) {
				if (s.getName().equals(username)) {
					if (s.getPassword().equals(password)) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
