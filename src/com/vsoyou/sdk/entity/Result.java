package com.vsoyou.sdk.entity;

import java.io.Serializable;

public class Result implements Serializable {

	private static final long serialVersionUID = 3559586765645519956L;
	
	public boolean success;
	
	public String message;
	
	public int code;

	@Override
	public String toString() {
		return "Result [success=" + success + ", message=" + message
				+ ", code=" + code + "]";
	}

}
