package main.java;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

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
	 * @param userName User name
	 * @param password User password
	 * @return true if successful, false if user name already in use
	 */
	public boolean createUser(String userName, String password) {
		PreparedStatement findUser = null;
		ResultSet rs = null;
		PreparedStatement insertUser = null;
		
		try {
			findUser = dbUtil.getConnection().prepareStatement("SELECT COUNT(*) FROM user WHERE ID=?");
			findUser.setString(1, userName);
			rs = findUser.executeQuery();
			if (rs.next() && rs.getInt(1) > 0) {
				return false;	//username already in use
			}
			
			insertUser = dbUtil.getConnection().prepareStatement("INSERT INTO user (ID, password) VALUES (?, ?)");
			insertUser.setString(1, userName);
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
	 * Verifies a user's password entry
	 * 
	 * @param userName
	 * @param password
	 * @return True if password is correct, false if not
	 */
	public boolean verifyPassword(String userName, String password) {
		boolean valid = false;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = dbUtil.getConnection().prepareStatement("SELECT password FROM user WHERE ID=?");
			pstmt.setString(1, userName);
			rs = pstmt.executeQuery();
			if (rs.next() && rs.getString(1).equals(password)) {
				valid = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResultSet(rs);
			DBUtil.closeStatement(pstmt);
		}
		return valid;
	}
	
	/**
	 * Get user by username
	 * 
	 * @param userName
	 * @return User object
	 */
	public User getUser(String userName) {
		User user = new User();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = 
					dbUtil.getConnection().prepareStatement("SELECT password, SAT_SCORE, ACT_SCORE "
							+ "FROM user WHERE ID=?");
			pstmt.setString(1, userName);
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
			DBUtil.closeResultSet(rs);
			DBUtil.closeStatement(pstmt);
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

	/**
	 * Deletes a user record
	 * 
	 * @param userName
	 */
	public void deleteUser(String userName) {
		PreparedStatement pstmt = null;
		try {
			pstmt = dbUtil.getConnection().prepareStatement("DELETE FROM user WHERE ID=?");
			pstmt.setString(1, userName);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeStatement(pstmt);
		}
	}
	
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
		//TODO do we need to handle case where user already has a residence entry (use ON DUPLICATE KEY UPDATE?)?
		//	or can we assume this is only called if one doesn't exist? If it's the former, we don't need a 
		//	modify method.
		//TODO this treats empty strings as such. Do we want to consider them null instead? At the very least
		//	we probably shouldn't add a location for "", "", "", right?
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
	//get fields of study?

	/**
	 * Adds a favorite field of study.
	 * Does not check: 
	 * 		-if ID exists in fieldsOfStudy table
	 * 		-if entry for this field and user already exists
	 * 		-if user has already assigned this rank
	 * 
	 * @param userName User adding the favorite
	 * @param fieldID ID of the field of study to add
	 * @param rank Rank for this field of study
	 */
	public void addFavField(String userName, int fieldID, int rank) {
		PreparedStatement pstmt = null;
		try {
			pstmt = 
					dbUtil.getConnection().prepareStatement(
							"INSERT INTO favoriteFieldsOfStudy (std_ID, field_ID, rank) "
							+ "VALUES (?, ?, ?)");
			pstmt.setString(1, userName);
			pstmt.setInt(2, fieldID);
			pstmt.setInt(3, rank);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeStatement(pstmt);
		}
	}

	/**
	 * Gets a user's favorite fields of study
	 * 
	 * @param userName The user whose favorites we are grabbing
	 * @return A list of favorite fields of study from highest (i.e. 1) to lowest
	 */
	public List<FavoriteFieldOfStudy> getFavFields(String userName) {
		LinkedList<FavoriteFieldOfStudy> favs = new LinkedList<FavoriteFieldOfStudy>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = 
					dbUtil.getConnection().prepareStatement(
							"SELECT favoriteFieldsOfStudy.rank, fieldsOfStudy.name "
							+ "FROM favoriteFieldsOfStudy "
							+ "JOIN fieldsOfStudy ON favoriteFieldsOfStudy.field_ID = fieldsOfStudy.ID "
							+ "WHERE favoriteFieldsOfStudy.std_ID=? "
							+ "ORDER BY favoriteFieldsOfStudy.rank ASC");
			pstmt.setString(1, userName);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				FavoriteFieldOfStudy fav = new FavoriteFieldOfStudy();
				fav.setRank(rs.getInt(1));
				fav.setFieldOfStudy(rs.getString(2));
				favs.addLast(fav);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResultSet(rs);
			DBUtil.closeStatement(pstmt);
		}
		return favs;
	}
	//update favorite field of study
	//delete favorite field of study
	
	//SCHOOLS
	//add favorite school
	//get favorite schools
	//update favorite schools
	//delete favorite schools

}
