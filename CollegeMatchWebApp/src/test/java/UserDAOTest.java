package test.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.java.DBUtil;
import main.java.FavoriteFieldOfStudy;
import main.java.User;
import main.java.UserDAO;

/**
 * JUnit test class for UserDAO.java. Could use cleaning up.
 * 
 * @author Geoff
 *
 */
public class UserDAOTest {
	private static final String CITY_1 = "Nowheresvilletownburg";
	private static final int STATE_1 = 12;
	private static final int ZIP_1 = 12345;
	
	private static final String CITY_2 = "Sunnydale";
	private static final int STATE_2 = 6;
	private static final int ZIP_2 = 99999;
	
	private static final String CITY_3 = "Fran Sancisco";
	private static final int ZIP_3 = 98765;
	
	
	private static final String USERNAME_1 = "cool_username_bro25";
	private static final String PASSWORD_1 = "tHisPWisS00safeIswear";
	
	private static final String USERNAME_2 = "even_better_username";
	private static final String PASSWORD_2 = "7h3gr34t357pw3v3r";
	
	private static final String USERNAME_3 = "coolcoolcool25";
	private static final String PASSWORD_3 = "abc123";
	
	private static final String USERNAME_4 = "catscatscats";
	private static final String PASSWORD_4 = "55555";
	private DBUtil dbUtil;
	private UserDAO userDAO;
	
	@Before
	public void setUp() {
		dbUtil = new DBUtil();
		userDAO = new UserDAO();
		userDAO.createUser(USERNAME_1, PASSWORD_1);	//used by testAddResidence, testDeleteFavField
		userDAO.createUser(USERNAME_3, PASSWORD_3);	//used by testAddResidence, testAddFavField
		userDAO.createUser(USERNAME_4, PASSWORD_4);	//used by testAddResidence, testGetFavFields
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
	public void testVerifyPassword() {
		String userName = "imAuserYESiAM";
		String password = "mYpassW0RD_#";
		userDAO.createUser(userName, password);
		assertFalse("Bad password validated", userDAO.verifyPassword(userName, "justmakingthisup"));
		assertTrue("Good password rejected", userDAO.verifyPassword(userName, password));
	}
	
	@Test
	public void testGetUser() {
		//non-existent user
		assertFalse("Invalid user not listed as invalid", userDAO.getUser("didntcreatethisuser").isValid());
		//existing user
		userDAO.createUser("testingGetUser", "456abc789");
		userDAO.updateUser("testingGetUser", "456abc789", 1555, 30);
		User user = userDAO.getUser("testingGetUser");
		assertTrue("Valid user not listed as valid", user.isValid());
		assertEquals("User password not correct", "456abc789", user.getPassword());
		assertEquals("SAT score not correct", 1555, user.getSatScore());
		assertEquals("ACT score not correct", 30, user.getActScore());
	}
	
	@Test
	public void testUpdateUser() {
		String userName = "goingtoupdatethis";
		userDAO.createUser(userName, "awesomesauce");
		//No existing score values
		userDAO.updateUser(userName, "newpassword", 1600, 30);
		User user = userDAO.getUser(userName);
		assertEquals("Password not updated", "newpassword", user.getPassword());
		assertEquals("SAT score not updated", 1600, user.getSatScore());
		assertEquals("ACT score not updated", 30, user.getActScore());
		//Modify score values, keep password
		userDAO.updateUser(userName, "newpassword", 1200, 10);
		User userTakeTwo = userDAO.getUser(userName);
		assertEquals("Password not updated", "newpassword", userTakeTwo.getPassword());
		assertEquals("SAT score not updated", 1200, userTakeTwo.getSatScore());
		assertEquals("ACT score not updated", 10, userTakeTwo.getActScore());
	}
	
	@Test
	public void testDeleteUser() {
		String userName = "goingtodeletethis";
		userDAO.createUser(userName, "anotherpassword");
		userDAO.deleteUser(userName);
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = dbUtil.getConnection().prepareStatement("SELECT COUNT(*) FROM user WHERE ID=?");
			pstmt.setString(1, userName);
			rs = pstmt.executeQuery();
			rs.next();
			assertEquals("User not deleted", 0, rs.getInt(1));
		} catch (SQLException e) {
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
		PreparedStatement pstmt3 = null;
		ResultSet rs3 = null;
		String q = "INSERT INTO location (city, state, ZIP) VALUES (?, ?, ?)";
		PreparedStatement pstmt4 = null;
		ResultSet rs4 = null;
		
		PreparedStatement pstmt5 = null;
		ResultSet rs5 = null;
		String q2 = "INSERT INTO location (city, ZIP) VALUES (?, ?)";
		PreparedStatement pstmt6 = null;
		ResultSet rs6 = null;
		try {
			pstmt3 = dbUtil.getConnection().prepareStatement(q, Statement.RETURN_GENERATED_KEYS);
			pstmt3.setString(1, CITY_2);
			pstmt3.setInt(2, STATE_2);
			pstmt3.setInt(3, ZIP_2);
			pstmt3.executeUpdate();
			rs3 = pstmt3.getGeneratedKeys();
			assertTrue("No key generated", rs3.next());
			int id = rs3.getInt(1);
			userDAO.addResidence(USERNAME_3, CITY_2, STATE_2, ZIP_2);
			pstmt4 = dbUtil.getConnection().prepareStatement("SELECT loc_ID FROM residence WHERE std_ID=?");
			pstmt4.setString(1, USERNAME_3);
			rs4 = pstmt4.executeQuery();
			assertTrue("Residence not found", rs4.next());
			assertTrue("Location ID does not match existing location", rs4.getInt(1) == id);
			
			//existing location but one value is null
			pstmt5 = dbUtil.getConnection().prepareStatement(q2, Statement.RETURN_GENERATED_KEYS);
			pstmt5.setString(1, CITY_3);
			pstmt5.setInt(2, ZIP_3);
			pstmt5.executeUpdate();
			rs5 = pstmt5.getGeneratedKeys();
			assertTrue("No key generated", rs5.next());
			int id2 = rs5.getInt(1);
			userDAO.addResidence(USERNAME_4, CITY_3, 41, ZIP_3);
			pstmt6 = dbUtil.getConnection().prepareStatement("SELECT loc_ID FROM residence WHERE std_ID=?");
			pstmt6.setString(1, USERNAME_4);
			rs6 = pstmt6.executeQuery();
			assertTrue("Residence not found", rs6.next());
			assertFalse("Inappropriately uses existing location", rs6.getInt(1) == id2);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeStatement(pstmt3);
			DBUtil.closeResultSet(rs3);
			DBUtil.closeStatement(pstmt4);
			DBUtil.closeResultSet(rs4);
			DBUtil.closeStatement(pstmt5);
			DBUtil.closeResultSet(rs5);
			DBUtil.closeStatement(pstmt6);
			DBUtil.closeResultSet(rs6);
		}
	}
	
	@Test
	public void testAddFavField() {
		int fieldID = userDAO.getFieldID("Psychology");
		userDAO.addFavField(USERNAME_3, fieldID, 1);
		FavoriteFieldOfStudy field = userDAO.getFavFields(USERNAME_3).get(0);
		assertEquals("Added field of study name not correct", "Psychology", field.getFieldOfStudy());
		assertEquals("Added field of study rank not correct", 1, field.getRank());
	}
	
	@Test
	public void testGetFavFields() {
		//no favs
		assertEquals("User without favorite fields returns list size > 0", 0, userDAO.getFavFields(USERNAME_4).size());
		
		//one fav
		int fieldID = userDAO.getFieldID("Mathematics and Statistics");
		int fieldID2 = userDAO.getFieldID("Engineering");
		int fieldID3 = userDAO.getFieldID("Computer and Information Sciences and Support Services");

		userDAO.addFavField(USERNAME_4, fieldID, 4);
		List<FavoriteFieldOfStudy> fields = userDAO.getFavFields(USERNAME_4);
		assertEquals("More than one favorite field of study found", 1, fields.size());
		assertEquals("Field name not correct", "Mathematics and Statistics", fields.get(0).getFieldOfStudy());
		assertEquals("Rank not correct", 4, fields.get(0).getRank());
		
		//multiple favs
		userDAO.addFavField(USERNAME_4, fieldID2, 2);
		userDAO.addFavField(USERNAME_4, fieldID3, 6);
		fields = userDAO.getFavFields(USERNAME_4);
		assertEquals("Wrong number of favorite fields", 3, fields.size());
		assertEquals("Field name not correct", "Engineering", fields.get(0).getFieldOfStudy());
		assertEquals("Rank not correct", 2, fields.get(0).getRank());
		assertEquals("Field name not correct", "Mathematics and Statistics", fields.get(1).getFieldOfStudy());
		assertEquals("Rank not correct", 4, fields.get(1).getRank());
		assertEquals("Field name not correct", "Computer and Information Sciences and Support Services", 
				fields.get(2).getFieldOfStudy());
		assertEquals("Rank not correct", 6, fields.get(2).getRank());
	}
		
	@Test
	public void testDeleteFavField() {
		String field1 = "Biological and Biomedical Sciences";
		String field2 = "Foreign Languages, Literatures, and Linguistics";
		String field3 = "Philosophy and Religious Studies";
		
		int fieldID = userDAO.getFieldID(field1);
		userDAO.addFavField(USERNAME_1, fieldID, 2);

		//delete only fav
		userDAO.deleteFavField(USERNAME_1, fieldID);
		assertEquals("Favorite field not deleted", 0, userDAO.getFavFields(USERNAME_1).size());

		int fieldID2 = userDAO.getFieldID(field2);
		int fieldID3 = userDAO.getFieldID(field3);
		userDAO.addFavField(USERNAME_1, fieldID2, 3);
		userDAO.addFavField(USERNAME_1, fieldID3, 1);
		
		//delete 1 of 2
		userDAO.deleteFavField(USERNAME_1, fieldID3);
		assertEquals("Wrong number of favorite fields remaining", 1, userDAO.getFavFields(USERNAME_1).size());
		assertEquals("Wrong field deleted", field2, userDAO.getFavFields(USERNAME_1).get(0).getFieldOfStudy());
		
		//delete 2 of 2
		userDAO.deleteFavField(USERNAME_1, fieldID2);
		assertEquals("Favorite field not deleted", 0, userDAO.getFavFields(USERNAME_1).size());
	}
	
	@Test
	public void testAddFavSchool() {
		//TODO implement. Holding off until we have schools in our db
	}
	
	@Test
	public void testUpdateFavSchool() {
		//TODO implement. Holding off until we have schools in our db
	}
	
	@Test
	public void testDeleteFavSchool() {
		//TODO implement. Holding off until we have schools in our db
	}

	@After
	public void cleanUp() {
		cleanUpResidence();
		cleanUpLocations();
		cleanUpFavoriteFields();
		cleanUpFavoriteSchools();
		cleanUpUser();
		
		//ALWAYS LAST
		dbUtil.closeConnection();
	}
	
	private void cleanUpLocations() {
		PreparedStatement pstmt = null;
		try {
			//using city condition because when we have real locations 
			//populated from schools we don't want to delete them
			pstmt = dbUtil.getConnection().prepareStatement("DELETE FROM location WHERE city=? OR city=? OR city=?");
			pstmt.setString(1, CITY_1);
			pstmt.setString(2, CITY_2);
			pstmt.setString(3, CITY_3);
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
	
	private void cleanUpFavoriteFields() {
		Statement stmt = null;
		try {
			stmt = dbUtil.getConnection().createStatement();
			stmt.executeUpdate("DELETE FROM favoriteFieldsOfStudy");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeStatement(stmt);
		}
	}
	
	private void cleanUpFavoriteSchools() {
		Statement stmt = null;
		try {
			stmt = dbUtil.getConnection().createStatement();
			stmt.executeUpdate("DELETE FROM favoriteSchools");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeStatement(stmt);
		}
	}
}
