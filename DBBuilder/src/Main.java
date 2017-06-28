import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

	public static void main(String[] args) {
		DBUtil db = new DBUtil();
		db.openConnection();
		
		try {
			//on first page result, remember to get total number of results from metadata.total, 
			//then divide by 20 to know how many more iterations to do
			//SQL stuff
		}
		/*//need something that throws SQLException to have catch block
		catch (SQLException e) {
			//do something
		}
		*/
		finally {
			//call closing methods
		}
	}

}
