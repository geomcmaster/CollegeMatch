package main.java;

public class Condition {
	private String columnName;
	private CondType conditionType;
	private CondVal value;
	/**
	 * @return the columnName
	 */
	public String getColumnName() {
		return columnName;
	}
	/**
	 * @param columnName the columnName to set
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	/**
	 * @return the conditionType
	 */
	public CondType getConditionType() {
		return conditionType;
	}
	/**
	 * @param conditionType the conditionType to set
	 */
	public void setConditionType(CondType conditionType) {
		this.conditionType = conditionType;
	}
	/**
	 * @return the value
	 */
	public CondVal getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(CondVal value) {
		this.value = value;
	}
}