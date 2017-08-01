package main.java;

/*
 * Represents field of study record
 * 
 * @author Joseph
 */

public class FieldOfStudy {
	private int id;
	private String studyField;
	
	public FieldOfStudy(int ID, String field) {
		this.id = ID;
		this.studyField = field;
	}
	
	public int getID() {
		return id;
	}
	public String getField() {
		return studyField;
	}
}
