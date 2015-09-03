package com.modesteam.urutau.controller.model;

import com.modesteam.urutau.controller.model.system.Configurations;

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
