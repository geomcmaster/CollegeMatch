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
		//TODO finish the map
		stateMap.put(9, "CT");
		stateMap.put(23, "ME");
		stateMap.put(25, "MA");
		stateMap.put(33, "NH");
		stateMap.put(44, "RI");
	}
	
	private int id;
	private String city;
	private int state;
	private int zip;
	//region is stored in region table. only need to set this field if you care about returning the location's
	//region
	private String region;
	
	/**
	 * Returns the abbreviation for a state
	 * 
	 * @param stateID The state number as stored in the table
	 * @return The abbreviation for the state
	 */
	public String getStateAbbreviation(Integer stateID) {
		return stateMap.get(stateID);
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
	 * @return the state
	 */
	public int getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(int state) {
		this.state = state;
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
}
