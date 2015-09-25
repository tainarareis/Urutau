package com.modesteam.urutau;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;

import com.modesteam.urutau.model.Requirement;

@SessionScoped
public class RequirementManager implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Requirement requirement;
	
	public Requirement getRequirement() {
		return requirement;
	}
	public void setRequirement(Requirement requirement) {
		this.requirement = requirement;
	}
	
	


}
