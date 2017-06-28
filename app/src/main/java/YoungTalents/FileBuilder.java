package youngtalents;

import java.util.ArrayList;
import java.util.List;

public class FileBuilder {

	private List<Aufgabe> aufgaben = new ArrayList<Aufgabe>();
	
	String xmlPartTemplate = "<aufgabe><zahl1>${zahl1}</zahl1><zahl2>${zahl2}</zahl2><operation>${operation}</operation></aufgabe>";
	String xmlLoginTemplate = "<login><user>${user}</user><pasw>${pasw}</pasw></login>";
	String xmlRegisterTemplate = "<register><user>${user}</user><pasw>${pasw}</pasw><email>${email}</email></register>";
	
	public FileBuilder() {
		// TODO Auto-generated constructor stub
	}
	
	public void addAufgabe(Aufgabe aufgabe){
		aufgaben.add(aufgabe);
	}
	/**
	 * Aufgaben sammeln/erstellen
	 * @return
	 */
	
	public List<String> buildFileAsStringArray(){
		
		List<String> lines = new ArrayList<String>();
		
		lines.add("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		
		for(Aufgabe aufgabe : aufgaben){
			
			String taskLine = xmlPartTemplate;
			taskLine = taskLine.replace("${zahl1}", aufgabe.getNumber1() + "");
			taskLine = taskLine.replace("${zahl2}", aufgabe.getNumber2() + "");
			taskLine = taskLine.replace("${operation}", aufgabe.getOp() + "");

			lines.add(taskLine);
		}
		
		
		return lines;
	}

	public List<String> buildLoginAsStringArray(String u, String p){
		List<String> lines = new ArrayList<String>();

		lines.add("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
			String taskLine = xmlLoginTemplate;
		taskLine = taskLine.replace("${user}", u + "");
		taskLine = taskLine.replace("${pasw}", p + "");

		lines.add(taskLine);
		return lines;
	}

	public List<String> buildRegisterAsStringArray(String u, String p, String e){
		List<String> lines = new ArrayList<String>();

		lines.add("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		String taskLine = xmlRegisterTemplate;
		taskLine = taskLine.replace("${user}", u + "");
		taskLine = taskLine.replace("${pasw}", p + "");
		taskLine = taskLine.replace("${email}", e + "");

		lines.add(taskLine);
		return lines;
	}
	
}
