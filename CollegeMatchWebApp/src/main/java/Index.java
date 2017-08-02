package main.java;

/**
 * Represents the index within the PreparedStatement
 * 
 * @author Geoff
 *
 */
public class Index {
	private int i;
	
	/**
	 * Initializes index to one
	 */
	public Index() {
		i = 1;
	}
	
	/**
	 * 
	 * @return The current index value
	 */
	public int getIndex() {
		return i;
	}
	
	/**
	 * Increments the index value and returns value prior to increment
	 * 
	 * @return The index value
	 */
	public int getAndIncrement() {
		int i = this.i;
		this.i++;
		return i;
	}
	
	/**
	 * Increments the index value
	 */
	public void increment() {
		i++;
	}
}
