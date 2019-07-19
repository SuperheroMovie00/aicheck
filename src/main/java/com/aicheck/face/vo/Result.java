package com.aicheck.face.vo;

public class Result {
	
	private boolean success;
	private Object message;
	private String msg;
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public Object getMessage() {
		return message;
	}
	public void setMessage(Object message) {
		this.message = message;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	@Override
	public String toString() {
		return "Result [success=" + success + ", message=" + message + ", msg=" + msg + "]";
	}
	public Result(boolean success, Object message, String msg) {
		super();
		this.success = success;
		this.message = message;
		this.msg = msg;
	}
	public Result() {
		super();
	}
	
	

}
