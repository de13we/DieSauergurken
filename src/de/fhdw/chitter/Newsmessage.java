package de.fhdw.chitter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Newsmessage {

	private String date;
	private String author;
	private List<String> topics;
	private String headline;
	private String text;

	public Newsmessage(String date, String author, List<String> topics, String headline, String text) {
		this.date = date;
		this.author = author;
		this.topics = topics;
		this.headline = headline;
		this.text = text;
	}

	public Newsmessage() {

	}

	// Schreibt die veröffentlichte Nachricht in eine txt-Datei
	public void writeToFile(String filename)
	{
		try {
		      FileWriter myWriter = new FileWriter(filename);
		      myWriter.write(date);
		      myWriter.write("\n");
		      myWriter.write(author);
		      myWriter.write("\n");
		      myWriter.write(topics.toString());
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

	// Liest die Datei
	public void readFromFile(String filename)
	{
		try {
		      BufferedReader myReader = new BufferedReader(new FileReader(filename));
		      this.date = myReader.readLine();
		      this.author = myReader.readLine();
			  this.topics = getTopicsFromList(myReader.readLine());
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

	private List<String> getTopicsFromList(String topicListUnformatted) {
		topicListUnformatted = topicListUnformatted.replaceAll("^\\[", "");
		topicListUnformatted = topicListUnformatted.replaceAll("\\]", "");
		List<String> list_with_topics = new ArrayList<>();
		Arrays.stream(topicListUnformatted.split(",")).forEach(topic -> list_with_topics.add(topic.replaceAll("^\\s", "")));

		return list_with_topics;
	}

	public String getDate() {
		return date;
	}

	public String getAuthor() {
		return author;
	}

	public List<String> getTopics() {
		return topics;
	}

	public String getHeadline() {
		return headline;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}


