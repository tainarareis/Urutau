package com.modesteam.urutau.controller;

import java.util.ArrayList;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;

import com.modesteam.urutau.annotation.View;
import com.modesteam.urutau.model.Artifact;
import com.modesteam.urutau.model.Storie;
import com.modesteam.urutau.model.UseCase;
import com.modesteam.urutau.service.RequirementService;

/**
 * This class is responsable to manager simple operations of requirements!
 * 
 */
@Controller
public class RequirementController {
	
	private Result result;
	
	private RequirementService requirementService;
	
	public RequirementController() {
		this(null, null);
	}
	
	@Inject
	public RequirementController(Result result, RequirementService requirementService) {
		this.result = result;
		this.requirementService = requirementService;
	}
	
	@Post("/registerRequirement")
	public void registerRequirement(Artifact requirement) {
		requirementService.save(requirement);
		result.redirectTo(this).register();
	}
	
	@Post
	@Path("/registerUserHistory")
	public void registerUserHistory(Storie storie) {
		requirementService.save(storie);
		result.redirectTo(this).register();
	}
	
	@Post
	@Path("/registerUseCase")
	public void registerUseCase(UseCase useCase) {
		requirementService.save(useCase);
		result.redirectTo(this).register();
	}
	
	@Get
	@Path("/showAllRequirements")
	public void showAllRequirements() {
		ArrayList<Artifact> generics = null;
		ArrayList<Artifact> useCases = null;
		ArrayList<Artifact> userHistories = null;	
		// An new object generic		
		//generics = requirementService.load();
		useCases = requirementService.loadAll(UseCase.class.getName());
		userHistories = requirementService.loadAll(Storie.class.getName());
		// Usar logger!
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
		// rethink method
	}
	
	@View
	public void detailRequirement() {
		
	}
	@View
	public void register() {
		
	}

}
