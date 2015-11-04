package com.modesteam.urutau.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;

import com.modesteam.urutau.UserManager;
import com.modesteam.urutau.annotation.View;
import com.modesteam.urutau.model.Artifact;
import com.modesteam.urutau.model.Epic;
import com.modesteam.urutau.model.Feature;
import com.modesteam.urutau.model.Generic;
import com.modesteam.urutau.model.Storie;
import com.modesteam.urutau.model.UseCase;
import com.modesteam.urutau.model.User;
import com.modesteam.urutau.service.RequirementService;

/**
 * This class is responsible to manager simple operations of requirements!
 * The systems operations are received by the path /requirement followed
 * by the operation defined path.
 */
@Controller
public class RequirementController {
	
	private static final Logger logger = LoggerFactory.getLogger(RequirementController.class);

	private static final String REQUIREMENT_EXCLUSION_ERROR = "requirementExclusionError";
	private static final String REQUIREMENT_MODIFICATION_ERROR = "requirementModificationError";

	private static final String NULL_INFORMATION_ERROR = "nullInformationError";
	
	private final Result result;
	
	private final UserManager userSession;
	
	private final RequirementService requirementService;
	
	private final Validator validator;
	
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
	public void createGeneric(Generic generic) {
		create(generic);
	}
	
	@Post
	public void createUseCase(UseCase useCase) {
		create(useCase);
	}
	
	@Post
	public void createFeature(Feature feature) {
		create(feature);
	}
	
	private void create(Artifact requirement) {
		logger.info("Requirement will be persisted: " + requirement.getTitle());
		
		Date currentDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		
		requirement.setDateOfCreation(calendar);
		
		User logged = userSession.getUserLogged();
		requirement.setAuthor(logged);
		
		logger.info("Requesting for requirement service");
		requirementService.save(requirement);
		
		result.redirectTo(this).showCreationResult(requirement.getId());
	}
	
	/**
	 * Presents the informations about the result requirement's creation.
	 * @param requirementId
	 */
	@Get
	public void showCreationResult(long requirementId) {
		
		boolean isCreated = requirementService.verifyRequirementExistence(requirementId);
		
		logger.info("Showing the result of the creation");
		
		if(isCreated){
			result.include("message", "O requisito foi cadastrado com sucesso.");
		} else {			
			result.include("message", "Não foi possível registrar o requisito solicitado."
				+ "Por gentileza, tente novamente");
		}
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
	@Path("/{id}/{title}")
	public Artifact show(int id, String title) throws UnsupportedEncodingException{
		title = URLDecoder.decode(title, "utf-8");
		
		logger.info("Show requirement " + title);
		
		Artifact requirement = requirementService.getRequirement(id, title);
		
		return requirement;
	}
	
	/**
	 * Used to present a requirement in the view.
	 * @param id
	 * @return a requirement. 
	 */
	@Get
	public Artifact showRequirementById(int id){
			
		logger.info("Requirement id = " + id);
		
		Artifact requirement = requirementService.getRequirementById(id);
		return requirement;
	}
	
	@Get("/showAll")
	public List<? extends Artifact> showAllRequirements() {		
		logger.info("Starting the requisition for all requirements");
		
		List<? extends Artifact> requirements  = requirementService.loadAllRequirements();
		
		logger.info("Have " + requirements.size() + "requirements");
		
		result.include("artifact", requirements);
		
		return requirements;
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
	
	/**
	 * This method is used to delete one requirement
	 * @param requirementId
	 */
	@Delete
	@Path("/excludeRequirement/{id}")
	public void excludeRequirement(Long requirementId) {
		
		
		logger.info("The requirement with the id " +requirementId + " is solicitated for exclusion.");
		
		requirementService.excludeRequirement(requirementId);
		
		boolean requirementExistence = requirementService.verifyRequirementExistence(requirementId);
		
		if(!requirementExistence) {
			logger.info("The requirement was succesfully excluded.");
			result.redirectTo(this).showAllRequirements();
		}else {
			logger.info("The requirement wasn't excluded yet.");
			validator.add(new SimpleMessage(REQUIREMENT_EXCLUSION_ERROR, "Não foi possível excluir o requisito solicitado."));	
			validator.onErrorForwardTo(this).showAllRequirements();
		}
		
	}
	
	@Get
	@Path("/editRequirement/{id}")
	public Artifact requestRequirementEdition(Long requirementID) {
		
		logger.info("Starting the function requestRequirementEdition. The requirement id is: "+requirementID);
		
		//If to guarantee the parameter received isn't null
		if(requirementID == null) {
			validator.add(new SimpleMessage(NULL_INFORMATION_ERROR, "Nenhum requisito foi passado como parâmetro."));
			validator.onErrorForwardTo(RequirementController.class).showAllRequirements();
		}else {		
			boolean requirementExistence = requirementService.verifyRequirementExistence(requirementID);
			
			if(requirementExistence) {
				Artifact requirement = requirementService.detail(requirementID);
				result.include("artifact", requirement);			
				return requirement;
			} else {
				logger.info("The requirement id is unknown.");
				validator.add(new SimpleMessage(REQUIREMENT_MODIFICATION_ERROR, "Não é possível alterar"
						+ " o requisito solicitado pois o mesmo não existe no sistema."));
				result.redirectTo(this).showAllRequirements();		
				return null;			
			}
		}
		return null;
	}
	
	/**
	 * Allows the modification of an unique artifact
	 * @param 
	 */
	@Post
	public void modifyRequirement(Artifact requirement) {		
		
		boolean updateResult = requirementService.modifyRequirement(requirement);
		
		if(updateResult){
			logger.info("The update was sucessfully executed.");
		}else{
			logger.info("The update wasn't sucessfully executed.");
		}
		
		result.redirectTo(this).showAllRequirements();
	
	}


	@View
	public void detailRequirement() {
		
	}
	
	@View
	public void create() {
		
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
	
	@View
	public void showExclusionResult() {
		
	}
	
}
