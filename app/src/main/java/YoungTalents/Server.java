package YoungTalents;

import javax.security.auth.login.AppConfigurationEntry;

import core.network.AppListener;

public class Server {

	public Server() {
		
		Queue queue = new Queue();
		
		new AppListener(queue);
	}
	
}
