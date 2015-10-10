package com.modesteam.urutau.service;

import java.util.List;

import javax.inject.Inject;

import com.modesteam.urutau.dao.RequirementDAO;
import com.modesteam.urutau.model.Artifact;

public class RequirementService {
	private RequirementDAO requirementDAO;
	
	public RequirementService() {
		this(null);
	}
	
	@Inject
	public RequirementService(RequirementDAO requirementDAO) {
		this.requirementDAO = requirementDAO;
	}

	public void save(Artifact requirement) {
		requirementDAO.create(requirement);
	}

	public List<? extends Artifact> loadAll(String type) {
		return requirementDAO.loadAll(type);
	}

	public Artifact detail(long id) {
		return requirementDAO.find(id);
	}
	/**
	 * Returns an requirement getted by title that have an certly id
	 * 
	 * @param id unique
	 * @param title name of Requirement, an usual identificator, but not unique
	 * @return an requirement
	 */
	public Artifact getRequirement(int id, String title) {
		Artifact requirement = requirementDAO.get("title", title);
		
		if (requirement.getId() == new Long(id)) {
			return requirement;
		} else {
			throw new IllegalArgumentException("This requirement not exist");
		}
	}
	
	public void excludeRequirement(Artifact requirement){
		requirementDAO.destroy(requirement);
	}
}
