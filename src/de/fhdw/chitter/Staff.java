package de.fhdw.chitter;

public class Staff {

	private String name;
	private String passwort;

	// Konstruktor
	public Staff(String name, String passwort){
		this.name = name;
		this.passwort = passwort;
	}

	// Getter und Setter
	public String getName() {
		return name;
	}
	public String getPasswort() {
		return passwort;
	}
}
