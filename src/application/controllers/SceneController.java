package application.controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import application.models.JournalModel;
import application.models.PasswordModel;
import application.models.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Parent class for all scene controllers
 * provides access to a user model that persists through application's lifetime
 */
public class SceneController {
	private static final String viewPackagePath = "resources/views/";
	
	// static class variable to avoid being reset upon re-instantiation
	private static View prevView;
	
	private UserModel userModel;
	private PasswordModel passwordModel;
	
	
	/**
	 * Creates new SceneController
	 */
	public SceneController() {
		this.userModel = new UserModel();
		this.passwordModel = new PasswordModel();
	}
	
	
	/**
	 * Gets the UserModel representing the user's info
	 * 
	 * @return a UserModel representing the user's info
	 */
	public UserModel getUserModel() {
		return this.userModel;
	}
	
	
	/**
	 * Gets the PasswordModel representing the user's password
	 * 
	 * @return a PasswordModel representing the user's password
	 */
	public PasswordModel getPasswordModel() {
		return this.passwordModel;
	}

	
	/**
	 * Changes the previous view of the application to a specified view
	 * 
	 * @param view the new previous view
	 */
	private static void setPrevView(View view) {
		prevView = view;
	}
	
	
	/**
	 * Gets the previous view of our application
	 * 
	 * @return the previous view of our application
	 */
	private static View getPrevView() {
		return prevView;
	}
	
	
	/**
	 * Gets the initial login scene for the application
	 * 
	 * @return the login scene of the application
	 * @throws MalformedURLException an exception indicating that the login view's URL was invalid
	 * @throws IOException an exception indicating that the loginView's fxml file does not exist
	 */
	public static Scene getInitialScene() throws MalformedURLException, IOException {		
		// get the Login View from local files and create scene
		String loginViewPath = viewPackagePath + View.LOGIN.getValue();
		File loginView = new File(loginViewPath);
		BorderPane root = FXMLLoader.load(loginView.toURI().toURL());
		
		Scene scene = new Scene(root, 600, 400);
		return scene;
	}
	

	/**
	 * Handles the logic for switching from one view to another
	 * 
	 * @param e an event given by some user action on the application
	 * @param view one of the scene views offered by the View enum
	 * @param prevView the view that the user is switching from
	 */
	protected void switchToView(ActionEvent e, View view, View prevView) {
		try {
			setPrevView(prevView);
			
			// load the view from fxml file and create new scene
			String viewPath = viewPackagePath + view.getValue();
			File viewFile = new File(viewPath);
			BorderPane root = FXMLLoader.load(viewFile.toURI().toURL());
			
			// set scene on stage
			Scene scene = new Scene(root);
			this.updateStageScene(e, scene);
			
		} catch (IOException ex) {
			System.out.println("Failed to switch scene");
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	
	/**
	 * Handles the logic for switching from the Search Page to the Edit Page.
	 * This separate method is needed because Edit is a special case of the Create Page.
	 * 
	 * @param e an event given by some user action on the application 
	 * @param journal the journal entry to populate the edit page with
	 */
	protected void switchToEditView(ActionEvent e, JournalModel journal) {
		try {
			setPrevView(View.SEARCH);
			
			// load the view from fxml file and create new scene
			String viewPath = viewPackagePath + "Edit.fxml";
			File viewFile = new File(viewPath);
			FXMLLoader loader = new FXMLLoader(viewFile.toURI().toURL());
			BorderPane root = loader.load();
			
			// initalize the journal data on the page
			CreateController controller = loader.getController();
			controller.initializeOldJournal(journal);
			
			// set scene on stage
			Scene scene = new Scene(root);
			this.updateStageScene(e, scene);
			
		} catch (IOException ex) {
			System.out.println("Failed to switch scene");
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	
	/**
	 * Updates the scene being displayed on the application's stage
	 * 
	 * @param e an event given by some user action on the application
	 * @param scene the scene to be shown on the stage
	 */
	private void updateStageScene(ActionEvent e, Scene scene) {
		// Get event source so primary stage can be retrieved
		// then show the new scene on the stage
		Node source = (Node) e.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
	
	
	/**
	 * Handles the logic for switching to the view that
	 * the user was previously on
	 * 
	 * @param e an event given by some user action on the application
	 * @param currView the current view that the user is on
	 */
	protected void switchToPrevView(ActionEvent e, View currView) {
		this.switchToView(e, getPrevView(), currView);
	}
	
	
	
	/**
	 * Enumerates the possible views the application can switch to,
	 * excluding "Edit" which must be accessed via switchToView method.
	 */
	protected enum View {
		LOGIN("Login.fxml"),
		HOME("Home.fxml"),
		CHANGE_PASSWORD("ChangePassword.fxml"),
		RESET_PASSWORD("ResetPassword.fxml"),
		CREATE("Create.fxml"),
		SEARCH("Search.fxml");
		
		private final String view;
		
		/**
		 * Constructs the view and associates it with a String value
		 * 
		 * @param view the next scene view to switch to
		 */
		private View(String view) {
			this.view = view;
		}
		
		/**
		 * Provides the value associated with a view
		 * 
		 * @return a String representing the path to a view's location in local files
		 */
		public String getValue() {
			return this.view;
		}
	}
	
}
