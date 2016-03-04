package com.modesteam.urutau.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.modesteam.urutau.model.system.setting.UserSetting;

/**
 * This class implements the generic user witch can be extended to an
 * Administrator.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "userType", discriminatorType = DiscriminatorType.INTEGER)
@DiscriminatorValue("1")
public class User {

	@Id
	@GeneratedValue
	private Long userID;
	
	@NotNull
	@Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\."
			+ "[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@"
			+ "(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", 
			message = "{invalid.email}")
	// ^ font: https://docs.oracle.com/cd/E19798-01/821-1841/gkahq/index.html
	private String email;
	@NotNull
	@Size(min = 3, max = 20)
	private String name;
	@NotNull
	@Size(min = 3, max = 20)
	private String lastName;
	@NotNull
	@Size(min = 6, max = 20)
	private String login;
	@NotNull
	@Size(min = 6, max = 20)
	private String password;
	@Transient
	private String passwordVerify;
	/*
	 * 0 - wait (default value) 1 - confirmed
	 */
	private int confirmed = 0;
	@ManyToMany(mappedBy = "responsables")
	private List<Artifact> artifactsDelegates = new ArrayList<Artifact>();
	
	/*
	 * All projects that user is integrated
	 */
	@ManyToMany(mappedBy="members", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private List<Project> projects = new ArrayList<Project>();
	
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true, mappedBy="user")
	private List<UserSetting> settings = new ArrayList<UserSetting>();

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	/**
	 * Verifies if the password inserted is confirmed correctly.
	 * 
	 * @return boolean isConfirmed.
	 */
	public boolean isConfirmed() {
		return confirmed == 1;
	}

	/**
	 * Outputs the password for verification.
	 * 
	 * @return String - The password settled up for verification .
	 */
	public String getPasswordVerify() {
		return passwordVerify;
	}

	/**
	 * Sets up the password for verification.
	 * 
	 * @param passwordVerify
	 *            .
	 */
	public void setPasswordVerify(String passwordVerify) {
		this.passwordVerify = passwordVerify;
	}

	/**
	 * Getter for "name".
	 * 
	 * @return String - the user's name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets up the user name as a String
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter for "lastName"
	 * 
	 * @return String - the user last name
	 */

	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets up the user last name as String
	 * 
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Getter for the user "login"
	 * 
	 * @return String - user login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Sets up the user login as String
	 * 
	 * @param login
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Getter for "password"
	 * 
	 * @return String - user password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets up the user password as a String
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Getter for the user "email"
	 * 
	 * @return String - user email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets up the user email a String
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	public int getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(int confirmed) {
		this.confirmed = confirmed;
	}

	public List<Artifact> getArtifactsDelegates() {
		return artifactsDelegates;
	}

	public void setArtifactsDelegates(List<Artifact> artifactsDelegates) {
		this.artifactsDelegates = artifactsDelegates;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public List<UserSetting> getSettings() {
		return settings;
	}

	public void setSettings(List<UserSetting> settings) {
		this.settings = settings;
	}
}
