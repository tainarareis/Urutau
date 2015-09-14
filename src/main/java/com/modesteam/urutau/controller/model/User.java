package com.modesteam.urutau.controller.model;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * URUTAU - 2015 
 * This class implements the generic user witch can be extended to an Administrator.
 */
@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="userType", discriminatorType=DiscriminatorType.INTEGER)
@DiscriminatorValue("1")
public class User {

	@Id
	@GeneratedValue
	private Long id;
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
	@NotNull
	@Size(min = 6, max = 20)
	private String passwordVerify;
	private boolean isConfirmed;

	/**
	 * Getter for "id"
	 * @return Long - the user id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets up the user id
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Verifies if the password inserted is confirmed correctly.
	 * @return boolean isConfirmed.
	 */
	public boolean isConfirmed() {
		return isConfirmed;
	}

	/**
	 * Set up the confirmation password.
	 * @param isConfirmed.
	 */
	public void setConfirmed(boolean isConfirmed) {
		this.isConfirmed = isConfirmed;
	}

	/**
	 * Outputs the password for verification.
	 * @return String - The password settled up for verification .
	 */
	public String getPasswordVerify() {
		return passwordVerify;
	}

	/**
	 * Sets up the password for verification.
	 * @param passwordVerify.
	 */
	public void setPasswordVerify(String passwordVerify) {
		this.passwordVerify = passwordVerify;
	}

	/**
	 * Getter for "name".
	 * @return String - the user's name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets up the user name as a String
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Getter for "lastName"
	 * @return String - the user last name
	 */

	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets up the user last name as String
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Getter for the user "login"
	 * @return String - user login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Sets up the user login as String
	 * @param login
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Getter for "password"
	 * @return String - user password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets up the user password as a String
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Getter for the user "email"
	 * @return String - user email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets up the user email a String
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

}
