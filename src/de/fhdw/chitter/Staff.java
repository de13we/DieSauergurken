package de.fhdw.chitter;

public class Staff {

	private String name;
	
	private String passwort;

	public Staff(String name, String passwort){
		this.name = name;
		this.passwort = passwort;
	}

	public String getName() {
		return name;
	}

	public String getPasswort() {
		return passwort;
	}
}
