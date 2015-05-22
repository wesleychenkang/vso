package com.vsoyou.sdk.vscenter.entiy.requestparam;

import org.json.JSONObject;

import android.content.Context;

import com.vsoyou.sdk.main.entity.requestparam.CommonRequestParam;
import com.vsoyou.sdk.util.ConfigUtil;
import com.vsoyou.sdk.util.DESCoder;
import com.vsoyou.sdk.vscenter.entiy.User;

public class PhoneBindRequstParam extends CommonRequestParam {

	private Context context;
	private User user;
	private String phoneNumber;
	private String smsCode;

	public PhoneBindRequstParam(Context context, String phoneNumber,
			String smsCode) {
		super(context);
		this.context = context;
		user = new User(context);
		this.phoneNumber = phoneNumber;
		this.smsCode = smsCode;

	}

	public String toJson() {
		JSONObject json = new JSONObject();
		try {
			json.put("channelId", ConfigUtil.getChannelId(context));
			json.put("userName", user.getUserName());
			json.put("userId", user.getUserId());
			json.put("phoneNumber", phoneNumber);
			json.put("smsCode", smsCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return DESCoder.encryptoPubAndPri(context, json.toString());
	}

}
