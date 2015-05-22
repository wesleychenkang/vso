package com.vsoyou.sdk.main.entity.requestparam;

import java.io.Serializable;

import org.json.JSONObject;

import android.content.Context;

import com.vsoyou.sdk.entity.DeviceInfo;
import com.vsoyou.sdk.util.DESCoder;
import com.vsoyou.sdk.util.LogUtil;

public class AddEmailRequetParam extends DeviceInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Context context;
	private long userId;
	private String userName;
	private String eMail;
	
	public AddEmailRequetParam(Context context,long userId,String userName,String eMail) {
		super(context);
		this.context = context;
		this.userId = userId;
		this.userName = userName;
		this.eMail = eMail;
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
			json.put("userId", userId);
			json.put("eMail", eMail);
			json.put("nImsi", nImsi);
		} catch (Exception e) {
			LogUtil.e("AddEmailRequetParam.toJson", "" + e);
		}
		return DESCoder.encryptoPubAndPri(context, json.toString());
	}

}
