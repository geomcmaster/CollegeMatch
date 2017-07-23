public class Main {

	/**
	 * 
	 * @param args
	 *  args[0] API key for dataset
	 *  args[1] MySQL username
	 *  args[2] MySQL password
	 *  args[3] Database options
	 *  	1: drop db if exists. create db.
	 *  	2: drop tables if exists. create tables.
	 *  	3: clear all data from tables.
	 *  	4: do nothing to existing data.
	 */
	public static void main(String[] args) {
		String apiKey = args[0];
		String mysqluser = args[1];
		String mysqlpw = args[2];

		DBUtil dbutil = new DBUtil(mysqluser, mysqlpw);
		
		switch(Integer.parseInt(args[3])) {
			case 1: dbutil.createDatabase();	//if creating database, fall through to create tables
			case 2: dbutil.createTables();
					break;
			case 3: dbutil.clearTables();
					break;
			case 4: dbutil.prepareDB();
					break;
			default: break;
		}
		
		JSONUtil jutil = new JSONUtil(apiKey, dbutil);
		
		jutil.processAllRecords();
		
		dbutil.closeConnection();
	}

}
