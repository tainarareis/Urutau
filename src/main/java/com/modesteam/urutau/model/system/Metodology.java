package com.modesteam.urutau.model.system;

public enum Metodology {
	GENERIC(1, "Generic"), 
	SCRUM(2, "Scrum"), 
	UNIFIED_PROCESS(3, "Unified process");

	private final int id;
	private final String name;

	private Metodology(final int id, final String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}