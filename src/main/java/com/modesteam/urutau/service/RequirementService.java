package com.modesteam.urutau.service;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.dao.RequirementDAO;
import com.modesteam.urutau.model.Artifact;

public class RequirementService {
	private RequirementDAO requirementDAO;
	private static final Logger logger = LoggerFactory.getLogger(RequirementService.class);
	
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

	public List<? extends Artifact> loadAllRequirements() {
		return requirementDAO.loadAllRequirements();
	}

	public Artifact detail(long id) {
		return requirementDAO.find(id);
	}
	/**
	 * Returns a requirement caught by title that have a certain id
	 * 
	 * @param id unique
	 * @param title name of Requirement, an usual identifier, but not unique
	 * @return a requirement
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
	public void excludeRequirement(Long requirementId) {
		if( requirementId != null ) {
			Artifact requirement = requirementDAO.find(requirementId);
			
			if(requirement != null) {
				requirementDAO.destroy(requirement);
			} else {
				throw new IllegalArgumentException("This requirement not exist");
			}
		} else {
			logger.info("RequirementService cant find requirementID");
		}
	}
	
	/**
	 * Verifies the existence of a requirement by its id
	 * @param requirementId
	 * @return true if the requirement exists
	 */
	public boolean verifyRequirementExistence(long requirementId) {
		
		logger.info("Verifying the requirement existence in database.");
		
		Artifact requirement = requirementDAO.get("id", requirementId);
		
		if (requirement == null) {
			logger.info("The requirement is null");
			return false;
		} else {
			logger.info("The requirement isn't null");
			return true;
		}
		
	}
	/**
	 * Modifies an artifact characteristics 
	 * @param artifact
	 * @return
	 */
	public boolean modifyRequirement(Artifact requirement){		
		boolean updateResult = requirementDAO.update(requirement);		
		return updateResult;
	}

	/**
	 * Captures a unique requirement based on its id
	 * @param id of the requirement
	 * @return a requirement
	 */
	public Artifact getRequirementById(int id) {
		
		logger.info("Starting DAO search.");
		
		Artifact requirement = requirementDAO.get("id", id);
		
		if (requirement != null) {
			logger.info("RequirementDAO returned zero requirements.");
			return requirement;
		} else {
			throw new IllegalArgumentException("The requirement does not exist.");
		}
	}
	
}
