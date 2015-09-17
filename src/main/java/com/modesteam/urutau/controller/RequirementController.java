package com.modesteam.urutau.controller;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.modesteam.urutau.dao.RequirementDAO;
import com.modesteam.urutau.model.Requirement;

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
		requirementDAO.save(requirement);
		result.redirectTo(this).registerRequirement();
	}
	

}
