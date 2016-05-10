package com.modesteam.urutau.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.modesteam.urutau.model.UrutaUser;

/**
 * Default implementation of UserDAO
 */
public class DefaultUserDAO extends GenericDAO<UrutaUser> implements UserDAO {

	/* Value used to get by field */
	private static final String FIELD_VALUE = "value";

	private final EntityManager manager;
	private final DaoHelper daoHelper;
	
	/**
	 * @deprecated only CDI eye
	 */
	public DefaultUserDAO() {
		this(null, null);
	}

	/**
	 * To inject manager into GenericDAO is required {@link Inject} annotation
	 */
	@Inject
	public DefaultUserDAO(EntityManager manager, DaoHelper helper) {
	    this.manager = manager;
	    this.daoHelper = helper;
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
	public void reload(UrutaUser user) {
		manager.refresh(user);
	}
}
