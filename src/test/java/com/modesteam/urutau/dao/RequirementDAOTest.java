package com.modesteam.urutau.dao;

import static org.junit.Assert.*;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.modesteam.urutau.model.Requirement;


public class RequirementDAOTest{
	
	@Inject
	private EntityManager manager;
	
	@Test
	public void shouldTestPersistMethod(){
		Requirement requirement = new Requirement();
		Requirement requirementTest = new Requirement();
		requirement.setDescription("desc");
		requirement.setTitle("title");
		manager.persist(requirement);
		requirementTest = manager.find(Requirement.class, 1);
		assertEquals(requirementTest.getDescription(), requirement.getDescription());
		
		
	}

}
