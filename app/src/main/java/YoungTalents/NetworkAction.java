package youngtalents;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public interface NetworkAction { //Diese Methode wird benutzt um die Verbindung zwischen Client Socket und ServerSocket durchzuführen

    void performAction(PrintWriter pw, BufferedReader br) throws IOException;

}
