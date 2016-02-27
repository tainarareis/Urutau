package com.modesteam.urutau.builder;

import com.modesteam.urutau.model.Project;

public class ProjectBuilder {
	
	private String title;
	private String description;
	private String metodologyName;
	private Long id;
	
	public ProjectBuilder title(String title){
		this.title = title;
		return this;
	}
	
	public ProjectBuilder description(String description){
		this.description = description;
		return this;
	}
	
	public ProjectBuilder id(Long id) {
		this.id = id;
		return this;
	}

	public ProjectBuilder metodology(String metodologyName) {
		this.metodologyName = metodologyName;
		return this;
	}
	
	public Project builProject(){
		Project project = new Project();
		project.setId(id);
		project.setTitle(title);
		project.setDescription(description);
		project.setMetodology(metodologyName);
		return project;
	}

}
