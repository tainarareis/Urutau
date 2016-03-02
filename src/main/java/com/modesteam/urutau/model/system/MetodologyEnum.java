package com.modesteam.urutau.model.system;

public enum MetodologyEnum {
	GENERIC(1, "Generic"), 
	SCRUM(2, "Scrum"), 
	UNIFIED_PROCESS(3, "Unified process");

	private final int id;
	private final String name;
	
	private MetodologyEnum(final int id, final String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
	/**
	 * See if name are equals to metologyName
	 * 
	 * @param metodologyName to compare
	 * @return true if have the same value
	 */
	public boolean refersTo(String metodologyName){
		return this.name.equals(metodologyName);
	}
}