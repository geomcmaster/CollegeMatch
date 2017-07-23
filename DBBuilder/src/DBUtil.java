import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Utility class for db operations.
 * 
 * @author Geoff
 *
 */
public class DBUtil {
	private final String URL = "jdbc:mysql://localhost/?useSSL=false";
	private final String USER;
	private final String PASSWORD;
	
	//TODO write all the create statements
	private final String SCHOOL_TBL = "";
	private final String USER_TBL = "CREATE TABLE user (ID INT AUTO_INCREMENT, password INT NOT NULL, SAT_SCORE INT,"
			+ "ACT_SCORE INT, PRIMARY KEY (ID) ); ";
	private final String REGION_TBL = "";
	private final String LOC_TBL = "";
	private final String SCHOOL_LOC_TBL = "";
	private final String RESIDENCE_TBL = "";
	private final String FIELD_TBL = "";
	private final String GENDER_TBL = "";
	private final String ETHNIC_TBL = "";
	private final String FAV_FIELD_TBL = "";
	private final String FAV_SCHOOL_TBL = "";
	private final String OFFERS_TBL = "";
	
	private final String DROP_TABLES_PROC = "DROP PROCEDURE IF EXISTS createTables";
	private final String CREATE_TABLES_STORED_PROC = "CREATE PROCEDURE createTables() BEGIN DROP TABLE IF EXISTS "
			+ "school, user, region, location, school_loc, residence, fieldOfStudy, genderDemographics, "
			+ "ethnicDemographics, favoriteFieldsOfStudy, favoriteSchools, offers; " + SCHOOL_TBL + USER_TBL + 
			REGION_TBL + LOC_TBL + SCHOOL_LOC_TBL + RESIDENCE_TBL + FIELD_TBL + GENDER_TBL + ETHNIC_TBL + 
			FAV_FIELD_TBL + FAV_SCHOOL_TBL + OFFERS_TBL + "END";
	private Statement stmt;
	private CallableStatement cstmt;
	public Connection conn;
	
	public DBUtil(String user, String pw) {
		USER = user;
		PASSWORD = pw;
		conn = openConnection();	
	}
	
	private Connection openConnection() {
		Connection c = null;
		try {
			c = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		return c;
	}
	
	public void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				System.out.println(e.toString());
			}
		}
	}
	
	public void closeStatement() {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				System.out.println(e.toString());
			}
		}
	}
	
	public void closeConnection() {
		if (conn != null) {
			try {
				closeStatement();
				conn.close();
			} catch (SQLException e) {
				System.out.println(e.toString());
			}
		}
	}
	
	/**
	 * Drops database if it exists then creates database.
	 */
	public void createDatabase() {
		try {
			stmt = conn.createStatement();
			stmt.execute("DROP DATABASE IF EXISTS CollegeMatch");
			stmt.execute("CREATE DATABASE CollegeMatch");
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
	}
	
	/**
	 * Drops tables if they exist then creates all tables.
	 */
	public void createTables() {
		setDB();
		
		try {
			stmt.execute(DROP_TABLES_PROC);
			stmt.execute(CREATE_TABLES_STORED_PROC);
			cstmt = conn.prepareCall("{call createTables()}");
			cstmt.execute();
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
	}
	
	/**
	 * Clears all data from all tables.
	 */
	public void clearTables() {
		setDB();
		
		//TODO implement
	}
	
	/**
	 * Sets the connection's database
	 */
	private void setDB() {
		try {
			conn.setCatalog("CollegeMatch");
			stmt = conn.createStatement();	//statement needs to be created AFTER catalog set to respect it
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
	}
	
	/**
	 * Set the connection's database
	 */
	public void prepareDB() {
		setDB();
	}
}
