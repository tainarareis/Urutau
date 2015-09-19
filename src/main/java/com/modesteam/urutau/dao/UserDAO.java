package com.modesteam.urutau.dao;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.model.User;

/**
 *
 * Accesses the database related to the user.
 */
@RequestScoped
public class UserDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);
	
	@Inject
	private EntityManager manager;
	
	/**
	 * Make the user instance managed and persistent.
	 * @param user
	 */
	public void add(User user){
		manager.persist(user);
	}
	
	/**
	 * Method to verify if exist a user with same email or same login
	 * @param user
	 * @return false if the verification fails
	 */
	public boolean existsField(Object value, String field) {
		logger.info("Verification of user, field = "+field);
		
		String sql = "SELECT user FROM User user WHERE user."+field+"=:"+field;
		Query query = manager.createQuery(sql);
		query.setParameter(field, value);
		
		if(query.getResultList().size() == 0){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Method to recover user from database.
	 * @param user
	 * @return
	 */
	public User find(User user){
		return manager.find(User.class, user);
	}

	/**
	 * Method to update user from database.
	 * @param user
	 */
	public void update(User user) {
		manager.merge(user);
		
	}
	
	/**
	 * Allows the creation of the user directly in database throughout the manager entity.
	 * @param user
	 */
	public void create(User user) {
		manager.persist(user);		
	}
	

	/**
	 * Merge the state of the given entity into the current persistence context.
	 * @param user
	 */
	public void newUserSettings(User user) {
		manager.merge(user);
	}
	

}
