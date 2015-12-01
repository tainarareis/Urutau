package com.modesteam.urutau.controller;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;

import com.modesteam.urutau.UserSession;
import com.modesteam.urutau.model.Artifact;
import com.modesteam.urutau.model.Artifact.ArtifactType;
import com.modesteam.urutau.model.Feature;
import com.modesteam.urutau.model.Generic;
import com.modesteam.urutau.model.Storie;
import com.modesteam.urutau.model.User;
import com.modesteam.urutau.service.RequirementService;

@Controller
public class RequirementEditor {

	private static final Logger logger = LoggerFactory.getLogger(RequirementCreator.class);

	//Validation String
	private static final String REQUIREMENT_MODIFICATION_ERROR = null;
	
	//Objects to be injected
	private final Result result;
	private final Validator validator;
	private final UserSession userSession;
	private final RequirementService requirementService;
	
	public RequirementEditor() {
		this(null, null, null, null);
	}
	
	@Inject
	public RequirementEditor(Result result, Validator validator, 
			UserSession userSession, RequirementService requirementService) {
		this.result = result;
		this.validator = validator;
		this.userSession = userSession;
		this.requirementService = requirementService;
	}
	
	@Get
	@Path("/edit/{requirementID}")
	public void edit(Long requirementID) {
		
		logger.trace("Starting the function edit. Requirement id is " + requirementID);
		
		boolean requirementExistence = requirementService.verifyExistence(requirementID);
		
		// Verifies the acceptance of the requirement to proceed the requisition
		if(requirementExistence) {
			logger.info("The requirement exists in database");
			
			Artifact requirement = requirementService.detail(requirementID);
			
//			result.include("requirement", requirement);			
			redirectToEditionPage(requirement, ArtifactType.EPIC);
		} else {
			logger.info("The requirement id informed is unknown.");
			validator.add(new SimpleMessage(REQUIREMENT_MODIFICATION_ERROR, "It is not possible to "
					+ " edit an unknown requirement."));	
		}
		
		validator.onErrorForwardTo(UserController.class).home();
	}
	
	/**
	 * Provides the redirecting to the requirement type edition page.
	 * @param requirement
	 * @param artifactType
	 */
	private void redirectToEditionPage(Artifact requirement, ArtifactType artifactType) {
		logger.info("Starting the function redirectToEditionPage.");

		logger.trace(requirement.toString());
		
		switch (artifactType) {
			case GENERIC:
				result.redirectTo(this).editGeneric((Generic) requirement);
				break;
			case EPIC:
				result.include(requirement.toString(), requirement);
				result.redirectTo(this).editEpic();
				break;
			case FEATURE:
				result.redirectTo(this).editFeature((Feature) requirement);
				break;
			case STORIE:
				result.redirectTo(this).editUserStory((Storie) requirement);
			default:
				break;
		}
		validator.onErrorForwardTo(UserController.class).home();
	}

	/**
	 * Allows the modification of an unique requirement
	 * @param requirement
	 */
	public void modifyRequirement(Artifact requirement) {		
		logger.info("Starting the function modifyRequirement");
		
		//Setting the current date and current user 
		Calendar lastModificationDate = getCurrentDate();
		requirement.setLastModificationDate(lastModificationDate);		
		User loggedUser = userSession.getUserLogged();
		requirement.setLastModificationAuthor(loggedUser);
		
		boolean updateResult = requirementService.modifyRequirement(requirement);		
		
		if(updateResult){
			logger.info("The update was sucessfully executed.");
		}else{
			logger.info("The update wasn't sucessfully executed.");
		}
		
		result.include("message", "Edition successfully executed.");
		result.redirectTo(UserController.class).home();
	
	}
	
	@Get
	public void editEpic() {
		
	}
	
	
	@Post
	public void editFeature(Feature feature) {
		modifyRequirement(feature);
	}
	
	@Post
	public void editGeneric(Generic generic) {
		modifyRequirement(generic);		
	}
	
	@Post
	public void editUserStory(Storie storie) {
		modifyRequirement(storie);
	}
	
	/**
	 * Get an instance of current date through of {@link Calendar}
	 * @return current date
	 */
	private Calendar getCurrentDate() {
		Date currentDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		
		return calendar;
	}
	
}
