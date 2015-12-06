package com.modesteam.urutau;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.model.User;

@SessionScoped
@Named("userSession")
public class UserSession implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(UserSession.class);
	
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
	 * Destroy userLogged.Makes possible the logging out. 
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
	
	/**
	 * Verifies any user in session
	 * 
	 * @return true if user is logged
	 */
	public boolean isLogged(){
		boolean isLogged =  true;
		
		// verifies through if and possible nullpointerexception
		try {
			if(userLogged.getUserID() == null){
				isLogged = false;
			}
		}catch(NullPointerException npe) {
			isLogged = false;
		}
		
		logger.info("User is logged?" + isLogged);
		
		return isLogged;
	}

}
