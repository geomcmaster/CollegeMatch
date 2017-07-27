
public class User {
	private String id;
	private String password;
	private int satScore;
	private int actScore;
	//location is stored in residence table. only need to set this field if you care about returning the student's
	//residence info
	private Location location;
	//TODO how to store favorites??
	
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
}
