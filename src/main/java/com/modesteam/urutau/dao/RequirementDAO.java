package com.modesteam.urutau.dao;

import java.util.ArrayList;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.modesteam.urutau.model.Administrator;
import com.modesteam.urutau.model.Artifact;
import com.modesteam.urutau.model.UseCase;
import com.modesteam.urutau.model.Storie;


@RequestScoped
public class RequirementDAO {

	@Inject
	private EntityManager manager;
	
	public void saveGeneric(Artifact requirement) {
		manager.persist(requirement);
	}
	public void saveUserHistory(Storie userHistory) {
		manager.persist(userHistory);
	}
	public void saveUseCase(UseCase useCase) {
		manager.persist(useCase);
	}
	public ArrayList<Artifact> loadAll() {
		String sql = "SELECT r FROM Requirement r";
		Query query = manager.createQuery(sql);
		ArrayList<Artifact> requirements;
		requirements =(ArrayList<Artifact>) query.getResultList();
		return requirements;
	}
	public ArrayList<Artifact> loadGenerics(){
		String sql = "SELECT r FROM Requirement r WHERE actors = :actors "
				+ "AND discretion = :discretion";
		Query query = manager.createQuery(sql);
		query.setParameter("actors", "NULL");
		query.setParameter("discretion", "NULL");
		ArrayList<Artifact> requirements;
		requirements =(ArrayList<Artifact>) query.getResultList();
		return requirements;
	}
	public ArrayList<Artifact> loadUseCases(){
		String sql = "SELECT r FROM Requirement r WHERE actors <> :actors "
				+ "AND discretion = :discretion";
		Query query = manager.createQuery(sql);
		query.setParameter("actors", "NULL");
		query.setParameter("discretion", "NULL");
		ArrayList<Artifact> requirements;
		requirements =(ArrayList<Artifact>) query.getResultList();
		return requirements;
	}
	public ArrayList<Artifact> loadUserHistories(){
		String sql = "SELECT r FROM Requirement r WHERE actors = :actors "
				+ "AND discretion <> :discretion";
		Query query = manager.createQuery(sql);
		query.setParameter("actors", "NULL");
		query.setParameter("discretion", "NULL");
		ArrayList<Artifact> requirements;
		requirements =(ArrayList<Artifact>) query.getResultList();
		return requirements;
	}
	public Artifact detail(long id) {
		Artifact requirement;
		requirement = manager.find(Artifact.class, id);
		return requirement;		
	}
	
	 

	
}
