package main.java;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data access object for user-related queries
 * 
 * @author Geoff
 *
 */
public class UserDAO {
	private DBUtil dbUtil;
	
	public UserDAO() {
		dbUtil = new DBUtil();
	}
	
	//TODO probably want to create classes to represent objects (or lists of objects) that could be returned.
	
	//USER
	/**
	 * Create a user
	 * 
	 * @param id User ID
	 * @param password User password
	 * @return true if successful, false if user name already in use
	 */
	public boolean createUser(String id, String password) {
		PreparedStatement findUser = null;
		ResultSet rs = null;
		PreparedStatement insertUser = null;
		
		try {
			findUser = dbUtil.getConnection().prepareStatement("SELECT COUNT(*) FROM user WHERE ID=?");
			findUser.setString(1, id);
			rs = findUser.executeQuery();
			if (rs.next() && rs.getInt(1) > 0) {
				return false;	//username already in use
			}
			
			insertUser = dbUtil.getConnection().prepareStatement("INSERT INTO user (ID, password) VALUES (?, ?)");
			insertUser.setString(1, id);
			insertUser.setString(2, password);
			insertUser.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeStatement(findUser);
			DBUtil.closeResultSet(rs);
			DBUtil.closeStatement(insertUser);
		}
		return true;
	}
	//get user
	//update user
	//delete user
	
	//RESIDENCE
	/**
	 * Adds an entry in the residence table that references location table. Creates location if necessary.
	 * 
	 * @param stdID user ID
	 * @param city City of residence
	 * @param state State of residence
	 * @param zip ZIP code of residence
	 */
	public void addResidence(String stdID, String city, int state, int zip) {
		//TODO handle case where user already has a residence entry. use ON DUPLICATE KEY UPDATE?
		//TODO this treats empty strings as such. Do we want to consider them null instead?
		PreparedStatement findLoc = null;
		String getLocIdCnt = 
				"SELECT id, COUNT(*) "
				+ "FROM LOCATION "
				+ "WHERE city = ? AND state = ? AND ZIP = ?";
		PreparedStatement insertWithID = null;
		String insertForExistingLoc = 
				"INSERT INTO residence (std_ID, loc_ID) "
				+ "VALUES (?, ?)";
		PreparedStatement newLoc = null;
		String createLoc = 
				"INSERT INTO location (city, state, ZIP) "
				+ "VALUES (?, ?, ?)";
		PreparedStatement resWithNewLoc = null;
		String createResForNewLoc = 
				"INSERT INTO residence (std_ID, loc_ID) "
				+ "VALUES (?, LAST_INSERT_ID())";
		ResultSet rs = null;
		
		try {
			findLoc = dbUtil.getConnection().prepareStatement(getLocIdCnt);
			findLoc.setString(1, city);
			findLoc.setInt(2, state);
			findLoc.setInt(3, zip);
			rs = findLoc.executeQuery();
			
			//does location already exist?
			if (rs.next() && rs.getInt(2) > 0) {
				insertWithID = dbUtil.getConnection().prepareStatement(insertForExistingLoc);
				insertWithID.setString(1, stdID);
				insertWithID.setInt(2, rs.getInt(1));	//existing location ID
				insertWithID.executeUpdate();
			} else {
				//create location
				newLoc = dbUtil.getConnection().prepareStatement(createLoc);
				newLoc.setString(1, city);
				newLoc.setInt(2, state);
				newLoc.setInt(3, zip);
				newLoc.executeUpdate();
				//create residence
				resWithNewLoc = dbUtil.getConnection().prepareStatement(createResForNewLoc);
				resWithNewLoc.setString(1, stdID);
				resWithNewLoc.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeStatement(findLoc);
			DBUtil.closeStatement(insertWithID);
			DBUtil.closeStatement(newLoc);
			DBUtil.closeStatement(resWithNewLoc);
			DBUtil.closeResultSet(rs);
		}
	}
	//modify residence
	//get residence
	
	//FIELDS OF STUDY
	//add favorite field of study
	//get favorite fields of study
	//update favorite field of study
	//delete favorite field of study
	
	//SCHOOLS
	//add favorite school
	//get favorite schools
	//update favorite schools
	//delete favorite schools

}
