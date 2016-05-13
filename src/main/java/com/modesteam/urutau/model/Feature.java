package com.modesteam.urutau.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import com.modesteam.urutau.model.system.ArtifactType;

@Entity
public class Feature extends Artifact {

	private String content;

	@OneToOne
	private Epic epic;

	public Feature() {
		super.setArtifactType(ArtifactType.FEATURE);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
