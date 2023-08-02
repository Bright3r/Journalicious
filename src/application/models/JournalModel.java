package application.models;

/**
 * A class representing a journal entry in the application
 */
public class JournalModel {
	private int id;
	private String title;
	private String date;
	private int hour;
	private int minute;
	private String context;
	
	/**
	 * Constructs a new journal entry
	 * 
	 * @param id the id of the journal entry in the sqlite DB
	 * @param title the title of the journal entry
	 * @param date the date of the journal entry
	 * @param hour the hour of the time the journal entry was written
	 * @param minute the minute of the time the journal entry was written
	 * @param context the context or body of the journal entry
	 */
	public JournalModel(int id, String title, String date, int hour, int minute, String context) {
		this.id = id;
		this.title = title;
		this.date = date;
		this.hour = hour;
		this.minute = minute;
		this.context = context;
	}
	
	
	/**
	 * Gets the title of the journal entry
	 * 
	 * @return the title of the journal entry
	 */
	public String getTitle() {
		return this.title;
	}
	
	/**
	 * Gets the date of the journal entry
	 * 
	 * @return the date of the journal entry
	 */
	public String getDate() {
		return this.date;
	}
	
	/**
	 * Gets a string representation of the time of the journal entry
	 * 
	 * @return the time of the journal entry as a string
	 */
	public String getTime() {
		return String.format("%02d:%02d", this.hour, this.minute);
	}
	
	/**
	 * Gets the hour of the time the journal entry was written
	 * 
	 * @return the hour of the time the journal entry was written
	 */
	public int getHour() {
		return this.hour;
	}
	
	
	/**
	 * Gets the minute of the time the journal entry was written
	 * 
	 * @return the minute of the time the journal entry was written
	 */
	public int getMinute() {
		return this.minute;
	}

	
	/**
	 * Gets the context or body of the journal entry
	 * 
	 * @return the context of the journal entry
	 */
	public String getContext() {
		return this.context;
	}
	
	/**
	 * Gets the DB id of the journal entry
	 * 
	 * @return the id of the journal entry in the sqlite DB
	 */
	public int getID() {
		return this.id;
	}
	
}
