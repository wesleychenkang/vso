package com.vsoyou.sdk.main.entity.requestparam;

import java.io.Serializable;

import org.json.JSONObject;

import android.content.Context;

import com.vsoyou.sdk.entity.DeviceInfo;
import com.vsoyou.sdk.util.DESCoder;
import com.vsoyou.sdk.util.LogUtil;

public class GetPayCateRequestParam extends DeviceInfo implements Serializable {

	private static final long serialVersionUID = -6464937608692780165L;

	private Context context;
	private int productId;
	
	public GetPayCateRequestParam(Context context, int productId) {
		super(context);
		this.context = context;
		this.productId = productId;
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
			json.put("productId", productId);
			json.put("nImsi", nImsi);
		} catch (Exception e) {
			LogUtil.e("GetPayCateRequestParam.toJson", "" + e);
		}
		return DESCoder.encryptoPubAndPri(context, json.toString());
	}

}
