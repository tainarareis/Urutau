package com.modesteam.urutau.controller;

import javax.inject.Inject;

import com.modesteam.urutau.controller.dao.SystemDAO;
import com.modesteam.urutau.controller.dao.UserDAO;
import com.modesteam.urutau.controller.model.Administrator;
import com.modesteam.urutau.controller.model.User;

import br.com.caelum.vraptor.Result;

/**
 * Manage the users
 */
public class UserController {
	@Inject
	private Result result;
	@Inject
	private SystemDAO systemDAO;
	@Inject
	private UserDAO userDAO;
	
	/**
	 * Create new user. If there is no user, create
	 * the first administrator
	 */
	public void register(User user) {
		// If no account registered, create the first administrator
		if(!systemDAO.existAdministrator()) {
			systemDAO.createFirstAdministrator();
		} else {
			// Create new user
			userDAO.create(user);
		}
	}
	
	

}
