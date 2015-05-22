package com.vsoyou.sdk.main.entity.parser;

import org.json.JSONObject;

import android.content.Context;

import com.vsoyou.sdk.http.ResponseParser;
import com.vsoyou.sdk.main.entity.UserLoginEntity;
import com.vsoyou.sdk.util.DESCoder;
import com.vsoyou.sdk.util.LogUtil;

public class UserLoginEntityParser implements ResponseParser<UserLoginEntity> {
	
	private static final String TAG = "UrlEntityParser";

	@Override
	public UserLoginEntity getResponse(Context context, String response) {
		try {
			JSONObject json = new JSONObject(response);
			UserLoginEntity userLoginEntity = new UserLoginEntity();
			userLoginEntity.success = json.getBoolean("success");
			userLoginEntity.code = json.getInt("code");
			if(userLoginEntity.success){
				String msg = DESCoder.decryptoPriAndPub(context, json.getString("message"));
				JSONObject json1 = new JSONObject(msg);
				userLoginEntity.loginId = json1.getLong("loginId");
				JSONObject json2 = new JSONObject(json1.getString("users"));
				userLoginEntity.status = json2.getInt("status");
				try {
					userLoginEntity.eMail = json2.getString("eMail");
				} catch (Exception e) {
					// TODO: handle exception
				}
				userLoginEntity.id = json2.getLong("id");
				userLoginEntity.userName = json2.getString("userName");
				userLoginEntity.passWord = json2.getString("passWord");
			}else{
				userLoginEntity.message = json.getString("message");
			}
			return userLoginEntity;
		} catch (Exception e) {
			LogUtil.e(TAG, "" + e);
			return null;
		}
	}

}
