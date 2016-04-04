package com.modesteam.urutau.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.exception.InvalidUserActionException;
import com.modesteam.urutau.model.UrutaUser;

/**
 * Default implementation of UserDAO
 */
public class DefaultUserDAO extends GenericDAO<UrutaUser> implements UserDAO {

	private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);
	
	/* Value used to get by field */
	private static final String FIELD_VALUE = "value";

	@Inject
	private EntityManager manager;
	
	/**
	 * To inject manager into GenericDAO is required {@link Inject} annotation
	 */
	@Inject
	public DefaultUserDAO(EntityManager manager) {
		super.setEntityManager(manager);
	}
	
	/**
	 * Gets user instance from DB
	 * 
	 * @param userID
	 * @return
	 */
	public UrutaUser find(final Long userID) {
		return manager.getReference(UrutaUser.class, userID);
	}
	
	@Override
	public boolean hasAnyRegister() {
		String sql = "SELECT user FROM " + UrutaUser.class.getName() + " user";
		Query query = manager.createQuery(sql);
		
		return !query.getResultList().isEmpty();
	}

	@Override
	public UrutaUser get(String field, Object value) throws Exception {
		
		if(!isValidParameter(value)) {
			throw new InvalidUserActionException("Invalid value object");
		} else {
			// continue 
		}
		
		String sql = "SELECT user FROM " + UrutaUser.class.getName() 
				+ " user WHERE user." + field + "=:value";
		
		logger.info(sql);
		
		UrutaUser userFound = null;
		
		try {
			Query query = manager.createQuery(sql);	
			query.setParameter(FIELD_VALUE, value);
			userFound = (UrutaUser) query.getSingleResult();
		} catch (NonUniqueResultException exception) {
			throw new NonUniqueResultException();
		} catch (NoResultException exception) {
			exception.printStackTrace();
		}
		
		return userFound;
	}
	
	/**
	 * Called only by DAOs, this is a validation of parameter instance 
	 * 
	 */
	private boolean isValidParameter(Object value) {
		boolean validParameter = false;
		
		if (value instanceof Integer || value instanceof String || value instanceof Long) {
			validParameter = true;
		} 
		
		return validParameter;
	}
}
