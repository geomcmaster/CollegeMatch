package test.java;

import main.java.*;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserDAOTest {
	private static final String CITY_1 = "Nowheresvilletownburg";
	private static final int STATE_1 = 12;
	private static final int ZIP_1 = 12345;
	
	private static final String USERNAME = "cool_username_bro25";
	private static final String PASSWORD = "tHisPWisS00safeIswear";
	private Connection conn;
	private UserDAO userDAO;
	
	@Before
	public void setUp() {
		conn = DBUtil.getConnection();
		userDAO = new UserDAO();
		
		//I haven't implemented createUser yet so adding to db manually for now to test with residence
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("INSERT INTO user (ID, password) VALUES (?, ?)");
			pstmt.setString(1, USERNAME);
			pstmt.setString(2, PASSWORD);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeStatement(pstmt);
		}
	}

	@Test
	public void testAddResidence() {
		//NON-EXISTENT LOCATION
		
		//add residence in CITY_1
		userDAO.addResidence(USERNAME, CITY_1, STATE_1, ZIP_1);
		
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		try {
			String getLoc = "SELECT ID, city, state, ZIP FROM location WHERE city=?";
			pstmt1 = conn.prepareStatement(getLoc);
			pstmt1.setString(1, CITY_1);
			rs1 = pstmt1.executeQuery();
			int locID = -1;
			if (rs1.next()) {	//row 1
				locID = rs1.getInt(1);
				assertEquals("State is incorrect", STATE_1, rs1.getInt(3));
				assertEquals("ZIP is incorrect", ZIP_1, rs1.getInt(4));
			} else {
				fail("City not found in location table");
			}
			if (rs1.next()) {
				fail("Multiple location rows for this city");
			}
			
			pstmt2 = conn.prepareStatement("SELECT loc_ID FROM residence WHERE std_ID=?");
			pstmt2.setString(1, USERNAME);
			rs2 = pstmt2.executeQuery();
			if (rs2.next()) {
				assertEquals("Location ID doesn't match", locID, rs2.getInt(1));
			} else {
				fail("Row not found in residence table");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeStatement(pstmt1);
			DBUtil.closeStatement(pstmt2);
			DBUtil.closeResultSet(rs1);
			DBUtil.closeResultSet(rs2);
		}
		
		
		//EXISTING LOCATION
		//TODO
	}

	@After
	public void cleanUp() {
		cleanUpResidence();
		cleanUpLocations();
		cleanUpUser();
		
		//ALWAYS LAST
		DBUtil.closeConnection();
	}
	
	private void cleanUpLocations() {
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("DELETE FROM location WHERE city=?");
			pstmt.setString(1, CITY_1);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeStatement(pstmt);
		}
	}
	
	private void cleanUpResidence() {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate("DELETE FROM residence");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeStatement(stmt);
		}
	}
	
	private void cleanUpUser() {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate("DELETE FROM user");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeStatement(stmt);
		}
	}
}
