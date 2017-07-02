import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

	/**
	 * 
	 * @param args
	 * 	args[0] :	api key
	 */
	public static void main(String[] args) {
		String apiKey = args[0];
		String mysqluser = args[1];
		String mysqlpw = args[2];

		DBUtil dbutil = new DBUtil(mysqluser, mysqlpw);
		JSONUtil jutil = new JSONUtil(apiKey, dbutil);
		
		jutil.processAllRecords();
	}

}
