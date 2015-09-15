package com.modesteam.urutau.model.system;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Configuration {
	@Id
	private Long id;
	private String registerType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRegisterType() {
		return registerType;
	}

	public void setRegisterType(String registerType) {
		this.registerType = registerType;
	}
}
