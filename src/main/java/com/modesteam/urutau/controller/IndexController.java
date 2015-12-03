package com.modesteam.urutau.controller;

import javax.inject.Inject;

import com.modesteam.urutau.UserSession;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;

/**
 * 
 * The default controller
 */
@Controller
public class IndexController {
	
	@Inject
	private UserSession userSession;
	
	@Inject
	private Result result;
	
	@Path("/")
	public void index() {
		if(userSession.isLogged()) {
			result.forwardTo(UserController.class).home();
		} else {
			// redirect to real index
		}
	}
}
