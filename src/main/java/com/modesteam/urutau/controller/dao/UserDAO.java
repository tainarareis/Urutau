package com.modesteam.urutau.controller.dao;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.enterprise.context.RequestScoped;

import com.modesteam.urutau.controller.model.User;

@RequestScoped
public class UserDAO {
	@Inject
	private EntityManager manager;
	
	public void add(User user){
		manager.persist(user);
	}
}
