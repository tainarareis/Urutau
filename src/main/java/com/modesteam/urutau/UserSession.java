package com.modesteam.urutau;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.exception.SystemBreakException;
import com.modesteam.urutau.model.User;

@SessionScoped
@Named("userSession")
public class UserSession implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(UserSession.class);
	
	private User userLogged;
	
	@Inject
	private Event<User> loginEvent;

	/**
	 * Saves user in session
	 * 
	 * @param user to be save in session
	 */
	public void login(User user){
		this.userLogged = user;
		
		loginEvent.fire(userLogged);
	}
	
	/**
	 * Destroy userLogged.Makes possible the logging out. 
	 */
	public void logout(){
		this.userLogged = null;
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
			if(userLogged.getUserID() == null) {
				isLogged = false;
			} else {
				logger.info("User is logged!");
			}
		}catch(NullPointerException npe) {
			isLogged = false;
		}
		
		return isLogged;
	}
	
	/**
	 * Reload session
	 *  
	 * @param logged user to be reloaded
	 */
	public void reload(User logged) {
		if(logged.getUserID() == userLogged.getUserID()) {
			logout();
			login(userLogged);
		} else {
			throw new SystemBreakException();
		}
	}

	public User getUserLogged() {
		return userLogged;
	}
}
