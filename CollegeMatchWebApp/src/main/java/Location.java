package main.java;

import java.util.HashMap;

/**
 * Represents a location record
 * 
 * @author Geoff
 *
 */
public class Location {
	
	private static HashMap<Integer, String> stateMap;
	
	//create stateMap
	static {
		stateMap = new HashMap<Integer, String>();
		stateMap.put(9, "CT");
		stateMap.put(23, "ME");
		stateMap.put(25, "MA");
		stateMap.put(33, "NH");
		stateMap.put(44, "RI");
		stateMap.put(50, "VT");
		stateMap.put(10, "DE");
		stateMap.put(11, "DC");
		stateMap.put(24, "MD");
		stateMap.put(34, "NJ");
		stateMap.put(36, "NY");
		stateMap.put(42, "PA");
		stateMap.put(17, "IL");
		stateMap.put(18, "IN");
		stateMap.put(26, "MI");
		stateMap.put(39, "OH");
		stateMap.put(55, "WI");
		stateMap.put(19, "IA");
		stateMap.put(20, "KS");
		stateMap.put(27, "MN");
		stateMap.put(29, "MO");
		stateMap.put(31, "NE");
		stateMap.put(38, "ND");
		stateMap.put(46, "SD");
		stateMap.put(1, "AL");
		stateMap.put(5, "AR");
		stateMap.put(12, "FL");
		stateMap.put(13, "GA");
		stateMap.put(21, "KY");
		stateMap.put(22, "LA");
		stateMap.put(28, "MS");
		stateMap.put(37, "NC");
		stateMap.put(45, "SC");
		stateMap.put(47, "TN");
		stateMap.put(51, "VA");
		stateMap.put(54, "WV");
		stateMap.put(4, "AZ");
		stateMap.put(35, "NM");
		stateMap.put(40, "OK");
		stateMap.put(48, "TX");
		stateMap.put(8, "CO");
		stateMap.put(16, "ID");
		stateMap.put(30, "MT");
		stateMap.put(49, "UT");
		stateMap.put(56, "WY");
		stateMap.put(2, "AK");
		stateMap.put(6, "CA");
		stateMap.put(15, "HI");
		stateMap.put(32, "NV");
		stateMap.put(41, "OR");
		stateMap.put(53, "WA");
		stateMap.put(60, "AS");
		stateMap.put(64, "FM");
		stateMap.put(66, "GU");
		stateMap.put(68, "MH");
		stateMap.put(69, "MP");
		stateMap.put(72, "PR");
		stateMap.put(70, "PW");
		stateMap.put(78, "VI");
	}
	
	private int id;
	private String city;
	private int stateInt;
	private String stateStr;
	private int zip;
	private boolean valid;
	//region is stored in region table. only need to set this field if you care about returning the location's
	//region
	private String region;
	
	/**
	 * Returns the abbreviation for this state
	 * 
	 * @return The abbreviation for the state
	 */
	public String getStateAbbreviation() {
		return stateMap.get(stateInt);
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the zip
	 */
	public int getZip() {
		return zip;
	}
	/**
	 * @param zip the zip to set
	 */
	public void setZip(int zip) {
		this.zip = zip;
	}

	/**
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * @param region the region to set
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * @return Whether this is a valid location (fields set)
	 */
	public boolean isValid() {
		return valid;
	}

	/**
	 * @param valid Whether this is a valid location (fields set)
	 */
	public void setValid(boolean valid) {
		this.valid = valid;
	}

	/**
	 * @return the stateInt
	 */
	public int getStateInt() {
		return stateInt;
	}

	/**
	 * @param stateInt the stateInt to set
	 */
	public void setStateInt(int stateInt) {
		this.stateInt = stateInt;
	}

	/**
	 * @return the stateStr
	 */
	public String getStateStr() {
		return stateStr;
	}

	/**
	 * @param stateStr the stateStr to set
	 */
	public void setStateStr(String stateStr) {
		this.stateStr = stateStr;
	}
}
