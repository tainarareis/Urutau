package com.modesteam.urutau.controller;

import java.util.ArrayList;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;

import com.modesteam.urutau.RequirementManager;
import com.modesteam.urutau.dao.RequirementDAO;
import com.modesteam.urutau.model.Artifact;
import com.modesteam.urutau.model.Storie;
import com.modesteam.urutau.model.UseCase;
import com.mysql.jdbc.log.Log;

@Controller
public class RequirementController {
	
	private static final Logger logger = LoggerFactory.getLogger(RequirementController.class);
	
	@Inject
	private Result result;
	@Inject
	private RequirementDAO requirementDAO;
	@Inject
	private RequirementManager requirementManager;
	
	@Get
	@Path("/registerRequirement")
	public void registerRequirement() {
		
	}
	/**
	 * 
	 * @param requirement
	 */
	@Post
	@Path("/registerRequirement")
	public void registerRequirement(Artifact requirement) {
		requirementDAO.create(requirement);
		result.redirectTo(this).registerRequirement();
	}
	
	/**
	 * 
	 * @param userHistory
	 */
	@Post
	@Path("/registerUserHistory")
	public void registerUserHistory(Storie userHistory) {
		requirementDAO.create(userHistory);
		result.redirectTo(this).registerRequirement();
	}
	
	/**
	 * 
	 * @param useCase
	 */
	@Post
	@Path("/registerUseCase")
	public void registerUseCase(UseCase useCase) {
		requirementDAO.create(useCase);
		result.redirectTo(this).registerRequirement();
	}
	
	@Get
	@Path("/showAllRequirements")
	public void showAllRequirements() {
		ArrayList<Artifact> generics = null;
		ArrayList<Artifact> useCases = null;
		ArrayList<Artifact> userHistories = null;
		generics = requirementDAO.loadGenerics();
		useCases = requirementDAO.loadUseCases();
		userHistories = requirementDAO.loadUserHistories();
		logger.info("load all requirements");
		result.include("generics",generics);
		result.include("useCases",useCases);
		result.include("userHistories",userHistories);
	}
	

	/**
	 * 
	 * @param requirement
	 */
	@Post
	@Path("/detailRequirement")
	public void detailRequirement(Artifact requirement) {
		requirement = requirementDAO.find(requirement.getId());
		requirementManager.setRequirement(requirement);
		result.redirectTo(this).detailRequirement();
	}
	
	@Get
	@Path("/detailRequirement")
	public void detailRequirement() {
		logger.info(requirementManager.getRequirement().getTitle());
		result.include("requirement",requirementManager.getRequirement());
	}

}
