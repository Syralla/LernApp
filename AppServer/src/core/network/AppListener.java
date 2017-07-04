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
import java.util.List;

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

				int command = Integer.parseInt(br.readLine()); // TODO
																// Gescheiter
																// Stream

				System.out.println("Command ist " + command);

				switch (command) {
				case 0:	//DisplayTasks
					readTasks(br);
					break;
				case 1: //PushStatistic
					//pushStatistics(pw);
					break;
				case 2: //Login
					PrintStream p = new PrintStream(so.getOutputStream());
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

	

	private void readTasks(BufferedReader br) throws IOException {

		String xmlFile = "";

		for (String line = null; (line = br.readLine()) != null;) {
			xmlFile = xmlFile + line;
			System.out.println("Line of XML: " + line);
		}

		XMLParser parser = new XMLParser();
		List<Aufgabe> aufgaben = parser.parseAufgaben(xmlFile);

		queue.handleNewTasks(aufgaben);

	}

	private void pushStatistics(PrintWriter pw) {

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
		}
		
	}
}
