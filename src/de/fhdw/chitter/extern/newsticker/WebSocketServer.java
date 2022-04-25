package de.fhdw.chitter.extern.newsticker;

import java.io.File;
import java.io.IOException;

import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.nettosphere.Config;
import org.atmosphere.nettosphere.Handler;
import org.atmosphere.nettosphere.Nettosphere;
import org.atmosphere.websocket.WebSocket;
import org.atmosphere.websocket.WebSocketHandler;
import org.atmosphere.websocket.WebSocketProcessor.WebSocketException;

import de.fhdw.chitter.Newsmessage;

//https://github.com/Atmosphere/nettosphere

public class WebSocketServer implements WebSocketHandler {

	private Nettosphere server;
	
	public WebSocket lastClient;
	
	public static WebSocketServer instance = new WebSocketServer("127.0.0.1", 8081);;
	
	private WebSocketServer(String address, int port) {
		server = new Nettosphere.Builder().config(
                new Config.Builder()
                .host(address)
                .port(port)
                .resource("/*", this)
                .build())
             .build();

		System.out.println("WebSocket Server active, waiting for clients");
	}
	
	public void start() {
		server.start();
	}
	
	@Override
	public void onByteMessage(WebSocket webSocket, byte[] data, int offset, int length) throws IOException {
		System.out.println("onByteMessage" + data);
	}

	@Override
	public void onClose(WebSocket webSocket) {
		System.out.println("onClose" + webSocket);
		
		lastClient = null;
	}
	
	@Override
	public void onError(WebSocket webSocket, WebSocketException t) {
		System.out.println("onError" + webSocket);
	}
	
	@Override
	public void onOpen(WebSocket webSocket) throws IOException {
		System.out.println("onOpen " + webSocket);
		
		lastClient = webSocket;
		
		lastClient.write("Hello Client from Server");
	}
	
	@Override
	public void onTextMessage(WebSocket webSocket, String data) throws IOException {
		System.out.println("onTextMessage" + data);
	}

}
