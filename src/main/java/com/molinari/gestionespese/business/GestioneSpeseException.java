package com.molinari.gestionespese.business;

public class GestioneSpeseException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	public GestioneSpeseException(String message) {
		super(message);
	}
	
	public GestioneSpeseException(Exception e) {
		super(e);
	}


}
