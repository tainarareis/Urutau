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
import com.modesteam.urutau.model.Epic;
import com.modesteam.urutau.model.Feature;
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
		result.redirectTo(this).register();
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
		result.redirectTo(this).register();
	}
	
	/**
	 * 
	 * @param userHistory
	 */
	@Post
	@Path("/registerUserHistory")
	public void registerUserHistory(Storie storie) {
		requirementService.save(storie);
		result.redirectTo(this).register();
	}
	
	/**
	 * 
	 * @param useCase
	 */
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
		useCases = (ArrayList<Artifact>) requirementService.loadAll(UseCase.class.getName());
		userHistories = (ArrayList<Artifact>) requirementService.loadAll(Storie.class.getName());
		// Usar logger!
		System.out.println(generics.get(1).getTitle());
		System.out.println(useCases.get(1).getTitle());
		System.out.println(userHistories.get(1).getTitle());
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
		// rethink method
	}
	
	@View
	public void detailRequirement() {
		
	}
	@View
	public void register() {
		
	}

}
