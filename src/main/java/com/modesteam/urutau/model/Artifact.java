package com.modesteam.urutau.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.modesteam.urutau.model.system.ArtifactType;
import com.modesteam.urutau.model.system.Layer;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Artifact {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private UrutaUser author;

	// Project associated
	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;

	@Transient
	private Long projectID;

	@OneToOne(optional = true)
	private UrutaUser lastModificationAuthor;

	/* Optional relationship */
	@OneToOne(optional = true)
	private Status status;

	/* Artifact can be delegated to one or more persons */
	@ManyToMany
	@JoinTable(name = "Artifact_Delegate", joinColumns = @JoinColumn(name = "artifact_id") ,
			inverseJoinColumns = @JoinColumn(name = "user_id") )
	private List<UrutaUser> responsables;

	/* Should be generate automatically */
	private Calendar dateOfCreation;
	private Calendar lastModificationDate;

	@NotNull(message = "{artifact.title.empty}")
	@Length(min = 2, max = 90, message = "{artifact.title.length}")
	private String title;

	@Transient
	// Generated from title
	private String encodedTitle;

	private String description;
	private ArtifactType artifactType;

	@ManyToOne
	@JoinColumn(name = "layer_id")
	private Layer layer;

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

	public String getEncodedTitle() throws UnsupportedEncodingException {
		setEncodedTitle(title);
		return encodedTitle;
	}

	public void setEncodedTitle(String encodedTitle) throws UnsupportedEncodingException {
		this.encodedTitle = URLEncoder.encode(encodedTitle, StandardCharsets.UTF_8.name());
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Calendar getDateOfCreation() {
		return dateOfCreation;
	}

	public void setDateOfCreation(Calendar dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}

	public UrutaUser getAuthor() {
		return author;
	}

	public void setAuthor(UrutaUser author) {
		this.author = author;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Long getProjectID() {
		return projectID;
	}

	public void setProjectID(Long projectID) {
		this.projectID = projectID;
	}

	public List<UrutaUser> getResponsables() {
		return responsables;
	}

	public void setResponsables(List<UrutaUser> responsables) {
		this.responsables = responsables;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public UrutaUser getLastModificationAuthor() {
		return lastModificationAuthor;
	}

	public void setLastModificationAuthor(UrutaUser lastModificationAuthor) {
		this.lastModificationAuthor = lastModificationAuthor;
	}

	public Calendar getLastModificationDate() {
		return lastModificationDate;
	}

	public void setLastModificationDate(Calendar lastModificationDate) {
		this.lastModificationDate = lastModificationDate;
	}

	public ArtifactType getArtifactType() {
		return artifactType;
	}

	public void setArtifactType(ArtifactType artifactType) {
		this.artifactType = artifactType;
	}

	public Layer getLayer() {
		return layer;
	}

	public void setLayer(Layer layer) {
		this.layer = layer;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName().toLowerCase();
	}
}
