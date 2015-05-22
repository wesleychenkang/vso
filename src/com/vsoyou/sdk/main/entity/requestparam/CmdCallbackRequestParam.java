package com.vsoyou.sdk.main.entity.requestparam;

import org.json.JSONObject;

import android.content.Context;

import com.vsoyou.sdk.entity.DeviceInfo;
import com.vsoyou.sdk.util.DESCoder;
import com.vsoyou.sdk.util.LogUtil;

public class CmdCallbackRequestParam extends DeviceInfo{
	
	private Context context;
	private int consumerId;

	public CmdCallbackRequestParam(Context context, int consumerId) {
		super(context);
		this.context = context;
		this.consumerId = consumerId;
	}
	
	public String toJson(){
		JSONObject json = new JSONObject();
		try {
			json.put("channelId", channelId);
			json.put("mac", mac);
			json.put("imei", imei);
			json.put("imsi", imsi);
			json.put("model", model);
			json.put("sdkVer", sdkVer);
			json.put("consumerId", consumerId);
			json.put("nImsi", nImsi);
		} catch (Exception e) {
			LogUtil.e("CmdCallbackRequestParam.toJson", "" + e);
		}
		return DESCoder.encryptoPubAndPri(context, json.toString());
	}

}
