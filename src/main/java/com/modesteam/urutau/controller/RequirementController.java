package com.modesteam.urutau.controller;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.modesteam.urutau.dao.RequirementDAO;
import com.modesteam.urutau.model.Requirement;
import com.modesteam.urutau.model.UseCase;
import com.modesteam.urutau.model.UserHistory;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;

@Controller
public class RequirementController {
	
	@Inject
	private Result result;
	@Inject
	private RequirementDAO requirementDAO;
	
	@Get
	@Path("/registerRequirement")
	public void registerRequirement() {
		
	}
	@Post
	@Path("/registerRequirement")
	public void registerRequirement(Requirement requirement) {
		requirementDAO.saveGeneric(requirement);
		result.redirectTo(this).registerRequirement();
	}
	
	@Post
	@Path("/registerUserHistory")
	public void registerUserHistory(UserHistory userHistory) {
		requirementDAO.saveUserHistory(userHistory);
		result.redirectTo(this).registerRequirement();
	}
	
	@Post
	@Path("/registerUseCase")
	public void registerUseCase(UseCase useCase) {
		requirementDAO.saveUseCase(useCase);
		result.redirectTo(this).registerRequirement();
	}
	
	@Get
	@Path("/showAllRequirements")
	public void showAllRequirements() {
		ArrayList<Requirement> requirements = null;
		requirements = requirementDAO.loadAll();
		result.include("requirements",requirements);
	}
	
}
