package application.models;

import application.dal.PasswordDAO;

/**
 * A class representing a Password for the application,
 * handles the logic related to checking passwords and 
 * changing passwords 
 */
public class PasswordModel {
	private static final String DEFAULT_PASSWORD = "p";
	
	private PasswordDAO passDAO;
	private String password;
	
	
	public PasswordModel() {
		this.passDAO = new PasswordDAO();
		
		this.initializePassword();
	}
	
	
	private void initializePassword() {
		this.passDAO.updatePassword(this);
	}
	
	/**
	 * Changes the password stored in the system
	 * 
	 * @param newPassword the new password to change the current password to
	 */
	public void setPassword(String newPassword) {
		// change password in flat files
		this.passDAO.setPassword(newPassword);
		
		// Redundantly change password in model (already updated when getting password)
		this.password = newPassword;
	}
	
	
	private String getPassword() {
		// update current password to match flat files
		this.passDAO.updatePassword(this);
		
		// return current password
		return this.password;
	}
	
	
	/**
	 * Checks if the user is a first time user
	 * 
	 * @return a boolean indicating whether user is a first time user
	 */
	public boolean isFirstTimeUser() {
		String password = this.getPassword();
		boolean passwordIsDefault = password.equals(DEFAULT_PASSWORD);
		
		return passwordIsDefault;
	}
	
	
	/**
	 * Checks if a given password matches the stored password
	 * 
	 * @param enteredPassword the password entered by the user
	 * @return a boolean indicating if the user entered the correct password
	 */
	public boolean isCorrectPassword(String enteredPassword) {
		String password = this.getPassword();
		boolean passwordIsCorrect = enteredPassword.equals(password);
		
		return passwordIsCorrect;
	}
	
	
	/**
	 * Checks if a given String is a valid new password
	 * 
	 * @param newPassword the new password entered by the user
	 * @return a boolean indicating whether the user's new password is valid
	 */
	public boolean isValidNewPassword(String newPassword) {
		boolean passwordIsValid = !newPassword.equals(DEFAULT_PASSWORD);

		return passwordIsValid;
	}
	
}
