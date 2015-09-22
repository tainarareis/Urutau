package com.modesteam.urutau.service;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.NonUniqueResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.dao.UserDAO;
import com.modesteam.urutau.model.User;

@RequestScoped
public class UserService {
	
	private final static Logger logger = LoggerFactory.getLogger(UserService.class);
	
	private final UserDAO userDAO;
	
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

	public boolean existsUser(User user) {
		boolean userExistence;
		userExistence = userDAO.existsUser(user);
		return userExistence;
	}


}
