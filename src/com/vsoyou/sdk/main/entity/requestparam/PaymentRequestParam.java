package com.vsoyou.sdk.main.entity.requestparam;

import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.vsoyou.sdk.entity.DeviceInfo;
import com.vsoyou.sdk.util.DESCoder;
import com.vsoyou.sdk.util.LogUtil;

public class PaymentRequestParam extends DeviceInfo {

	private Context context;
	private int productId; // 商品Id
	private String orderNo; // 订单号
	private int netType;// 网络类型

	public PaymentRequestParam(Context context, int productId, String orderNo) {
		super(context);
		this.context = context;
		this.productId = productId;
		this.orderNo = orderNo;
		initNetType();
	}

	private void initNetType() {
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		if (telephonyManager != null) {
			switch (telephonyManager.getNetworkType()) {
			case TelephonyManager.NETWORK_TYPE_1xRTT:
			case TelephonyManager.NETWORK_TYPE_CDMA:
			case TelephonyManager.NETWORK_TYPE_EDGE:
			case TelephonyManager.NETWORK_TYPE_GPRS:
			case TelephonyManager.NETWORK_TYPE_IDEN:
			case TelephonyManager.NETWORK_TYPE_UNKNOWN:
				netType = 2;
				break;
			case TelephonyManager.NETWORK_TYPE_EVDO_0:
			case TelephonyManager.NETWORK_TYPE_EVDO_A:
			case TelephonyManager.NETWORK_TYPE_HSDPA:
			case TelephonyManager.NETWORK_TYPE_HSPA:
			case TelephonyManager.NETWORK_TYPE_HSUPA:
			case TelephonyManager.NETWORK_TYPE_UMTS:
				netType = 3;
				break;
			default:
				netType = 0;
			}
			String proxyHost = android.net.Proxy.getDefaultHost();
			if (proxyHost != null)
				netType = 4; // Wap上网
		}

		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager != null) {
			NetworkInfo networkInfo = connectivityManager
					.getActiveNetworkInfo();// 获取网络的连接情况
			if (networkInfo != null) {
				if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
					netType = 1;
				}
			}
		}
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
			json.put("orderNo", orderNo);
			json.put("netType", netType);
			json.put("nImsi", nImsi);
		} catch (Exception e) {
			LogUtil.e("PaymentRequestParam.toJson", "" + e);
		}
		return DESCoder.encryptoPubAndPri(context, json.toString());
	}

}
