package core.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import core.Configuration;
import core.Queue;
import xml.Aufgabe;
import xml.XMLParser;

public class AppListener {

	private Queue queue = null;
	private int port = Configuration.serverPort;

	public AppListener(Queue queue) throws SQLException {
		this.queue = queue;
		runDummyServer();
	}

	private void runDummyServer() throws SQLException {

		ServerSocket seso = null;
		Socket so = null;

		System.out.println("Running Server");
		System.out.println("Running server on port " + port);

		String xmlFile = "";

		PrintWriter pw = null;
		BufferedReader br = null;

		try {
			System.out.println("Opning ServerSocket");
			seso = new ServerSocket(port);

			while (true) {
				System.out.println("Waiting for accept");
				so = seso.accept();

				System.out.println("Accepted");
				System.out.println("Client ip is " + so.getRemoteSocketAddress());

				br = new BufferedReader(new InputStreamReader(so.getInputStream()));
				pw = new PrintWriter(so.getOutputStream());
				PrintStream p = new PrintStream(so.getOutputStream());
				PrintStream q = new PrintStream(so.getOutputStream());

				int command = Integer.parseInt(br.readLine()); // TODO
																// Gescheiter
																// Stream

				System.out.println("Command ist " + command);

				switch (command) {
				case 0:	//DisplayTasks
					
					
					 
					
					
					q.println(getTask(br));
					q.flush();
					q.close();
					break;
				case 1: //PushStatistic
					pushStatistics(br);
					break;
				case 2: //Login
					
					if(Login(br) == true){
						
						p.println("true");
						System.out.println("true");
					}
					else{
						p.println("false");
						System.out.println("false");
					}
						p.flush();
						p.close();
					
					break;
				case 3: //Register
					Register(br);
					break;
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

			if (pw != null) {
				pw.close();
			}
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
				}
			}
		}

	}

	

	private String getTask(BufferedReader br) throws IOException {

		
		String xml = "";
		String line = br.readLine();
		int spez;
		String op;
		int st;
		int zahl1;
		int zahl2;
		ArrayList<String> operations = new ArrayList<String>();
		ArrayList<Integer> stellen = new ArrayList<Integer>();
		//for (String lin = null; (lin = br.readLine()) != null;){
		//	line = line + lin;
		//	System.out.println("Line of Register Xml: " + lin);
			
		//}
		if(line.length() >= 5){
			
		
		System.out.println("Line of XML: " + line);
		System.out.println("Char at 2" + line.charAt(2));
		if(line.charAt(1) == '1'){ //Wenn die zweite Stelle 1 ist wird plus mit eingefügt
			operations.add("plus");
		}
		if(line.charAt(2) == '1'){ //Wenn die dritte Stelle 1 ist wird minus mit eingefügt
			System.out.println("test minus");
			operations.add("minus");
		}
		if(line.charAt(3) == '1'){ //Wenn die vierte Stelle 1 ist wird mal mit eingefügt
			operations.add("mult");
		}
		if(line.charAt(4) == '1'){ //Wenn die fünfte Stelle 1 ist wird geteilt mit eingefügt
			operations.add("div");
		}
		if(line.charAt(5) == '1'){ //Wenn die sechste Stelle 1 ist wird einstellig mit eingefügt
			stellen.add(10);
		}
		if(line.charAt(6) == '1'){ //Wenn die siebte Stelle 1 ist wird zweistellig mit eingefügt
			stellen.add(100);
		}
		if(line.charAt(7) == '1'){ //Wenn die achte Stelle 1 ist wird dreistellig mit eingefügt
			stellen.add(1000);
		}
		System.out.println("operations" + operations.size());
		System.out.println("stellen" + stellen.size());
		op = operations.get(ThreadLocalRandom.current().nextInt(0, operations.size()) ); //nimm eine random funktion
		st = stellen.get(ThreadLocalRandom.current().nextInt(0, stellen.size())  ); //nimm eine random stellen anzahl
		
		zahl1 = 0;
		zahl2 = 0;
		switch (op) {
		case "plus":
			zahl1 = ThreadLocalRandom.current().nextInt((int) (st * 0.1) , st);
			zahl2 = ThreadLocalRandom.current().nextInt((int) (st * 0.1) , st);
			break;
		case "minus" : 
			zahl1 = ThreadLocalRandom.current().nextInt((int) (st * 0.1) , st);
			zahl2 = ThreadLocalRandom.current().nextInt((int) (st * 0.1) , zahl1);
			break;
		case "mult" : 
			zahl1 = ThreadLocalRandom.current().nextInt((int) (st * 0.1) , st);
			zahl2 = ThreadLocalRandom.current().nextInt((int) (st * 0.1) , st);
			
			
			break;
		case "div" : 
			if(st >= 100){
				st = 10;
			}
			zahl2 = ThreadLocalRandom.current().nextInt((int) (st * 0.1) , (st / 2));
			zahl1 = (ThreadLocalRandom.current().nextInt((int) (st * 0.1) , st) * zahl2);
			break;
		}
		
		xml = xml + "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
		xml = xml + "<aufgabe><zahl1>" + zahl1 + "</zahl1><zahl2>" + zahl2 + "</zahl2><op>" + op + "</op></aufgabe>";
		}
		
		return xml;

	}

	private void pushStatistics(BufferedReader br) throws IOException, SQLException {
		
		String xmlFile = "";
		String user = "";
		
		String sql = "";
		
		for (String line = null; (line = br.readLine()) != null;){
			xmlFile = xmlFile + line;
			System.out.println("Line of Register Xml: " + line);
			
		}
		
		XMLParser parser = new XMLParser();
		List<String> list = parser.parsepushstat(xmlFile);
		
		
		//List[1-7] spezifizieren die Art der Aufgabe und [8] ist 1=richtig und 0 = falsch und [9] ist der username
			
		
		DBConnector db = new DBConnector();
		sql = "UPDATE Statistic SET gesamt = gesamt + 1, richtig = richtig + '" + list.get(8) + "', plusgesamt = plusgesamt + '" + list.get(0) + "', plusrichtig = plusrichtig +'" + list.get(8) + "', minusgesamt = minusgesamt + '" + list.get(1) + "', minusrichtig = minusrichtig +'" + list.get(8) + "', malgesamt = malgesamt + '" + list.get(2) + "', malrichtig = malrichtig +'" + list.get(8) + "', geteiltgesamt = geteiltgesamt + '" + list.get(3) + "', geteiltrichtig = geteiltrichtig +'" + list.get(8) + "' WHERE Statistik.username = '" + list.get(9) + "';";
	     db.insert(sql);
		
		
	}
		
		

	
	//Login Methode
	private boolean Login(BufferedReader br) throws IOException, SQLException{
		String xmlFile = "";
		String user = "";
		String pasw = "";
		System.out.println("trying to login");
		
		
		String line = null; 
		line = br.readLine();
			xmlFile = xmlFile + line;
			System.out.println("Line of Login Xml: " + line);
			System.out.println("eigentlich login line obendrüber");
		
		
		
		if(xmlFile.length() <= 2){
			return false;
		}
		else{
			
		
		XMLParser parser = new XMLParser();
		List<String> list = parser.parseLogin(xmlFile);
		
		user = list.get(0);
		pasw = list.get(1);
		
		String SQLIP = "jdbc:mysql://localhost:3306/Lernapp";
		String DBUSER = "root";
		 String DBPW = "LernApp";
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		String sql1 = "SELECT * FROM user WHERE user = '" + user + "' AND pasw = '" + pasw + "';";
		
		
		try {
			// 1. Get a connection to database
			myConn = DriverManager.getConnection(SQLIP, DBUSER , DBPW);
			
			// 2. Create a statement
			myStmt = myConn.createStatement();
			
			// 3. Execute SQL query
			myRs = myStmt.executeQuery(sql1);
			
			// 4. Process the result set
			
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
		
		
		
		
		if (myRs.next() == true){ //Wenn ein Resultset vorhanden ist wird ein success zurück gesendet an die App
			
			return true;
			
			
		}else{
			return false;
		}
		
		}
		
		
	}
	
	private void Register(BufferedReader br) throws IOException, SQLException{
		String xmlFile = "";
		String user = "";
		String pasw = "";
		String email = "";
		String sql = "";
		
		for (String line = null; (line = br.readLine()) != null;){
			xmlFile = xmlFile + line;
			System.out.println("Line of Register Xml: " + line);
			
		}
		
		XMLParser parser = new XMLParser();
		List<String> list = parser.parseRegister(xmlFile);
		
		user = list.get(0);
		pasw = list.get(1);
		email = list.get(2);
		
		if(user != null && pasw != null && email != null){
			
		
		DBConnector db = new DBConnector();
		sql = "INSERT INTO user (user, pasw, email) VALUES ('" + user + "', '" + pasw + "', '" + email + "');";
		db.insert(sql);
		sql = "INSERT INTO Statistik (username) VALUES ('" + user + "');";
		db.insert(sql);
		}
		
	}
}
