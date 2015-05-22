package com.vsoyou.sdk.vscenter.entiy.requestparam;

import org.json.JSONObject;

import android.content.Context;

import com.vsoyou.sdk.main.entity.requestparam.CommonRequestParam;
import com.vsoyou.sdk.util.ConfigUtil;
import com.vsoyou.sdk.util.DESCoder;
import com.vsoyou.sdk.vscenter.entiy.User;

public class QuetionListRequestParam extends CommonRequestParam {
	private Context context;
	private User user;
	private int status;
	private int page;

	public QuetionListRequestParam(Context context, int status, int page) {
		super(context);
		this.status = status;
		this.page = page;
		this.context = context;
		user = new User(context);
	}

	public String toJson() {
		JSONObject json = new JSONObject();
		try {
			json.put("channelId", ConfigUtil.getChannelId(context));
			json.put("userName", user.getUserName());
			json.put("userId", user.getUserId());
			json.put("status", status);
			json.put("page", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return DESCoder.encryptoPubAndPri(context, json.toString());
	}

}
