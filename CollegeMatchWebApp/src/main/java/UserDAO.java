package main.java;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
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
		CallableStatement cstmt = null;
		boolean success = false;
		try {
			cstmt = dbUtil.getConnection().prepareCall("{call create_user(?, ?, ?)}");
			cstmt.setString(1, userName);
			cstmt.setString(2, password);
			cstmt.registerOutParameter(3, Types.INTEGER);
			cstmt.execute();
			int valid = cstmt.getInt(3);
			if (valid == 1) {
				success = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeStatement(cstmt);
		}
		return success;
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
		CallableStatement cstmt = null;
		ResultSet rs = null;
		try {
			cstmt = dbUtil.getConnection().prepareCall("{call get_user(?)}");
			cstmt.setString(1, userName);
			rs = cstmt.executeQuery();
			if (rs.next()) {
				user.setValid(true);
				String pw = rs.getString(1);
				if (!rs.wasNull()) {
					user.setPasswordIsNotNull(true);
					user.setPassword(pw);
				}
				int SATScore = rs.getInt(2);
				if (!rs.wasNull()) {
					user.setSatScoreIsNotNull(true);
					user.setSatScore(SATScore);
				}
				int ACTScore = rs.getInt(3);
				if (!rs.wasNull()) {
					user.setActScoreIsNotNull(true);
					user.setActScore(ACTScore);
				}
			} else {
				user.setValid(false);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResultSet(rs);
			DBUtil.closeStatement(cstmt);
		}
		return user;
	}
	
	/**
	 * Updates user with new password
	 * 
	 * @param userName
	 * @param newPassword
	 */
	public void updatePassword(String userName, String newPassword) {
		PreparedStatement pstmt = null;
		try {
			pstmt = 
					dbUtil.getConnection().prepareStatement("UPDATE user "
							+ "SET password=? "
							+ "WHERE id=?");
			pstmt.setString(1, newPassword);
			pstmt.setString(2, userName);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeStatement(pstmt);
		}
	}
	
	/**
	 * Updates user with given SAT score, ACT score
	 * 
	 * @param userName
	 * @param satScore
	 * @param actScore
	 */
	public void updateUser(String userName, int satScore, int actScore) {
		PreparedStatement pstmt = null;
		try {
			pstmt = 
					dbUtil.getConnection().prepareStatement("UPDATE user "
							+ "SET SAT_SCORE=?, ACT_SCORE=? "
							+ "WHERE id=?");
			pstmt.setInt(1, satScore);
			pstmt.setInt(2, actScore);
			pstmt.setString(3, userName);
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
	//let's just stick to int values for states? 
	//can uncomment if we change our minds, but that might require other updates
//	/**
//	 * Modifies or adds (if it doesn't exist) an entry in the residence table that references location table. 
//	 * Creates location if necessary.
//	 * 
//	 * @param stdID user ID
//	 * @param city City of residence
//	 * @param state State of residence
//	 * @param zip ZIP code of residence
//	 */
//	public void modifyResidence(String stdID, String city, String state, int zip) {
//		//TODO this treats empty strings as such. Do we want to consider them null instead? At the very least
//		//	we probably shouldn't add a location for "", "", "", right?
//		PreparedStatement findLoc = null;
//		String getLocIdCnt = 
//				"SELECT id, COUNT(*) "
//				+ "FROM LOCATION "
//				+ "WHERE city = ? AND state_string = ? AND ZIP = ?";
//		PreparedStatement insertWithID = null;
//		String insertForExistingLoc = 
//				"INSERT INTO residence (std_ID, loc_ID) "
//				+ "VALUES (?, ?) ON DUPLICATE KEY UPDATE loc_ID=?";
//		PreparedStatement newLoc = null;
//		String createLoc = 
//				"INSERT INTO location (city, state_string, ZIP) "
//				+ "VALUES (?, ?, ?)";
//		PreparedStatement resWithNewLoc = null;
//		String createResForNewLoc = 
//				"INSERT INTO residence (std_ID, loc_ID) "
//				+ "VALUES (?, LAST_INSERT_ID()) ON DUPLICATE KEY UPDATE loc_ID=LAST_INSERT_ID()";
//		ResultSet rs = null;
//		
//		try {
//			findLoc = dbUtil.getConnection().prepareStatement(getLocIdCnt);
//			findLoc.setString(1, city);
//			findLoc.setString(2, state);
//			findLoc.setInt(3, zip);
//			rs = findLoc.executeQuery();
//			
//			//does location already exist?
//			if (rs.next() && rs.getInt(2) > 0) {
//				insertWithID = dbUtil.getConnection().prepareStatement(insertForExistingLoc);
//				insertWithID.setString(1, stdID);
//				int existingID = rs.getInt(1);			//existing location ID
//				insertWithID.setInt(2, existingID);
//				insertWithID.setInt(3, existingID);
//				insertWithID.executeUpdate();
//			} else {
//				//create location
//				newLoc = dbUtil.getConnection().prepareStatement(createLoc);
//				newLoc.setString(1, city);
//				newLoc.setString(2, state);
//				newLoc.setInt(3, zip);
//				newLoc.executeUpdate();
//				//create residence
//				resWithNewLoc = dbUtil.getConnection().prepareStatement(createResForNewLoc);
//				resWithNewLoc.setString(1, stdID);
//				resWithNewLoc.executeUpdate();
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			DBUtil.closeStatement(findLoc);
//			DBUtil.closeStatement(insertWithID);
//			DBUtil.closeStatement(newLoc);
//			DBUtil.closeStatement(resWithNewLoc);
//			DBUtil.closeResultSet(rs);
//		}
//	}
	
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
				int id = rs.getInt(1);
				if (!rs.wasNull()) {
					loc.setIdIsNotNull(true);
					loc.setId(id);
				}
				String city = rs.getString(2);
				if (!rs.wasNull()) {
					loc.setCityIsNotNull(true);
					loc.setCity(city);
				}
				int state = rs.getInt(3);
				if (!rs.wasNull()) {
					loc.setStateIntIsNotNull(true);
					loc.setStateInt(state);
				}
				int zip = rs.getInt(4);
				if (!rs.wasNull()) {
					loc.setZipIsNotNull(true);
					loc.setZip(zip);
				}
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
				int rank = rs.getInt(1);
				if (!rs.wasNull()) {
					fav.setRankIsNotNull(true);
					fav.setRank(rank);
				}
				String field = rs.getString(2);
				if (!rs.wasNull()) {
					fav.setFieldOfStudyIsNotNull(true);
					fav.setFieldOfStudy(field);
				}
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
	 * @return Whether insertion was successful. If school was already a favorite, this returns false.
	 */
	public boolean addFavSchool(String userName, int schoolID) {
		boolean success = true;
		PreparedStatement checkExistence = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			checkExistence = 
					dbUtil.getConnection().prepareStatement(
							"SELECT school_ID FROM favoriteSchools WHERE std_ID = ?");
			checkExistence.setString(1, userName);
			rs = checkExistence.executeQuery();
			while (rs.next()) {
				if (rs.getInt(1) == schoolID) {
					success = false;
				}
			}
			if (success) {
				pstmt = 
						dbUtil.getConnection().prepareStatement(
								"INSERT INTO favoriteSchools (std_ID, school_ID) VALUES (?, ?)");
				pstmt.setString(1, userName);
				pstmt.setInt(2, schoolID);
				pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResultSet(rs);
			DBUtil.closeStatement(checkExistence);
			DBUtil.closeStatement(pstmt);
		}
		return success;
	}
	
	/**
	 * Gets a user's favorite schools
	 * 
	 * @param userName The user whose favorites we are grabbing
	 * @return A list of favorite schools from highest (i.e. 1) to lowest
	 */
	public List<FavoriteSchool> getFavSchools(String userName) {
		LinkedList<FavoriteSchool> favs = new LinkedList<FavoriteSchool>();
		CallableStatement cstmt = null;
		ResultSet rs = null;
		try {
			cstmt = dbUtil.getConnection().prepareCall("{call get_favorite_schools(?)}");
			cstmt.setString(1, userName);
			rs = cstmt.executeQuery();
			while (rs.next()) {
				FavoriteSchool fav = new FavoriteSchool();
				School school = new School();
				int id = rs.getInt(1);
				if (!rs.wasNull()) {
					fav.setSchoolIsNotNull(true);
					school.setIdIsNotNull(true);
					school.setId(id);
				}
				String name = rs.getString(2);
				if (!rs.wasNull()) {
					school.setNameIsNotNull(true);
					school.setName(name);
				}
				fav.setSchool(school);
				int rank = rs.getInt(3);
				if (!rs.wasNull()) {
					fav.setRankIsNotNull(true);
					fav.setRank(rank);
				}
				String status = rs.getString(4);
				if (!rs.wasNull()) {
					fav.setStatusIsNotNull(true);
					fav.setStatus(status);
				}
				int financialAid = rs.getInt(5);
				if (!rs.wasNull()) {
					fav.setFinancialAidIsNotNull(true);
					fav.setFinancialAid(financialAid);
				}
				int loan = rs.getInt(6);
				if (!rs.wasNull()) {
					fav.setLoanIsNotNull(true);
					fav.setLoan(loan);
				}
				int merit = rs.getInt(7);
				if (!rs.wasNull()) {
					fav.setMeritIsNotNull(true);
					fav.setMerit(merit);
				}
				double satAvg = rs.getDouble(8);
				if (!rs.wasNull()) {
					school.setSatAvgIsNotNull(true);
					school.setSatAvg(satAvg);
				}
				double actAvg = rs.getDouble(9);
				if (!rs.wasNull()) {
					school.setActAvgIsNotNull(true);
					school.setActAvg(actAvg);
				}
				double admissionRate = rs.getDouble(10);
				if (!rs.wasNull()) {
					school.setAdmissionRateIsNotNull(true);
					school.setAdmissionRate(admissionRate);
				}
				String url = rs.getString(11);
				if (!rs.wasNull()) {
					school.setWebsiteIsNotNull(true);
					school.setWebsite(url);
				}
				int tuitionOut = rs.getInt(12);
				if (!rs.wasNull()) {
					school.setTuitionOutIsNotNull(true);
					school.setTuitionOut(tuitionOut);
				}
				int tuitionIn = rs.getInt(13);
				if (!rs.wasNull()) {
					school.setTuitionInIsNotNull(true);
					school.setTuitionIn(tuitionIn);
				}
				Location location = new Location();
				String city = rs.getString(14);
				if (!rs.wasNull()) {
					school.setLocationIsNotNull(true);
					location.setCityIsNotNull(true);
					location.setCity(city);
				}
				String stateStr = rs.getString(15);
				if (!rs.wasNull()) {
					school.setLocationIsNotNull(true);
					location.setStateStrIsNotNull(true);
					location.setStateStr(stateStr);
				}
				school.setLocation(location);
				favs.addLast(fav);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResultSet(rs);
			DBUtil.closeStatement(cstmt);
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
