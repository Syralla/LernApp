package client;

import xml.Aufgabe;
import xml.FileBuilder;
import xml.Operations;

public class ClientMain {
	
	/** 
	 * über TestClient Aufgaben senden
	 * @param args
	 */

	public static void main(String[] args) {
		TestClient tc = new TestClient();
		
		FileBuilder builder = new FileBuilder();
		
		builder.addAufgabe(new Aufgabe(20,30, Operations.ADD));
		builder.addAufgabe(new Aufgabe(33,11, Operations.SUB));
		builder.addAufgabe(new Aufgabe(2,1, Operations.DIV));
		
		
		tc.sendAufgabe(builder);
		tc.helloworld();
		
	}
	
}
