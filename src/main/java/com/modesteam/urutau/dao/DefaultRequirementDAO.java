package com.modesteam.urutau.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
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

	private final DaoHelper daoHelper;
	
	/**
	 * To inject manager into GenericDAO is required {@link Inject} annotation
	 */
	@Inject
	public DefaultRequirementDAO(EntityManager manager, DaoHelper helper) {
	    this.manager = manager;
	    this.daoHelper = helper;
		super.setEntityManager(manager);
	}
	
	
	@Override
	public Artifact get(final String field, final Object value) {
	    Artifact artifact = null;

	    if(daoHelper.isValidParameter(value)) {
	        try {
	            final String sql = daoHelper.getSelectQuery(Artifact.class, field);
	            Query query = manager.createQuery(sql);
	            query.setParameter("value", value);
	            
	            artifact = (Artifact) query.getSingleResult();
	        } catch (NonUniqueResultException exception){
	            throw new NonUniqueResultException();
	        } catch (NoResultException exception) {
	            exception.printStackTrace();
	        }
		} else {
		    throw new IllegalArgumentException("Invalid param has been passed");
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
		
		List<Artifact> results = query.getResultList();
		
		logger.info("Number of results is " + results.size());
		
		return results; 
	}
}
