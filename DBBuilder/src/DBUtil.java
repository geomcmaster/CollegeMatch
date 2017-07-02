import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Utility class for db operations. Intended to be used from JSONUtil.
 * 
 * @author Geoff
 *
 */
public class DBUtil {
	private final String URL = "jdbc:mysql://localhost/CollegeMatch";
	private final String USER;
	private final String PASSWORD;
	public Connection conn;
	
	public DBUtil(String user, String pw) {
		USER = user;
		PASSWORD = pw;
	}
	
	public void openConnection() {
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
	}
	
	public void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				System.out.println(e.toString());
			}
		}
	}
	
	public void closeStatement(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				System.out.println(e.toString());
			}
		}
	}
	
	public void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println(e.toString());
			}
		}
	}
}
