package com.modesteam.urutau.controller;

import java.util.ArrayList;

import javax.inject.Inject;

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

@Controller
public class RequirementController {
	
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
	@Post
	@Path("/registerRequirement")
	public void registerRequirement(Artifact requirement) {
		requirementDAO.saveGeneric(requirement);
		result.redirectTo(this).registerRequirement();
	}
	
	@Post
	@Path("/registerUserHistory")
	public void registerUserHistory(Storie userHistory) {
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
		ArrayList<Artifact> generics = null;
		ArrayList<Artifact> useCases = null;
		ArrayList<Artifact> userHistories = null;
		generics = requirementDAO.loadGenerics();
		useCases = requirementDAO.loadUseCases();
		userHistories = requirementDAO.loadUserHistories();
		System.out.println(generics.get(1).getTitle());
		System.out.println(useCases.get(1).getTitle());
		System.out.println(userHistories.get(1).getTitle());
		result.include("generics",generics);
		result.include("useCases",useCases);
		result.include("userHistories",userHistories);
	}
	

	
	@Post
	@Path("/detailRequirement")
	public void detailRequirement(Artifact requirement) {
		requirement = requirementDAO.detail(requirement.getId());
		requirementManager.setRequirement(requirement);
		result.redirectTo(this).detailRequirement();
	}
	
	@Get
	@Path("/detailRequirement")
	public void detailRequirement() {
		System.out.println(requirementManager.getRequirement().getTitle());
		System.out.println(requirementManager.getRequirement().getId());
		result.include("requirement",requirementManager.getRequirement());
	}

}
