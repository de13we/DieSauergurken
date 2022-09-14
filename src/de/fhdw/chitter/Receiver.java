package de.fhdw.chitter;

public class Receiver {

	// Generelle Funktion, um Message zu erhalten
	public void receiveMessage(Newsmessage msg) {
		System.out.println(msg.topic + "nachricht erhalten" + msg);
	}
}
