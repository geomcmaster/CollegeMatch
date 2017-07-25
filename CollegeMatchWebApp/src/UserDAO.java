import java.sql.Connection;

/**
 * Data access object for user-related queries
 * 
 * @author Geoff
 *
 */
public class UserDAO {
	private Connection conn;
	
	public UserDAO() {
		conn = DBUtil.getConnection();
	}

}
