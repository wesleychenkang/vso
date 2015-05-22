package com.vsoyou.sdk.vscenter.entiy.requestparam;

import org.json.JSONObject;

import android.content.Context;

import com.vsoyou.sdk.main.entity.requestparam.CommonRequestParam;
import com.vsoyou.sdk.util.ConfigUtil;
import com.vsoyou.sdk.util.DESCoder;

public class PersonCenterRequestParam extends CommonRequestParam {
	private Context context;
	private long userId;
	private String userName;

	public PersonCenterRequestParam(Context context,long userId,String userName) {
		super(context);
		this.userId = userId;
		this.userName = userName;
		this.context =context;
	}
	
	public String toJson(){
		JSONObject json = new JSONObject();
		try{
			json.put("channelId", ConfigUtil.getChannelId(context));	
			json.put("userName", userName);
			json.put("userId", userId);
		}catch(Exception e){
			e.printStackTrace();
		}
		return  DESCoder.encryptoPubAndPri(context,json.toString());
	}

}
