package com.vsoyou.sdk.main.entity.requestparam;

import java.io.Serializable;

import org.json.JSONObject;

import android.content.Context;

import com.vsoyou.sdk.entity.DeviceInfo;
import com.vsoyou.sdk.util.DESCoder;
import com.vsoyou.sdk.util.LogUtil;

public class LoginRequestParam extends DeviceInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Context context;
	public String userName;
	public String passWord;
	
	public LoginRequestParam(Context context, String userName, String passWord) {
		super(context);
		this.context = context;
		this.userName = userName;
		this.passWord = passWord;
	}
	
	public String toJson() {
		JSONObject json = new JSONObject();
		try {
			json.put("channelId", channelId);
			json.put("mac", mac);
			json.put("imei", imei);
			json.put("imsi", imsi);
			json.put("model", model);
			json.put("sdkVer", sdkVer);
			json.put("userName", userName);
			json.put("passWord", passWord);
			json.put("nImsi", nImsi);
		} catch (Exception e) {
			LogUtil.e("LoginRequestParam.toJson", "" + e);
		}
		return DESCoder.encryptoPubAndPri(context, json.toString());
	}
}
