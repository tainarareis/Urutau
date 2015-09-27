package com.modesteam.urutau.service;

import java.nio.file.attribute.UserPrincipalLookupService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.UserManager;
import com.modesteam.urutau.dao.UserDAO;
import com.modesteam.urutau.model.User;

@RequestScoped
public class UserService {
	
	private final static Logger logger = LoggerFactory.getLogger(UserService.class);
	
	private final UserDAO userDAO;
	private UserManager userLogged = null;

	public UserService() {
		this(null);
	}
	
	@Inject
	public UserService(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	/**
	 * See {@link UserDAO#create(User)}
	 */
	public void create(User user){
		userDAO.create(user);
	}
	
	/**
	 * Method to verify if exist a user with same email or same login
	 * @param user
	 * @return false if the verification fails
	 */
	public boolean existsField(String field, Object value) {
		logger.info("Verification of field");
		try{
			if(userDAO.get(field, value) != null){
				return false;
			} else {
				return true;
			}
		} catch (NonUniqueResultException exception){
			return false;
		}
	}

	
	/**
	 * See {@link UserDAO#update(User)}
	 */
	public void update(User user) {
		userDAO.create(user);
	}
	
	/**
	 * See if an user exist
	 * 
	 * @param login
	 * @return
	 */
	public boolean existsUser(String login) {
		boolean userExistence;
		if(userDAO.get("login", login) != null) {
			userExistence = true;
		} else {
			userExistence = false;
		}
		return userExistence;
	}

	public User confirmateAuthentication(String login, String password, User userReceived){

		if (existsUser(login)){
			logger.info("The login is" + login);
			boolean correctPassword = isCorrectPassword(password);
			if(correctPassword == true){
				logger.info("The password is correct");	
				return userReceived;
			}else{
				logger.info("The password is wrong.");
				return null;
			}
		}else{
			logger.info("The login informed doesn't exist at the system");
			return null;
		}
	}

	
	/**
	 * Verifies if the password inserted at the login moment is correct with the user login.
	 * @param password
	 * @return  true if the password is correct
	 */
	public boolean isCorrectPassword(String password) {
		boolean correctPassword;
		if(userDAO.get("password", password) != null) {
			correctPassword = true;
		} else {
			correctPassword = false;
		}
		return correctPassword;
	}
}
