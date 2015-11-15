package com.modesteam.urutau.controller;

import com.modesteam.urutau.service.DaoInterface;

public abstract class EntityCreator<Entity> {

	private DaoInterface<Entity> dao;
	
	/**
	 * To override
	 * 
	 * @param entity 
	 */
	public void validate(Entity entity) {

	}

	public void create(Entity entity) {
		validate(entity);
		dao.create(entity);
	}

	public DaoInterface<Entity> getDao() {
		return dao;
	}

	public void setDao(DaoInterface<Entity> dao) {
		this.dao = dao;
	}

}
