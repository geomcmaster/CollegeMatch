import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
	private static final String URL = "jdbc:mysql://localhost/?useSSL=false";
	private final String USER;
	private final String PASSWORD;
	private Statement stmt;
	private CallableStatement cstmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	public Connection conn;
	
	/////////////////////////////
	//TABLE CREATION STATEMENTS//
	/////////////////////////////
	private static final String REGION_TBL = "CREATE TABLE region ("
			+ "state INT, "
			+ "region_name VARCHAR(255), "
			+ "PRIMARY KEY (state) ); ";
	private static final String GENDER_TBL = "CREATE TABLE GenderDemographics ("
			+ "ID INT AUTO_INCREMENT, "
			+ "female DOUBLE, "
			+ "male DOUBLE, "
			+ "PRIMARY KEY (ID) ); ";
	private static final String ETHNIC_TBL = "CREATE TABLE EthnicDemographics ("
			+ "ID INT AUTO_INCREMENT, "
			+ "white DOUBLE, "
			+ "black DOUBLE, "
			+ "hispanic DOUBLE, "
			+ "asian DOUBLE, "
			+ "american_indian DOUBLE, "
			+ "two_or_more DOUBLE, "
			+ "unknown DOUBLE, "
			+ "nonresident DOUBLE, "
			+ "PRIMARY KEY (ID) ); ";
	private static final String LOC_TBL = "CREATE TABLE location ("
			+ "ID INT AUTO_INCREMENT, "
			+ "city VARCHAR(255), "
			+ "state INT, "
			+ "ZIP INT, "
			+ "PRIMARY KEY (ID), "
			+ "FOREIGN KEY (state) REFERENCES region(state) ); ";
	private static final String SCHOOL_TBL = "CREATE TABLE school ("
			+ "ID INT, "
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
			+ "GenderDemographics_ID INT NOT NULL, "
			+ "EthnicDemographics_ID INT NOT NULL, "
			+ "PRIMARY KEY (ID), "
			+ "FOREIGN KEY (GenderDemographics_ID) REFERENCES GenderDemographics(ID), "
			+ "FOREIGN KEY (EthnicDemographics_ID) REFERENCES EthnicDemographics(ID) ); ";
	private static final String USER_TBL = "CREATE TABLE user ("
			+ "ID VARCHAR(255), "
			+ "password INT NOT NULL, "
			+ "SAT_SCORE INT, "
			+ "ACT_SCORE INT, "
			+ "PRIMARY KEY (ID) ); ";
	private static final String SCHOOL_LOC_TBL = "CREATE TABLE school_loc ("
			+ "school_ID INT, "
			+ "loc_ID INT NOT NULL, "
			+ "PRIMARY KEY (school_ID), "
			+ "FOREIGN KEY (loc_ID) REFERENCES location(ID) ); ";
	private static final String RESIDENCE_TBL = "CREATE TABLE residence ("
			+ "std_ID VARCHAR(255), "
			+ "loc_ID INT NOT NULL, "
			+ "PRIMARY KEY (std_ID), "
			+ "FOREIGN KEY (std_ID) REFERENCES user(ID), "
			+ "FOREIGN KEY (loc_ID) REFERENCES location(ID) ); ";
	private static final String FIELD_TBL = "CREATE TABLE fieldsOfStudy ("
			+ "ID INT AUTO_INCREMENT, "
			+ "name VARCHAR(255) NOT NULL, "
			+ "PRIMARY KEY (ID) ); ";
	private static final String FAV_FIELD_TBL = "CREATE TABLE favoriteFieldsOfStudy ("
			+ "std_ID VARCHAR(255), "
			+ "field_ID INT, "
			+ "rank INT, "
			+ "PRIMARY KEY (std_ID, field_ID), "
			+ "FOREIGN KEY (std_ID) REFERENCES user(ID), "
			+ "FOREIGN KEY (field_ID) REFERENCES fieldsOfStudy(ID) ); ";
	private static final String FAV_SCHOOL_TBL = "CREATE TABLE favoriteSchools("
			+ "std_ID VARCHAR(255), "
			+ "school_ID INT, "
			+ "rank INT, "
			+ "app_status VARCHAR(255), "
			+ "fin_aid INT, "
			+ "loan_amt INT, "
			+ "merit_scholarships INT, "
			+ "PRIMARY KEY (std_ID, school_ID), "
			+ "FOREIGN KEY (std_ID) REFERENCES user(ID), "
			+ "FOREIGN KEY (school_ID) REFERENCES school(ID) ); ";
	private static final String OFFERS_TBL = "CREATE TABLE offers ("
			+ "school_ID INT, "
			+ "field_ID INT, "
			+ "PRIMARY KEY (school_ID, field_ID), "
			+ "FOREIGN KEY (school_ID) REFERENCES school(ID), "
			+ "FOREIGN KEY (field_ID) REFERENCES fieldsOfStudy(ID) ); ";
	
	////////////////////////////////////
	//TABLE CREATION STORED PROCEDURES//
	////////////////////////////////////
	//Tables that are referenced by other tables need to be created by separate stored procedures before
	//the tables that reference them.
	private static final String DROP_TABLES_PROC_1 = "DROP PROCEDURE IF EXISTS createTables1";
	private static final String CREATE_TABLES_STORED_PROC_1 = "CREATE PROCEDURE createTables1() "
			+ "BEGIN "
			+ "DROP TABLE IF EXISTS "
			+ "user, "
			+ "fieldOfStudy, "
			+ "genderDemographics, "
			+ "ethnicDemographics, "
			+ "region; "
			+ USER_TBL
			+ FIELD_TBL
			+ GENDER_TBL
			+ ETHNIC_TBL
			+ REGION_TBL 
			+ "END";
	private static final String DROP_TABLES_PROC_2 = "DROP PROCEDURE IF EXISTS createTables2";
	private static final String CREATE_TABLES_STORED_PROC_2 = "CREATE PROCEDURE createTables2() "
			+ "BEGIN "
			+ "DROP TABLE IF EXISTS "
			+ "location, "
			+ "school; "
			+ LOC_TBL
			+ SCHOOL_TBL
			+ "END";
	private static final String DROP_TABLES_PROC_3 = "DROP PROCEDURE IF EXISTS createTables3";
	private static final String CREATE_TABLES_STORED_PROC_3 = "CREATE PROCEDURE createTables3() "
			+ "BEGIN "
			+ "DROP TABLE IF EXISTS "
			+ "school_loc, "
			+ "residence, "
			+ "favoriteFieldsOfStudy, "
			+ "favoriteSchools, "
			+ "offers; " 
			+ SCHOOL_LOC_TBL 
			+ RESIDENCE_TBL 
			+ FAV_FIELD_TBL 
			+ FAV_SCHOOL_TBL 
			+ OFFERS_TBL 
			+ "END";
	
	///////////
	//REGIONS//
	///////////
	private static final String NEW_ENGLAND = "New England";
	private static final String MID_EAST = "Mid East";
	private static final String GREAT_LAKES = "Great Lakes";
	private static final String PLAINS = "Plains";
	private static final String SE = "Southeast";
	private static final String SW = "Southwest";
	private static final String ROCKY_MTNS = "Rocky Mountains";
	private static final String FAR_WEST = "Far West";
	private static final String OUTLYING_AREAS = "Outlying Areas";
	
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
	
	private void closeResultSet() {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				System.out.println(e.toString());
			}
		}
	}
	
	private void closeStatements() {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				System.out.println(e.toString());
			}
		}
		
		if (cstmt != null) {
			try {
				cstmt.close();
			} catch (SQLException e) {
				System.out.println(e.toString());
			}
		}
		
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				System.out.println(e.toString());
			}
		}
	}
	
	public void closeConnection() {
		closeResultSet();
		closeStatements();
		if (conn != null) {
			try {
				closeStatements();
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
	 * Drops tables if they exist then creates all tables. Also populates static tables.
	 */
	public void createTables() {
		setDB();
		
		try {
			stmt.execute(DROP_TABLES_PROC_1);
			stmt.execute(CREATE_TABLES_STORED_PROC_1);
			stmt.execute(DROP_TABLES_PROC_2);
			stmt.execute(CREATE_TABLES_STORED_PROC_2);
			stmt.execute(DROP_TABLES_PROC_3);
			stmt.execute(CREATE_TABLES_STORED_PROC_3);
			cstmt = conn.prepareCall("{call createTables1()}");
			cstmt.execute();
			cstmt = conn.prepareCall("{call createTables2()}");
			cstmt.execute();
			cstmt = conn.prepareCall("{call createTables3()}");
			cstmt.execute();
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		
		populateRegion();
		populateFieldsOfStudy();
	}
	
	/**
	 * Clears all data from all tables.
	 */
	public void clearTables() {
		setDB();
		
		//omitting region and fieldsOfStudy since those are hand populated and should not be modified
		try {
			stmt.execute("DELETE FROM school");
			stmt.execute("DELETE FROM user");
			stmt.execute("DELETE FROM location");
			stmt.execute("DELETE FROM school_loc");
			stmt.execute("DELETE FROM residence");
			stmt.execute("DELETE FROM genderDemographics");
			stmt.execute("DELETE FROM ethnicDemographics");
			stmt.execute("DELETE FROM favoriteFieldsOfStudy");
			stmt.execute("DELETE FROM favoriteSchools");
			stmt.execute("DELETE FROM offers");
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
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
		
		try {
			rs = stmt.executeQuery("SELECT COUNT(*) FROM region");
			if (rs.next() && rs.getInt(1) == 0) {	//data already exists
				populateRegion();
			}
			
			rs = stmt.executeQuery("SELECT COUNT(*) FROM fieldsOfStudy");
			if (rs.next() && rs.getInt(1) == 0) {	//data already exists
				populateFieldsOfStudy();
			}
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
	}
	
	/**
	 * buckle up
	 */
	private void populateRegion() {
		try {
			
			pstmt = conn.prepareStatement("INSERT INTO region (state, region_name) VALUES (?, ?)");
			
			//NEW ENGLAND
			pstmt.setInt(1, 9);	//CT
			pstmt.setString(2, NEW_ENGLAND);
			pstmt.execute();
			
			pstmt.setInt(1, 23); //ME
			pstmt.setString(2, NEW_ENGLAND);
			pstmt.execute();
			
			pstmt.setInt(1, 25); //MA
			pstmt.setString(2, NEW_ENGLAND);
			pstmt.execute();
			
			pstmt.setInt(1, 33); //NH
			pstmt.setString(2, NEW_ENGLAND);
			pstmt.execute();
			
			pstmt.setInt(1, 44); //RI
			pstmt.setString(2, NEW_ENGLAND);
			pstmt.execute();
			
			pstmt.setInt(1, 50); //VT
			pstmt.setString(2, NEW_ENGLAND);
			pstmt.execute();
			
			//MID EAST
			pstmt.setInt(1, 10); //DE
			pstmt.setString(2, MID_EAST);
			pstmt.execute();
			
			pstmt.setInt(1, 11); //DC
			pstmt.setString(2, MID_EAST);
			pstmt.execute();
			
			pstmt.setInt(1, 24); //MD
			pstmt.setString(2, MID_EAST);
			pstmt.execute();
			
			pstmt.setInt(1, 34); //NJ
			pstmt.setString(2, MID_EAST);
			pstmt.execute();
			
			pstmt.setInt(1, 36); //NY
			pstmt.setString(2, MID_EAST);
			pstmt.execute();
			
			pstmt.setInt(1, 42); //PA
			pstmt.setString(2, MID_EAST);
			pstmt.execute();
			
			//Great Lakes
			pstmt.setInt(1, 17); //IL
			pstmt.setString(2, GREAT_LAKES);
			pstmt.execute();
			
			pstmt.setInt(1, 18); //IN
			pstmt.setString(2, GREAT_LAKES);
			pstmt.execute();
			
			pstmt.setInt(1, 26); //MI
			pstmt.setString(2, GREAT_LAKES);
			pstmt.execute();
			
			pstmt.setInt(1, 39); //OH
			pstmt.setString(2, GREAT_LAKES);
			pstmt.execute();
			
			pstmt.setInt(1, 55); //WI
			pstmt.setString(2, GREAT_LAKES);
			pstmt.execute();
			
			//Plains
			pstmt.setInt(1, 19); //IA
			pstmt.setString(2, PLAINS);
			pstmt.execute();
			
			pstmt.setInt(1, 20); //KS
			pstmt.setString(2, PLAINS);
			pstmt.execute();
			
			pstmt.setInt(1, 27); //MN
			pstmt.setString(2, PLAINS);
			pstmt.execute();
			
			pstmt.setInt(1, 29); //MO
			pstmt.setString(2, PLAINS);
			pstmt.execute();
			
			pstmt.setInt(1, 31); //NE
			pstmt.setString(2, PLAINS);
			pstmt.execute();
			
			pstmt.setInt(1, 38); //ND
			pstmt.setString(2, PLAINS);
			pstmt.execute();
			
			pstmt.setInt(1, 46); //SD
			pstmt.setString(2, PLAINS);
			pstmt.execute();
			
			//Southeast
			pstmt.setInt(1, 1); //AL
			pstmt.setString(2, SE);
			pstmt.execute();
			
			pstmt.setInt(1, 5); //AR
			pstmt.setString(2, SE);
			pstmt.execute();
			
			pstmt.setInt(1, 12); //FL
			pstmt.setString(2, SE);
			pstmt.execute();
			
			pstmt.setInt(1, 13); //GA
			pstmt.setString(2, SE);
			pstmt.execute();
			
			pstmt.setInt(1, 21); //KY
			pstmt.setString(2, SE);
			pstmt.execute();
			
			pstmt.setInt(1, 22); //LA
			pstmt.setString(2, SE);
			pstmt.execute();
			
			pstmt.setInt(1, 28); //MS
			pstmt.setString(2, SE);
			pstmt.execute();
			
			pstmt.setInt(1, 37); //NC
			pstmt.setString(2, SE);
			pstmt.execute();
			
			pstmt.setInt(1, 45); //SC
			pstmt.setString(2, SE);
			pstmt.execute();
			
			pstmt.setInt(1, 47); //TN
			pstmt.setString(2, SE);
			pstmt.execute();
			
			pstmt.setInt(1, 51); //VA
			pstmt.setString(2, SE);
			pstmt.execute();
			
			pstmt.setInt(1, 54); //WV
			pstmt.setString(2, SE);
			pstmt.execute();
			
			//Southwest
			pstmt.setInt(1, 4); //AZ
			pstmt.setString(2, SW);
			pstmt.execute();
			
			pstmt.setInt(1, 35); //NM
			pstmt.setString(2, SW);
			pstmt.execute();
			
			pstmt.setInt(1, 40); //OK
			pstmt.setString(2, SW);
			pstmt.execute();
			
			pstmt.setInt(1, 48); //TX
			pstmt.setString(2, SW);
			pstmt.execute();
			
			//Rocky Mountains
			pstmt.setInt(1, 8); //CO
			pstmt.setString(2, ROCKY_MTNS);
			pstmt.execute();
			
			pstmt.setInt(1, 16); //ID
			pstmt.setString(2, ROCKY_MTNS);
			pstmt.execute();
			
			pstmt.setInt(1, 30); //MT
			pstmt.setString(2, ROCKY_MTNS);
			pstmt.execute();
			
			pstmt.setInt(1, 49); //UT
			pstmt.setString(2, ROCKY_MTNS);
			pstmt.execute();
			
			pstmt.setInt(1, 56); //WY
			pstmt.setString(2, ROCKY_MTNS);
			pstmt.execute();
			
			//Far West
			pstmt.setInt(1, 2); //AK
			pstmt.setString(2, FAR_WEST);
			pstmt.execute();
			
			pstmt.setInt(1, 6); //CA
			pstmt.setString(2, FAR_WEST);
			pstmt.execute();
			
			pstmt.setInt(1, 15); //HI
			pstmt.setString(2, FAR_WEST);
			pstmt.execute();
			
			pstmt.setInt(1, 32); //NV
			pstmt.setString(2, FAR_WEST);
			pstmt.execute();
			
			pstmt.setInt(1, 41); //OR
			pstmt.setString(2, FAR_WEST);
			pstmt.execute();
			
			pstmt.setInt(1, 53); //WA
			pstmt.setString(2, FAR_WEST);
			pstmt.execute();
			
			//Outlying Areas
			pstmt.setInt(1, 60); //AS
			pstmt.setString(2, OUTLYING_AREAS);
			pstmt.execute();
			
			pstmt.setInt(1, 64); //FM
			pstmt.setString(2, OUTLYING_AREAS);
			pstmt.execute();
			
			pstmt.setInt(1, 66); //GU
			pstmt.setString(2, OUTLYING_AREAS);
			pstmt.execute();
			
			pstmt.setInt(1, 68); //MH
			pstmt.setString(2, OUTLYING_AREAS);
			pstmt.execute();
			
			pstmt.setInt(1, 69); //MP
			pstmt.setString(2, OUTLYING_AREAS);
			pstmt.execute();
			
			pstmt.setInt(1, 72); //PR
			pstmt.setString(2, OUTLYING_AREAS);
			pstmt.execute();
			
			pstmt.setInt(1, 70); //PW
			pstmt.setString(2, OUTLYING_AREAS);
			pstmt.execute();
			
			pstmt.setInt(1, 78); //VI
			pstmt.setString(2, OUTLYING_AREAS);
			pstmt.execute();
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
	}
	
	private void populateFieldsOfStudy() {
		try {
			pstmt = conn.prepareStatement("INSERT INTO fieldsOfStudy (name) VALUES (?)");
			
			pstmt.setString(1, "Agriculture, Agriculture Operations, and Related Sciences");
			pstmt.execute();
			
			pstmt.setString(1, "Natural Resources and Conservation");
			pstmt.execute();
			
			pstmt.setString(1, "Architecture and Related Services");
			pstmt.execute();
			
			pstmt.setString(1, "Area, Ethnic, Cultural, Gender, and Group Studies");
			pstmt.execute();
			
			pstmt.setString(1, "Communication, Journalism, and Related Programs");
			pstmt.execute();
			
			pstmt.setString(1, "Communications Technologies/Technicians and Support Services");
			pstmt.execute();
			
			pstmt.setString(1, "Computer and Information Sciences and Support Services");
			pstmt.execute();
			
			pstmt.setString(1, "Personal and Culinary Services");
			pstmt.execute();
			
			pstmt.setString(1, "Education");
			pstmt.execute();
			
			pstmt.setString(1, "Engineering");
			pstmt.execute();
			
			pstmt.setString(1, "Engineering Technologies and Engineering-Related Fields");
			pstmt.execute();
			
			pstmt.setString(1, "Foreign Languages, Literatures, and Linguistics");
			pstmt.execute();
			
			pstmt.setString(1, "Family and Consumer Sciences/Human Sciences");
			pstmt.execute();
			
			pstmt.setString(1, "Legal Professions and Studies");
			pstmt.execute();
			
			pstmt.setString(1, "English Language and Literature/Letters");
			pstmt.execute();
			
			pstmt.setString(1, "Liberal Arts and Sciences, General Studies and Humanities");
			pstmt.execute();
			
			pstmt.setString(1, "Library Science");
			pstmt.execute();
			
			pstmt.setString(1, "Biological and Biomedical Sciences");
			pstmt.execute();
			
			pstmt.setString(1, "Mathematics and Statistics");
			pstmt.execute();
			
			pstmt.setString(1, "Military Technologies and Applied Sciences");
			pstmt.execute();
			
			pstmt.setString(1, "Multi/Interdisciplinary Studies");
			pstmt.execute();
			
			pstmt.setString(1, "Parks, Recreation, Leisure, and Fitness Studies");
			pstmt.execute();
			
			pstmt.setString(1, "Philosophy and Religious Studies");
			pstmt.execute();
			
			pstmt.setString(1, "Theology and Religious Vocations");
			pstmt.execute();
			
			pstmt.setString(1, "Physical Sciences");
			pstmt.execute();
			
			pstmt.setString(1, "Science Technologies/Technicians");
			pstmt.execute();
			
			pstmt.setString(1, "Psychology");
			pstmt.execute();
			
			pstmt.setString(1, "Homeland Security, Law Enforcement, Firefighting and Related Protective ");
			pstmt.execute();
			
			pstmt.setString(1, "Public Administration and Social Service Professions");
			pstmt.execute();
			
			pstmt.setString(1, "Social Sciences");
			pstmt.execute();
			
			pstmt.setString(1, "Construction Trades");
			pstmt.execute();
			
			pstmt.setString(1, "Mechanic and Repair Technologies/Technicians");
			pstmt.execute();
			
			pstmt.setString(1, "Precision Production");
			pstmt.execute();
			
			pstmt.setString(1, "Transportation and Materials Moving");
			pstmt.execute();
			
			pstmt.setString(1, "Visual and Performing Arts");
			pstmt.execute();
			
			pstmt.setString(1, "Health Professions and Related Programs");
			pstmt.execute();
			
			pstmt.setString(1, "Business, Management, Marketing, and Related Support Services");
			pstmt.execute();
			
			pstmt.setString(1, "History");
			pstmt.execute();
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
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
