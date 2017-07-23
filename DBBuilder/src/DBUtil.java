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
	private final String GENDER_TBL = "CREATE TABLE GenderDemographics "
			+ "(ID INT AUTO_INCREMENT, "
			+ "female DOUBLE, "
			+ "male DOUBLE, "
			+ "PRIMARY KEY (ID)); ";
	private final String ETHNIC_TBL = "CREATE TABLE EthnicDemographics "
			+ "(ID INT AUTO_INCREMENT, "
			+ "white DOUBLE, "
			+ "black DOUBLE, "
			+ "hispanic DOUBLE, "
			+ "asian DOUBLE, "
			+ "american_indian DOUBLE, "
			+ "two_or_more DOUBLE, "
			+ "unknown DOUBLE, "
			+ "nonresident DOUBLE, "
			+ "PRIMARY KEY (ID)); ";
	private final String SCHOOL_TBL = "CREATE TABLE school "
			+ "(ID INT, "
			+ "name VARCHAR(255), "
			+ "url VARCHAR(255), "
			+ "alias VARCHAR(255), "
			+ "SAT_pct_25 DOUBLE, "
			+ "SAT_pct_75 DOUBLE, "
			+ "SAT_avg DOUBLE, "
			+ "ACT_pct_25 DOUBLE, "
			+ "ACT_pct_75 DOUBLE, "
			+ "ACT_avg DOUBLE, "
			+ "avg_earnings INT, "
			+ "avg_cost INT, "
			+ "control INT, "
			+ "med_debt DOUBLE, "
			+ "std_bdy_sz INT, "
			+ "pop_prog_1 INT, "
			+ "pop_prog_2 INT, "
			+ "pop_prog_3 INT, "
			+ "pop_prog_4 INT, "
			+ "pop_prog_5 INT, "
			+ "adm_rate DOUBLE, "
			+ "avg_fam_inc INT, "
			+ "med_fam_inc INT, "
			+ "tuition_out INT, "
			+ "tuition_in INT, "
			+ "avg_entry_age INT, "
			+ "1_gen_std DOUBLE, "
			+ "level INT, "
			+ "dist_learning INT, "
			+ "GenderDemographics_ID INT, "
			+ "EthnicDemographics_ID INT, "
			+ "PRIMARY KEY (ID), "
			+ "FOREIGN KEY (GenderDemographics_ID) REFERENCES GenderDemographics(ID), "
			+ "FOREIGN KEY (EthnicDemographics_ID) REFERENCES EthnicDemographics(ID)); ";
	private final String USER_TBL = "CREATE TABLE user "
			+ "(ID VARCHAR(255), "
			+ "password INT NOT NULL, "
			+ "SAT_SCORE INT, "
			+ "ACT_SCORE INT, "
			+ "PRIMARY KEY (ID) ); ";
	private final String REGION_TBL = "";
	private final String LOC_TBL = "";
	private final String SCHOOL_LOC_TBL = "";
	private final String RESIDENCE_TBL = "";
	private final String FIELD_TBL = "";
	private final String FAV_FIELD_TBL = "";
	private final String FAV_SCHOOL_TBL = "";
	private final String OFFERS_TBL = "";
	
	private final String DROP_TABLES_PROC = "DROP PROCEDURE IF EXISTS createTables";
	private final String CREATE_TABLES_STORED_PROC = "CREATE PROCEDURE createTables() "
			+ "BEGIN "
			+ "DROP TABLE IF EXISTS "
			+ "school, user, region, location, school_loc, residence, fieldOfStudy, genderDemographics, "
			+ "ethnicDemographics, favoriteFieldsOfStudy, favoriteSchools, offers; " 
			+ SCHOOL_TBL + USER_TBL + REGION_TBL + LOC_TBL + SCHOOL_LOC_TBL + RESIDENCE_TBL + FIELD_TBL + GENDER_TBL 
			+ ETHNIC_TBL + FAV_FIELD_TBL + FAV_SCHOOL_TBL + OFFERS_TBL 
			+ "END";
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
		prepareDB();
		
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
		prepareDB();
		
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
	 * Set the connection's database, makes sure static tables are populated
	 */
	public void prepareDB() {
		setDB();
		//TODO let's ensure all static tables are populated first
		//	-region
		//	-fieldOfStudy
	}
	
	/**
	 * Updates database with school info
	 */
	public void processSchool(Result result) {
		//TODO implement this method
		//making notes on what we're going to need to do here
		//might want helper methods for each table
		//1. insert into demographics tables
		//	ID will be an AUTO INCREMENT field for those
		//  we will need to grab those IDs for when we populate the record in the school table for this school
		//2. insert into school
		//	pre-processing: top 5 fields, demographics IDs (as noted above)
		//3. insert into location and school_loc
		//	after inserting into location (which should be an AUTO_INCREMENT) we grab that ID to insert into school_loc
	}
}
