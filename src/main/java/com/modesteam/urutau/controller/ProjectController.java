package com.modesteam.urutau.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.UserManager;
import com.modesteam.urutau.service.RequirementService;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Validator;

/**
 * This class is responsible to manager simple operations of projects!
 * The systems operations are received by the path /requirement followed
 * by the operation defined path.
 */

@Controller
public class ProjectController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);
	
	private final Result result;
	
	private final UserManager userSession;
	
	private final RequirementService requirementService;
	
	private final Validator validator;
	
	@Inject
	public ProjectController(Result result, UserManager userSession, 
			RequirementService requirementService, Validator validator) {
		this.result = result;
		this.userSession = userSession;
		this.requirementService = requirementService;
		this.validator = validator; 
	}

}
