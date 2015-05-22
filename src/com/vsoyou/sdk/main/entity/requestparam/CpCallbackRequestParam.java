package com.vsoyou.sdk.main.entity.requestparam;

import org.json.JSONObject;

import android.content.Context;

import com.vsoyou.sdk.entity.DeviceInfo;
import com.vsoyou.sdk.util.DESCoder;
import com.vsoyou.sdk.util.LogUtil;

public class CpCallbackRequestParam extends DeviceInfo {

	private Context context;
	private int productId;
	private String orderNum; // 订单号
	private String payType;
	private int price;
	private long userId;
	private String userName;

	public CpCallbackRequestParam(Context context, int productId,
			String orderNum, String payType, int price, long userId,
			String userName) {
		super(context);
		this.context = context;
		this.productId = productId;
		this.orderNum = orderNum;
		this.payType = payType;
		this.price = price;
		this.userId = userId;
		this.userName = userName;
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
			json.put("orderNo", orderNum);
			json.put("payType", payType);
			json.put("price", price);
			json.put("userId", userId);
			json.put("userName", userName);
			json.put("nImsi", nImsi);
		} catch (Exception e) {
			LogUtil.e("CpCallbackRequestParam.toJson", "" + e);
		}
		return DESCoder.encryptoPubAndPri(context, json.toString());
	}

}
