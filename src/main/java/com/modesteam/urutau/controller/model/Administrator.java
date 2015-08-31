package com.modesteam.urutau.controller.model;

import javax.persistence.Entity;

import com.modesteam.urutau.controller.model.system.Configurations;

@Entity
public class Administrator extends User {

	private Configurations configurations;

	public Administrator() {
		super();
	}

	public Configurations getConfigurations() {
		return configurations;
	}

	public void setConfigurations(Configurations configurations) {
		this.configurations = configurations;
	}
}
