package com.modesteam.urutau.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.modesteam.urutau.model.system.Layer;
import com.modesteam.urutau.model.system.MetodologyEnum;

@Entity
public class Project implements Cloneable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String title;
	private String description;

	@Transient
	private String metodology;
	// Only this are persisted
	private int metodologyCode;

	@OneToMany(mappedBy = "project")
	private List<Artifact> requirements;

	@ManyToOne
	@JoinColumn(name = "userID")
	private User author;

	/* Artifact can be delegated to one or more persons */
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinTable(name = "User_Project", joinColumns = @JoinColumn(name = "projectID") , 
		inverseJoinColumns = @JoinColumn(name = "userID") )
	private List<User> members = new ArrayList<User>();

	/* Should be generate automatically */
	private Calendar dateOfCreation;

	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name = "Project_Layer", joinColumns = @JoinColumn(name = "project_id") ,
		inverseJoinColumns = @JoinColumn(name = "layer_id") )
	private List<Layer> layers = new ArrayList<Layer>();

	@Transient
	private boolean isEmpty;

	@Override
	public Project clone() throws CloneNotSupportedException {
		return (Project) super.clone();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMetodology() {
		return metodology;
	}

	public void setMetodology(String metodology) {
		this.metodology = metodology;
	}

	public int getMetodologyCode() {
		return metodologyCode;
	}

	public void setMetodologyCode(int metodologyCode) {
		this.metodologyCode = metodologyCode;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public List<User> getMembers() {
		return members;
	}

	public void setMembers(List<User> members) {
		this.members = members;
	}

	public Calendar getDateOfCreation() {
		return dateOfCreation;
	}

	public void setDateOfCreation(Calendar dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}

	public List<Artifact> getRequirements() {
		return requirements;
	}

	public void setRequirements(List<Artifact> requirements) {
		this.requirements = requirements;
	}

	public boolean isScrum() {
		return MetodologyEnum.SCRUM.getId() == metodologyCode;
	}

	public boolean isUP() {
		return MetodologyEnum.UNIFIED_PROCESS.getId() == metodologyCode;
	}

	public boolean isGeneric() {
		return MetodologyEnum.GENERIC.getId() == metodologyCode;
	}

	public boolean isEmpty() {
		return requirements.isEmpty();
	}

	public void setEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}

	public List<Layer> getLayers() {
		return layers;
	}

	public void setLayers(List<Layer> layers) {
		this.layers = layers;
	}
	
	/**
	 * Add an single layer
	 */
	public void add(Layer layer) {
		this.layers.add(layer);
	}
}
