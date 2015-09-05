package com.modesteam.urutau.controller.dao;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import java.util.List;

import javax.enterprise.context.RequestScoped;

import com.modesteam.urutau.controller.model.User;

@RequestScoped
public class UserDAO {
	@Inject
	private EntityManager manager;
	
	public void add(User user){
		manager.persist(user);
	}
	
	public int verifyUser(User user){
		String sqlLogin = "SELECT E.login FROM User E";
		String sqlEmail = "SELECT E.email FROM User E";
		Query queryLogin = manager.createQuery(sqlLogin);
		Query queryEmail = manager.createQuery(sqlEmail);
		List<String> login =  queryLogin.getResultList();
		List<String> email =  queryEmail.getResultList();
			for(String log : login){
				if(log.equalsIgnoreCase(user.getLogin())==true){
					return 1;
				}
			}
		for(String emailAux : email){
			if(emailAux.equalsIgnoreCase(user.getEmail())==true){
				return 2;
			}
		}
		return 0;
	}

}
