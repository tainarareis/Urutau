package com.modesteam.urutau.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.model.Artifact;
import com.modesteam.urutau.service.GenericDAO;

/**
 * File Name: 
 * Accesses the database related to the Requirements.
 */
@RequestScoped
public class RequirementDAO extends GenericDAO<Artifact> {
	
	private static final Logger logger = LoggerFactory.getLogger(RequirementDAO.class.getName());
	
	@Inject
	private EntityManager manager;
	
	public RequirementDAO() {
		
	}
	
	@Inject
	public RequirementDAO(EntityManager manager){
		super.setEntityManager(manager);
	}
	
	
	/**
	 * Captures a single artifact if it exists in database
	 * @param field - identifies the object, is used to search for it
	 * @param value - object  
	 */
	@Override
	public Artifact get(String field, Object value) {
		logger.debug("field." + field + "=" + value);
		
		// Select a child of artifact
		String sql = "SELECT requirement FROM "+ Artifact.class.getName() +" requirement"
				+ " WHERE requirement." + field + "=:value";
		
		logger.info(sql);
		
		try {
			// Run sql
			Query query = manager.createQuery(sql);
			query.setParameter("value", value);
			return (Artifact) query.getSingleResult();
		} catch (NonUniqueResultException exception){
			throw new NonUniqueResultException();
		} catch (NoResultException exception) {
			return null;
		}
	}

	@Override
	public Artifact find(Long id) {
		logger.info("The Artifact id is:" +id);
		Artifact artifact = manager.find(Artifact.class, id);
		logger.info("The Artifact find is:" +artifact);
		return artifact;
	}
	
	/**
	 * Get into project, your requirements from firstResult until the sum of maxResult
	 *  
	 * @return list of {@link Artifact} that contain only an delimited quantity of results
	 */
	public List<Artifact> getIntoProjectInInterval(Long projectID, int firstResult, int maxResult) {
		logger.info("Get requirement between " + firstResult + " and " 
				+ (firstResult+maxResult));
		
		String sql = "SELECT requirement FROM "+ Artifact.class.getName() + " requirement "
				+ "JOIN FETCH  requirement.project project WHERE project.projectID=:projectID";

		Query query = manager.createQuery(sql);
		query.setParameter("projectID", projectID);
		
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResult);
		
		List<Artifact> results = query.getResultList();
		logger.info("Number of results is " + results.size());
		
		return results; 
	}
		
}