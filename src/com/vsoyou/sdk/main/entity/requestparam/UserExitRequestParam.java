package com.vsoyou.sdk.main.entity.requestparam;

import java.io.Serializable;

import org.json.JSONObject;

import android.content.Context;

import com.vsoyou.sdk.entity.DeviceInfo;
import com.vsoyou.sdk.util.DESCoder;
import com.vsoyou.sdk.util.LogUtil;

public class UserExitRequestParam extends DeviceInfo implements Serializable {

	private static final long serialVersionUID = -6319912487741182269L;

	private Context context;
	private long loginId;
	
	public UserExitRequestParam(Context context, long loginId) {
		super(context);
		this.context = context;
		this.loginId = loginId;
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
			json.put("loginId", loginId);
			json.put("nImsi", nImsi);
		} catch (Exception e) {
			LogUtil.e("UserExitRequestParam.toJson", "" + e);
		}
		return DESCoder.encryptoPubAndPri(context, json.toString());
	}

}
