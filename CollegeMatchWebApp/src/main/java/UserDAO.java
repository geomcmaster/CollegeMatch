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
	 * Modifies or adds (if it doesn't exist) an entry in the residence table that references location table. 
	 * Creates location if necessary.
	 * 
	 * @param stdID user ID
	 * @param city City of residence
	 * @param state State of residence
	 * @param zip ZIP code of residence
	 */
	public void modifyResidence(String stdID, String city, int state, int zip) {
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
				+ "VALUES (?, ?) ON DUPLICATE KEY UPDATE loc_ID=?";
		PreparedStatement newLoc = null;
		String createLoc = 
				"INSERT INTO location (city, state, ZIP) "
				+ "VALUES (?, ?, ?)";
		PreparedStatement resWithNewLoc = null;
		String createResForNewLoc = 
				"INSERT INTO residence (std_ID, loc_ID) "
				+ "VALUES (?, LAST_INSERT_ID()) ON DUPLICATE KEY UPDATE loc_ID=LAST_INSERT_ID()";
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
				int existingID = rs.getInt(1);			//existing location ID
				insertWithID.setInt(2, existingID);
				insertWithID.setInt(3, existingID);
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
	
	/**
	 * Gets the current residence of a user. If none found, Location.isValid() returns false.
	 * 
	 * @param userName The user to get the residence for
	 * @return A location object representing the user's residence
	 */
	public Location getResidence(String userName) {
		Location loc = new Location();
		loc.setValid(false);
		
		String query = 
				"SELECT location.ID, location.city, location.state, location.zip "
				+ "FROM residence JOIN location ON residence.loc_ID = location.ID "
				+ "WHERE residence.std_ID=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = dbUtil.getConnection().prepareStatement(query);
			pstmt.setString(1, userName);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				loc.setValid(true);
				loc.setId(rs.getInt(1));
				loc.setCity(rs.getString(2));
				loc.setState(rs.getInt(3));
				loc.setZip(rs.getInt(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResultSet(rs);
			DBUtil.closeStatement(pstmt);
		}
		
		return loc;
	}
	
	//FIELDS OF STUDY
	//get fields of study?
	
	/**
	 * Returns the ID for a field of study by name. Throws a runtime exception if no field matching that name is found.
	 * 
	 * @param fieldName Field of study
	 * @return The ID of this field of study
	 */
	public int getFieldID(String fieldName) {
		int fieldID = -1;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = dbUtil.getConnection().prepareStatement("SELECT ID FROM fieldsOfStudy WHERE name=?");
			pstmt.setString(1, fieldName);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				fieldID = rs.getInt(1);
			} else {
				throw new RuntimeException();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResultSet(rs);
			DBUtil.closeStatement(pstmt);
		}
		return fieldID;
	}

	/**
	 * Modifies a favorite field of study or adds it if entry doesn't exist.
	 * Does not check: 
	 * 		-if ID exists in fieldsOfStudy table
	 * 		-if entry for this field and user already exists
	 * 		-if user has already assigned this rank
	 * 
	 * @param userName User adding the favorite
	 * @param fieldID ID of the field of study to add
	 * @param rank Rank for this field of study
	 */
	public void modifyFavField(String userName, int fieldID, int rank) {
		PreparedStatement pstmt = null;
		try {
			pstmt = 
					dbUtil.getConnection().prepareStatement(
							"INSERT INTO favoriteFieldsOfStudy (std_ID, field_ID, rank) "
							+ "VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE rank=?");
			pstmt.setString(1, userName);
			pstmt.setInt(2, fieldID);
			pstmt.setInt(3, rank);
			pstmt.setInt(4, rank);
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

	/**
	 * Deletes one of a user's favorites field of study
	 * 
	 * @param userName The user deleting a favorite field
	 * @param fieldID The ID of the field to delete from this user's favorites
	 */
	public void deleteFavField(String userName, int fieldID) {
		PreparedStatement pstmt = null;
		try {
			pstmt = 
					dbUtil.getConnection().prepareStatement(
							"DELETE FROM favoriteFieldsOfStudy WHERE std_ID=? AND field_ID=?");
			pstmt.setString(1, userName);
			pstmt.setInt(2, fieldID);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeStatement(pstmt);
		}
	}
	
	//SCHOOLS
	/**
	 * Adds a favorite school for this user
	 * 
	 * @param userName The user adding a favorite
	 * @param schoolID The ID of the school to add
	 */
	public void addFavSchool(String userName, int schoolID) {
		PreparedStatement pstmt = null;
		try {
			pstmt = 
					dbUtil.getConnection().prepareStatement(
							"INSERT INTO favoriteSchools (std_ID, school_ID) VALUES (?, ?)");
			pstmt.setString(1, userName);
			pstmt.setInt(2, schoolID);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeStatement(pstmt);
		}
	}
	
	/**
	 * Gets a user's favorite schools
	 * 
	 * @param userName The user whose favorites we are grabbing
	 * @return A list of favorite schools from highest (i.e. 1) to lowest
	 */
	public List<FavoriteSchool> getFavSchools(String userName) {
		LinkedList<FavoriteSchool> favs = new LinkedList<FavoriteSchool>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = 
					dbUtil.getConnection().prepareStatement(
							"SELECT school.name, rank, app_status, fin_aid, loan_amt, merit_scholarships "
							+ "FROM favoriteSchools "
							+ "JOIN fieldsSchools ON favoriteSchools.school_ID = school.ID "
							+ "WHERE std_ID=? "
							+ "ORDER BY rank ASC");
			pstmt.setString(1, userName);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				FavoriteSchool fav = new FavoriteSchool();
				School school = new School();
				school.setName(rs.getString(1));
				fav.setSchool(school);
				fav.setRank(rs.getInt(2));
				fav.setStatus(rs.getString(3));
				fav.setFinancialAid(rs.getInt(4));
				fav.setLoan(rs.getInt(5));
				fav.setMerit(rs.getInt(6));
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

	/**
	 * Updates a favorite school entry. Currently no way to update only certain columns.
	 * 
	 * @param userName The user making the update
	 * @param schoolID The school entry to update
	 * @param rank The new rank value
	 * @param appStatus The new app_status value
	 * @param finAid The new fin_aid value
	 * @param loanAmt The new loan_amt value
	 * @param meritScholarships The new merit_scholarships value
	 */
	public void updateFavSchool(String userName, int schoolID, 
			int rank, String appStatus, int finAid, int loanAmt, int meritScholarships) {
		String query = 
				"UPDATE favoriteSchools "
				+ "SET rank=?, app_status=?, fin_aid=?, loan_amt=?, merit_scholarships=? "
				+ "WHERE std_ID=? AND school_ID=?";
		PreparedStatement pstmt = null;
		try {
			pstmt = dbUtil.getConnection().prepareStatement(query);
			pstmt.setInt(1, rank);
			pstmt.setString(2, appStatus);
			pstmt.setInt(3, finAid);
			pstmt.setInt(4, loanAmt);
			pstmt.setInt(5, meritScholarships);
			pstmt.setString(6, userName);
			pstmt.setInt(7, schoolID);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeStatement(pstmt);
		}
	}

	/**
	 * Deletes a favorite school for a user
	 * 
	 * @param userName The user deleting the entry
	 * @param schoolID The school entry to delete
	 */
	public void deleteFavSchool(String userName, int schoolID) {
		PreparedStatement pstmt = null;
		try {
			pstmt = 
					dbUtil.getConnection().prepareStatement(
							"DELETE FROM favoriteSchools "
							+ "WHERE std_ID=? AND school_ID=?");
			pstmt.setString(1, userName);
			pstmt.setInt(2, schoolID);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeStatement(pstmt);
		}
	}

}
