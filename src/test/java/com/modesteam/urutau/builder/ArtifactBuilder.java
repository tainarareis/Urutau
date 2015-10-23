package com.modesteam.urutau.builder;

import com.modesteam.urutau.model.Artifact;
import com.modesteam.urutau.model.Epic;
import com.modesteam.urutau.model.Feature;
import com.modesteam.urutau.model.User;

public class ArtifactBuilder {
	private String title;
	private String description;
	private Long id;
	
	public ArtifactBuilder title(String title){
		this.title = title;
		return this;
	}
	
	public ArtifactBuilder description(String description){
		this.description = description;
		return this;
	}
	
	public ArtifactBuilder id(Long id) {
		this.id = id;
		return this;
	}
	
	
	public Epic buildEpic(){
		Epic epic = new Epic();
		epic.setId(id);
		epic.setTitle(title);
		epic.setDescription(description);
		return epic;
	}
	
	public Feature buildFeature(){
		Feature feature = new Feature();
		feature.setId(id);
		feature.setTitle(title);
		feature.setDescription(description);
		return feature;
	}

	

}
