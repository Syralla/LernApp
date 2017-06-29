package core;

import java.sql.SQLException;

import javax.security.auth.login.AppConfigurationEntry;

import core.network.AppListener;

public class Server {

	public Server() throws SQLException {
		
		Queue queue = new Queue();
		
		new AppListener(queue);
	}
	
}
