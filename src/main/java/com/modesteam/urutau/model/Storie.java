package com.modesteam.urutau.model;

import javax.persistence.Entity;

@Entity
public class UserHistory extends Requirement{
	private String history;
	private String scenary;
	private String discretion;
	
	public String getHistory() {
		return history;
	}
	public void setHistory(String history) {
		this.history = history;
	}
	public String getScenary() {
		return scenary;
	}
	public void setScenary(String scenary) {
		this.scenary = scenary;
	}
	public String getDiscretion() {
		return discretion;
	}
	public void setDiscretion(String discretion) {
		this.discretion = discretion;
	}
	
	
}
