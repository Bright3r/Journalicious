package application.controllers;

import javafx.event.ActionEvent;

/**
 * Controller for the "Home" screen.
 * 
 */
public class HomeController extends SceneController {
	/**
	 * Switches to "Login" screen.
	 * 
	 * @param e An event given by some user action on the application.
	 */
	public void switchToLogin(ActionEvent e) {
		super.switchToView(e, View.LOGIN, View.HOME);
	}
	
	/**
	 * Switches to "Create" screen.
	 * 
	 * @param e An event given by some user action on the application.
	 */
	public void switchToCreate(ActionEvent e) {
		super.switchToView(e, View.CREATE, View.HOME);
	}
	
	/**
	 * Switches to "Search" screen.
	 * 
	 * @param e An event given by some user action on the application.
	 */
	public void switchToSearch(ActionEvent e) {
		super.switchToView(e, View.SEARCH, View.HOME);
	}
	
	/**
	 * Switches to "Change Password" screen.
	 * 
	 * @param e An event given by some user action on the application.
	 */
	public void switchToChangePassword(ActionEvent e) {
		super.switchToView(e, View.CHANGE_PASSWORD, View.HOME);
	}
	
}
