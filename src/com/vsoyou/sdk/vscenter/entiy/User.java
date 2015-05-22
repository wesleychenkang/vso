package com.vsoyou.sdk.vscenter.entiy;

import com.vsoyou.sdk.main.Constants;
import com.vsoyou.sdk.util.DESCoder;
import com.vsoyou.sdk.util.LocalStorage;

import android.content.Context;

public class User {
	private long userId;
	private String userName;
	private Context ctx;
	private LocalStorage storage;

	public User(Context ctx) {
		this.ctx = ctx;
		 storage = LocalStorage.getInstance(ctx);
	}

	public long getUserId() {
		userId = storage.getLong(Constants.USER_ID, -1);
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		userName = DESCoder.decryptoPriAndPub(ctx,
				storage.getString(Constants.USERNAME, ""));
		return userName ;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public void updatePassWord(Context ctx,String passWord){
		String pwd = DESCoder.encryptoPubAndPri(ctx, passWord);
		storage.putString(Constants.USERPASSWORD, pwd);
		
	}

}
