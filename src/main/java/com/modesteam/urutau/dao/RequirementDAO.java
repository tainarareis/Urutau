package com.modesteam.urutau.dao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.modesteam.urutau.model.Requirement;
import com.modesteam.urutau.model.UseCase;
import com.modesteam.urutau.model.UserHistory;

@RequestScoped
public class RequirementDAO {

	@Inject
	private EntityManager manager;
	
	public void saveGeneric(Requirement requirement) {
		manager.persist(requirement);
	}
	public void saveUserHistory(UserHistory userHistory) {
		manager.persist(userHistory);
	}
	public void saveUseCase(UseCase useCase) {
		manager.persist(useCase);
	}

	
}
