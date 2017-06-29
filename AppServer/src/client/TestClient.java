package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import xml.Aufgabe;
import xml.FileBuilder;
import xml.Operations;

public class TestClient {

	private String serverIP = "127.0.0.1";
	private int port = 8818;

	public TestClient() {
		// TODO Auto-generated constructor stub
	}
/**
 * Verbindung zu Server aufbauen über (Server)Socket mit Port und IP
 * @param action
 */
	public void connectToServer(NetworkAction action) {

		Socket so = null;
		PrintWriter pw = null;

		try {

			so = new Socket(serverIP, port);

			pw = new PrintWriter(so.getOutputStream());

			action.performAction(pw);

			pw.flush();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				so.close();
			} catch (IOException e) {
				// ignore
			}

			pw.close();

		}

	}
	/**
	 * über Interface Aufgaben erstellen (action) und in String List abspeichern,
	 * danach connectToServer --> Lücke schließen
	 * @param builder
	 */
	public void sendAufgabe(final FileBuilder builder) {

		System.out.println("Send aufgabe");
		
		NetworkAction action = new NetworkAction() {

			@Override
			public void performAction(PrintWriter pw) {

				List<String> lines = builder.buildFileAsStringArray();

				for (String line : lines) {
					pw.println(line);
				}

			}
		};

		connectToServer(action);	// Interface in "Lücke" speichern

	}

	public void helloworld() {

		NetworkAction action = new NetworkAction() {

			@Override
			public void performAction(PrintWriter pw) {
				// TODO Auto-generated method stub
				
				String hello = "helloworld";
				
				pw.println(hello);
				
			}
		};
		connectToServer(action);
	}

}
