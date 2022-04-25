package de.fhdw.chitter.extern.newsticker;

import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandshaker;

public class MainNewsticker {
	
	public static void main(String[] args) throws InterruptedException {
		NewstickerGUI client = new NewstickerGUI();		
		
		WebSocketClient wsclient = new WebSocketClient(client);
		
	}
	
}
