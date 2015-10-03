package com.modesteam.urutau.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.TableGenerator;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Artifact {
	@TableGenerator(name = "ARTIFACT_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL")
	@Id
	@GeneratedValue(generator = "ARTIFACT_GEN", strategy = GenerationType.TABLE)
	private long id;
	
	@OneToOne(fetch = FetchType.LAZY, orphanRemoval=true, optional=false)
	private User author;

	/* Optional relationship */
	@OneToOne(fetch= FetchType.LAZY, orphanRemoval=false, optional=true)
	private Status status;
	
	/* Should be generate automatically */
	private Date dateOfCreation;
	
	/* Artifact can be delegated to one or more persons */
	@ManyToMany
	@JoinTable(name="Artifacts_Delegates", 
		joinColumns = @JoinColumn(name="artifact_id"), 
		inverseJoinColumns = @JoinColumn(name="user_id"))
	private List<User> responsables;
	
	private String title;
	private String description;

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public Date getDateOfCreation() {
		return dateOfCreation;
	}

	public void setDateOfCreation(Date dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public List<User> getResponsables() {
		return responsables;
	}

	public void setResponsables(List<User> responsables) {
		this.responsables = responsables;
	}
}
