package com.modesteam.urutau.controller;


import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import com.modesteam.urutau.UserManager;
import com.modesteam.urutau.annotation.View;
import com.modesteam.urutau.controller.dao.SystemDAO;
import com.modesteam.urutau.controller.dao.UserDAO;
import com.modesteam.urutau.controller.model.User;

/**
 * Manage the users
 */
@Controller
public class UserController {
	
	@Inject
	private Result result;
	@Inject
	private SystemDAO systemDAO;
	@Inject
	private UserDAO userDAO;
	@Inject
	private UserManager userManager;
	
	/**
	 * Create new user. If there is no user, create
	 * the first administrator
	 */
	@Post
	@Path("/register")
	public void register(User user) {
		
	}
	
	@View
	public void welcomeAdministrator() {
		
	}
	
	@Path("/welcomeUser")
	public void welcomeUser() {
		
	}
	
	@Post
	@Path("/login")
	public void login(User user) {
		if(systemDAO.isFirstAdministrator()){
			result.redirectTo(this).firstAdministratorSettings();
		} else {
			result.redirectTo(this).welcomeUser();
		}
	}
	
	@Path("/firstAdministratorSettings")
	public void firstAdministratorSettings() {
		
	}
	
	/**
	 * Set the new first administrator login and password
	 */
	@Post
	@Path("/administratorSettings")
	public void administratorSettings(User user) {
		User logged = userManager.getUserLogged();
		logged.setLogin(user.getLogin());
		logged.setPassword(user.getPassword());
		userManager.setUserLogged(logged);
		userDAO.newUserSettings(logged);
		result.redirectTo(this).welcomeAdministrator();
	}

}
