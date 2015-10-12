package com.modesteam.urutau.builder;

import java.util.Calendar;

import com.modesteam.urutau.model.Artifact;


/**
 * File Name: ArtifactBuilder
 * Purpose: The Artifact Builder is important for supporting the tests
 * related to the class Artifact making easy the instantiation of it.
 */
public class ArtifactBuilder {
	
	private long id;
	private Calendar dateOfCreation;
	private String title;
	private String description;
	
	public ArtifactBuilder id(long id) {
		this.id = id;
		return this;		
	}

	public ArtifactBuilder dateOfCreation(Calendar dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
		return this;
	}

	public ArtifactBuilder title(String title) {
		this.title = title;
		return this;
	}
	public ArtifactBuilder description(String description) {
		this.description = description;
		return this;
	}	
	
	/**
	 * Responsible for building an object of Artifact
	 * @return Artifact instance
	 */
	public Artifact build() {
		Artifact artifact = new Artifact();
		artifact.setId(id);
		artifact.setDateOfCreation(dateOfCreation);
		artifact.setTitle(title);
		artifact.setDescription(description);
		return artifact;
		
	}
}