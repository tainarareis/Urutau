package com.modesteam.urutau.builder;

import com.modesteam.urutau.model.Artifact;
import com.modesteam.urutau.model.User;

public class ArtifactBuilder {
	private String title;
	private String description;
	
	public ArtifactBuilder title(String title){
		this.title = title;
		return this;
	}
	
	public ArtifactBuilder login(String description){
		this.description = description;
		return this;
	}
	
	
	public User build(){
		Generic artifact = new Generic();
		artifact.setTitle(title);
		artifact.setDescription(description);
		return artifact;
	}
}
