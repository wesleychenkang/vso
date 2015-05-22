package com.vsoyou.sdk.ad.entity;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.vsoyou.sdk.entity.DeviceInfo;
import com.vsoyou.sdk.util.DESCoder;

public class AdCallbackRequestParam extends DeviceInfo {
	
	public String dataType;
	public int adId;
	public int carrierId;
	
	public Context context;

	public AdCallbackRequestParam(Context context, String dataType, int adId, int carrierId) {
		super(context);
		this.context = context;
		this.carrierId = carrierId;
		this.dataType = dataType;
		this.adId = adId;
	}
	
	public String toJSON() {

//		JSONObject jso = new JSONObject();
		JSONObject jso1 = new JSONObject();
		try {
//			JSONObject jso1 = new JSONObject();
			jso1.put("channelId", channelId);
			jso1.put("mac", mac);
			jso1.put("imei", imei);
			jso1.put("imsi", imsi);
			jso1.put("model", model);
			jso1.put("sdkVer", sdkVer);
			jso1.put("dataType", dataType);
			jso1.put("adId", adId);
			jso1.put("carrierId", carrierId);
		    jso1.put("nImsi", nImsi);
//			jso.put("param", DESCoder.ebotongEncrypto(
//					ConfigUtil.getAppKey(context),
//					DESCoder.ebotongEncrypto(Constants.PUBLIC_KEY,
//							jso1.toString())));
//			jso.put("appId", appId);
//			jso.put("sdkCode", sdkCode);
		} catch (JSONException ex) {
			// Ignore
		}
		return  DESCoder.encryptoPubAndPri(context,
						jso1.toString());
	}

}
