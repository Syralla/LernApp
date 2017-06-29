package xml;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

public class XMLParser {

	public XMLParser() {
		// TODO Auto-generated constructor stub
	}

	public List<Aufgabe> parseAufgaben(String xmlFile){
		 
		List<Aufgabe> list = new ArrayList<Aufgabe>();
		 
		Document doc = Jsoup.parse(xmlFile, "", Parser.xmlParser());
		System.out.println(doc.select("aufgabe").size());
		for (Element e : doc.select("aufgabe")) {
			int zahl1 = 0;
			int zahl2 = 0;
			String op =  "";
			
			zahl1 = Integer.parseInt(e.select("zahl1").text());
			zahl2 = Integer.parseInt(e.select("zahl2").text());
			op = e.select("operation").text();
			
//		    System.out.println(zahl1);
//		    System.out.println(zahl2);
//		    System.out.println(op);
		 
		    list.add(new Aufgabe(zahl1, zahl2, parseOperation(op)));
		}
		
		return list;
	 }
	
	public List<String> parseRegister(String xmlFile){
		
		List<String> list = new ArrayList<String>();
		
		Document doc = Jsoup.parse(xmlFile, "", Parser.xmlParser());
		for (Element e : doc.select("register")) {
			String user = "";
			String pasw = "";
			String email =  "";
			
			user = e.select("user").text();
			pasw = e.select("pasw").text();
			email = e.select("email").text();
			

		 
		    list.add(new String(user));
		    list.add(new String(pasw));
		    list.add(new String(email));
		}
		
		
		
		return list;
	}
	
	public List<String> parseLogin(String xmlFile){
		
		List<String> list = new ArrayList<String>();
		
		Document doc = Jsoup.parse(xmlFile, "", Parser.xmlParser());
		for (Element e : doc.select("login")) {
			String user = "";
			String pasw = "";
			
			user = e.select("user").text();
			pasw = e.select("pasw").text();
			

		 
		    list.add(new String(user));
		    list.add(new String(pasw));
		}
		
		
		
		return list;
	}

	private Operations parseOperation(String opString) {

		switch (opString) {
		case "ADD":
			return Operations.ADD;
		case "SUB":
			return Operations.SUB;
		case "DIV":
			return Operations.DIV;
		case "MUL":
			return Operations.MUL;
		}
		return null;
	}

}
