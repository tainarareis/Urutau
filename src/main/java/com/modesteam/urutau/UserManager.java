package com.modesteam.urutau;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.modesteam.urutau.model.User;

@SessionScoped
@Named("manager")
public class UserManager implements Serializable {

	private static final long serialVersionUID = 1L;

	private User userLogged;
	
	/**
	 * Saves user in session
	 * 
	 * @param user to be save in session
	 */
	public void login(User user){
		setUserLogged(user);
	}
	
	/**
	 * Destroy userLogged
	 */
	public void logout(){
		setUserLogged(null);
	}

	public User getUserLogged() {
		return userLogged;
	}

	public void setUserLogged(User userLogged) {
		this.userLogged = userLogged;
	}
}
