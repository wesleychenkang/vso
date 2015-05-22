package com.vsoyou.sdk.ad.entity;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.vsoyou.sdk.entity.DeviceInfo;
import com.vsoyou.sdk.util.DESCoder;
import com.vsoyou.sdk.util.LogUtil;

public class GetMoreRequestParam extends DeviceInfo {
	
	private Context context;
	
	public int page;
	public int adId;
	
	public GetMoreRequestParam(Context context,int page,int adId) {
		super(context);
		this.context = context;
		this.page = page;
		this.adId = adId;
	}
	
	public String toJSON() {

//		JSONObject jso = new JSONObject();
		JSONObject jso1 = new JSONObject();
		try {
//			jso.put("sdkCode", sdkCode);
//			jso.put("appId", appId);
//			
//			JSONObject jso1 = new JSONObject();
			jso1.put("adId", adId);
			jso1.put("page", page);
			jso1.put("channelId", channelId);
			jso1.put("mac", mac);
			jso1.put("imei", imei);
			jso1.put("imsi", imsi);
			jso1.put("model", model);
			jso1.put("sdkVer", sdkVer);
			jso1.put("nImsi", nImsi);
//			String param = DESCoder.ebotongEncrypto(
//					ConfigUtil.getAppKey(context),
//					DESCoder.ebotongEncrypto(Constants.PUBLIC_KEY,
//							jso.toString()));
			
//			jso.put("param", param);
		} catch (JSONException ex) {
			LogUtil.e("GetMoreRequestParam.toJson", "" + ex);
		}
		return DESCoder.encryptoPubAndPri(context,
				jso1.toString());
	}

}
