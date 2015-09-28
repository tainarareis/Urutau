package com.modesteam.urutau.builder;

import com.modesteam.urutau.model.User;

public class UserBuilder {
	private String email;
	private String login;
	private String name;
	private String lastname;
	private String password;
	private String passwordVerify;
	
	public UserBuilder email(String email){
		this.email = email;
		return this;
	}
	
	public UserBuilder login(String login){
		this.login = login;
		return this;
	}
	
	public UserBuilder name(String name){
		this.name = name;
		return this;
	}
	
	public UserBuilder lastName(String lastName){
		this.lastname = lastName;
		return this;
	}
	
	public UserBuilder password(String password){
		this.password = password;
		return this;
	}
	
	public UserBuilder passwordVerify(String passwordVerify){
		this.passwordVerify = passwordVerify;
		return this;
	}
	
	public User build(){
		User user = new User();
		user.setEmail(email);
		user.setLogin(login);
		user.setName(name);
		user.setPassword(password);
		user.setPasswordVerify(passwordVerify);
		return user;
	}
}
