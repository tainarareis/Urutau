package com.modesteam.urutau.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.annotation.View;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Result;
/**
 * Executes main logics of administrator
 */
@Controller
public class AdministratorController {
	
	private static final Logger logger = LoggerFactory.getLogger(AdministratorController.class);
	
	private final Result result;
	
	public AdministratorController() {
		this(null);
	}
	
	@Inject
	public AdministratorController(Result result) {
		this.result = result;
	}

	@View
	public void welcomeAdministrator() {
		
	}
	
	@View
	public void changeFirstSettings() {
		
	}
}
