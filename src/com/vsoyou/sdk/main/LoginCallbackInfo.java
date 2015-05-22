package com.vsoyou.sdk.main;

import java.io.Serializable;

public class LoginCallbackInfo implements Serializable {

	private static final long serialVersionUID = -2972223832321657354L;
	
	public long userId;
	public String userName;
	public int statusCode;
	public String statusDes;
	public int type; // 1 = 登陆  2 = 注册
	
	public LoginCallbackInfo(long userId, String userName, int statusCode,
			String statusDes, int type) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.statusCode = statusCode;
		this.statusDes = statusDes;
		this.type = type;
	}

	@Override
	public String toString() {
		return "LoginCallbackInfo [userId=" + userId + ", userName=" + userName
				+ ", statusCode=" + statusCode + ", statusDes=" + statusDes
				+ ", type=" + type + "]";
	}

}
