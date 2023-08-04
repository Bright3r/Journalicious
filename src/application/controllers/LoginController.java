package application.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import application.models.PasswordModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Controller for the login page, initialized on startup
 * 
 */
public class LoginController extends SceneController implements Initializable {
	@FXML private TextField passwordField;
	@FXML private Label errorMsgLbl;
	@FXML private Button loginBtn;
	@FXML private Hyperlink forgotPasswordLink;

	
	/**
	 * Handles logic for clicking the login button
	 * 
	 * @param e an event given by some user action on the application
	 */
	public void handleLoginAttempt(ActionEvent e) {		
		PasswordModel passwordModel = super.getPasswordModel();
		String enteredPassword = passwordField.getText();
		
		boolean passwordIsCorrect = passwordModel.isCorrectPassword(enteredPassword);
		if (passwordIsCorrect) {
			// first time users must go to change password
			if (passwordModel.isFirstTimeUser()) {
				super.switchToView(e, View.CHANGE_PASSWORD, View.LOGIN);
			}
			// returning users go straight to home
			else {
				super.switchToView(e, View.HOME, View.LOGIN);
			}
		}
		// if user enters wrong password, display error msg and try again
		else {
			// reset password field
			passwordField.setText("");
			// display error message depending on user status
			displayErrorMsg(passwordModel);
		}
	}
	
	
	/**
	 * Displays an error message on the screen depending on the user's login status
	 * 
	 * @param passwordModel the PasswordModel representing the user's current password
	 */
	private void displayErrorMsg(PasswordModel passwordModel) {
		if (!passwordModel.isFirstTimeUser()) {
			errorMsgLbl.setText("Error: Password is invalid");
		}
		else {
			errorMsgLbl.setText("Error: Invalid Password. Please enter the default password \"p\"");
		}
	}

	
	/**
	 * Handles logic for clicking the forgot password button
	 * 
	 * @param e an event given by some user action on the application
	 */
	public void handleForgotPassword(ActionEvent e) {
		super.switchToView(e, View.RESET_PASSWORD, View.LOGIN);
	}
	
	
	/**
	 * Event listener on "Enter Password" field for when key is pressed,
	 * submits form if the ENTER key is pressed
	 * 
	 * @param e an event given by some user action on the application
	 */
	public void handleKeyPress(KeyEvent e) {
		if (e.getCode() == KeyCode.ENTER) {
			ActionEvent actionEvent = new ActionEvent(e.getSource(), e.getTarget());
			handleLoginAttempt(actionEvent);
		}
	}
	
	
	/**
	 * Updates UI according to user status on page startup
	 * 
	 * @param location the location of a file or directory
	 * @param resources the resources required to locate the root element
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		PasswordModel passwordModel = super.getPasswordModel();
		
		// if first time user, hide reset password and remind them about default password
		if (passwordModel.isFirstTimeUser()) {
			forgotPasswordLink.setVisible(false);
			errorMsgLbl.setText("Reminder: On first time login, default password is \"p\"");
		}
	}
	
}