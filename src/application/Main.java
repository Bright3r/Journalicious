package application;
	
import application.controllers.SceneController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Starter class that is launched upon running application
 **/
public class Main extends Application {
	/**
	 * Shows the user the login page upon application start
	 * 
	 * @param primaryStage the highest level container for the application that hosts scenes 
	 **/
	@Override
	public void start(Stage primaryStage) {
		try {
			
			Scene scene = SceneController.getInitialScene();
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Journalicious");
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Main class that launches the JavaFX application
	 * 
	 * @param args List of command line arguments to pass to JVM
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
