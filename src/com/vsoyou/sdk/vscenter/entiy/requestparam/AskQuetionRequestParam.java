package com.vsoyou.sdk.vscenter.entiy.requestparam;

import org.json.JSONObject;

import android.content.Context;

import com.vsoyou.sdk.main.entity.requestparam.CommonRequestParam;
import com.vsoyou.sdk.util.ConfigUtil;
import com.vsoyou.sdk.util.DESCoder;
import com.vsoyou.sdk.vscenter.entiy.User;

public class AskQuetionRequestParam extends CommonRequestParam {
	private Context context;
	private User user;
	private String questionContent;
	private String phoneNumber;
	private String email;

	public AskQuetionRequestParam(Context context,
			String questionContent,String phoneNumber,String email) {
		super(context);
		this.context = context;
		user = new User(context);
		this.questionContent = questionContent;
		this.phoneNumber = phoneNumber;
		this.email = email;

	}

	public String toJson() {
		JSONObject json = new JSONObject();
		try {
			json.put("channelId", ConfigUtil.getChannelId(context));
			json.put("userName", user.getUserName());
			json.put("userId", user.getUserId());
			json.put("questionContent", questionContent);
			json.put("phoneNumber",phoneNumber);
			json.put("eMail", email);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return DESCoder.encryptoPubAndPri(context, json.toString());
	}

}
