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
import com.modesteam.urutau.service.DaoInterface;

/**
 * 
 * Accesses the database related to the user.
 */
@RequestScoped
public class UserDAO implements DaoInterface<User>{
	
	private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);
	
	@Inject
	private EntityManager manager;
		
	@Override
	public void create(User user) {
		logger.info("An new user will be persist");
		manager.persist(user);		
	}
	
	@Override
	public User find(Long id){
		logger.info("Find an user with follow id: " +  id);
		return manager.find(User.class, id);
	}

	@Override
	public User get(String field, Object value) {
		String sql = "SELECT user FROM User user";
		
		// Verifies if select have an criteria
		boolean isGenericSelect = field != null && value != null;
		
		if(isGenericSelect) {
			sql = sql + " WHERE user.".concat(field).concat("=:value");
		} else {
			// default sql string
		}
		
		logger.info(sql);
		
		try {
			Query query = manager.createQuery(sql);
			
			if(isGenericSelect) {
				query.setParameter("value", value);
			} else {
				// without param
			}
			
			return (User) query.getSingleResult();
		} catch (NonUniqueResultException exception){
			throw new NonUniqueResultException();
		} catch (NoResultException exception) {
			return null;
		}
	}
	
	@Override
	public boolean destroy(User entity) {
		try {
			manager.remove(entity);
			return true;
		} catch (Exception exception) {
			logger.error("Cant remove an entity");
			return false;
		} 
	}

	@Override
	public boolean update(User entity) {
		try {
			manager.merge(entity);
			return true;
		} catch(Exception exception) {
			logger.error("Cant update an entity");
			return false;
		}
	}

	
	
	
    

}
