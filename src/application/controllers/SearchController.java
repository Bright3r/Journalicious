package application.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.models.JournalModel;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Controller for the "Search" screen.
 */
public class SearchController extends SceneController implements Initializable {
	@FXML TextField searchTextField;
	@FXML ListView<JournalModel> journalListView;
	
	private ObservableList<JournalModel> journalsObsList;
	private ToggleGroup radioBtnToggleGroup;
	
	
	/**
	 * Constructs a SearchController object
	 */
	public SearchController() {
		this.journalsObsList = FXCollections.observableArrayList();
		this.radioBtnToggleGroup = new ToggleGroup();
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
		// add all journals to journalListView
		this.populateJournals();
		
		// provide journalListView with an overridden cell factory
		// that defines content and styling for each cell of the list
		journalListView.setCellFactory(param -> new JournalCell());
		
		// disable the default focusing behavior when clicking a cell
		journalListView.setFocusTraversable(false);
	}
	
	
	private void populateJournals() {
		// add all journals to the observation list
		resetJournalsObsList();
		
		// populate the listView with observable list
		journalListView.setItems(this.journalsObsList);
	}
	
	
	private JournalModel getToggledJournal() {
		RadioButton selectedRadioBtn = (RadioButton) this.radioBtnToggleGroup.getSelectedToggle();
		
		JournalModel journal = null;
		if (selectedRadioBtn != null) {
			journal = (JournalModel) selectedRadioBtn.getUserData();
		}
		
		return journal;
	}
	
	
	/**
	 * Handles logic for clicking the Delete button
	 * 
	 * @param e An event given by some user action on the application
	 */
	public void handleDelete(ActionEvent e) {
		JournalModel journal = getToggledJournal();
		
		if (journal != null) {
			// delete journal from db
			journal.deleteSelf();
			
			// update observable list to reflect deletion
			this.journalsObsList.remove(journal);
			// refresh list view to reflect deletion
			this.journalListView.refresh();
			
			// uncheck the radio button
			this.radioBtnToggleGroup.selectToggle(null);
		}
	}
	
	
	/**
	 * Handles logic for clicking the Edit button
	 * 
	 * @param e An event given by some user action on the application
	 */
	public void handleEdit(ActionEvent e) {
		Toggle selectedRadioBtn = this.radioBtnToggleGroup.getSelectedToggle();
		
		if (selectedRadioBtn != null) {
			JournalModel journal = (JournalModel) selectedRadioBtn.getUserData();
			super.switchToEditView(e, journal);	
		}
	}
	
	
	/**
	 * Handles mouse clicks on the "Search" screen.
	 * 
	 * @param e An event given by some user action on the application.
	 */
	public void handleBackClick(ActionEvent e) {
		super.switchToView(e, View.HOME, View.SEARCH);
	}
	
	
	/**
	 * Event listener on "Enter Password" field for when key is pressed,
	 * searches keyword if the ENTER key is pressed
	 * 
	 * @param e an event given by some user action on the application
	 */
	public void handleKeyPress(KeyEvent e) {
		if (e.getCode() == KeyCode.ENTER) {
			ActionEvent actionEvent = new ActionEvent(e.getSource(), e.getTarget());
			handleSearch(actionEvent);
		}
	}
	
	
	/**
	 * Handles logic for searching for a journal entry
	 * 
	 * @param e An event given by some user action on the application
	 */
	public void handleSearch(ActionEvent e) {
		// get search keyword
		String keyword = searchTextField.getText();
		boolean keywordIsEmpty = keyword.equals("");
		
		if (!keywordIsEmpty) {
			updateJournalsObsListByKeyword(keyword);
		}
		else {
			resetJournalsObsList();
		}
		
	}
	
	
	private void updateJournalsObsListByKeyword(String keyword) {
		ArrayList<JournalModel> userJournals = JournalModel.getJournals(keyword);
		
		this.journalsObsList.clear();
		this.journalsObsList.addAll(userJournals);
	}
	
	
	private void resetJournalsObsList() {
		ArrayList<JournalModel> userJournals = JournalModel.getJournals();
		
		this.journalsObsList.clear();
		this.journalsObsList.addAll(userJournals);
	}
	
	
	
	/**
	 * Inner class that defines the content and styles of a journal entry's cell in the ListView
	 */
	private class JournalCell extends ListCell<JournalModel> {
		// layout panes
		private final HBox container;
		private final VBox journalInfoContainer;
		private final VBox journalContentContainer;
		
		// visual elements
		private final RadioButton radioBtn;
		private final Text title;
		private final Text date;
		private final Text time;
		private final Label context;
		
		// typography constants
		private static final int TITLE_FONT_SIZE = 15;
		private static final int DEFAULT_FONT_SIZE = 12;
		
		// sizing and spacing constants
		private static final int CELL_WIDTH = 200;
		private static final int CELL_SPACING = 10;
		private static final int CONTEXT_WIDTH = 300;
		private static final int CONTAINER_VERTICAL_PADDING = 5;
		private static final int CONTAINER_SPACING = 15;
		
		private JournalCell() {
			radioBtn = new RadioButton();
			title = new Text();
			date = new Text();
			time = new Text();
			context = new Label();
			
			// typography
			// title
			Font titleFont = new Font(TITLE_FONT_SIZE);
			title.setFont(titleFont);
			title.setStyle("-fx-font-weight: bold;");
			title.setWrappingWidth(CELL_WIDTH);
			// journal info
			Font defaultFont = new Font(DEFAULT_FONT_SIZE);
			date.setFont(defaultFont);
			time.setFont(defaultFont);
			context.setFont(defaultFont);
			
			// radio button
			// configure radio buttons to select list view's cell when clicked
			radioBtn.setOnAction(event -> updateListViewSelection());
			radioBtn.setOnMouseClicked(event -> updateListViewSelection());
			
			// journal info
			journalInfoContainer = new VBox(title, date, time);
			// sizing and spacing
			journalInfoContainer.setMinWidth(CELL_WIDTH);
			journalInfoContainer.setMaxWidth(CELL_WIDTH);
			journalInfoContainer.setSpacing(CELL_SPACING);
			
			// journal content
			journalContentContainer = new VBox(context);
			context.setWrapText(true); 
			context.setMaxWidth(CONTEXT_WIDTH);
			
			// parent container
			container = new HBox(radioBtn, journalInfoContainer, journalContentContainer);
			// sizing and spacing
			container.setPadding(new Insets(CONTAINER_VERTICAL_PADDING, 0, CONTAINER_VERTICAL_PADDING, 0));
			container.setSpacing(CONTAINER_SPACING);
		}
		
		
		/**
		 * Update the list view's selection to match the selected radio button
		 */
		private void updateListViewSelection() {
			ListView<JournalModel> listView = this.getListView();
			
			if (radioBtn.isSelected()) {
				listView.getSelectionModel().select(this.getIndex());
			}
			else {
				listView.getSelectionModel().clearSelection(this.getIndex());
			}
		}
		
		
		/**
		 * Method that executes every time the selection of the ListView changes
		 * 
		 * @param selected A boolean value representing whether the current cell is selected or not
		 */
		@Override
		public void updateSelected(boolean selected) {
			// toggle the radio button of the cell
			radioBtnToggleGroup.selectToggle(this.radioBtn);
		}
		
		
		/**
		 * Method that runs when a new JournalCell is instantiated in the ListView,
		 * responsible for configuring and populating its fields
		 * 
		 * @param journal the journal entry whose info will populate this JournalCell
		 * @param empty a boolean value indicating whether the cell is empty
		 */
		@Override
		protected void updateItem(JournalModel journal, boolean empty) {
			super.updateItem(journal, empty);
			
			if (empty || journal == null) {
				setGraphic(null);
			}
			else {
				radioBtn.setToggleGroup(radioBtnToggleGroup);
				// associate the journal with the radio button
				// so it can be retrieved from its toggle group
				radioBtn.setUserData(journal);
				// set the selection status of this radio button based on ListView
				radioBtn.setSelected(getListView().getSelectionModel().isSelected(this.getIndex()));
				
				// update rest of fields to match journal model
				title.setText(journal.getTitle());
				date.setText(journal.getDate());
				time.setText(journal.getTime());
				
				// Defer updating preview text until the container has been fully instantiated
				// so that dimensions of the container can be safely accessed
				Platform.runLater(() -> {
					String previewText = journal.getContext();
					context.setText(previewText);
					// set max height to height of parent container
					context.setMaxHeight(container.getHeight());
				});
				
				// make the cell visible
				setGraphic(container);
			}
		}
	}

}