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
	private Connection conn;
	
	public UserDAO() {
		conn = DBUtil.getConnection();
	}
	
	//create user
	//get user
	//update user
	//delete user
	
	//RESIDENCE
	//add residence
	public void addResidence(String stdID, String city, int state, int zip) {
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
			findLoc = conn.prepareStatement(getLocIdCnt);
			findLoc.setString(1, city);
			findLoc.setInt(2, state);
			findLoc.setInt(3, zip);
			rs = findLoc.executeQuery();
			
			//does location already exist?
			if (rs.next() && rs.getInt(2) > 0) {
				insertWithID = conn.prepareStatement(insertForExistingLoc);
				insertWithID.setString(1, stdID);
				insertWithID.setInt(2, rs.getInt(1));	//existing location ID
				insertWithID.executeUpdate();
			} else {
				//create location
				newLoc = conn.prepareStatement(createLoc);
				newLoc.setString(1, city);
				newLoc.setInt(2, state);
				newLoc.setInt(3, zip);
				newLoc.executeUpdate();
				//create residence
				resWithNewLoc = conn.prepareStatement(createResForNewLoc);
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
