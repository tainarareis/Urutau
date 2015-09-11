package com.modesteam.urutau.controller.dao;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.modesteam.urutau.UserManager;
import com.modesteam.urutau.controller.model.User;

/**
 * URUTAU - 2015
 * Accesses the database related to the user.
 */
@RequestScoped
public class UserDAO {
	
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
	 * @return 0 if the verification fails
	 */
	public int verifyUser(User user) {
		String sqlLogin = "SELECT E.login FROM User E";
		String sqlEmail = "SELECT E.email FROM User E";
		Query queryLogin = manager.createQuery(sqlLogin);
		Query queryEmail = manager.createQuery(sqlEmail);
		List<String> login =  queryLogin.getResultList();
		List<String> email =  queryEmail.getResultList();
			for(String log : login){
				if(log.equalsIgnoreCase(user.getLogin())==true) {
					return 1;
				}
			}
			for(String emailAux : email) {
				if(emailAux.equalsIgnoreCase(user.getEmail())==true) {
					return 2;
				}
			}
		return 0;
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

	@Inject
	private UserManager userManager;
	
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
