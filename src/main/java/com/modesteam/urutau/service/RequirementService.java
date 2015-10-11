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
	 * Returns a requirement caught by title that have a certain id
	 * 
	 * @param id unique
	 * @param title name of Requirement, an usual identifier, but not unique
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
	
	/**
	 * Method to communicate with the DAO
	 * asking for the requirement exclusion from database.
	 * @param requirement
	 */
	public void excludeRequirement(Artifact requirement) {
		requirementDAO.destroy(requirement);
	}
	
	/**
	 * Verifies the existence of a requirement by its id
	 * @param requirementId
	 * @return true if the requirement exists
	 */
	public boolean verifyRequirementExistence(long requirementId) {
		Artifact requirement = requirementDAO.get("id", requirementId);
		
		if (requirement == null) {
			return false;
		} else {
			return true;
		}
		
	}
	
	public boolean modifyRequirement(Artifact artifact){
		boolean updateResult = requirementDAO.update(artifact);		
		return updateResult;
	}
	
}
