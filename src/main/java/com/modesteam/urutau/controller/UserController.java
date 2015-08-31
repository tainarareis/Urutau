package com.modesteam.urutau.controller;

import javax.inject.Inject;

import com.modesteam.urutau.controller.dao.SystemDAO;
import com.modesteam.urutau.controller.dao.UserDAO;
import com.modesteam.urutau.controller.model.Administrator;
import com.modesteam.urutau.controller.model.User;

import br.com.caelum.vraptor.Post;
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
	@Post
	public void register(User user) {
		// If no account registered, create the first administrator
		if(!systemDAO.existAdministrator()) {
			systemDAO.createFirstAdministrator();
			result.redirectTo(this).welcomeAdministrator();
		} else {
			// Create new user
			userDAO.create(user);
			result.redirectTo(this).welcomeUser();
		}
	}
	
	//
	public void welcomeAdministrator() {
		
	}
	
	public void welcomeUser() {
		
	}
	

}
