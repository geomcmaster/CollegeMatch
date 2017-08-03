package main.java;

import java.util.List;

/**
 * Represents a user record
 * 
 * @author Geoff
 *
 */
public class User {
	private String id;
	private boolean idIsNotNull;
	private String password;
	private boolean passwordIsNotNull;
	private int satScore;
	private boolean satScoreIsNotNull;
	private int actScore;
	private boolean actScoreIsNotNull;
	//location is stored in residence table. only need to set this field if you care about returning the student's
	//residence info
	private Location location;
	private boolean locationIsNotNull;
	private List<FavoriteSchool> favoriteSchools;
	private boolean favoriteSchoolsIsNotNull;
	private List<FavoriteFieldOfStudy> favoriteFieldsOfStudy;
	private boolean favoriteFieldsOfStudyIsNotNull;
	private boolean isValid;
	
	public User() {
		setNulls();
	}
	
	//initialize all fields to indicate null
	private void setNulls() {
		setIdIsNotNull(false);
		setPasswordIsNotNull(false);
		setSatScoreIsNotNull(false);
		setActScoreIsNotNull(false);
		setLocationIsNotNull(false);
		setFavoriteSchoolsIsNotNull(false);
		setFavoriteFieldsOfStudyIsNotNull(false);
	}
	
	/**
	 * @return the idIsNotNull
	 */
	public boolean isIdNotNull() {
		return idIsNotNull;
	}

	/**
	 * @param idIsNotNull the idIsNotNull to set
	 */
	public void setIdIsNotNull(boolean idIsNotNull) {
		this.idIsNotNull = idIsNotNull;
	}

	/**
	 * @return the passwordIsNotNull
	 */
	public boolean isPasswordNotNull() {
		return passwordIsNotNull;
	}

	/**
	 * @param passwordIsNotNull the passwordIsNotNull to set
	 */
	public void setPasswordIsNotNull(boolean passwordIsNotNull) {
		this.passwordIsNotNull = passwordIsNotNull;
	}

	/**
	 * @return the satScoreIsNotNull
	 */
	public boolean isSatScoreNotNull() {
		return satScoreIsNotNull;
	}

	/**
	 * @param satScoreIsNotNull the satScoreIsNotNull to set
	 */
	public void setSatScoreIsNotNull(boolean satScoreIsNotNull) {
		this.satScoreIsNotNull = satScoreIsNotNull;
	}

	/**
	 * @return the actScoreIsNotNull
	 */
	public boolean isActScoreNotNull() {
		return actScoreIsNotNull;
	}

	/**
	 * @param actScoreIsNotNull the actScoreIsNotNull to set
	 */
	public void setActScoreIsNotNull(boolean actScoreIsNotNull) {
		this.actScoreIsNotNull = actScoreIsNotNull;
	}

	/**
	 * @return the locationIsNotNull
	 */
	public boolean isLocationNotNull() {
		return locationIsNotNull;
	}

	/**
	 * @param locationIsNotNull the locationIsNotNull to set
	 */
	public void setLocationIsNotNull(boolean locationIsNotNull) {
		this.locationIsNotNull = locationIsNotNull;
	}

	/**
	 * @return the favoriteSchoolsIsNotNull
	 */
	public boolean isFavoriteSchoolsNotNull() {
		return favoriteSchoolsIsNotNull;
	}

	/**
	 * @param favoriteSchoolsIsNotNull the favoriteSchoolsIsNotNull to set
	 */
	public void setFavoriteSchoolsIsNotNull(boolean favoriteSchoolsIsNotNull) {
		this.favoriteSchoolsIsNotNull = favoriteSchoolsIsNotNull;
	}

	/**
	 * @return the favoriteFieldsOfStudyIsNotNull
	 */
	public boolean isFavoriteFieldsOfStudyNotNull() {
		return favoriteFieldsOfStudyIsNotNull;
	}

	/**
	 * @param favoriteFieldsOfStudyIsNotNull the favoriteFieldsOfStudyIsNotNull to set
	 */
	public void setFavoriteFieldsOfStudyIsNotNull(boolean favoriteFieldsOfStudyIsNotNull) {
		this.favoriteFieldsOfStudyIsNotNull = favoriteFieldsOfStudyIsNotNull;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the satScore
	 */
	public int getSatScore() {
		return satScore;
	}
	/**
	 * @param satScore the satScore to set
	 */
	public void setSatScore(int satScore) {
		this.satScore = satScore;
	}
	/**
	 * @return the actScore
	 */
	public int getActScore() {
		return actScore;
	}
	/**
	 * @param actScore the actScore to set
	 */
	public void setActScore(int actScore) {
		this.actScore = actScore;
	}
	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}
	/**
	 * @return the favoriteSchools
	 */
	public List<FavoriteSchool> getFavoriteSchools() {
		return favoriteSchools;
	}
	/**
	 * @param favoriteSchools the favoriteSchools to set
	 */
	public void setFavoriteSchools(List<FavoriteSchool> favoriteSchools) {
		this.favoriteSchools = favoriteSchools;
	}
	/**
	 * @return the favoriteFieldsOfStudy
	 */
	public List<FavoriteFieldOfStudy> getFavoriteFieldsOfStudy() {
		return favoriteFieldsOfStudy;
	}
	/**
	 * @param favoriteFieldsOfStudy the favoriteFieldsOfStudy to set
	 */
	public void setFavoriteFieldsOfStudy(List<FavoriteFieldOfStudy> favoriteFieldsOfStudy) {
		this.favoriteFieldsOfStudy = favoriteFieldsOfStudy;
	}
	/**
	 * @return the isValid
	 */
	public boolean isValid() {
		return isValid;
	}
	/**
	 * @param isValid the isValid to set
	 */
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
}
