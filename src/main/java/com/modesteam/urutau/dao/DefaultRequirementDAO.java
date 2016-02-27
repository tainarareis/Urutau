package com.modesteam.urutau.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.exception.InvalidUserActionException;
import com.modesteam.urutau.model.Artifact;

/**
 * Default implementation of RequirementDAO
 */
public class DefaultRequirementDAO extends GenericDAO<Artifact> implements RequirementDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(RequirementDAO.class.getName());
	
	@Inject
	private EntityManager manager;
	
	public DefaultRequirementDAO() {
	
	}
	
	/**
	 * To inject manager into GenericDAO is required {@link Inject} annotation
	 */
	@Inject
	public DefaultRequirementDAO(EntityManager manager){
		super.setEntityManager(manager);
	}
	
	
	/**
	 * Captures a single artifact if it exists in database
	 * @param field - identifies the object, is used to search for it
	 * @param value - object  
	 * @throws InvalidUserActionException 
	 */
	@Override
	public Artifact get(final String field, final Object value) throws InvalidUserActionException {
		if(!DaoValidator.isValidParameter(value)){
			throw new InvalidUserActionException("Get method was called with invalid value");
		} else {
			// continue
		}
		
		// Select a child of artifact
		String sql = "SELECT requirement FROM "+ Artifact.class.getName() +" requirement"
				+ " WHERE requirement." + field + "=:value";
		
		logger.info(sql);
		
		Artifact artifact = null;
		
		try {
			Query query = manager.createQuery(sql);
			query.setParameter("value", value);
			artifact = (Artifact) query.getSingleResult();
		} catch (NonUniqueResultException exception){
			throw new NonUniqueResultException();
		} catch (NoResultException exception) {
			exception.printStackTrace();
		}
		
		return artifact;
	}

	@Override
	public Artifact find(final Long id) {
		logger.info("The Artifact id is " + id);
		
		return manager.find(Artifact.class, id);
	}
	
	/**
	 * Get into project, your requirements from firstResult until the sum of maxResult
	 *  
	 * @return list of {@link Artifact} that contain only an delimited quantity of results
	 */
	public List<Artifact> getRequirementBetweenInterval(final Long projectID, 
			final int firstResult, final int maxResult) {
		
		logger.info("Get requirement between " + firstResult + " and " 
				+ (firstResult+maxResult));
		
		String sql = "SELECT requirement FROM "+ Artifact.class.getName() + " requirement"
				+ " JOIN FETCH  requirement.project project"
				+ " WHERE project.id=:projectID"
				+ " ORDER BY requirement.dateOfCreation DESC";
		
		logger.info(sql);

		Query query = manager.createQuery(sql);
		
		query.setParameter("projectID", projectID);
		
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResult);
		
		@SuppressWarnings("unchecked")
		List<Artifact> results = query.getResultList();
		
		logger.info("Number of results is " + results.size());
		
		return results; 
	}

}
