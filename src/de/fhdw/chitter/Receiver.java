package de.fhdw.chitter;

public class Receiver {

	public void receiveMessage(Newsmessage msg, String resort) {
		System.out.println(resort + "nachricht erhalten" + msg);
	}
	public void receiveSportMessage(Newsmessage msg)
	{
		System.out.println("Sportnachricht erhalten" + msg);
	}
	
	public void receivePolitikMessage(Newsmessage msg)
	{
		System.out.println("Politiknachricht erhalten" + msg);
	}
	
	public void receiveWirtschaftMessage(Newsmessage msg)
	{
		System.out.println("Wirtschaftsnachricht erhalten" + msg);
	}
	
}
