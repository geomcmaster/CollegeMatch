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
	
	private static final String USERNAME_1 = "cool_username_bro25";
	private static final String PASSWORD_1 = "tHisPWisS00safeIswear";
	private static final String USERNAME_2 = "even_better_username";
	private static final String PASSWORD_2 = "7h3gr34t357pw3v3r";
	private DBUtil dbUtil;
	private UserDAO userDAO;
	
	@Before
	public void setUp() {
		dbUtil = new DBUtil();
		userDAO = new UserDAO();
		userDAO.createUser(USERNAME_1, PASSWORD_1);
	}

	@Test
	public void testCreateUser() {
		assertTrue(userDAO.createUser(USERNAME_2, PASSWORD_2));
		assertFalse(userDAO.createUser(USERNAME_2, "a_new_pw"));
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = dbUtil.getConnection().prepareStatement("SELECT ID, password FROM user WHERE ID=?");
			pstmt.setString(1, USERNAME_2);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				assertEquals("User doesn't have right password stored", PASSWORD_2, rs.getString(2));
			}
			assertFalse("Duplicate user created", rs.next());
		} catch (SQLException e){
			e.printStackTrace();
		} finally {
			DBUtil.closeResultSet(rs);
			DBUtil.closeStatement(pstmt);
		}
	}
	
	@Test
	public void testAddResidence() {
		//NON-EXISTENT LOCATION
		
		//add residence in CITY_1
		userDAO.addResidence(USERNAME_1, CITY_1, STATE_1, ZIP_1);
		
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		try {
			String getLoc = "SELECT ID, city, state, ZIP FROM location WHERE city=?";
			pstmt1 = dbUtil.getConnection().prepareStatement(getLoc);
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
			assertFalse("Multiple location rows for this city", rs1.next());
			
			pstmt2 = dbUtil.getConnection().prepareStatement("SELECT loc_ID FROM residence WHERE std_ID=?");
			pstmt2.setString(1, USERNAME_1);
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
		//TODO test existing location. include test where there are null values
	}

	@After
	public void cleanUp() {
		cleanUpResidence();
		cleanUpLocations();
		cleanUpUser();
		
		//ALWAYS LAST
		dbUtil.closeConnection();
	}
	
	private void cleanUpLocations() {
		PreparedStatement pstmt = null;
		try {
			pstmt = dbUtil.getConnection().prepareStatement("DELETE FROM location WHERE city=?");
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
			stmt = dbUtil.getConnection().createStatement();
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
			stmt = dbUtil.getConnection().createStatement();
			stmt.executeUpdate("DELETE FROM user");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeStatement(stmt);
		}
	}
}
