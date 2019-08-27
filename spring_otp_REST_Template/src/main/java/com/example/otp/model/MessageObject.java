package com.example.otp.model;

public class MessageObject {
	
	private boolean success;
	private String successMessage;
	
	
	public MessageObject(boolean success, String successMessage) {
		super();
		this.success = success;
		this.successMessage = successMessage;
	}
	public MessageObject() {
		super();
		// TODO Auto-generated constructor stub
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getSuccessMessage() {
		return successMessage;
	}
	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}
	@Override
	public String toString() {
		return "MessageObject [success=" + success + ", successMessage=" + successMessage + "]";
	}
	
	
	

}
