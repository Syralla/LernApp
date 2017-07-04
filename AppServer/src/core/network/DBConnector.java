package core.network;

import java.sql.*;



public class DBConnector {
	
	
	public static String SQLIP = "jdbc:mysql://localhost:3306/Lernapp";
	public static String DBUSER = "root";
	public static String DBPW = "LernApp";
	Connection myConn = null;
	Statement myStmt = null;
	ResultSet myRs = null;
	String SQL = null;
	
	public DBConnector(){
		
				
	}

	
	public ResultSet select(String sql) throws SQLException{
		
		try {
			// 1. Get a connection to database
			myConn = DriverManager.getConnection(SQLIP, DBUSER , DBPW);
			
			// 2. Create a statement
			myStmt = myConn.createStatement();
			
			// 3. Execute SQL query
			myRs = myStmt.executeQuery(sql);
			
			// 4. Process the result set
			
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
		finally {
			if (myRs != null) {
				myRs.close();
			}
			
			if (myStmt != null) {
				myStmt.close();
			}
			
			if (myConn != null) {
				myConn.close();
			}
		}
		return myRs;
		
		
		
		//JDBC.SaveKruschtinDB(Configuration.DBIP, String qurey);
		
	}
	
	public boolean insert(String sql) throws SQLException{
		
		
		try {
			// 1. Get a connection to database
			myConn = DriverManager.getConnection(SQLIP, DBUSER , DBPW);
			// 2. Create a statement
			myStmt = myConn.createStatement();
			// 3. Execute SQL query
			myStmt.executeUpdate(sql);
			
			} catch (Exception exc) {
			exc.printStackTrace();
			return false;
			} finally {
			if (myStmt != null) {
			myStmt.close();
			return false;
			}
			if (myConn != null) {
			myConn.close();
			return false;
			}
			}
		
		
		
		
		
		return true;
	}
	
	public boolean update(String sql) throws SQLException{
		
		
		try {
			// 1. Get a connection to database
			myConn = DriverManager.getConnection(SQLIP, DBUSER , DBPW);
			// 2. Create a statement
			myStmt = myConn.createStatement();
			// 3. Execute SQL query
			myStmt.executeUpdate(sql);
			
			} catch (Exception exc) {
			exc.printStackTrace();
			return false;
			} finally {
			if (myStmt != null) {
			myStmt.close();
			return false;
			}
			if (myConn != null) {
			myConn.close();
			return false;
			}
			}
		
		
		
		
		
		return true;
	}
	
}
