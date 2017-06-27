package core;

import client.TestClient;

public class ServerMain {

	public static void main(String[] args) {

		Configuration.loadProperties();
		
		new Server();
		
	}
	
}
