package com.vsoyou.sdk.main;

import java.io.Serializable;

public class PayCallbackInfo implements Serializable {

	private static final long serialVersionUID = -6302398432671662954L;
	
	public String orderNo;
	public int appId;
	public int channelId;
	public int productId;
	public long userId;
	public int price;
	public String extra;
	public String payType;
	public String userName;
	public int statusCode;
	public String statusDes;
	
	public PayCallbackInfo() {
		
	}
	
	public PayCallbackInfo(String orderNo, int appId, int channelId,
			int productId, long userId, int price, String extra,
			String payType, String userName, int statusCode, String statusDes) {
		super();
		this.orderNo = orderNo;
		this.appId = appId;
		this.channelId = channelId;
		this.productId = productId;
		this.userId = userId;
		this.price = price;
		this.extra = extra;
		this.payType = payType;
		this.userName = userName;
		this.statusCode = statusCode;
		this.statusDes = statusDes;
	}

	@Override
	public String toString() {
		return "PayCallbackInfo [orderNo=" + orderNo + ", appId=" + appId
				+ ", channelId=" + channelId + ", productId=" + productId
				+ ", userId=" + userId + ", price=" + price + ", extra="
				+ extra + ", payType=" + payType + ", userName=" + userName
				+ ", statusCode=" + statusCode + ", statusDes=" + statusDes
				+ "]";
	}

}
