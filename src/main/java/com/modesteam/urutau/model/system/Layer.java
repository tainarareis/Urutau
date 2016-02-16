package com.modesteam.urutau.model.system;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.modesteam.urutau.model.Artifact;

@Entity
public class Layer {
	@Id
	@GeneratedValue
	private Long layerID;

	@NotEmpty
	@Size(min = 2)
	private String name;

	private String description;

	@OneToMany(mappedBy = "layer")
	private List<Artifact> requirements = new ArrayList<Artifact>();

	public Long getLayerID() {
		return layerID;
	}

	public void setLayerID(Long layerID) {
		this.layerID = layerID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Artifact> getRequirements() {
		return requirements;
	}

	public void setRequirements(List<Artifact> requirements) {
		this.requirements = requirements;
	}

}
