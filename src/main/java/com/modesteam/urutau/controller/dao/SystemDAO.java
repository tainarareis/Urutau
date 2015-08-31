package com.modesteam.urutau.controller.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class SystemDAO {
	private EntityManager manager;
	/**
	 * Check if there is any account registered on DB as administrator
	 * 
	 * @return true if there is any administrator registered
	 * 
	 */
	public boolean existAdministrator() {
		String sql = "SELECT adm FROM ADMINISTRATOR adm";
		Query query = manager.createQuery(sql);
		int existenceOfAdministrator = query.getFirstResult();
		if(existenceOfAdministrator == 0) {
			return false;
		} else {
			return true;
		}
	}

}
