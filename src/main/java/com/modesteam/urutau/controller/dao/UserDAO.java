package com.modesteam.urutau.controller.dao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.modesteam.urutau.UserManager;
import com.modesteam.urutau.controller.model.User;

@RequestScoped
public class UserDAO {
	@Inject
	private EntityManager manager;
	
	@Inject
	private UserManager userManager;
	
	public void create(User user) {
		manager.persist(user);		
	}
	

	public void newUserSettings(User user) {
		manager.merge(user);
	}
	

}
