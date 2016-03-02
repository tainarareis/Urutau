package com.modesteam.urutau.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.model.system.settings.GlobalSetting;

public class DefaultSettingDAO extends GenericDAO<GlobalSetting> implements SettingDAO {

	private static final Logger logger = LoggerFactory.getLogger(SettingDAO.class);
	
	@Inject
	private EntityManager manager;
	
	public DefaultSettingDAO() {
	
	}
	
	@Inject
	public DefaultSettingDAO(EntityManager manager) {
		super.setEntityManager(manager);
	}
	
	@Override
	public GlobalSetting get(String field, Object value) throws Exception {
		return null;
	}
}
