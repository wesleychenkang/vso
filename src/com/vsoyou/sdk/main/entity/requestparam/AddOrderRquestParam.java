package com.vsoyou.sdk.main.entity.requestparam;

import java.io.Serializable;

import org.json.JSONObject;

import android.content.Context;

import com.vsoyou.sdk.entity.DeviceInfo;
import com.vsoyou.sdk.util.DESCoder;
import com.vsoyou.sdk.util.LogUtil;

public class AddOrderRquestParam extends DeviceInfo implements Serializable {

	private static final long serialVersionUID = -540335917637553877L;

	private Context context;
	private int productId;
	private int serviceId; //游戏分区标示
	private long userId;
	private String userName;
	private long theId; //支付方式ID
	private String orderNo;
	private int totalPrice; //分
	private String resultParam; //自定义参数
	private String cardNum;
	private String cardPwd;
	
	
	
	public void setProductId(int productId) {
		this.productId = productId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setTheId(long theId) {
		this.theId = theId;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public void setResultParam(String resultParam) {
		this.resultParam = resultParam;
	}

	public AddOrderRquestParam(Context context) {
		super(context);
		this.context = context;
	}
	
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public void setCardPwd(String cardPwd) {
		this.cardPwd = cardPwd;
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
			json.put("serviceId", serviceId);
			json.put("userId", userId);
			json.put("userName", userName);
			json.put("theId", theId);
			json.put("orderNo", orderNo);
			json.put("totalPrice", totalPrice);
			json.put("resultParam", resultParam);
			json.put("cardNum", cardNum);
			json.put("cardPass", cardPwd);
			json.put("nImsi", nImsi);
		} catch (Exception e) {
			LogUtil.e("AddOrderRquestParam.toJson", "" + e);
		}
		return DESCoder.encryptoPubAndPri(context, json.toString());
	}

}
