package com.modesteam.urutau.dao;

import java.util.ArrayList;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.modesteam.urutau.model.Administrator;
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
	public ArrayList<Requirement> loadAll() {
		String sql = "SELECT r FROM Requirement r";
		Query query = manager.createQuery(sql);
		ArrayList<Requirement> requirements;
		requirements =(ArrayList<Requirement>) query.getResultList();
		return requirements;
		
	}

	
}
