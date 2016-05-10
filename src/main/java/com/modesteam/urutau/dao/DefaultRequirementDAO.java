package com.modesteam.urutau.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.model.Artifact;

/**
 * Default implementation of RequirementDAO
 */
public class DefaultRequirementDAO extends GenericDAO<Artifact> implements RequirementDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(RequirementDAO.class.getName());

	private final EntityManager manager;
	
	/**
	 * @deprecated CDI only
	 */
	public DefaultRequirementDAO() {
	    this(null);
	}
	
	/**
	 * To inject manager into GenericDAO is required {@link Inject} annotation
	 */
	@Inject
	public DefaultRequirementDAO(EntityManager manager) {
	    this.manager = manager;
		super.setEntityManager(manager);
	}

    @Override
	public Artifact find(final Long id) {		
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
		
		List<Artifact> results = query.getResultList();
		
		logger.info("Number of results is " + results.size());
		
		return results; 
	}
}
