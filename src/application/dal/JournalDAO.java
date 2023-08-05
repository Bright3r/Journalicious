package application.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import application.dal.DBConnection.Database;
import application.models.JournalModel;

/**
 * A class that faciliates interacting with and manipulating journal data
 */
public class JournalDAO {
	/**
	 * Creates a journal entry and inserts it into the journals database
	 * 
	 * @param title the title of the journal entry in string format
	 * @param date the date of the journal entry in string format
	 * @param hour the hour of the time the journal entry was created in integer format
	 * @param minute the minute of the time the journal entry was created in integer format
	 * @param context the context of the journal entry in string format
	 */
	public void createJournal(String title, String date, int hour, int minute, String context) {
		String updateQuery = "INSERT INTO journal (title, date, hour, minute, context) VALUES (?, ?, ?, ?, ?)";
		
		try {
			// get DB connection
			Connection connection = DBConnection.getDBConnection(Database.JOURNALS);
			PreparedStatement statement = connection.prepareStatement(updateQuery);
			
			// configure query
			statement.setString(1, title);
			statement.setString(2, date);
			statement.setInt(3, hour);
			statement.setInt(4, minute);
			statement.setString(5, context);
			
			int rowsAdded = statement.executeUpdate();
			if (rowsAdded == 0) {
				System.out.println("Could not add journal to database!");
			}
			else {
				System.out.println("Added journal to DB!");	
			}
			
		} catch (Exception ex) {
			System.out.println("Failed to add journal to database!");
			ex.printStackTrace();
		}
	}
	

	/**
	 * Gets all journal entries that are stored in the DB
	 * 
	 * @return an ArrayList containing a JournalModel for every journal in the DB
	 */
	public ArrayList<JournalModel> getJournals() {
		ArrayList<JournalModel> journals = new ArrayList<>();
		String query = "SELECT * FROM journal";
		
		try {
			// get DB connection
			Connection connection = DBConnection.getDBConnection(Database.JOURNALS);
			Statement statement = connection.createStatement();
			
			// iterate through every row in journals db
			ResultSet results = statement.executeQuery(query);
			while (results.next()) {
				// get all fields from the row of the db
				int id = results.getInt("id");
				String title = results.getString("title");
				String date = results.getString("date");
				int hour = results.getInt("hour");
				int minute = results.getInt("minute");
				String context = results.getString("context");
				
				// create a new JournalModel and add it to output list
				JournalModel newJournal = new JournalModel(id, title, date, hour, minute, context);
				journals.add(newJournal);
			}
		} catch (Exception ex) {
			System.out.println("Failed to retrieve journal entries!");
			ex.printStackTrace();
		}
		
		return journals;
	}
	
	/**
	 * Gets all journal entries in the DB that contain a given keyword in the title or context
	 * 
	 * @param keyword the keyword that a journal entry must contain
	 * @return an ArrayList containing a JournalModel for every journal entry in the DB that contains the given keyword
	 */
	public ArrayList<JournalModel> getJournals(String keyword) {
		ArrayList<JournalModel> journals = new ArrayList<>();
		String updateQuery = "SELECT * FROM journal WHERE title LIKE ? OR context LIKE ?";
		
		try {
			// get DB connection
			Connection connection = DBConnection.getDBConnection(Database.JOURNALS);
			PreparedStatement statement = connection.prepareStatement(updateQuery);
			
			// set up query to check if entry has any substring containing keyword
			String keywordSearch = "%" + keyword + "%";
			statement.setString(1, keywordSearch);
			statement.setString(2, keywordSearch);
			
			// iterate through every row in journals db
			ResultSet results = statement.executeQuery();
			while (results.next()) {
				// get column data from row in db
				int id = results.getInt("id");
				String title = results.getString("title");
				String date = results.getString("date");
				int hour = results.getInt("hour");
				int minute = results.getInt("minute");
				String context = results.getString("context");
				
				// create a new journal model and add it to output list
				JournalModel newJournal = new JournalModel(id, title, date, hour, minute, context);
				journals.add(newJournal);
			}
		} catch (Exception ex) {
			System.out.println("Failed to retrieve journal entries!");
			ex.printStackTrace();
		}
		
		return journals;
	}
	
	/**
	 * Deletes a journal entry from the DB given its JournalModel
	 * 
	 * @param journal the JournalModel representation of a journal entry
	 */
	public void deleteJournal(JournalModel journal) {
		String updateQuery = "DELETE FROM journal WHERE id = ?";
		int id = journal.getID();
		
		try {
			// get DB connection
			Connection connection = DBConnection.getDBConnection(Database.JOURNALS);
			PreparedStatement statement = connection.prepareStatement(updateQuery);
			
			statement.setInt(1,  id);
			
			int rowsDeleted = statement.executeUpdate();
			if (rowsDeleted > 0) {
				System.out.println("Deleted entry from DB!");
			}
			else {
				System.out.println("Could not delete journal from DB!");
			}
		} catch (Exception ex) {
			System.out.println("Failed to delete journal entry from DB!");
			ex.printStackTrace();
		}
	}
	
	/**
	 * Updates an existing journal entry within the DB
	 * 
	 * @param journal the JournalModel representing the journal we would like to update in the DB
	 */
	public void updateJournal(JournalModel journal) {
		String updateQuery = "UPDATE journal SET title = ?, date = ?, hour = ?, minute = ?, context = ? WHERE id = ?";
		
		try {
			// get DB connection
			Connection connection = DBConnection.getDBConnection(Database.JOURNALS);
			PreparedStatement statement = connection.prepareStatement(updateQuery);
			
			// configure query
			statement.setString(1, journal.getTitle());
			statement.setString(2, journal.getDate());
			statement.setInt(3, journal.getHour());
			statement.setInt(4, journal.getMinute());
			statement.setString(5, journal.getContext());
			statement.setInt(6, journal.getID());
			
			int rowsAdded = statement.executeUpdate();
			if (rowsAdded > 0) {
				System.out.println("Updated journal in DB!");	
			}
			else {
				System.out.println("Could not update journal!");
			}
		} catch (Exception ex) {
			System.out.println("Failed to add journal to database!");
			ex.printStackTrace();
		}
	}
	
}
