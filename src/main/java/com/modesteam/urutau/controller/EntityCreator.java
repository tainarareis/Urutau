package com.modesteam.urutau.controller;

import com.modesteam.urutau.service.GenericDAO;

public abstract class EntityCreator<Entity> {

	private GenericDAO<Entity> dao;
	
	/**
	 * To override
	 * 
	 * @param entity 
	 */
	protected void validate(Entity entity) {}

	public void create(Entity entity) {
		validate(entity);
		dao.create(entity);
	}

	public void setDao(GenericDAO<Entity> dao) {
		this.dao = dao;
	}

}
