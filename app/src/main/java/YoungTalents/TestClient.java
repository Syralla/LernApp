package youngtalents;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;


public class TestClient {

	//private String serverIP = "simon-f.com";
	private String serverIP = "simon-f.com";// Surface IP, da Emulator Vm
	private int port = 8080;

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
		BufferedReader br = null;

		try {

			so = new Socket(serverIP, port);

			pw = new PrintWriter(so.getOutputStream());
			br = new BufferedReader(new InputStreamReader(so.getInputStream()));

			action.performAction(pw, br);

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

			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					//ignore
				}
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
					public void performAction(PrintWriter pw, BufferedReader br) {

						List<String> lines = builder.buildFileAsStringArray();

						pw.println(0);//heist sende Aufgabe
						pw.flush();

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

	public void recieveStatistic() {

		System.out.println("Ziehe Statistik");

		Runnable r = new Runnable(){

			@Override
			public void run() {
				NetworkAction action = new NetworkAction() {

					@Override
					public void performAction(PrintWriter pw, BufferedReader br) {



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
			public void performAction(PrintWriter pw, BufferedReader br) {
				// TODO Auto-generated method stub
				
				String hello = "helloworld";
				
				pw.println(hello);
				
			}
		};
		connectToServer(action);
	}

	public Statistics getStatisticsFromServer(){

		NetworkAction action = new NetworkAction() {
			@Override
			public void performAction(PrintWriter pw, BufferedReader br) {

				pw.print(1);//heist erwarte Statistik



			}
		};
	return null;
	}


	public void Login(final List<String> line) {

		System.out.println("Sende Login");

		Runnable r = new Runnable(){

			@Override
			public void run() {
				NetworkAction action = new NetworkAction() {

					@Override
					public void performAction(PrintWriter pw, BufferedReader br) {



						pw.println(2);//heist sende Login
						pw.flush();
						pw.println(line);


					}
				};

				connectToServer(action);	// Interface in "L�cke" speichern
			}
		};

		Thread t = new Thread(r);
		t.start();

	}


	public void Register(final List<String> line) {

		System.out.println("Sende Login");

		Runnable r = new Runnable(){

			@Override
			public void run() {
				NetworkAction action = new NetworkAction() {

					@Override
					public void performAction(PrintWriter pw, BufferedReader br) {



						pw.println(3);//heist sende Register
						pw.flush();
						pw.println(line);


					}
				};

				connectToServer(action);	// Interface in "L�cke" speichern
			}
		};

		Thread t = new Thread(r);
		t.start();

	}

}
