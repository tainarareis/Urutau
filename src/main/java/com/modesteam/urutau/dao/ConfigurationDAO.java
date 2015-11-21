package com.modesteam.urutau.dao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.model.Administrator;
import com.modesteam.urutau.model.User;
import com.modesteam.urutau.model.system.Configuration;
import com.modesteam.urutau.service.GenericDAO;

/**
 * Class responsible to ensure/allow administrator interaction 	
 * through the database management information about Administrators
 */
@RequestScoped
public class ConfigurationDAO extends GenericDAO<Configuration>{
	
	private static final Logger logger = LoggerFactory.getLogger(ConfigurationDAO.class);
	
	@Inject
	private EntityManager entityManager;	
	
	public ConfigurationDAO() {
		super.setEntityManager(entityManager);
	}
	
	@Override
	public Configuration find(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Configuration get(String field, Object value) {
		// TODO Auto-generated method stub
		return null;
	}
}
