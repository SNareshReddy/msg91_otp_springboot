package com.example.otp.model;


public class MessageDetails {
	
	//{"message":"396144757834313734363033","type":"success"}
	
	private String message;
	private String type;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "MessageDetails [message=" + message + ", type=" + type + "]";
	}
	
	

}
