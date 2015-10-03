package com.modesteam.urutau.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.TableGenerator;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Artifact {
	@TableGenerator(name = "ARTIFACT_GEN", table = "ID_GEN", 
			pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL")
	@Id
	@GeneratedValue(generator = "ARTIFACT_GEN", strategy = GenerationType.TABLE)
	private long id;
	private String title;
	private String description;
	
	public Artifact() {
		super();
	}
	
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
	
	

}
