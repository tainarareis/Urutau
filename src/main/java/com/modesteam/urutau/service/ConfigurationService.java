package com.modesteam.urutau.service;

import javax.inject.Inject;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.dao.ConfigurationDAO;
import com.modesteam.urutau.dao.UserDAO;
import com.modesteam.urutau.model.Administrator;
import com.modesteam.urutau.model.system.Configuration;


public class ConfigurationService {
	
	private final static Logger logger = LoggerFactory.getLogger(ConfigurationService.class);
	
	private final ConfigurationDAO configurationDAO;
	
	public ConfigurationService() {
		this(null);
	}
	
	@Inject
	public ConfigurationService(ConfigurationDAO configurationDAO) {
		this.configurationDAO = configurationDAO;
	}
	
	public void put(Configuration configuration) {
		configurationDAO.create(configuration);
	}
	
}
