package de.fhdw.chitter.extern.newsticker;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.concurrent.CompletionStage;

public class WebSocketClient implements WebSocket.Listener{
	
	NewstickerGUI gui;
	WebSocket ws;
	
	public WebSocketClient(NewstickerGUI gui) {
		this.gui = gui;
				
		this.ws = HttpClient
                .newHttpClient()
                .newWebSocketBuilder()
                .buildAsync(URI.create("ws://localhost:8081"), this)
                .join();
		
	}
	
	public void onOpen(WebSocket webSocket) {
        System.out.println("onOpen using subprotocol " + webSocket.getSubprotocol());
        webSocket.sendText("Hello from Newsticker Client", false);
        WebSocket.Listener.super.onOpen(webSocket);
    }

    @Override
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
        gui.txtText.append("### BEGIN #############\n");
		gui.txtText.append(data + "\n");
		gui.txtText.append("### END ###############\n");
		 
        return  WebSocket.Listener.super.onText(webSocket, data, last);
    }

    @Override
    public void onError(WebSocket webSocket, Throwable error) {
        System.out.println("onError: Bad day! " + webSocket.toString());
        WebSocket.Listener.super.onError(webSocket, error);
    }
}
