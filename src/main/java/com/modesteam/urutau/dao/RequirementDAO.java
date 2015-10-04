package com.modesteam.urutau.dao;

import java.util.ArrayList;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.model.Administrator;
import com.modesteam.urutau.model.Artifact;
import com.modesteam.urutau.model.UseCase;
import com.modesteam.urutau.model.User;
import com.modesteam.urutau.service.DaoInterface;
import com.modesteam.urutau.model.Storie;



/**
 * 
 * Accesses the database related to the Requirements.
 */
@RequestScoped
public abstract class RequirementDAO implements DaoInterface<Artifact>{
	
	private static final Logger logger = LoggerFactory.getLogger(RequirementDAO.class);

	@Inject
	private EntityManager manager;
	
	@Override
	public void create(Artifact artifact) {
		logger.info("An new artifact will be persist");
		manager.persist(artifact);		
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
	
	
	public Artifact find(long id) {
		Artifact requirement;
		requirement = manager.find(Artifact.class, id);
		logger.info("Find an Artifact with follow id: " +  id);
		return requirement;		
	}
	
	 

	
}
