package application.dal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import application.models.PasswordModel;

/**
 * A class that faciliates the interaction and manipulation of password data
 * within the flat files
 */
public class PasswordDAO {
	private static final String passwordPath = "resources/txts/password.txt";
	private static final File passFile = new File(passwordPath);

	
	/**
	 * Updates the password of the password model to reflect the password stored in flat files
	 * 
	 * @param passwordModel the PasswordModel to be updated
	 */
	public void updatePassword(PasswordModel passwordModel) {
		String password = "";
		try (Scanner in = new Scanner(passFile)) {	
			// get password from file
			if (in.hasNextLine()) {
				password = in.nextLine();
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("Could not find password.txt in file system!");
			e.printStackTrace();
		}
		
		// update password model
		passwordModel.setPassword(password);
	}
	
	
	/**
	 * Overwrites the password in the file system
	 * 
	 * @param newPassword the new password to store in file system
	 */
	public void setPassword(String newPassword) {
		// if newPassword is null, then user entered an empty string
		if (newPassword == null) {
			newPassword = "";
		}
		
		try (FileWriter writer = new FileWriter(passFile)) {
			// write new password to file
			writer.write(newPassword);
			
		} catch (IOException e) {
			System.out.println("Failed to write to password.txt!");
			e.printStackTrace();
		}
	}
	
}
