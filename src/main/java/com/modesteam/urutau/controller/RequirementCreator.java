package com.modesteam.urutau.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;

import com.modesteam.urutau.UserSession;
import com.modesteam.urutau.annotation.View;
import com.modesteam.urutau.dao.RequirementDAO;
import com.modesteam.urutau.model.Actor;
import com.modesteam.urutau.model.Artifact;
import com.modesteam.urutau.model.ArtifactType;
import com.modesteam.urutau.model.Epic;
import com.modesteam.urutau.model.Feature;
import com.modesteam.urutau.model.Generic;
import com.modesteam.urutau.model.Project;
import com.modesteam.urutau.model.Storie;
import com.modesteam.urutau.model.UseCase;
import com.modesteam.urutau.model.User;
import com.modesteam.urutau.model.system.ErrorMessage;
import com.modesteam.urutau.model.system.FieldMessage;
import com.modesteam.urutau.service.ProjectService;

/**
 * This is an concrete implementation of {@link EntityCreator}, part of pattern abstract factory 
 * He was born to reduce the coupling of {@link RequirementController}, 
 * and increase your cohesion.
 */
@Controller
@Path("/requirement")
public class RequirementCreator extends EntityCreator<Artifact> {

	private static final Logger logger = LoggerFactory.getLogger(RequirementCreator.class);

	private static final String PROJECT_ERROR_MESSAGE = "Project id was not passed!";

	//Objects to be injected
	private final Result result;
	private final Validator validator;
	private final UserSession userSession;
	private final ProjectService projectService;
	
	public RequirementCreator() {
		this(null, null, null, null, null);
	}
	
	/**
	 * The Superclass {@link EntityCreator} requires an DAO to work
	 */
	@Inject
	public RequirementCreator(Result result, Validator validator, 
			RequirementDAO requirementDAO, UserSession userSession, 
			ProjectService projectService) {
		this.result = result;
		this.validator = validator;
		this.userSession = userSession;
		this.projectService = projectService;
		// superclass needs an DAO
		super.setDao(requirementDAO);
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
	 * Called by all methods that makes requirements creation,
	 * this have basic implementation to persisted.
	 * IMPORTANT: validate before call this method through by 
	 * {@link #validate()}
	 * 
	 * @param requirement to be persisted
	 */
	private void save(Artifact requirement) {
		
		logger.info("Try persist " + requirement.getTitle());
		
		// Setting current date
		Calendar calendar = getCurrentDate();
		requirement.setDateOfCreation(calendar);
		
		// Setting author
		settingOwner(requirement);
		
		// Setting project
		settingProject(requirement);
		
		logger.info("Requesting persistence of requirement...");
		
		create(requirement);
		
		result.include(FieldMessage.SUCCESS.toString(), "Requirement succesfully registered.");
		result.nothing();
	}
	
	/**
	 * Setting owner({@link User}) of Requirement
	 *  
	 * @param requirement inserted by form
	 */
	private void settingOwner(Artifact requirement) {
		User logged = userSession.getUserLogged();
		
		assert(logged == null);
		
		requirement.setAuthor(logged);		
	}
	
	/**
	 * Setting project of Requirement
	 *  	 
	 * @param requirement inserted by form
	 */
	private void settingProject(Artifact requirement) {
		Long projectID = requirement.getProjectID();
		
		assert(projectID == null);

		// Load by id
		Project associatedProject = projectService.load(projectID);
		requirement.setProject(associatedProject);
	}

	/**
	 * Basic and generic validation of requirements
	 * 
	 * @param requirement to be persisted
	 */
	@Override
	public void validate(Artifact requirement) {
		logger.info("Apply basic validate in requirement");
				
		if(userSession.getUserLogged() == null) {
			logger.warn("User try create requirement without an user logged!");
			
			validator.add(new SimpleMessage(FieldMessage.ERROR.toString(), ErrorMessage.USER_NOT_LOGGED.toString()));
		} else if(requirement.getTitle() == null) {
			logger.warn("Requirement title is null...");
			
			validator.add(new SimpleMessage(FieldMessage.ERROR.toString(), ErrorMessage.USER_NOT_LOGGED.toString()));
		} else if(requirement.getProjectID() == null) {
			logger.warn("ProjectID is null!");
			
			validator.add(new SimpleMessage(FieldMessage.ERROR.toString(), PROJECT_ERROR_MESSAGE));
		}
		
    	validator.onErrorUsePageOf(UserController.class).home();
	}
	
	/**
	 * Use case creation is more specific so this method
	 * implementation is more robust than the others
	 * @param useCase
	 */
	@Post
	public void createUseCase(UseCase useCase) {		
		
		if(useCase.getFakeActors() != null) { //Main flow 
			useCase = setUseCaseActors(useCase);
		} else { //Alternative flow
			validator.add(new SimpleMessage(FieldMessage.ERROR.toString(), 
					"Use case needs at least one author"));
		}
		
    	validator.onErrorUsePageOf(UserController.class).home();
		
		save(useCase);
	}
	
	/**
	 * Sets up a String containing all the actors
	 * involved at the current use case. 
	 * @param useCase
	 */
	private UseCase setUseCaseActors (UseCase useCase) {
		
		String fakeActors[] = useCase.getFakeActors().split(","); // Separating each actor by ','
		List<Actor> actors = new ArrayList<Actor>();
		
		for(String actorName : fakeActors) {
			Actor actor = new Actor();
			actor.setName(actorName);
			actors.add(actor);
		}
		
		useCase.setActors(actors);
		
		return useCase;
	}
	
	/**
	 * Get an instance of current date through of {@link Calendar}
	 * 
	 * @return current date
	 */
	private Calendar getCurrentDate() {
		Date currentDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		
		return calendar;
	}
	
	@View
	public void showCreationResult() {
		
	}

	@View
	public void generic() {
		
	}
	
	@View
	public void storie() {
		
	}
	
	@View
	public void feature() {
		
	}
	
	@View
	public void epic() {
		
	}
	
	@View
	public void useCase() {
		
	}
}
