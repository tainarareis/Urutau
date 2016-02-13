package com.modesteam.urutau.dao;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.model.User;
import com.modesteam.urutau.service.GenericDAO;

/**
 * 
 * Accesses the database related to the user.
 */
@RequestScoped
public class UserDAO extends GenericDAO<User>{

	private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);
	
	@Inject
	private EntityManager manager;
	
	public UserDAO() {
		
	}
	
	@Inject
	public UserDAO(EntityManager manager) {
		super.setEntityManager(manager);
	}
	
	
	@Override
	public User find(Long id){
		logger.info("Find an user with follow id: " +  id);
		return manager.find(User.class, id);
	}

	@Override
	public User get(String field, Object value) {
		String sql = "SELECT user FROM User user WHERE user."+field+"=:value";
		
		logger.info(sql);
		
		try {
			Query query = manager.createQuery(sql);	
			query.setParameter("value", value);
			return (User) query.getSingleResult();
		} catch (NonUniqueResultException exception) {
			throw new NonUniqueResultException();
		} catch (NoResultException exception) {
			return null;
		}
	}
	
	/**
	 * Verifies if have some user registred
	 * 
	 * @return true if some user exists
	 */
	public boolean hasAnyRegister() {
		String sql = "SELECT user FROM " + User.class.getName() + " user";
		Query query = manager.createQuery(sql);
		
		return !query.getResultList().isEmpty();
	}
	
	/**
	 * Get user instance from db
	 * 
	 * @param userID
	 * @return
	 */
	public User getReference(Long userID) {
		return manager.getReference(User.class, userID);
	}
}
