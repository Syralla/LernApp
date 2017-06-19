package de.derandroidpro.sendtexttoservertutorial;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;


public class TestClient {

	private String serverIP = "192.168.0.16";		// Surface IP, da Emulator Vm
	private int port = 8818;

	public TestClient() {
		// TODO Auto-generated constructor stub
	}
/**
 * Verbindung zu Server aufbauen �ber (Server)Socket mit Port und IP
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
                if(so != null){
                    so.close();
                }

			} catch (IOException e) {
				// ignore
			}

			if(pw != null){
                pw.close();
            }


		}

	}
	/**
	 * �ber Interface Aufgaben erstellen (action) und in String List abspeichern,
	 * danach connectToServer --> L�cke schlie�en
	 * @param builder
	 */
	public void sendAufgabe(final FileBuilder builder) {

        System.out.println("Sende Aufgabe");

		Runnable r = new Runnable(){

			@Override
			public void run() {
				NetworkAction action = new NetworkAction() {

					@Override
					public void performAction(PrintWriter pw) {

						List<String> lines = builder.buildFileAsStringArray();

						for (String line : lines) {
							pw.println(line);
						}

					}
				};

				connectToServer(action);	// Interface in "L�cke" speichern
			}
		};

    Thread t = new Thread(r);
        t.start();

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
