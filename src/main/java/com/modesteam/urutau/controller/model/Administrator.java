package com.modesteam.urutau.controller.model;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
@DiscriminatorValue(value = "2")
public class Administrator extends User {
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "ConcernedAdministrator", joinColumns = @JoinColumn(name = "adm_id"), 
			inverseJoinColumns = @JoinColumn(name = "new_adm_id"))
	private List<Administrator> corcernedAdministrator;

	public Administrator() {
		super();
	}

	public List<Administrator> getCorcernedAdministrator() {
		return corcernedAdministrator;
	}

	public void setCorcernedAdministrator(
			List<Administrator> corcernedAdministrator) {
		this.corcernedAdministrator = corcernedAdministrator;
	}

}
