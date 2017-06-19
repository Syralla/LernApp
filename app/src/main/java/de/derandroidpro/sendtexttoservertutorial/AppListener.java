package de.derandroidpro.sendtexttoservertutorial;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class AppListener {

	String ip = "127.0.0.1";
	int port = 8818;

	public AppListener() {
		runDummyServer();
	}

	private void runDummyServer() {

		ServerSocket seso = null;
		Socket so = null;

		System.out.println("Running Server");
		
		try {
			System.out.println("Opning ServerSocket");
			seso = new ServerSocket(port);

			BufferedReader br = null;
			
			while (true) {
				System.out.println("Waiting for accept");
				so = seso.accept();
				
				System.out.println("Accepted");
				br = new BufferedReader(new InputStreamReader(so.getInputStream()));
				
				for(String line = null; (line = br.readLine()) != null;){
					System.out.println(line);
				}
				
//				System.out.println(br.readLine());
				
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				seso.close();
			} catch (IOException e) {
				// ignore
			}
		}

	}

}
