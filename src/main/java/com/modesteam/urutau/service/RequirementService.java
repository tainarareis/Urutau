package com.modesteam.urutau.service;

import java.io.UnsupportedEncodingException;
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
	
	/**
	 * Returns a requirement caught by title that have a certain id
	 * 
	 * @param id unique
	 * @param title name of Requirement, an usual identifier, but not unique
	 * @return a requirement
	 */
	public Artifact getRequirement(int id, String title) throws UnsupportedEncodingException {
		assert(id < 0 || title == null);
		
		Artifact requirement = requirementDAO.find(new Long(id));
		
		logger.info("Decoded title is " + title);

		// Compares decoded title with instance of database
		if (requirement.getTitle().equalsIgnoreCase(title)) {
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
	public void delete(Long requirementId) {
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
	public boolean verifyExistence(long requirementId) {
		
		logger.info("Verifying the requirement existence in database.");
		
		Artifact requirement = null;
		try {
			requirement = requirementDAO.get("id", requirementId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (requirement == null) {
			logger.info("The requirement is null");
			return false;
		} else {
			logger.info("The requirement isn't null");
			return true;
		}
	}
	
	public boolean create(Artifact requirement) {
		return requirementDAO.create(requirement);
	}
	
	/** 
	 * @param artifact
	 * @return
	 */
	public boolean update(Artifact requirement){		
		boolean updateResult = requirementDAO.update(requirement);		
		return updateResult;
	}

	/**
	 * Captures a unique requirement based on its id
	 * @param id of the requirement
	 * @return a requirement
	 */
	public Artifact getByID(Long id) {
		
		logger.info("Starting DAO search.");
		
		Artifact requirement = requirementDAO.find(id);
		
		if (requirement != null) {
			logger.info("RequirementDAO returned zero requirements.");
			return requirement;
		} else {
			throw new IllegalArgumentException("The requirement does not exist.");
		}
	}
	/**
	 * Get the list of requirement into defined interval
	 * 
	 * @param projectID belongs to current project
	 * @param quantity number of results search
	 * @param page current index of page
	 * 
	 * @return an List of {@link Artifact} delimited through quantity and page. 
	 */
	public List<Artifact> recover(long projectID, int quantity, int page) {
		int firstResult = quantity * page;
		
		return requirementDAO.getRequirementBetweenInterval(projectID, firstResult, quantity);
	}
	
}
