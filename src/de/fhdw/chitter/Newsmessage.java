package de.fhdw.chitter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Newsmessage {

	public String date;
	public String author;
	public String topic;
	public String headline;
	public String text;
	
	
	public void writeToFile(String filename)
	{
		try {
		      FileWriter myWriter = new FileWriter(filename);
		      myWriter.write(date);
		      myWriter.write("\n");
		      myWriter.write(author);
		      myWriter.write("\n");
		      myWriter.write(topic);
		      myWriter.write("\n");
		      myWriter.write(headline);
		      myWriter.write("\n");
		      myWriter.write(text);
		      myWriter.write("\n");
		      
		      myWriter.close();
		      System.out.println("Successfully write to the file.");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}
	
	public void readFromFile(String filename)
	{
		try {
		      BufferedReader myReader = new BufferedReader(new FileReader(filename));
		      this.date = myReader.readLine();
		      this.author = myReader.readLine();
		      this.topic = myReader.readLine();
		      this.headline = myReader.readLine();
		      
		      StringBuffer msgBuffer = new StringBuffer();
		      String line = myReader.readLine();
		      while(line != null)
		      {
		    	  msgBuffer.append(line);
		    	  line = myReader.readLine();
		      }
		      this.text = msgBuffer.toString();
		      
		      System.out.println("Successfully read from file.");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}
}


