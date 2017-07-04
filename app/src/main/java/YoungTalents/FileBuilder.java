package youngtalents;

import java.util.ArrayList;
import java.util.List;

public class FileBuilder {


    String xmlLoginTemplate = "<login><user>${user}</user><pasw>${pasw}</pasw></login>"; //XML Template für den Login
    String xmlRegisterTemplate = "<register><user>${user}</user><pasw>${pasw}</pasw><email>${email}</email></register>"; //XML Template für die Registrierung


    public FileBuilder() {
        // TODO Auto-generated constructor stub
    }


    public List<String> buildLoginAsStringArray(String u, String p) { //In dieser Methode wird der Login (zwei Strings) zu einer String List umgebaut, welche die beiden Werte beinhaltet
        List<String> lines = new ArrayList<String>();

        lines.add("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        String taskLine = xmlLoginTemplate;
        taskLine = taskLine.replace("${user}", u + "");
        taskLine = taskLine.replace("${pasw}", p + "");

        lines.add(taskLine);
        return lines;
    }

    public List<String> buildRegisterAsStringArray(String u, String p, String e) { //Hier das selbe bloß für die Registrierung
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
