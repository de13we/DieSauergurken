package de.fhdw.chitter;

public class Receiver implements Subscriber {

	// Generelle Funktion, um Message zu erhalten
	public void update(Newsmessage msg) {
		System.out.println(msg.topic + "nachricht erhalten" + msg);
	}
}
