package de.fhdw.chitter;

public class Receiver implements Subscriber {
	ReceiverGUI receiverGUI;
	public Receiver (ReceiverGUI receiverGUI){
		this.receiverGUI = receiverGUI;
	}

	// Generelle Funktion, um Message zu erhalten
	public void update(Newsmessage msg) {
		System.out.println(msg.getMainTopic() + "-Nachricht erhalten");
		receiverGUI.printNewsmessage(msg);
	}
}
