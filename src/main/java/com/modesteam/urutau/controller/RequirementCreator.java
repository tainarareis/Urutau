package com.modesteam.urutau.controller;

import java.io.UnsupportedEncodingException;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.formatter.RequirementFormatter;
import com.modesteam.urutau.model.Artifact;
import com.modesteam.urutau.model.Epic;
import com.modesteam.urutau.model.Feature;
import com.modesteam.urutau.model.Generic;
import com.modesteam.urutau.model.Storie;
import com.modesteam.urutau.model.UseCase;
import com.modesteam.urutau.model.system.ArtifactType;
import com.modesteam.urutau.model.system.FieldMessage;
import com.modesteam.urutau.service.RequirementService;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.com.caelum.vraptor.view.Results;

/**
 * This is a implementation of {@link EntityCreator}, 
 * part of pattern abstract factory 
 * He was born to reduce the coupling and 
 * increase your cohesion of {@link RequirementController}, 
 */
@Controller
@Path("/requirement")
public class RequirementCreator {

	private static final Logger logger = LoggerFactory.getLogger(RequirementCreator.class);

	private static final String PROJECT_ID_INPUT_VALUE = "projectID";

	//Objects to be injected
	private final Result result;
	private final Validator validator;
	private final RequirementService service;
	private final RequirementFormatter formatter;
	
	/**
	 * CDI only eye
	 */
	public RequirementCreator() {
		this(null, null, null, null);
	}
	
	@Inject
	public RequirementCreator(Result result, 
			Validator validator,
			RequirementService service, 
			RequirementFormatter formatter) {
		this.result = result;
		this.validator = validator;
		this.service = service;
		this.formatter = formatter;
	}
	
	/**
	 * Server side validation
	 *  
	 * @param title to validate
	 */
	@Post
	public void validate(String title) {
		validator.addIf(title == null || title.isEmpty(), 
				new I18nMessage("title", "title_can_not_be_empty"));
		validator.onErrorUse(Results.json()).withoutRoot().from(validator.getErrors()).serialize();
	}
	
	@Post
	public void createGeneric(Generic generic) {
		generic.setArtifactType(ArtifactType.GENERIC);
		save(generic);
	}
	
	@Post
	public void createFeature(Feature feature) {
		feature.setArtifactType(ArtifactType.FEATURE);
		save(feature);
	}
	
	@Post
	public void createUserStory(Storie storie) {
		storie.setArtifactType(ArtifactType.STORIE);
		save(storie);
	}

	@Post
	public void createEpic(Epic epic) {
		epic.setArtifactType(ArtifactType.EPIC);
		save(epic);
	}
	
	/**
	 * Use case creation is more specific so this method
	 * implementation is more robust than the others
	 */
	@Post
	public void createUseCase(UseCase useCase) {		
		
		if(useCase.getFakeActors() != null) {
			useCase.formatToRealActors();
		} else { 
			validator.add(new SimpleMessage(FieldMessage.ERROR, 
					"needs_author"));
		}
		
		save(useCase);
	}
	
	/**
	 * This projectID is a reference of the project 
	 * which the requirement will be created. 
	 * All part of URL is created through a script into home.jsp, 
	 * projectID number will be included into view page to fill a input hidden.
	 * 
	 * URL: /requirementType/idNumber
	 * View: /WEB-INF/jsp/requirementCreator/requirementType
	 * 
	 * @param projectID Truly is an Long number, converted into script to 
	 * 	be changed foward to Long again.
	 */
	@Get("generic/{projectID}")
	public void generic(String projectID) {
		result.include(PROJECT_ID_INPUT_VALUE, projectID);
	}
	
	/**
	 * See {@link RequirementCreator#generic(String)}
	 */
	@Get("storie/{projectID}")
	public void storie(String projectID) {
		result.include(PROJECT_ID_INPUT_VALUE, projectID);		
	}
	
	/**
	 * See {@link RequirementCreator#generic(String)}
	 */
	@Get("feature/{projectID}")
	public void feature(String projectID) {
		result.include(PROJECT_ID_INPUT_VALUE, projectID);
	}
	
	/**
	 * See {@link RequirementCreator#generic(String)}
	 */
	@Get("epic/{projectID}")
	public void epic(String projectID) {
		result.include(PROJECT_ID_INPUT_VALUE, projectID);
	}
	
	/**
	 * See {@link RequirementCreator#generic(String)}
	 */
	@Get("useCase/{projectID}")
	public void useCase(String projectID) {
		result.include(PROJECT_ID_INPUT_VALUE, projectID);
	}
	
	/**
	 * Server-side validates of basic information, 
	 * it will be execute by method save, right bellow
	 * 
	 * @param requirement that will be verifies
	 */
	private void validates(final Artifact requirement) {
		logger.info("Apply basic validate in requirement");

		validator.addIf(requirement.getAuthor() == null,
				new SimpleMessage(FieldMessage.ERROR, "needs_author"));
		
		validator.addIf(requirement.getProject() == null, 
				new SimpleMessage(FieldMessage.ERROR, "project_not_exist"));

		validator.onErrorRedirectTo(ApplicationController.class).dificultError();
	}
	
	/**
	 * Generic method to save a artifact, it can be 
	 * 
	 * @param requirement is a user output that will be verified and saved
	 */
	private void save(Artifact requirement) {		
		formatter.format(requirement);
		
		logger.info("Project ID is " + requirement.getProject().getId());
		
		validates(requirement);
		
		if(!validator.hasErrors()) {			
			service.create(requirement);
		} else {
			logger.error("Some errors was found");
		}
		
		result.include(FieldMessage.SUCCESS, "requirement_add_with_success");
		
		try {
			result.redirectTo(ProjectController.class)
				.show(requirement.getProject());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}