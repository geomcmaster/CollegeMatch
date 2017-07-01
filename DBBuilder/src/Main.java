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

		JSONUtil jutil = new JSONUtil();
		
		jutil.processAllRecords(apiKey);
	}

}
