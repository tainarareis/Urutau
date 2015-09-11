package com.modesteam.urutau.controller.model;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;


/**
 * URUTAU - 2015 
 * This class implements the Administrator model which is a special user. 
 */

@DiscriminatorValue(value = "2")
public class Administrator extends User {
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "ConcernedAdministrator", joinColumns = @JoinColumn(name = "adm_id"), 
			inverseJoinColumns = @JoinColumn(name = "new_adm_id"))
	
	private List<Administrator> corcernedAdministrator;

	/**
	 * Access to the class constructor method being inherited from User class.
	 */
	public Administrator() {
		super();
	}

	/**
	 * Getter for "corcernedAdministrator"
	 * @return corcernedAdministrator the list with the administrators of the application
	 */
	public List<Administrator> getCorcernedAdministrator() {
		return corcernedAdministrator;
	}

	/**
	 * Sets up the administrator
	 * @param corcernedAdministrator
	 */
	public void setCorcernedAdministrator(
			List<Administrator> corcernedAdministrator) {
		this.corcernedAdministrator = corcernedAdministrator;
	}

}
