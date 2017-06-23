package YoungTalents;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import YoungTalents.Configuration;
import YoungTalents.Queue;
import YoungTalents.Aufgabe;
import YoungTalents.XMLParser;

public class AppListener {

	private Queue queue = null;
	private int port = Configuration.serverPort;

	public AppListener(Queue queue) {
		this.queue = queue;
		runDummyServer();
	}	

	private void runDummyServer() {

		ServerSocket seso = null;
		Socket so = null;
		
		String xmlFile = "";
		
		System.out.println("Running Server");
		PrintWriter pw = null;
		BufferedReader br = null;
		
		
		try {
			System.out.println("Opning ServerSocket");
			seso = new ServerSocket(port);

			
			while (true) {
				System.out.println("Waiting for accept");
				so = seso.accept();
				
				System.out.println("Accepted");
				br = new BufferedReader(new InputStreamReader(so.getInputStream()));
				pw = new PrintWriter(so.getOutputStream());
				
				int command = Integer.parseInt(br.readLine()); //TODO Gescheiter Stream
				
				System.out.println("Command ist " + command);
				
				switch(command){
				case 0 : readTasks(br); break;
				case 1 : pushStatistics(pw); ; break;
				}
				
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				seso.close();
			} catch (IOException e) {
				// ignore
			}
			
			if(pw != null){
				pw.close();
			}
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
				}
			}
		}

	}

	private void readTasks(BufferedReader br) throws IOException{
		
		String xmlFile = "";
		
		for(String line = null; (line = br.readLine()) != null;){
			xmlFile = xmlFile + line;
		}
		
		XMLParser parser = new XMLParser();
		List<Aufgabe> aufgaben = parser.parseAufgaben(xmlFile);
		
		queue.handleNewTasks(aufgaben);
		
		
	}
	
	private void pushStatistics(PrintWriter pw){
		
	}
}
