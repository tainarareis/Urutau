package com.modesteam.urutau.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.modesteam.urutau.model.Artifact;
import com.modesteam.urutau.model.Project;
import com.modesteam.urutau.service.DaoInterface;

public class ProjectDAO implements DaoInterface{
	
	@Inject
	private EntityManager manager;

	@Override
	public void create(Object entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object get(String field, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object find(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean destroy(Object entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Object entity) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public List<? extends Project> loadAll() {
		//Needs to implement this method like requirementDAO
		return null;
	}

}
