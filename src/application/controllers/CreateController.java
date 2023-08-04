package application.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

import application.models.JournalModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

/**
 * Controller for the "Create" and "Edit" scenes.
 */
public class CreateController extends SceneController implements Initializable {
	@FXML private TextField titleField;
	@FXML private DatePicker datePicker;
	@FXML private Spinner<Integer> hourSpinner;
	@FXML private Spinner<Integer> minuteSpinner;
	@FXML private TextArea journalContextArea;
		
	/**
	 * A custom string converter object that formats times
	 */
	static final StringConverter<Integer> TIME_FORMAT_CONVERTER = new StringConverter<Integer>() {
		@Override
		public String toString(Integer val) {
			// pad the time element's value with 0s if it is a single digit
			return String.format("%02d", val);
		}

		@Override
		public Integer fromString(String str) {
			try {
				
				return Integer.parseInt(str);
				
			} catch (NumberFormatException ex) {
				return -1;
			}
		}
	};
	
	
	// instance vars
	private JournalModel journal;
	
	
	/**
	 * Creates an instance of CreateController
	 */
	public CreateController() {
		this.journal = null;
	}
	
	
	/**
	 * Initializes the page's fields and autofills each field
	 * with the default value
	 * 
	 * @param location the location of a file or directory
	 * @param resources the resources required to locate the root element
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// add event listener to the time spinners 
		// that updates the field when the user clicks out of the spinner
		this.addFocusLostEventListener(hourSpinner);
		this.addFocusLostEventListener(minuteSpinner);
		
		// set the limits of the hour and minute spinners
		SpinnerValueFactory<Integer> hourValueFactory = createTimeSpinnerValueFactory(0, 23);
		SpinnerValueFactory<Integer> minuteValueFactory = createTimeSpinnerValueFactory(0, 59);
		
		// set wrapping on context TextArea
		journalContextArea.setWrapText(true);
		
		
		// autofill the field values to the default values
		// autofill the date
		datePicker.setValue(LocalDate.now());
		
		// autofill the time
		LocalTime currTime = LocalTime.now();
		hourValueFactory.setValue(currTime.getHour());
		minuteValueFactory.setValue(currTime.getMinute());
		
		// initialize time spinners
		hourSpinner.setValueFactory(hourValueFactory);
		minuteSpinner.setValueFactory(minuteValueFactory);
	}
	
	
	/**
	 * Startup method that configures and sets up all forms on the Create page
	 * 
	 * @param journal the JournalModel of the entry to be edited
	 */
	public void initializeOldJournal(JournalModel journal) {
		this.journal = journal;
		
		// set the limits of new hour and minute spinners
		SpinnerValueFactory<Integer> hourValueFactory = createTimeSpinnerValueFactory(0, 23);
		SpinnerValueFactory<Integer> minuteValueFactory = createTimeSpinnerValueFactory(0, 59);
		
		// fill in fields with journal data
		fillOutFields(journal, hourValueFactory, minuteValueFactory);

		// initialize new time spinner value factories that reflect journal entry data
		hourSpinner.setValueFactory(hourValueFactory);
		minuteSpinner.setValueFactory(minuteValueFactory);
	}
	
	
	/**
	 * Creates a SpinnerValueFactory given a minimum and maximum value,
	 * with values of the spinner being padded with 0s to the tens place.
	 * 
	 * @param min the minimum value the spinner can contain
	 * @param max the maximum value the spinner can contain
	 * @return a SpinnerValueFactory with the given min and max values
	 */
	private SpinnerValueFactory<Integer> createTimeSpinnerValueFactory(int min, int max) {
		SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max);
		valueFactory.setConverter(TIME_FORMAT_CONVERTER);
		
		return valueFactory;
	}
	
	
	/**
	 * Populates fields of the Edit page with journal info
	 * 
	 * @param journal the JournalModel containing the journal entry's info
	 * @param hourValueFactory the SpinnerValueFactory for the hour spinner
	 * @param minuteValueFactory the SpinnerValueFactory for the minute spinner
	 */
	private void fillOutFields(JournalModel journal, SpinnerValueFactory<Integer> hourValueFactory, 
			SpinnerValueFactory<Integer> minuteValueFactory) {
		
		// fill out the title
		titleField.setText(journal.getTitle());
				
		// fill out the date
		LocalDate date = LocalDate.parse(journal.getDate());
		datePicker.setValue(date);
				
		// fill out the time
		hourValueFactory.setValue(journal.getHour());
		minuteValueFactory.setValue(journal.getMinute());
				
		// fill out the context
		journalContextArea.setText(journal.getContext());
	}
	
	
	/**
	 * Adds an event listener to a spinner that checks if spinners were clicked in/out of
	 * 
	 * @param spinner the spinner to add the event listener to
	 */
	private void addFocusLostEventListener(Spinner<Integer> spinner) {
		// addListener takes ChangeListener Functional Interface implementation as argument
		spinner.getEditor().focusedProperty().addListener((observableValue, previousValue, newValue) -> {
			// If new value is invalid, reset spinner to default
			if (!newValue) {
				spinner.increment(0);
			}
		});
	}
	
	
	/**
	 * Handles clicks on "Back" button.
	 * 
	 * @param e An event given by some user action on the application
	 */
	public void handleBackClick(ActionEvent e) {
		String title = titleField.getText();
		String context = journalContextArea.getText();
		
		boolean fieldsAreEdited = (!title.equals("") || !context.equals(""));
		if (fieldsAreEdited) {
			this.showAlert(e);
		}
		else {
			super.switchToPrevView(e, View.CREATE);
		}
	}
	
	
	/**
	 * Displays alert message warning the user that they will lose their progress
	 * 
	 * @param e An event given by some user action on the application
	 */
	void showAlert(ActionEvent e) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Confirm Leaving Page");
		alert.setHeaderText("Warning!");
		alert.setContentText("Are you sure you would like to leave this page? All progress will be lost if you select \"OK\".");
		
		// if user hits OK, redirect to home page
		alert.setOnCloseRequest(event -> {
			if (alert.getResult() == ButtonType.OK) {
				super.switchToPrevView(e, View.CREATE);
			}
		});
		
		// display alert after configuration
		alert.show();
	}
	
	
	/**
	 * Handles logic for clicking the Save button on the Create page
	 * 
	 * @param e An event given by some user action on the application
	 */
	public void handleSave(ActionEvent e) {
		// get title and context
		String title = titleField.getText();
		String context = journalContextArea.getText();
		
		// get time fields
		int hour = hourSpinner.getValue();
		int minute = minuteSpinner.getValue();
		
		// get date
		LocalDate enteredDate = datePicker.getValue();
		String date = enteredDate.toString();
		
		// update DB
		if (this.journal == null) {
			// create new journal
			JournalModel.createJournal(title, date, hour, minute, context);
		}
		else {
			// update journal model and DB
			this.journal.updateSelf(title, date, hour, minute, context);
		}
		
		// display success message (TODO!) and switch to home page
		super.switchToPrevView(e, View.CREATE);
	}	
}
