package YoungTalents;

import YoungTalents.TestClient;

public class ServerMain {

	public static void main(String[] args) {

		Configuration.loadProperties();
		
		new Server();
		
	}
	
}
