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
	
	public boolean verify(User user){
		String sql = "SELECT E.login FROM User E";
		Query query = manager.createQuery(sql);
		List<String> login =  query.getResultList();
		for(int i=0; i<=login.size(); i++){
			if(login.get(i).equalsIgnoreCase(user.getLogin())==true){
				return true;
			}
		}
		return false;
	}
}
