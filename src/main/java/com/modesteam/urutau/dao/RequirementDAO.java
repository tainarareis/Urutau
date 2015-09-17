package com.modesteam.urutau.dao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.modesteam.urutau.model.Requirement;

@RequestScoped
public class RequirementDAO {

	@Inject
	private EntityManager manager;
	
	public void save(Requirement requirement) {
		manager.persist(requirement);
	}
	
}
