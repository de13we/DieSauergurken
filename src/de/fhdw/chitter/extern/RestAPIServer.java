package de.fhdw.chitter.extern;

import java.io.File;
import java.io.IOException;

import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.nettosphere.Config;
import org.atmosphere.nettosphere.Handler;
import org.atmosphere.nettosphere.Nettosphere;

import de.fhdw.chitter.Newsmessage;

//https://github.com/Atmosphere/nettosphere

public class RestAPIServer implements Handler {

	private Nettosphere server;
	
	public RestAPIServer(String address, int port) {
		server = new Nettosphere.Builder()
				.config(new Config.Builder()
						.host(address)
						.port(port)
						.resource(this).build()).build();

	}
	
	public void start() {
		server.start();
	}
	
	
	@Override
	public void handle(AtmosphereResource r) {
		// 
		try {
			String path = r.getRequest().getPathInfo();

			// extract topic from path
			String topic = path.split("/")[1].toLowerCase();
			
			// build response text
			String[] files = new File("data").list();
			
			StringBuffer response = new StringBuffer();
			response.append("<html><body>");
			for (String f : files)
			{
				Newsmessage msg = new Newsmessage();
				msg.readFromFile("data/" + f);
				
				if(msg.topic.toLowerCase().equals(topic))
				{
					
					response.append(msg.headline + "[" + msg.topic + "]\n" + msg.text + "\n(" + msg.author + ")\n");
					response.append("<br><br>");
				}
					
			}
			
			response.append("</body></html>");
			
			
			// write text message to response
			r.getResponse().write(response.toString()).flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
