package com.modesteam.urutau.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.UserSession;
import com.modesteam.urutau.annotation.View;
import com.modesteam.urutau.exception.SystemBreakException;
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
import com.modesteam.urutau.model.system.Layer;
import com.modesteam.urutau.service.KanbanService;
import com.modesteam.urutau.service.ProjectService;
import com.modesteam.urutau.service.RequirementService;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;

/**
 * This is a implementation of {@link EntityCreator}, 
 * part of pattern abstract factory 
 * He was born to reduce the coupling and 
 * increase your cohesion of {@link RequirementController}, 
 */
@Controller
@Path("/requirement")
public class RequirementCreator implements EntityCreator<Artifact> {

	private static final Logger logger = LoggerFactory.getLogger(RequirementCreator.class);

	private static final String PROJECT_ERROR_MESSAGE = "Project id was not passed!";

	private static final String PROJECT_ID_INPUT_VALUE = "projectID";

	//Objects to be injected
	private final Result result;
	private final Validator validator;
	private final UserSession userSession;
	private final ProjectService projectService;
	private final RequirementService requirementService;
	private final KanbanService kanbanService;

	public RequirementCreator() {
		this(null, null, null, null, null, null);
	}
	
	/**
	 * The Superclass {@link EntityCreator} requires an DAO to work
	 */
	@Inject
	public RequirementCreator(Result result, Validator validator, 
			UserSession userSession, ProjectService projectService, 
			RequirementService requirementService, KanbanService kanbanService) {
		this.result = result;
		this.validator = validator;
		this.userSession = userSession;
		this.projectService = projectService;
		this.requirementService = requirementService;
		this.kanbanService = kanbanService;
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
	 * Called by all methods that makes requirements creation,
	 * this have basic implementation to persisted.
	 * IMPORTANT: validate before call this method through by 
	 * {@link #validate()}
	 * 
	 * @param requirement to be persisted
	 * @throws UnsupportedEncodingException 
	 * @throws NumberFormatException 
	 */
	private void save(Artifact requirement) {
		
		logger.info("Try persist " + requirement.getTitle());
		
		// Setting current date
		Calendar calendar = getCurrentDate();
		requirement.setDateOfCreation(calendar);
		
		// Setting author
		requirement.setAuthor(getCurrentAuthor());
		
		// Setting project
		final Long projectID = requirement.getProjectID();
		requirement.setProject(getCurrentProject(projectID));
		
		// Setting to Backlog Layer
		Layer layer = kanbanService.getBackLogLayer();
		requirement.setLayer(layer);
		
		logger.info("Requesting persistence of requirement...");
		
		validate(requirement);
		
		create(requirement);
	
		try {
			Project currentProject = requirement.getProject();
			
			logger.debug("Project ID is " + currentProject.getId());
			
			// Show success message
			result.include(FieldMessage.SUCCESS.toString(), "Requirement added with sucessful!");
			
			result.redirectTo(ProjectController.class)
				.show(currentProject);
		} catch (NumberFormatException | UnsupportedEncodingException e) {
			throw new SystemBreakException();
		}
	}
	
	/**
	 * Get owner({@link User}) of Requirement
	 *  
	 * @param requirement inserted by form
	 * @return 
	 */
	private User getCurrentAuthor() {
		User logged = userSession.getUserLogged();
		
		assert(logged == null);
		
		return logged;
	}
	
	/**
	 * Setting project of Requirement
	 *  	 
	 * @param requirement inserted by form
	 * @return 
	 */
	private Project getCurrentProject(final Long projectID) {
		
		assert(projectID == null);

		// Load by id
		Project referedProject = projectService.getByID(projectID);
		
		return referedProject;
	}

	@Override
	public boolean validate(final Artifact requirement) {
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
		return false;
	}
	
	@Override
	public boolean create(final Artifact requirement) {
		return requirementService.create(requirement);
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
	
	/**
	 * This projectID is a reference of the project 
	 * which the requirement will be created. 
	 * All part of url is created through a javascript into home.jsp, 
	 * projectID number will be included into view page to fill a input hidden.
	 * 
	 * @param projectID Truly is an Long number, converted into script to 
	 * be changed foward to Long again.
	 */
	@View
	@Get("generic/{projectID}")
	public void generic(String projectID) {
		result.include(PROJECT_ID_INPUT_VALUE, projectID);
	}
	
	/**
	 * See {@link RequirementCreator#generic(String)}
	 */
	@View
	@Get("storie/{projectID}")
	public void storie(String projectID) {
		result.include(PROJECT_ID_INPUT_VALUE, projectID);		
	}
	
	/**
	 * See {@link RequirementCreator#generic(String)}
	 */
	@View
	@Get("feature/{projectID}")
	public void feature(String projectID) {
		result.include(PROJECT_ID_INPUT_VALUE, projectID);
	}
	
	/**
	 * See {@link RequirementCreator#generic(String)}
	 */
	@View
	@Get("epic/{projectID}")
	public void epic(String projectID) {
		result.include(PROJECT_ID_INPUT_VALUE, projectID);
	}
	
	/**
	 * See {@link RequirementCreator#generic(String)}
	 */
	@View
	@Get("useCase/{projectID}")
	public void useCase(String projectID) {
		result.include(PROJECT_ID_INPUT_VALUE, projectID);
	}
}