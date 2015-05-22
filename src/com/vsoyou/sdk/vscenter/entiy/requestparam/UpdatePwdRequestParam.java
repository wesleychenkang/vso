package com.vsoyou.sdk.vscenter.entiy.requestparam;

import org.json.JSONObject;

import android.content.Context;

import com.vsoyou.sdk.main.entity.requestparam.CommonRequestParam;
import com.vsoyou.sdk.util.ConfigUtil;
import com.vsoyou.sdk.util.DESCoder;
import com.vsoyou.sdk.vscenter.entiy.User;

public class UpdatePwdRequestParam extends CommonRequestParam {
	private String oldPwd;
	private String newPwd;
	private Context context;
	private User user;

	public UpdatePwdRequestParam(Context context, String oldPwd, String newPwd) {
		super(context);
		this.context = context;
		this.oldPwd = oldPwd;
		this.newPwd = newPwd;
		user = new User(context);
	}

	public String toJson() {
		JSONObject json = new JSONObject();
		try {
			json.put("channelId", ConfigUtil.getChannelId(context));
			json.put("userName", user.getUserName());
			json.put("userId", user.getUserId());
			json.put("oldPwd", oldPwd);
			json.put("newPwd", newPwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return DESCoder.encryptoPubAndPri(context, json.toString());
	}

}
