package youngtalents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class Configuration {

	public static String serverIP = "simon-f.com";
	public static int serverPort = 8080;
	public static String DBIP = "127.0.0.1";
	public static String DBUSER = "";
	public static String DBPW = "";
	
	
	public static void loadProperties(){
		
		Properties prop = new Properties();
		try {
			prop.load(new InputStreamReader(new FileInputStream(new File("configuration/config.cfg"))));
			
			serverPort = Integer.parseInt(prop.getProperty("transferport"));
			DBIP = prop.getProperty("dbip");
			DBUSER = prop.getProperty("dbuser");
			DBPW = prop.getProperty("dbpw");
			
			
		} catch (IOException e) {
			System.err.println("Could not load from config file");
		}finally{
		
		}
		
	}
	
	
}
