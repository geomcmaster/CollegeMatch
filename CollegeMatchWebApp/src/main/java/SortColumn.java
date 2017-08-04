package main.java;

public class SortColumn {
	private String columnName;
	private boolean isAscending;
	
	public SortColumn(String columnName, boolean isAscending) {
		this.columnName = columnName;
		this.isAscending = isAscending;
	}

	/**
	 * @return the columnName
	 */
	public String getColumnName() {
		return columnName;
	}

	/**
	 * @return the isAscending
	 */
	public boolean isAscending() {
		return isAscending;
	}
}