package application.dal;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Singleton class for connecting to an sqlite database
 */
public class DBConnection {
	private static final String jdbcPathURL = "jdbc:sqlite:resources/sqlite/";
	
	private static DBConnection dbConnection = new DBConnection();
	private static Connection userInfoDBConnection;
	private static Connection journalsDBConnection;
	
	
	/**
	 * Constructs singleton by getting connections to specified databases
	 */
	private DBConnection() {
		try {
			// get a connection to each database
			userInfoDBConnection = DriverManager.getConnection(jdbcPathURL + Database.USER_INFO.getValue());
			journalsDBConnection = DriverManager.getConnection(jdbcPathURL + Database.JOURNALS.getValue());
			
		} catch (Exception ex) {
			System.out.println("Failed to connect to SQLite database");
			ex.printStackTrace();
		}
	}
	
	/**
	 * Gets the singleton instance of the DBConnection class
	 * 
	 * @return an instance of the DBConnection Singleton
	 */
	public static DBConnection getDBConnectionSingletonInstance() {
		return dbConnection;
	}
	
	
	/**
	 * Gets a connection to a given database
	 * 
	 * @param db One of the database options provided by the Database enum
	 * @return a connection to a chosen database
	 * @throws Exception if the database selection is not one of the supported databases from the Database enum
	 */
	public static Connection getDBConnection(Database db) throws Exception {
		Connection connection = null;
		switch (db) {
			case USER_INFO:
				connection = userInfoDBConnection;
				break;
			case JOURNALS:
				connection = journalsDBConnection;
				break;
			default:
				throw new Exception("The database you wish to connect to is not supported!");
		}
		
		return connection;
	}
	
	
	/**
	 * An enumeration of the possible databases to connect to
	 */
	public enum Database {
		USER_INFO("user_info_db.sqlite"),
		JOURNALS("journals_db.sqlite");
		
		private final String db;
		
		private Database(String db) {
			this.db = db;
		}

		/**
		 * Gets the file name of the sqlite database
		 * 
		 * @return the file name of the sqlite database
		 */
		public String getValue() {
			return this.db;
		}
	}
	
}