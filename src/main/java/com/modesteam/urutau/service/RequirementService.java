package com.modesteam.urutau.service;

import java.util.ArrayList;

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

	public ArrayList<Artifact> loadAll(String type) {
		return requirementDAO.loadAll(type);
	}

	public Artifact detail(long id) {
		return requirementDAO.find(id);
	}
}
