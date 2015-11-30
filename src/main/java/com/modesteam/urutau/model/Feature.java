package com.modesteam.urutau.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Feature extends Artifact {
	
	private String content;

	@OneToOne
	private Epic epic;

	
	public Feature () {
		super.setArtifactType("Feature");
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
