package com.modesteam.urutau.dao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.modesteam.urutau.model.Requirements;

@RequestScoped
public class RequirementsDAO {

	@Inject
	private EntityManager manager;
	
	public void save(Requirements requirement) {
		manager.persist(requirement);
	}
	
}
