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
	
	/**
	 * Control flux of index. If user are logged, redirect to project index,
	 *  else redirect to real index! 
	 */
	@Path(value = "/", priority=Path.HIGHEST)
	public void index() {
		if(userSession.isLogged()) {
			result.forwardTo(ProjectController.class).index();
		} else {
			// redirect to real index
		}
	}
}
