package core;

import java.sql.SQLException;

import client.TestClient;

public class ServerMain {

	public static void main(String[] args) throws SQLException {

		Configuration.loadProperties();
		
		new Server();
		
	}
	
}
