package com.modesteam.urutau.controller.model.system;

import java.util.List;

import javax.persistence.Entity;

import com.modesteam.urutau.controller.model.Administrator;

@Entity
public class Configurations {
	private String registerType;
	private List<Administrator> corcernedAdministrator;

	public String getRegisterType() {
		return registerType;
	}

	public void setRegisterType(String registerType) {
		this.registerType = registerType;
	}

	public List<Administrator> getCorcernedAdministrator() {
		return corcernedAdministrator;
	}

	public void setCorcernedAdministrator(
			List<Administrator> corcernedAdministrator) {
		this.corcernedAdministrator = corcernedAdministrator;
	}

}
