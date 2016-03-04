package com.modesteam.urutau.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import com.modesteam.urutau.model.system.ArtifactType;

@Entity
public class UseCase extends Artifact {
	@ManyToMany(cascade=CascadeType.PERSIST)
	@JoinTable(name = "Actors", joinColumns = @JoinColumn(name = "useCaseID"), 
		inverseJoinColumns = @JoinColumn(name = "actorID"))
	private List<Actor> actors;
	
	@Transient
	private String fakeActors;

	public UseCase () {
		super.setArtifactType(ArtifactType.USECASE);
	}
	
	public List<Actor> getActors() {
		return actors;
	}

	public void setActors(List<Actor> actors) {
		this.actors = actors;
	}

	public String getFakeActors() {
		return fakeActors;
	}

	public void setFakeActors(String fakeActors) {
		this.fakeActors = fakeActors;
	}
}
