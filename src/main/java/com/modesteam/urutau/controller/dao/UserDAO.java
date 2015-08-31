package com.modesteam.urutau.controller.dao;
import javax.enterprise.inject.*;
import javax.enterprise.context.RequestScoped;

import com.modesteam.urutau.controller.model.User;

@RequestScopped
public class UserDAO {
	@inject
	private EntityManager manager;
	
	private void add(User user){
		
	}
}
