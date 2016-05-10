package com.modesteam.urutau.model;

import javax.persistence.Entity;

import com.modesteam.urutau.model.system.ArtifactType;

@Entity
public class Epic extends Artifact {
	private String content;

	public Epic() {
		super.setArtifactType(ArtifactType.EPIC);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
