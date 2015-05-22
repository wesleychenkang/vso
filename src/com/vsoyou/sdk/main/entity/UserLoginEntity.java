package com.vsoyou.sdk.main.entity;

import java.io.Serializable;

import com.vsoyou.sdk.entity.Result;

public class UserLoginEntity extends Result implements Serializable {

	private static final long serialVersionUID = 2310913817301323195L;
	
	public long loginId; //用户登陆SessionId
	public long id; //用户Id
	public String userName;
	public String passWord;
	public String eMail;
	public int status;//邮箱绑定状态 0：未绑定 1：已绑定 
	
	
	@Override
	public String toString() {
		return "UserLoginEntity [loginId=" + loginId + ", id=" + id
				+ ", userName=" + userName + ", passWord=" + passWord
				+ ", eMail=" + eMail + ", status=" + status + ", success="
				+ success + ", message=" + message + ", code=" + code + "]";
	}
	
}
