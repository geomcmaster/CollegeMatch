package main.java;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Utility class for getting connection, closing connection/statement/result set.
 * Assumes existence of db.properties file containing user and password.
 * 
 * @author Geoff
 *
 */
public class DBUtil {
	private static final String URL = "jdbc:mysql://localhost/collegematch?useSSL=false";
	private static Connection conn;
	
	/**
	 * Retrieves connection. Opens it if one does not exist.
	 * 
	 * @return Database connection
	 */
    public static Connection getConnection() {
        if (conn != null) {
            return conn;
        }
 
        //InputStream inputStream = DBUtil.class.getClassLoader().getResourceAsStream("main/resources/db.properties");
        //Properties properties = new Properties();
        try {
            //properties.load(inputStream);
            //String user = properties.getProperty("user");
            //String password = properties.getProperty("password");
        	String user = "USER";	//must figure out correct file path for properties file
        	String password = "PASSWORD";
            conn = DriverManager.getConnection(URL, user, password );
            
        } /*catch (IOException e) {
            e.printStackTrace();
        }*/ catch (SQLException e) {
            e.printStackTrace();
        }
 
        return conn;
    }
    
    /**
     * Closes the connection
     */
    public static void closeConnection() {
    	if (conn != null) {
    		try {
    			conn.close();
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    	}
    }
    
    /**
     * Closes a ResultSet
     * 
     * @param rs The ResultSet to close
     */
	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Closes a Statement
	 * 
	 * @param stmt The statement to close
	 */
	public static void closeStatement(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
