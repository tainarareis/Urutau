package com.modesteam.urutau.controller;

import com.modesteam.urutau.annotation.View;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Validator;

public class KanbanController {	
	
	private final Result result;
	private final Validator validator;
	
	/**
	 * @deprecated CDI 
	 */
	public KanbanController() {
		this(null, null);
	}
	
	public KanbanController(Result result, Validator validator) {
		this.result = result;
		this.validator = validator;
	}
	
	public void customize() {
		
	}
	
	public void createLayer() {
		
	}
	
	public void deleteLayer() {

	}
	
	public void updateLayer() {
		
	}
	
	@View
	public void editLayer() {
		
	}
	
}
