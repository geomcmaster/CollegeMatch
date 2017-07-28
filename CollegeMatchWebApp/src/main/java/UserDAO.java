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
	
	/**
	 * Get user by username
	 * 
	 * @param username
	 * @return User object
	 */
	public User getUser(String username) {
		//TODO where will this method be used? Do we want to conditionally get location, favorites? Or just get those 
		//in separate method
		User user = new User();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = 
					dbUtil.getConnection().prepareStatement("SELECT password, SAT_SCORE, ACT_SCORE "
							+ "FROM user WHERE ID=?");
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user.setValid(true);
				user.setPassword(rs.getString(1));
				user.setSatScore(rs.getInt(2));
				user.setActScore(rs.getInt(3));
			} else {
				user.setValid(false);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbUtil.closeResultSet(rs);
			dbUtil.closeStatement(pstmt);
		}
		return user;
	}
	
	/**
	 * Updates user with given password, SAT score, ACT score
	 * 
	 * @param userName
	 * @param password
	 * @param satScore
	 * @param actScore
	 */
	public void updateUser(String userName, String password, int satScore, int actScore) {
		//assuming that we won't let them change username
		//assuming that existing values will be passed if they are to stay the same
			//alternative is having separate methods for each
		PreparedStatement pstmt = null;
		try {
			pstmt = 
					dbUtil.getConnection().prepareStatement("UPDATE user "
							+ "SET password=?, SAT_SCORE=?, ACT_SCORE=? "
							+ "WHERE id=?");
			pstmt.setString(1, password);
			pstmt.setInt(2, satScore);
			pstmt.setInt(3, actScore);
			pstmt.setString(4, userName);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeStatement(pstmt);
		}
	}
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
	//get all fields of study?
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
