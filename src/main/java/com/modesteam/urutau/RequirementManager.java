package com.modesteam.urutau;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;

import com.modesteam.urutau.model.Artifact;

@SessionScoped
public class RequirementManager implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Artifact requirement;
	
	public Artifact getRequirement() {
		return requirement;
	}
	public void setRequirement(Artifact requirement) {
		this.requirement = requirement;
	}
	
	


}
