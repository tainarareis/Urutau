package com.modesteam.urutau.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.modesteam.urutau.model.system.ArtifactType;

@Entity
public class Storie extends Artifact {
	private String history;

	@ManyToMany
	@JoinTable(name = "User_Criteria", joinColumns = @JoinColumn(name = "storie_id") ,
			inverseJoinColumns = @JoinColumn(name = "artifact_id") )
	private List<AcceptanceCriteria> acceptanceCriteria;

	public Storie() {
		super.setArtifactType(ArtifactType.STORIE);
	}

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	public List<AcceptanceCriteria> getAcceptanceCriteria() {
		return acceptanceCriteria;
	}

	public void setAcceptanceCriteria(List<AcceptanceCriteria> acceptanceCriteria) {
		this.acceptanceCriteria = acceptanceCriteria;
	}

}
