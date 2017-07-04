package youngtalents;

public class Server {

	public Server() {
		
		Queue queue = new Queue();
		
		new AppListener(queue);
	}
	
}
