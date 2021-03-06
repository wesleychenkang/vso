package com.vsoyou.sdk.main.entity.requestparam;

import java.io.Serializable;

import org.json.JSONObject;

import android.content.Context;

import com.vsoyou.sdk.entity.DeviceInfo;
import com.vsoyou.sdk.util.DESCoder;
import com.vsoyou.sdk.util.LogUtil;

public class UserCancleOrder extends DeviceInfo implements Serializable {

	private static final long serialVersionUID = 70609822317634175L;

	private Context context;
	private String orderNo;
	
	public UserCancleOrder(Context context, String orderNo) {
		super(context);
		this.context = context;
		this.orderNo = orderNo;
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
			json.put("orderNo", orderNo);
			json.put("nImsi", nImsi);
		} catch (Exception e) {
			LogUtil.e("UserCancleOrder.toJson", "" + e);
		}
		return DESCoder.encryptoPubAndPri(context, json.toString());
	}

}
