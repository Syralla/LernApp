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
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
				case 4: //GetStatistic
					p.println(getstats(br));
					p.flush();
					p.close();
					break;
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
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
			zahl2 = ThreadLocalRandom.current().nextInt((int) (1) , zahl1 + 1);
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
		System.out.println(list.toString());
		
		
		//List[1-7] 1-4 art 5,6,7 plus/minus/mal und richtig 8 richrigallgemein 9 user 10 richtig und geteilt
			
		
		DBConnector db = new DBConnector();
		sql = "UPDATE Statistik SET gesamt = gesamt + 1, richtig = richtig + '" + list.get(7) + "', plusgesamt = plusgesamt + '" + list.get(0) + "', plusrichtig = plusrichtig +'" + list.get(4) + "', minusgesamt = minusgesamt + '" + list.get(1) + "', minusrichtig = minusrichtig +'" + list.get(5) + "', malgesamt = malgesamt + '" + list.get(2) + "', malrichtig = malrichtig +'" + list.get(6) + "', geteiltgesamt = geteiltgesamt + '" + list.get(3) + "', geteiltrichtig = geteiltrichtig +'" + list.get(9) + "' WHERE Statistik.username = '" + list.get(8) + "';";
		System.out.println(sql);
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
	
	
	private String getstats(BufferedReader br) throws IOException, SQLException, ParserConfigurationException, TransformerException{
		String xmlFile = "";
		String user = "";
		
		
		
		
		String line = null; 
		line = br.readLine();
			xmlFile = xmlFile + line;
			System.out.println("Line of stats Xml: " + line);
			
		
		
		
		
		
			
		
		
		
		user = line;
		
		
		String SQLIP = "jdbc:mysql://localhost:3306/Lernapp";
		String DBUSER = "root";
		 String DBPW = "LernApp";
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		String sql1 = "SELECT * FROM Statistik WHERE username = '" + user + "';";
		 DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder builder = factory.newDocumentBuilder();
		    Document doc = builder.newDocument();
		    Element results = doc.createElement("Results");
		    doc.appendChild(results);
		
		
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
		
		
		
		
		ResultSetMetaData rsmd = myRs.getMetaData();
	    int colCount = rsmd.getColumnCount();

	    while (myRs.next()) {
	      Element row = doc.createElement("statistic");
	      results.appendChild(row);
	      for (int i = 1; i <= colCount; i++) {
	        String columnName = rsmd.getColumnName(i);
	        Object value = myRs.getObject(i);
	        Element node = doc.createElement(columnName);
	        node.appendChild(doc.createTextNode(value.toString()));
	        row.appendChild(node);
	      }
	    }
	    DOMSource domSource = new DOMSource(doc);
	    TransformerFactory tf = TransformerFactory.newInstance();
	    Transformer transformer = tf.newTransformer();
	    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
	    transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	    transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
	    StringWriter sw = new StringWriter();
	    StreamResult sr = new StreamResult(sw);
	    transformer.transform(domSource, sr);

	    System.out.println(sw.toString());

	    myConn.close();
	    myRs.close();
	    System.out.println(sw.toString());
		
		return sw.toString();
		
		
	}
}
