package main.java;
import java.sql.Connection;

/**
 * Data Access Object for school-related queries
 * 
 * @author Geoff
 *
 */
public class SchoolDAO {
	private DBUtil dbUtil;
	
	public SchoolDAO() {
		dbUtil = new DBUtil();
	}

}
