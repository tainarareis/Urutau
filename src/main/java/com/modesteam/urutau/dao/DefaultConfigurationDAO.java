package com.modesteam.urutau.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.model.system.Configuration;
import com.modesteam.urutau.service.GenericDAO;

public class DefaultConfigurationDAO extends GenericDAO<Configuration> implements ConfigurationDAO {

	private static final Logger logger = LoggerFactory.getLogger(ConfigurationDAO.class);
	
	@Inject
	private EntityManager manager;
	
	public DefaultConfigurationDAO() {
		
	}
	
	@Inject
	public DefaultConfigurationDAO(EntityManager manager) {
		super.setEntityManager(manager);
	}

	@Override
	public Configuration get(String field, Object value) throws Exception {
		return null;
	}
}
