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
import com.modesteam.urutau.exception.ActionException;
import com.modesteam.urutau.model.Actor;
import com.modesteam.urutau.model.Artifact;
import com.modesteam.urutau.model.Epic;
import com.modesteam.urutau.model.Feature;
import com.modesteam.urutau.model.Generic;
import com.modesteam.urutau.model.Storie;
import com.modesteam.urutau.model.UseCase;
import com.modesteam.urutau.model.User;

/**
 * This is an concrete implementation of {@link EntityCreator}, part of pattern abstract factory 
 * He was born to reduce the coupling of {@link RequirementController}, 
 * and increase your cohesion.
 */
@Controller
@Path("/requirement")
public class RequirementCreator extends EntityCreator<Artifact> {

	private static final Logger logger = LoggerFactory.getLogger(RequirementCreator.class);
	
	// Error treatment
	private static final String ERROR_FIELD = "errorField";
	private static final String ERROR_MESSAGE = "Title not be null.";
	
	private final Result result;
	private final Validator validator;
	private final UserSession userSession;
	
	@Inject
	public RequirementCreator(Result result, Validator validator, 
			RequirementDAO requirementDAO, UserSession userSession) {
		this.result = result;
		this.validator = validator;
		this.userSession = userSession;
		
		super.setDao(requirementDAO);
	}
	

	@Post
	public void createGeneric(Generic generic) {
		save(generic);
	}
	
	@Post
	public void createUseCase(UseCase useCase) {		
		// Validate actors
		if(useCase.getFakeActors() == null) {
			validator.add(new SimpleMessage(ERROR_FIELD, "Use case needs at least one author"));
		} else {
			// Separate each actors by ','
			String fakeActors[] = useCase.getFakeActors().split(",");
			List<Actor> actors = new ArrayList<Actor>();
			
			for(String actorName : fakeActors) {
				Actor actor = new Actor();
				actor.setName(actorName);
				actors.add(actor);
			}
			
			useCase.setActors(actors);
		}
		
    	validator.onErrorUsePageOf(UserController.class).home();
		
		save(useCase);
	}
	
	@Post
	public void createFeature(Feature feature) {
		save(feature);
	}
	
	@Post
	public void createUserStory(Storie storie) {
		save(storie);
	}

	@Post
	public void createEpic(Epic epic) {
		save(epic);
	}
	
	/**
	 * Basic and generic validation of requirements
	 * 
	 * @param requirement to be persisted
	 */
	@Override
	protected void validate(Artifact requirement) {
		logger.info("Apply basic validate in requirement");
				
		if(userSession.getUserLogged() == null) {
			logger.warn("User try create requirement without an user logged!");
			throw new ActionException();			
		} else if(requirement.getTitle() == null) {
			logger.debug("Title or description are wrong!");
			
			validator.add(new SimpleMessage(ERROR_FIELD, ERROR_MESSAGE));
		}
		
    	validator.onErrorUsePageOf(UserController.class).home();
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
		
		Calendar calendar = getCurrentDate();
		requirement.setDateOfCreation(calendar);
		
		User logged = userSession.getUserLogged();
		requirement.setAuthor(logged);
		
		logger.info("Requesting persistence of requirement...");
		
		create(requirement);
		
		result.redirectTo(UserController.class).home();
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
