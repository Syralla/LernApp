package youngtalents;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public interface NetworkAction {

	public void performAction(PrintWriter pw, BufferedReader br) throws IOException;
		
}
