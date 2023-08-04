package application.models;

import application.dal.UserDAO;

public class UserModel {	
	private UserDAO userDAO;
	private String securityQuestion;
	private String securityQuestionAnswer;
	
	public UserModel() {
		this.userDAO = new UserDAO();
		
		this.initializeSecurityQuestion();
		this.initializeSecurityQuestionAnswer();
	}
	
	
	private void initializeSecurityQuestion() {
		this.securityQuestion = this.userDAO.getSecurityQuestion();
	}
	
	private void initializeSecurityQuestionAnswer() {
		this.securityQuestionAnswer = this.userDAO.getSecurityQuestionAnswer();
	}
	
	/**
	 * Overwrites the user's security question to a new question
	 * 
	 * @param newSecurityQuestion the user's new security question
	 */
	public void setSecurityQuestion(String newSecurityQuestion) {
		// update in DB
		this.userDAO.setSecurityQuestion(newSecurityQuestion);
		
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
		this.userDAO.setSecurityQuestionAnswer(answer);
		
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