package com.modesteam.urutau.controller;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;

/**
 * URUTAU - 2015
 * The default controller
 */
@Controller
public class IndexController {
	
	@Inject
	private Result result;
	
	public IndexController() {
		this(null);
	}

	public IndexController(Result result) {
		this.result = result;
	}
	
	@Path("/")
	public void index() {

	}
}
