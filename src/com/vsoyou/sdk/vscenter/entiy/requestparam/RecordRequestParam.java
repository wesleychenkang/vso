package com.vsoyou.sdk.vscenter.entiy.requestparam;

import org.json.JSONObject;

import android.content.Context;

import com.vsoyou.sdk.main.entity.requestparam.CommonRequestParam;
import com.vsoyou.sdk.util.ConfigUtil;
import com.vsoyou.sdk.util.DESCoder;
import com.vsoyou.sdk.vscenter.entiy.User;

public class RecordRequestParam extends CommonRequestParam{
    private Context context;
    private User user ;
    private int page;
	public RecordRequestParam(Context context,int page) {
		super(context);
		this.context = context;
		user = new User(context);
		this.page = page;
	}
	
	
	public String toJson() {
		JSONObject json = new JSONObject();
		try {
			json.put("channelId", ConfigUtil.getChannelId(context));
			json.put("userName", user.getUserName());
			json.put("userId", user.getUserId());
			json.put("page", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return DESCoder.encryptoPubAndPri(context, json.toString());
	}
	 

}
