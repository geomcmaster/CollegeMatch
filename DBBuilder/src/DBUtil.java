import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
			+ "american_indian_alaskan_native DOUBLE, "
			+ "native_hawaiian_pacific_islander DOUBLE, "
			+ "two_or_more DOUBLE, "
			+ "unknown DOUBLE, "
			+ "nonresident DOUBLE, "
			+ "PRIMARY KEY (ID) ); ";
	private static final String LOC_TBL = "CREATE TABLE location ("
			+ "ID INT AUTO_INCREMENT, "
			+ "city VARCHAR(255), "
			+ "state_string VARCHAR(255), "
			+ "state INT, "
			+ "ZIP INT, "
			+ "PRIMARY KEY (ID), "
			+ "FOREIGN KEY (state) REFERENCES region(state) ); ";
	private static final String SCHOOL_TBL = "CREATE TABLE school ("
			+ "ID INT, "
			+ "name VARCHAR(255), "
			+ "url VARCHAR(255), "
			+ "alias VARCHAR(255), "
			+ "SAT_avg DOUBLE, "
			+ "SAT_pct_25_cumulative DOUBLE, " 
			+ "SAT_pct_75_cumulative DOUBLE, "
			+ "SAT_pct_25_reading DOUBLE, "
			+ "SAT_pct_75_reading DOUBLE, "
			+ "SAT_pct_25_math DOUBLE, "
			+ "SAT_pct_75_math DOUBLE, "
			+ "SAT_pct_25_writing DOUBLE, "
			+ "SAT_pct_75_writing DOUBLE, "
			+ "SAT_midpoint_reading DOUBLE, "
			+ "SAT_midpoint_math DOUBLE, "
			+ "SAT_midpoint_writing DOUBLE, "
			+ "ACT_pct_25_cumulative DOUBLE, "
			+ "ACT_pct_75_cumulative DOUBLE, "
			+ "ACT_pct_25_english DOUBLE, "
			+ "ACT_pct_75_english DOUBLE, "
			+ "ACT_pct_25_math DOUBLE, "
			+ "ACT_pct_75_math DOUBLE, "
			+ "ACT_pct_25_writing DOUBLE, "
			+ "ACT_pct_75_writing DOUBLE, "
			+ "ACT_avg DOUBLE, "
			+ "ACT_midpoint_english DOUBLE, "
			+ "ACT_midpoint_math DOUBLE, "
			+ "ACT_midpoint_writing DOUBLE, "
			+ "avg_earnings_6_years_after_matriculation INT, "
			+ "avg_cost INT, "
			+ "control INT, "
			+ "med_debt DOUBLE, "
			+ "std_bdy_sz INT, "
			+ "pop_prog_1 VARCHAR(255),"
			+ "pop_prog_2 VARCHAR(255), "
			+ "pop_prog_3 VARCHAR(255), "
			+ "pop_prog_4 VARCHAR(255), "
			+ "pop_prog_5 VARCHAR(255), "
			+ "adm_rate DOUBLE, "
			+ "avg_fam_inc INT, "
			+ "med_fam_inc INT, "
			+ "tuition_and_fees_out INT, "
			+ "tuition_and_fees_in INT, "
			+ "avg_entry_age INT, "
			+ "1_gen_std_share DOUBLE, "
			+ "level INT, "
			+ "dist_learning INT, "
			+ "GenderDemographics_ID INT NOT NULL, "
			+ "EthnicDemographics_ID INT NOT NULL, "
			+ "PRIMARY KEY (ID), "
			+ "FOREIGN KEY (GenderDemographics_ID) REFERENCES GenderDemographics(ID), "
			+ "FOREIGN KEY (EthnicDemographics_ID) REFERENCES EthnicDemographics(ID), "
			+ "FOREIGN KEY (pop_prog_1) REFERENCES fieldsOfStudy(ID), "
			+ "FOREIGN KEY (pop_prog_2) REFERENCES fieldsOfStudy(ID), "
			+ "FOREIGN KEY (pop_prog_3) REFERENCES fieldsOfStudy(ID), "
			+ "FOREIGN KEY (pop_prog_4) REFERENCES fieldsOfStudy(ID), "
			+ "FOREIGN KEY (pop_prog_5) REFERENCES fieldsOfStudy(ID) ); ";
	private static final String USER_TBL = "CREATE TABLE user ("
			+ "ID VARCHAR(255), "
			+ "password VARCHAR(255) NOT NULL, "
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
			+ "FOREIGN KEY (std_ID) REFERENCES user(ID) ON DELETE CASCADE, "
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
			+ "FOREIGN KEY (std_ID) REFERENCES user(ID) ON DELETE CASCADE, "
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
			+ "FOREIGN KEY (std_ID) REFERENCES user(ID) ON DELETE CASCADE, "
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
	
	private void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				System.out.println(e.toString());
			}
		}
	}
	
	private void closeStatement(Statement stmt) {
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
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.execute("DROP DATABASE IF EXISTS CollegeMatch");
			stmt.execute("CREATE DATABASE CollegeMatch");
		} catch (SQLException e) {
			System.out.println(e.toString());
		} finally {
			closeStatement(stmt);
		}
	}
	
	/**
	 * Drops tables if they exist then creates all tables. Also populates static tables.
	 */
	public void createTables() {
		setDB();
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.execute(DROP_TABLES_PROC_1);
			stmt.execute(CREATE_TABLES_STORED_PROC_1);
			stmt.execute(DROP_TABLES_PROC_2);
			stmt.execute(CREATE_TABLES_STORED_PROC_2);
			stmt.execute(DROP_TABLES_PROC_3);
			stmt.execute(CREATE_TABLES_STORED_PROC_3);
			
			CallableStatement cstmt = conn.prepareCall("{call createTables1()}");
			cstmt.execute();
			closeStatement(cstmt);
			CallableStatement cstmt2 = conn.prepareCall("{call createTables2()}");
			cstmt2.execute();
			closeStatement(cstmt2);
			CallableStatement cstmt3 = conn.prepareCall("{call createTables3()}");
			cstmt3.execute();
			closeStatement(cstmt3);
		} catch (SQLException e) {
			System.out.println(e.toString());
		} finally {
			closeStatement(stmt);
		}
		
		populateRegion();
		populateFieldsOfStudy();
	}
	
	/**
	 * Clears all data from all tables.
	 */
	public void clearTables() {
		setDB();
		
		Statement stmt = null;
		//omitting region and fieldsOfStudy since those are hand populated and should not be modified
		try {
			stmt = conn.createStatement();
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
		} finally {
			closeStatement(stmt);
		}
	}
	
	/**
	 * Sets the connection's database
	 */
	private void setDB() {
		try {
			conn.setCatalog("CollegeMatch");
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
	}
	
	/**
	 * Set the connection's database, makes sure static tables are populated
	 */
	public void prepareDB() {
		setDB();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT COUNT(*) FROM region");
			if (rs.next() && rs.getInt(1) == 0) {	//data already exists
				populateRegion();
			}
			closeResultSet(rs);
			
			ResultSet rs2 = stmt.executeQuery("SELECT COUNT(*) FROM fieldsOfStudy");
			if (rs2.next() && rs2.getInt(1) == 0) {	//data already exists
				populateFieldsOfStudy();
			}
			closeResultSet(rs2);
		} catch (SQLException e) {
			System.out.println(e.toString());
		} finally {
			closeStatement(stmt);
		}
	}
	
	/**
	 * buckle up
	 */
	private void populateRegion() {
		PreparedStatement pstmt = null;
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
			
			/* WE SHOULD DELETE THIS ONE. SEARCHING THROUGH DATABASE FOR SCHOOL
			 * MATCHING STATE ID==68 DOESN'T RETURN ANY SCHOOL
			pstmt.setInt(1, 68); //MH
			pstmt.setString(2, OUTLYING_AREAS);
			pstmt.execute();
			*/
			
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
		} finally {
			closeStatement(pstmt);
		}
	}
	
	private void populateFieldsOfStudy() {
		PreparedStatement pstmt = null;
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
		} finally {
			closeStatement(pstmt);
		}
	}
	
	//Fill GenderDemographics table to be used by processSchool()
	public int fillGenderDemographics(Result result) {
		PreparedStatement genderStmt = null;
		int GenderDemographicsID = 0; //0 by default so "uninitialized" error doesn't appear
		try {
			//1. Populate Gender Demographics
			genderStmt = conn.prepareStatement("INSERT INTO GenderDemographics (female, male) "
					+ "VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
			genderStmt.setDouble(1, result.students_female);
			genderStmt.setDouble(2, result.students_male);
			genderStmt.executeUpdate();
			ResultSet getGenderDemographicsIDResultSet = genderStmt.getGeneratedKeys();
			if(getGenderDemographicsIDResultSet.next())
			{
				GenderDemographicsID = getGenderDemographicsIDResultSet.getInt(1);
			}
		}
		catch (SQLException e) {
			System.out.println(e.toString());
		}
		finally {
			closeStatement(genderStmt);
		}
		return GenderDemographicsID;
	}
	
	//Fill EthnicDemographics table, to be used in processSchool()
	public int fillEthnicDemographics(Result result) {
		PreparedStatement ethnicityStmt = null;
		int EthnicDemographicsID = 0;
		try {
			//2. Populate Ethnicity Demographics table
			ethnicityStmt = conn.prepareStatement("INSERT INTO EthnicDemographics (white, black,"
					+ "hispanic, asian, american_indian_alaskan_native, native_hawaiian_pacific_islander, "
					+ "two_or_more, unknown, nonresident) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			ethnicityStmt.setDouble(1,  result.students_white);
			ethnicityStmt.setDouble(2, result.students_black);
			ethnicityStmt.setDouble(3, result.students_hispanic);
			ethnicityStmt.setDouble(4, result.students_asian);
			ethnicityStmt.setDouble(5, result.students_aian);
			ethnicityStmt.setDouble(6, result.students_nhpi);
			ethnicityStmt.setDouble(7, result.students_two_or_more);
			ethnicityStmt.setDouble(8, result.students_unknown);
			ethnicityStmt.setDouble(9, result.students_non_resident_alien);
			ethnicityStmt.executeUpdate();
			ResultSet getEthnicDemographicsIDResultSet = ethnicityStmt.getGeneratedKeys();
			if (getEthnicDemographicsIDResultSet.next())
			{
				EthnicDemographicsID = getEthnicDemographicsIDResultSet.getInt(1);
			}
		}
		catch (SQLException e) {
			System.out.println(e.toString());
		}
		finally {
			closeStatement(ethnicityStmt);
		}
		return EthnicDemographicsID;
	}
	
	//Fill school table, used in processSchool()
	public void fillSchool(Result result) {
		// 1. Fill in GenderDemographicsID table
			int GenderDemographicsID_for_school = fillGenderDemographics(result);
				
		// 2. Fill in EthnicDemographicsID table
			int EthnicDemographicsID_for_school = fillEthnicDemographics(result);
				
		PreparedStatement schoolStmt = null;
		try {
			//Populate school table
			String questionMarks = "";
			for (int i = 0; i<48; i++)
			{
				questionMarks += "?, ";
			}
			questionMarks += "?";
			
			//create SAT_pct_25_cumulative, SAT_pct_75_cumulative
			double SAT_pct_25_cumulative = result.SAT_25_math + result.SAT_25_reading + result.SAT_25_writing;
			double SAT_pct_75_cumulative = result.SAT_75_math + result.SAT_75_reading + result.SAT_75_writing;
			
			schoolStmt = conn.prepareStatement("INSERT INTO school (ID, name, url, "
					+ "alias, SAT_avg, SAT_pct_25_cumulative, SAT_pct_75_cumulative,"
					+ " SAT_pct_25_reading, SAT_pct_75_reading, "
					+ "SAT_pct_25_math, SAT_pct_75_math, SAT_pct_25_writing,"
					+ "SAT_pct_75_writing, SAT_midpoint_reading, SAT_midpoint_math,"
					+ "SAT_midpoint_writing,  ACT_pct_25_cumulative, ACT_pct_75_cumulative,"
					+ "ACT_pct_25_english, ACT_pct_75_english, ACT_pct_25_math,"
					+ "ACT_pct_75_math, ACT_pct_25_writing, ACT_pct_75_writing,"
					+ "ACT_avg, ACT_midpoint_english, ACT_midpoint_math, ACT_midpoint_writing,"
					+ "avg_earnings_6_years_after_matriculation, avg_cost, control, med_debt, std_bdy_sz,"
					+ "pop_prog_1, pop_prog_2, pop_prog_3, pop_prog_4, pop_prog_5,"
					+ "adm_rate, avg_fam_inc, med_fam_inc, tuition_and_fees_out, tuition_and_fees_in,"
					+ "avg_entry_age, 1_gen_std_share, level, dist_learning, GenderDemographics_ID,"
					+ "EthnicDemographics_ID) VALUES (" + questionMarks + ")");
			schoolStmt.setInt(1, result.id);
			schoolStmt.setString(2, result.schoolName);
			schoolStmt.setString(3, result.schoolURL);
			schoolStmt.setString(4, result.schoolAlias);
			schoolStmt.setDouble(5, result.SAT_avg);
			schoolStmt.setDouble(6, SAT_pct_25_cumulative);
			schoolStmt.setDouble(7, SAT_pct_75_cumulative);
			schoolStmt.setDouble(8,  result.SAT_25_reading);
			schoolStmt.setDouble(9,  result.SAT_75_reading);
			schoolStmt.setDouble(10, result.SAT_25_math);
			schoolStmt.setDouble(11, result.SAT_75_math);
			schoolStmt.setDouble(12, result.SAT_25_writing);
			schoolStmt.setDouble(13, result.SAT_75_writing);
			schoolStmt.setDouble(14, result.SAT_midpoint_reading);
			schoolStmt.setDouble(15, result.SAT_midpoint_math);
			schoolStmt.setDouble(16, result.SAT_midpoint_writing);
			schoolStmt.setDouble(17, result.ACT_25_cumulative);
			schoolStmt.setDouble(18, result.ACT_75_cumulative);
			schoolStmt.setDouble(19, result.ACT_25_english);
			schoolStmt.setDouble(20, result.ACT_75_english);
			schoolStmt.setDouble(21, result.ACT_25_math);
			schoolStmt.setDouble(22, result.ACT_75_math);
			schoolStmt.setDouble(23, result.ACT_25_writing);
			schoolStmt.setDouble(24, result.ACT_75_writing);
			schoolStmt.setDouble(25, result.ACT_avg);
			schoolStmt.setDouble(26, result.ACT_midpoint_english);
			schoolStmt.setDouble(27, result.ACT_midpoint_math);
			schoolStmt.setDouble(28, result.ACT_midpoint_writing);
			schoolStmt.setInt(29, result.moneyEarnings_6yrs_avg);
			schoolStmt.setInt(30, result.moneyAvgCost);
			schoolStmt.setInt(31, result.schoolOwnership);
			schoolStmt.setDouble(32, result.moneyMedianLoan);
			schoolStmt.setInt(33, result.schoolStudentSize);
			//adding top five fields
			ArrayList<String> topFive = result.topFiveFields();
			schoolStmt.setString(34, topFive.get(0));
			schoolStmt.setString(35, topFive.get(1));
			schoolStmt.setString(36, topFive.get(2));
			schoolStmt.setString(37, topFive.get(3));
			schoolStmt.setString(38, topFive.get(4));
			schoolStmt.setDouble(39, result.admissionRate);
			schoolStmt.setInt(40, result.moneyFamilyIncome_avg);
			schoolStmt.setInt(41, result.moneyFamilyIncome_median);
			schoolStmt.setInt(42, result.moneyTuition_and_fees_out_of_state);
			schoolStmt.setInt(43, result.moneyTuition_and_fees_in_state);
			schoolStmt.setInt(44, result.studentEntryAge);
			schoolStmt.setDouble(45, result.student_first_generation_students_share);
			schoolStmt.setInt(46, result.schoolLevel);
			schoolStmt.setInt(47, result.schoolDistanceLearning);
			//get IDs of GenderDemographics and EthnicDemographics tables
			//The IDs are identical because they have 1-to-1 relationship to
			//school
			if (GenderDemographicsID_for_school != 0)
			schoolStmt.setInt(48, GenderDemographicsID_for_school);
			if (EthnicDemographicsID_for_school != 0)
			schoolStmt.setInt(49, EthnicDemographicsID_for_school);
			
			//Execute the prepared statement to populate school table
			schoolStmt.execute();
		
		}
		catch (SQLException e) {
			System.out.println(e.toString());
		}
		finally {
			
			//close
			closeStatement(schoolStmt);	
		}
		
	}
	
	public int fillLocation(Result result) {
		PreparedStatement locationCheckStmt = null;
		PreparedStatement locationStmt = null;
		int locationID = 0;
		//If location exists, don't insert. Otherwise, insert.
		try {
			
			locationCheckStmt = conn.prepareStatement("SELECT * FROM location WHERE "
					+ "city=? AND state_string=? AND state=? AND ZIP=?;");
			locationCheckStmt.setString(1, result.locationCity);
			locationCheckStmt.setString(2, result.locationStateString);
			locationCheckStmt.setInt(3, result.locationState);
			locationCheckStmt.setInt(4, result.locationZIP);
			ResultSet locationExists = locationCheckStmt.executeQuery();
			if (!locationExists.next()) {
				//close resultset for db resources
				closeResultSet(locationExists);
				locationStmt = conn.prepareStatement("INSERT INTO location "
						+ "(city, state_string, state, ZIP) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
				locationStmt.setString(1, result.locationCity);
				locationStmt.setString(2, result.locationStateString);
				locationStmt.setInt(3, result.locationState);
				locationStmt.setInt(4, result.locationZIP);
				locationStmt.executeUpdate();
				ResultSet getLocationIDResultSet = locationStmt.getGeneratedKeys();
				if (getLocationIDResultSet.next())
				{
					locationID = getLocationIDResultSet.getInt(1);
				}
				closeResultSet(getLocationIDResultSet);
			}
		}
		catch (SQLException e) {
			System.out.println(e.toString());
		}
		finally {
			closeStatement(locationCheckStmt);
			closeStatement(locationStmt);
		}
		return locationID;
	}
	
	
	public void fillSchool_loc(Result result) {
		//execute fillLocation()
		int locationID_for_School_loc = fillLocation(result);
		PreparedStatement school_locStmt = null;
		if (locationID_for_School_loc != 0) {
			try {
				school_locStmt = conn.prepareStatement("INSERT INTO school_loc (school_ID, loc_ID) "
						+ "VALUES (?, ?)");
				school_locStmt.setInt(1, result.id);
				school_locStmt.setInt(2, locationID_for_School_loc);
				school_locStmt.executeUpdate();
			}
			catch (SQLException e) {
				System.out.println(e.toString());
			}
			finally {
				closeStatement(school_locStmt);
			}
		}
		
	}
	
	
	/**
	 * Updates database with school info
	 */
	public void processSchool(Result result) {
		
		//Fill in school table; also executes fillGenderDemographics
		// and fillEthnicDemographics methods
		fillSchool(result);
		
		//Fill location table & school_loc table
		fillSchool_loc(result);
		
		//TODO Offers
	}
}
