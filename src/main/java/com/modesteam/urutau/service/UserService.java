package com.modesteam.urutau.service;

import static javax.enterprise.event.TransactionPhase.AFTER_SUCCESS;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.NonUniqueResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.annotation.Updater;
import com.modesteam.urutau.dao.UserDAO;
import com.modesteam.urutau.exception.DataBaseCorruptedException;
import com.modesteam.urutau.model.UrutaUser;

@RequestScoped
public class UserService {
	
	private final static Logger logger = LoggerFactory.getLogger(UserService.class);
	
	private final UserDAO userDAO;
	
	/**
	 * @deprecated only CDI eye
	 */
	public UserService() {
		this(null);
	}
	
	@Inject
	public UserService(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	/**
	 * See {@link UserDAO#create(UrutaUser)}
	 */
	public void create(UrutaUser user) {
		userDAO.create(user);
	}
	
	/**
	 * Method to verify if exist a user with same email or same login
	 * 
	 * @param user
	 * @return false if the verification fails
	 */
	public boolean canBeUsed(final String attributeName, final Object value) {
		boolean valueNotUsed = false;
		
		try{
			if(userDAO.get(attributeName, value) == null) {
				valueNotUsed = true;
			}
		} catch (NonUniqueResultException exception) {
			throw new DataBaseCorruptedException(this.getClass().getSimpleName() 
					+ " returns twice " + attributeName + " equals");
		} catch (Exception exception) {
			exception.printStackTrace();
		} 
		
		return valueNotUsed;
	}

	
	/**
	 * See {@link UserDAO#update(UrutaUser)}
	 */
	public void update(UrutaUser user) {
		userDAO.create(user);
	}
	
	/**
	 * See if an user exist
	 * 
	 * @param login
	 * @return
	 */
	public boolean existsUser(String login) {
		boolean userExistence = false;
		
		try {
			if(userDAO.get("login", login) != null) {
				userExistence = true;
			} else {
				userExistence = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return userExistence;
	}
	
	/**
	 * If returns null, user was not authenticate
	 * 
	 * @param login to verifies
	 * @param password to compare with database instance
	 * @return {@link UrutaUser} instance of database
	 * @throws Exception TODO treat
	 */
	public UrutaUser authenticate(String login, String password) throws Exception {
		UrutaUser user = null;
		
		user = userDAO.get("login", login);
		
		// Case exists, login is true
		// Verifies password
		if (user != null && !user.getPassword().equals(password)) {
			// reset user
			user = null;
		} else {
			logger.info("The login informed doesn't exist at the system");
		}
		
		return user;
	}
	
	/**
	 * Get user reference
	 *  
	 * @param userID identifier
	 * @return user logged, uses into userSession
	 */
	public UrutaUser getUserByID(Long userID) {
		return userDAO.find(userID);
	}
	
	/**
	 * Reload exclusive userLogged from database
	 * @return 
	 */
	public void reloadInstance(@Observes(during=AFTER_SUCCESS) @Updater UrutaUser userLogged) {
		logger.trace("Reload user session");
		userDAO.reload(userLogged);
	}
}
