package com.security.demo.models;

public class DeleteResponse {
	String message;
	public DeleteResponse(boolean result){
		if(result)
			this.message = "Deleted Successfully";
		else
			this.message = "No such product";
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public DeleteResponse(String message) {
		super();
		this.message = message;
	}
	

}
