package application.controllers;


import java.net.URL;
import java.util.ResourceBundle;

import application.models.PasswordModel;
import application.models.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/** 
 * Controller class for Change Password page
 * 
 **/
public class ChangePasswordController extends SceneController implements Initializable {
	private static final String[] SECURITY_QUESTIONS = {
			"What was your first pet's name?",			
			"What is your mother's maiden name?",
			"What street did you grow up on?",
			"What is your favorite programming language?",
			"What is your favorite food?"
		};
	
	@FXML private TextField prevPasswordField;
	@FXML private TextField newPasswordField;
	@FXML private TextField confirmPasswordField;
	@FXML private ChoiceBox<String> securityQuestionChoiceBox;
	@FXML private TextField securityQuestionAnswerField;
	@FXML private Button submitBtn;
	@FXML private Label errorMsgLbl;
	
	private boolean isSecurityQuestionSelected;
	private String securityQuestion;
	
	
	/**
	 * Constructs a new Change Password Controller
	 **/
	public ChangePasswordController() {
		this.isSecurityQuestionSelected = false;
		this.securityQuestion = null;
	}
	
	
	/** 
	 * Initializes the choice box with the provided security questions 
	 * and connects it to a method for handling its logic
	 * 
	 * @param location the location of a file or directory
	 * @param resources the resources required to locate the root element
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		securityQuestionChoiceBox.getItems().addAll(SECURITY_QUESTIONS);
		securityQuestionChoiceBox.setOnAction(this::handleSecurityQuestionSelection);
	}
	
	
	/** 
	 * Handles logic for the selection of a security question
	 * from the choice box
	 *
	 * @param e An event given by some user action on the application 
	 **/
	public void handleSecurityQuestionSelection(ActionEvent e) {
		this.securityQuestion = securityQuestionChoiceBox.getValue();
		
		if (securityQuestion != null) {
			this.isSecurityQuestionSelected = true;
		}
		else {
			this.isSecurityQuestionSelected = false;
		}
	}

	
	/**
	 * Handles logic for clicking the submit button on the change password form
	 * 
	 * @param e an event given by some user action on the application
	 */
	public void handleSubmit(ActionEvent e) {
		// get user and password model
		UserModel userModel = super.getUserModel();
		PasswordModel passwordModel = super.getPasswordModel();
		
		// check if previous password was incorrect
		String prevPassword = prevPasswordField.getText();
		if (!passwordModel.isCorrectPassword(prevPassword)) {
			errorMsgLbl.setText("Error: Previous password was incorrect! Please try again.");
			return;
		}
		
		// check if new password is valid
		String newPassword = newPasswordField.getText();
		if (!passwordModel.isValidNewPassword(newPassword)) {
			errorMsgLbl.setText("Error: Your new password is invalid! Please try something else.");
			return;
		}
		
		// check if new password matches confirm new password field
		String confirmPassword = confirmPasswordField.getText();
		if (!newPassword.equals(confirmPassword)) {
			errorMsgLbl.setText("Error: New passwords did not match!");
			return;
		}
		
		// check for empty security question field
		if (!this.isSecurityQuestionSelected || this.securityQuestion == null || this.securityQuestion.equals("")) {
			errorMsgLbl.setText("Error: You must select a security question!");
			return;
		}
		
		// check if security question was answered
		String securityQuestionAnswer = securityQuestionAnswerField.getText();
		if (securityQuestionAnswer == null || securityQuestionAnswer.equals("")) {
			errorMsgLbl.setText("Error: You must answer your security question!");
			return;
		}
		
		
		// submit fields' data to "DB" (stored in model object's state for now)
		userModel.setSecurityQuestion(this.securityQuestion);
		userModel.setSecurityQuestionAnswer(securityQuestionAnswer);
		passwordModel.setPassword(newPassword);
		
		// display success message (TODO!) and redirect to home page
		super.switchToView(e, View.HOME, View.CHANGE_PASSWORD);
	}
	
	
	/**
	 * Event listener on "Change Password" field for when key is pressed,
	 * submits form if the ENTER key is pressed
	 * 
	 * @param e an event given by some user action on the application
	 */
	public void handleKeyPress(KeyEvent e) {
		if (e.getCode() == KeyCode.ENTER) {
			ActionEvent actionEvent = new ActionEvent(e.getSource(), e.getTarget());
			handleSubmit(actionEvent);
		}
	}
	
	/**
	 * Handles clicks on the "Cancel" button.
	 * 
	 * @param e an event given by some user action on the application
	 */
	public void handleCancelBtnClick(ActionEvent e) {
		super.switchToPrevView(e, View.CHANGE_PASSWORD);
	}
	
}
