package youngtalents;


import junit.framework.Test;

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
	public static boolean ret = false;
	public static String tasks = "";
	public static String stat = "";

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
			so.setSoTimeout(10000);

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
	 *
	 */
	public String sendAufgabe(final int spez) {

		String ret = "";

		Runnable r = new Runnable(){

			@Override
			public void run() {
				NetworkAction action = new NetworkAction() {

					@Override
					public void performAction(PrintWriter pw, BufferedReader br) throws IOException {



						pw.println(0);//heist erstelle Aufgaben
						pw.flush();
						pw.println(spez);
						TestClient.tasks = br.readLine();


					}
				};

				connectToServer(action);	// Interface in "L�cke" speichern
			}
		};

    Thread t = new Thread(r);
        t.start();
		while (t.isAlive()){

		}

		return TestClient.tasks;

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

	public String getStatisticsFromServer(final String user){




		Runnable r = new Runnable(){

			@Override
			public void run() {
				TestClient.stat = "";
				NetworkAction action = new NetworkAction() {

					@Override
					public void performAction(PrintWriter pw, BufferedReader br) throws IOException {
						pw.println(4);//heist get Statistic
						pw.flush();
						pw.println(user);
						pw.flush();

						String in = br.readLine();
						TestClient.stat = in;



					}
				};
				connectToServer(action);	// Interface in "L�cke" speichern
				System.out.println(TestClient.ret);




			}

		};

		Thread t = new Thread(r);
		t.start();
		while (t.isAlive()){

		}

		return TestClient.stat;
	}


	public boolean Login(final List<String> line) throws IOException {

		System.out.println("Sende Login");


		Runnable r = new Runnable(){

			@Override
			public void run() {
				TestClient.ret = false;
				NetworkAction action = new NetworkAction() {

					@Override
					public void performAction(PrintWriter pw, BufferedReader br) throws IOException {
						pw.println(2);//heist sende Login
						pw.flush();
						pw.println(line);
						pw.flush();
						String in = br.readLine();
						System.out.println(in);
						System.out.println("in test");
							if (in.contains("true") == true) {
								System.out.println(TestClient.ret);
								TestClient.ret = true;
								System.out.println(TestClient.ret);


							} else {

							}



					}
				};
				connectToServer(action);	// Interface in "L�cke" speichern
				System.out.println(TestClient.ret);




			}

		};
		System.out.println("test return");
		Thread t = new Thread(r);
		t.start();
		while (t.isAlive()){

		}

		return TestClient.ret;
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

    public String getTask(final String spez) {
		System.out.println("Sende spez");

		Runnable r = new Runnable(){

			@Override
			public void run() {
				NetworkAction action = new NetworkAction() {

					@Override
					public void performAction(PrintWriter pw, BufferedReader br) throws IOException {



						pw.println(0);//heist hole Aufgabe
						pw.flush();
						pw.println(spez);
						pw.flush();
						System.out.println(spez + "testin");

						String in = br.readLine();
						System.out.println(in + "testout");
						TestClient.tasks = in;



					}
				};

				connectToServer(action);	// Interface in "L�cke" speichern
			}
		};

		Thread t = new Thread(r);
		t.start();
		while (t.isAlive()){

		}


		return TestClient.tasks;
    }


	public void check( final String spez, final boolean check, final String user){
		System.out.println("Sende spez");
		final String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<check><spez>" + spez + "</spez><c>" + check + "</c><user>" + user + "</user></check>";
		System.out.println(xml);

		Runnable r = new Runnable(){

			@Override
			public void run() {
				NetworkAction action = new NetworkAction() {

					@Override
					public void performAction(PrintWriter pw, BufferedReader br) throws IOException {



						pw.println(1);//sende Ergebnisse
						pw.flush();
						pw.println(xml);
						pw.flush();






					}
				};

				connectToServer(action);	// Interface in "L�cke" speichern
			}
		};

		Thread t = new Thread(r);
		t.start();



	}
}
