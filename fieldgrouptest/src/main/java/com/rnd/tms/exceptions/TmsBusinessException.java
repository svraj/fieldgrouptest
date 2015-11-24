package com.rnd.tms.exceptions;

public class TmsBusinessException extends Exception{

	private String message;
	
	public TmsBusinessException(){
		super();
	}
	
	public TmsBusinessException(String message){
		super();
		this.setMessage(message);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
