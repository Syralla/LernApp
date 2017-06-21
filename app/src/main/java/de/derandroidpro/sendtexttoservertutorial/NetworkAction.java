package de.derandroidpro.sendtexttoservertutorial;

import java.io.BufferedReader;
import java.io.PrintWriter;

public interface NetworkAction {

	public void performAction(PrintWriter pw, BufferedReader br);
		
}
