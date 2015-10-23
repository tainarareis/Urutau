package com.modesteam.urutau.builder;

import com.modesteam.urutau.model.Artifact;
import com.modesteam.urutau.model.Epic;
import com.modesteam.urutau.model.Feature;
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
	
	
	public Epic build(){
		Epic epic = new Epic();
		epic.setTitle(title);
		epic.setDescription(description);
		return epic;
	}
	
	public Feature buildFeature(){
		Feature feature = new Feature();
		feature.setTitle(title);
		feature.setDescription(description);
		return feature;
	}
}
