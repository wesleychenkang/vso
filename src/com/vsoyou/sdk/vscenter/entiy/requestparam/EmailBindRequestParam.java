package com.vsoyou.sdk.vscenter.entiy.requestparam;

import org.json.JSONObject;

import android.content.Context;

import com.vsoyou.sdk.main.entity.requestparam.CommonRequestParam;
import com.vsoyou.sdk.util.ConfigUtil;
import com.vsoyou.sdk.util.DESCoder;
import com.vsoyou.sdk.vscenter.entiy.User;

public class EmailBindRequestParam extends CommonRequestParam{
	private Context context;
	private User user;
	private String eMail;
	private String smsCode;
	public EmailBindRequestParam(Context context, String eMail,
			String smsCode) {
		super(context);
		this.context = context;
		user = new User(context);
		this.eMail = eMail;
		this.smsCode = smsCode;

	}

	public String toJson() {
		JSONObject json = new JSONObject();
		try {
			json.put("channelId", ConfigUtil.getChannelId(context));
			json.put("userName", user.getUserName());
			json.put("userId", user.getUserId());
			json.put("eMail", eMail);
			json.put("smsCode", smsCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return DESCoder.encryptoPubAndPri(context, json.toString());
	}
}
