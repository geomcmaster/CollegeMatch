package main.java;

import java.util.List;

/**
 * Represents a value to use in a condition. Can be a String, int, double, pair of ints, or pair of doubles. 
 * Check the type to know which field to get.
 * 
 * @author Geoff
 *
 */
public class CondVal {
	private ValType type;
	
	private String strVal;
	private String subQuery;	//assumes that the caller is setting the entire subquery string
	private int intVal;
	private double doubleVal;
	private List<Condition> orConditions;	//for conditions of the form ...AND (cond1 OR cond2) AND...
	
	private int index;	//the index of the wildcard in the PreparedStatement
	
	//used for range condition (BETWEEN ? AND ?)
	private int minInt;
	private int maxInt;
	private int minDouble;
	private int maxDouble;
	//indices for range conditions
	private int indexOfMin;
	private int indexOfMax;
	
	public CondVal(ValType type) {
		this.type = type;
	}
	
	/**
	 * @return the type of value to insert into the condition
	 */
	public ValType getType() {
		return type;
	}
	/**
	 * @param type the type of value to insert into the condition
	 */
	public void setType(ValType type) {
		this.type = type;
	}
	/**
	 * @return the strVal
	 */
	public String getStrVal() {
		return strVal;
	}
	/**
	 * @param strVal the strVal to set
	 */
	public void setStrVal(String strVal) {
		this.strVal = strVal;
	}
	/**
	 * @return the intVal
	 */
	public int getIntVal() {
		return intVal;
	}
	/**
	 * @param intVal the intVal to set
	 */
	public void setIntVal(int intVal) {
		this.intVal = intVal;
	}
	/**
	 * @return the doubleVal
	 */
	public double getDoubleVal() {
		return doubleVal;
	}
	/**
	 * @param doubleVal the doubleVal to set
	 */
	public void setDoubleVal(double doubleVal) {
		this.doubleVal = doubleVal;
	}
	/**
	 * @return the minInt
	 */
	public int getMinInt() {
		return minInt;
	}
	/**
	 * @param minInt the minInt to set
	 */
	public void setMinInt(int minInt) {
		this.minInt = minInt;
	}
	/**
	 * @return the maxInt
	 */
	public int getMaxInt() {
		return maxInt;
	}
	/**
	 * @param maxInt the maxInt to set
	 */
	public void setMaxInt(int maxInt) {
		this.maxInt = maxInt;
	}
	/**
	 * @return the minDouble
	 */
	public int getMinDouble() {
		return minDouble;
	}
	/**
	 * @param minDouble the minDouble to set
	 */
	public void setMinDouble(int minDouble) {
		this.minDouble = minDouble;
	}
	/**
	 * @return the maxDouble
	 */
	public int getMaxDouble() {
		return maxDouble;
	}
	/**
	 * @param maxDouble the maxDouble to set
	 */
	public void setMaxDouble(int maxDouble) {
		this.maxDouble = maxDouble;
	}
	/**
	 * @return the index of the value in the PreparedStatement
	 */
	public int getIndex() {
		return index;
	}
	/**
	 * @param index the index of the value in the PreparedStatement
	 */
	public void setIndex(int index) {
		this.index = index;
	}
	/**
	 * @return the index of the min value in the PreparedStatement
	 */
	public int getIndexOfMin() {
		return indexOfMin;
	}
	/**
	 * @param indexOfMin the index of the min value in the PreparedStatement
	 */
	public void setIndexOfMin(int indexOfMin) {
		this.indexOfMin = indexOfMin;
	}
	/**
	 * @return the index of the max value in the PreparedStatement
	 */
	public int getIndexOfMax() {
		return indexOfMax;
	}
	/**
	 * @param indexOfMax the index of the max value in the PreparedStatement
	 */
	public void setIndexOfMax(int indexOfMax) {
		this.indexOfMax = indexOfMax;
	}

	/**
	 * @return the subQuery
	 */
	public String getSubQuery() {
		return subQuery;
	}

	/**
	 * @param subQuery the subQuery to set
	 */
	public void setSubQuery(String subQuery) {
		this.subQuery = subQuery;
	}

	/**
	 * @return the list of OR'd conditions
	 */
	public List<Condition> getOrConditions() {
		return orConditions;
	}

	/**
	 * @param orConditions List of conditions that should be OR'd
	 */
	public void setOrConditions(List<Condition> orConditions) {
		this.orConditions = orConditions;
	}
}
