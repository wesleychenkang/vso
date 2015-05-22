package com.vsoyou.sdk.main.entity;

import java.io.Serializable;

import com.vsoyou.sdk.entity.Result;

public class FindPwdEntity extends Result implements Serializable {

	private static final long serialVersionUID = -3698784457910174960L;
	
	public String eMail;
	
	public int status;

	@Override
	public String toString() {
		return "FindPwdEntity [eMail=" + eMail + ", status=" + status
				+ ", success=" + success + ", message=" + message + ", code="
				+ code + "]";
	}
	
}
