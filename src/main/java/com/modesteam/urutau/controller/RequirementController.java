package com.modesteam.urutau.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

import javax.inject.Inject;

import org.junit.experimental.theories.Theories;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Message;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;

import com.modesteam.urutau.UserManager;
import com.modesteam.urutau.annotation.View;
import com.modesteam.urutau.model.Artifact;
import com.modesteam.urutau.model.Epic;
import com.modesteam.urutau.model.Feature;
import com.modesteam.urutau.model.Storie;
import com.modesteam.urutau.model.UseCase;
import com.modesteam.urutau.model.User;
import com.modesteam.urutau.service.RequirementService;

/**
 * This class is responsible to manager simple operations of requirements!
 * 
 */
@Controller
public class RequirementController {
	
	private static final Logger logger = LoggerFactory.getLogger(RequirementController.class);
	
	private final Result result;
	
	private final UserManager userSession;
	
	private final RequirementService requirementService;
	
	private final Validator validator;
	
	private static final String REQUIREMENT_EXCLUSION_ERROR = "requirementExclusionError";

	private static final String REQUIREMENT_MODIFICATION_ERROR = "requirementModificationError";
	
	public RequirementController() {
		this(null, null, null, null);
	}
	
	@Inject
	public RequirementController(Result result, UserManager userSession, 
			RequirementService requirementService, Validator validator) {
		this.result = result;
		this.userSession = userSession;
		this.requirementService = requirementService;
		this.validator = validator; 
	}
	
	@Post
	public void create(Artifact requirement) {
		requirement.setDateOfCreation(new Date(System.currentTimeMillis()));
		
		User logged = userSession.getUserLogged();
		requirement.setAuthor(logged);
		
		requirementService.save(requirement);
		result.redirectTo(this).create();
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
	@Path("/requirement/{id}/{title}")
	public Artifact show(int id, String title) throws UnsupportedEncodingException{
		title = URLDecoder.decode(title, "utf-8");
		
		logger.info("Show requirement " + title);
		
		Artifact requirement = requirementService.getRequirement(id, title);
		return requirement;
	}
	
	@Get
	@Path("/registerFeature")
	public void registerFeature() {
		
	}

	/**
	 * 
	 * @param feature
	 */
	@Post
	@Path("/registerFeature")
	public void registerFeature(Feature feature) {
		requirementService.save(feature);
		result.redirectTo(this).create();
	}
	
	@Get
	@Path("/registerEpic")
	public void registerEpic() {
		
	}
	
	/**
	 * 
	 * @param epic
	 */
	@Post
	@Path("/registerEpic")
	public void registerEpic(Epic epic) {
		requirementService.save(epic);
		result.redirectTo(this).create();
	}
	
	/**
	 * 
	 * @param userHistory
	 */
	@Post
	@Path("/registerUserHistory")
	public void registerUserHistory(Storie storie) {
		requirementService.save(storie);
		result.redirectTo(this).create();
	}
	
	/**
	 * 
	 * @param useCase
	 */
	@Post
	@Path("/registerUseCase")
	public void registerUseCase(UseCase useCase) {
		requirementService.save(useCase);
		result.redirectTo(this).create();
	}
	
	@Get
	@Path("/showAllRequirements")
	public void showAllRequirements() {
		
	}
	

	/**
	 * 
	 * @param requirement
	 */
	@Post
	@Path("/detailRequirement")
	public void detailRequirement(Artifact requirement) {
		// rethink method
	}
	
	@Post
	@Path("/excludeRequirement")
	public void excludeRequirement(Artifact requirement) {
		
		long requirementId = requirement.getId();
		
		requirementService.excludeRequirement(requirement);
		
		boolean requirementExistence = requirementService.verifyRequirementExistence(requirementId);
		
		if(requirementExistence == false) {
			logger.info("The requirement was succesfully excluded.");
			result.redirectTo(this).showExclusionResult(requirement.getTitle());
		}else {
			logger.info("The requirement wasn't excluded yet.");
			validator.add(new SimpleMessage(REQUIREMENT_EXCLUSION_ERROR, "Não foi possível excluir o requisito solicitado."));	
			
		}
		
	}

	@View
	public void detailRequirement() {
		
	}
	
	@View
	@Get
	public void create() {
		
	}
	
	@View
	@Get	
	public void showExclusionResult(String requirementTitle) {
		
	}
	
	/**
	 * Allows the modification of an unique artifact
	 * @param artifact
	 */
	@Post
	@Path("/modifyRequirement")
	public void modifyRequirement(Artifact artifact) {		
		 
		boolean updateResult = requirementService.modifyRequirement(artifact);
		
		if(updateResult == true) {
			result.redirectTo(this).detailRequirement(artifact);
		} else {
			validator.add(new SimpleMessage(REQUIREMENT_MODIFICATION_ERROR, "Não foi possível alterar o requisito solicitado"));
			result.redirectTo(this).detailRequirement(artifact);
		}
	}
	
	

}
