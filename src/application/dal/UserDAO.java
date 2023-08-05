package application.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import application.dal.DBConnection.Database;
import application.models.UserModel;

/**
 * A class that facilitates the interaction and manipulation of user data
 */
public class UserDAO {
	// the id for the row where user info is stored in DB
	private static final String USER_INFO_ROW_ID = "info";

	
	/**
	 * Gets the stored security question from the database
	 * 
	 * @return the user's security question
	 */
	private String getSecurityQuestion() {
		String query = "SELECT * FROM user_info WHERE id = ?";
		String securityQuestion = "";
		
		try {
			// get DB connection
			Connection connection = DBConnection.getDBConnection(Database.USER_INFO);
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setString(1, USER_INFO_ROW_ID);
			
			ResultSet queryOutput = statement.executeQuery();
			if (queryOutput.next()) {
				securityQuestion = queryOutput.getString("security_question");
			}
			
		} catch (Exception ex) {
			System.out.println("Failed to retrieve Security Question from database!");
			ex.printStackTrace();
		}
		
		return securityQuestion;
	}
	
	
	/**
	 * Overwrites the user's security question in the database
	 * 
	 * @param newSecurityQuestion the new security question selected by the user
	 */
	public void setSecurityQuestion(String newSecurityQuestion) {
		String updateQuery = "UPDATE user_info SET security_question = ? WHERE id = ?";
		
		try {
			// get DB connection
			Connection connection = DBConnection.getDBConnection(Database.USER_INFO);
			PreparedStatement statement = connection.prepareStatement(updateQuery);
			
			statement.setString(1, newSecurityQuestion);
			statement.setString(2, USER_INFO_ROW_ID);
			
			statement.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("Failed to update security question in " + Database.USER_INFO.getValue() + "!");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Gets the answer to the user's security question that is stored in the database
	 * 
	 * @return the answer to the user's security question
	 */
	private String getSecurityQuestionAnswer() {
		String query = "SELECT * FROM user_info WHERE id = ?";
		String securityQuestionAnswer = "";
		
		try {
			// get DB connection
			Connection connection = DBConnection.getDBConnection(Database.USER_INFO);
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setString(1, USER_INFO_ROW_ID);
			
			ResultSet queryOutput = statement.executeQuery();
			if (queryOutput.next()) {
				securityQuestionAnswer = queryOutput.getString("security_question_answer");
			}
			
		} catch (Exception ex) {
			System.out.println("Failed to retrieve Security Question Answer from database!");
			ex.printStackTrace();
		}
		
		return securityQuestionAnswer;
	}
	
	
	/**
	 * Overwrites the answer to the user's security question stored in the database
	 * 
	 * @param newSecurityQuestionAnswer the user's new security question answer
	 */
	public void setSecurityQuestionAnswer(String newSecurityQuestionAnswer) {
		String updateQuery = "UPDATE user_info SET security_question_answer = ? WHERE id = ?";
		
		try {
			// get DB connection
			Connection connection = DBConnection.getDBConnection(Database.USER_INFO);
			PreparedStatement statement = connection.prepareStatement(updateQuery);
			
			statement.setString(1,  newSecurityQuestionAnswer);
			statement.setString(2, USER_INFO_ROW_ID);
			
			statement.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("Failed to update security question answer in " + Database.USER_INFO.getValue() + "!");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Updates a given UserModel to reflect the user info stored in DB
	 * 
	 * @param user the UserModel to be updated
	 */
	public void updateUserModel(UserModel user) {
		// get user info from DB
		String securityQuestion = this.getSecurityQuestion();
		String securityQuestionAnswer = this.getSecurityQuestionAnswer();
		
		// update model to reflect information in DB
		user.setSecurityQuestion(securityQuestion);
		user.setSecurityQuestionAnswer(securityQuestionAnswer);
	}
}