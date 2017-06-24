import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

	private static final String URL = "";
	private static final String USER = "";
	private static final String PASSWORD = "";
	private static Connection conn;
	
	private static final String BASE_DATA_URL = "";
	
	private static void openConnection() {
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			System.out.println("Unsuccessful connection attempt.\n");
		}
	}
	
	private static void closeConnection() {
		//
	}

	public static void main(String[] args) {

	}

}
