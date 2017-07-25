import java.sql.Connection;

/**
 * Data Access Object for school-related queries
 * 
 * @author Geoff
 *
 */
public class SchoolDAO {
	private Connection conn;
	
	public SchoolDAO() {
		conn = DBUtil.getConnection();
	}

}
