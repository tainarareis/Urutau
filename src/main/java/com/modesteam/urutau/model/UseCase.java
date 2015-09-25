package com.modesteam.urutau.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
public class UseCase extends Requirement {
	private String actors;

	public String getActors() {
		return actors;
	}

	public void setActors(String actors) {
		this.actors = actors;
	}

}
