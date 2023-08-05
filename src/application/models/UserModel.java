package application.models;

import application.dal.UserDAO;

/**
 * A class representing user data, handles logic related to
 * checking and changing the stored user info
 */
public class UserModel {	
	private String securityQuestion;
	private String securityQuestionAnswer;
	
	
	/**
	 * Creates a new user model
	 */
	public UserModel() {
		// initialize model to reflect DB
		UserDAO userDAO = new UserDAO();
		userDAO.updateUserModel(this);
	}
	
	
	/**
	 * Overwrites the user's security question to a new question
	 * 
	 * @param newSecurityQuestion the user's new security question
	 */
	public void setSecurityQuestion(String newSecurityQuestion) {
		// update in DB
		UserDAO userDAO = new UserDAO();
		userDAO.setSecurityQuestion(newSecurityQuestion);
		
		// update model
		this.securityQuestion = newSecurityQuestion;
	}
	
	
	/**
	 * Gets the security question set by the user
	 * 
	 * @return the user's security question
	 */
	public String getSecurityQuestion() {
		return securityQuestion;
	}
	

	/**
	 * Overwrites the answer to the user's security question to a new answer
	 * 
	 * @param answer the answer to the user's security question
	 */
	public void setSecurityQuestionAnswer(String answer) {
		// update in DB
		UserDAO userDAO = new UserDAO();
		userDAO.setSecurityQuestionAnswer(answer);
		
		// update model
		this.securityQuestionAnswer = answer;
	}
	
	
	/**
	 * Checks if the entered answer matches the correct answer to the user's security question.
	 * 
	 * @param enteredAnswer The entered answer to the security question.
	 * @return Returns true if the entered answer matches the answer to the security questions. Otherwise, returns false.
	 */
	public boolean isCorrectSecurityQuestionAnswer(String enteredAnswer) {
		boolean answerIsCorrect = enteredAnswer.equals(this.securityQuestionAnswer);
		return answerIsCorrect;
	}
}