package com.modesteam.urutau.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;

import com.modesteam.urutau.annotation.View;
import com.modesteam.urutau.model.Artifact;
import com.modesteam.urutau.model.system.FieldMessage;
import com.modesteam.urutau.service.RequirementService;

/**
 * This class is responsible to manager simple operations of requirements!
 * The systems operations are received by the path /requirement followed
 * by the operation defined path.
 */
@Controller
public class RequirementController {
	
	private static final Logger logger = LoggerFactory.getLogger(RequirementController.class);

	private static final int DEFAULT_PAGINATION_SIZE = 5;
	
	// Injected objects
	private final Result result;
	
	private final RequirementService requirementService;
	
	private final Validator validator;
	
	public RequirementController() {
		this(null, null, null);
	}
	
	@Inject
	public RequirementController(Result result,
		RequirementService requirementService, Validator validator) {
		this.result = result;
		this.requirementService = requirementService;
		this.validator = validator; 
	}
	
	/**
	 * Show a requirement that has a certain id and title 
	 * 
	 * @param id Unique attribute
	 * @param title various requirement can have same title
	 * 
	 * @return {@link Artifact} requirement from database
	 * 
	 * @throws UnsupportedEncodingException invalid characters or decodes fails
	 */
	@Get
	@Path("/show/{id}/{title}")
	public Artifact show(int id, String title) throws UnsupportedEncodingException {
		String decodedTitle = URLDecoder.decode(title, 
				StandardCharsets.UTF_8.toString());
		
		logger.info("Show requirement " + title);
		
		Artifact requirement = requirementService.getRequirement(id, decodedTitle);
		
		return requirement;
	}
	
	/**
	 * Paginate requirements into home page of projects
	 * 
	 * @param page, current page 
	 * 
	 * @return List of {@link Artifact} to be easy display into home page of a project
	 */
	@Get("{projectID}/paginate/{page}")
	public void paginate(int projectID, int page) {
		List<Artifact> requirements = requirementService
				.recover(projectID, DEFAULT_PAGINATION_SIZE, page);
		
		result.include("requirements", requirements);
	}
	
	/**
	 * View showAll can be used later to get requirements by ajax
	 * @return
	 */
	@Deprecated
	public List<? extends Artifact> showAll() {
		logger.info("Starting the requisition for all requirements");
		
		// Here was a method that took all the requirements
		List<? extends Artifact> requirements = null;
		
		result.include("requirements", requirements);
		
		return requirements;
	}
	
	/**
	 * This method is used to delete one requirement
	 * @param requirementId
	 */
	@Get
	@Path("/delete/{id}")
	public void delete(Long id) {
		
		logger.info("The requirement with the id " +id + " is solicitated for exclusion.");
		
		requirementService.delete(id);
		
		boolean requirementExistence = requirementService.verifyExistence(id);
		
		if(!requirementExistence) {
			logger.info("The requirement was succesfully excluded.");
			result.nothing();
		} else {
			logger.info("The requirement wasn't excluded yet.");
			validator.add(new SimpleMessage(FieldMessage.ERROR.toString(), 
					"Requirement was not excluded!"));	
			result.nothing();
		}
		
	}

	@View
	public void detailRequirement() {
		
	}
	
	@View
	public void showExclusionResult() {
		
	}
	
}
