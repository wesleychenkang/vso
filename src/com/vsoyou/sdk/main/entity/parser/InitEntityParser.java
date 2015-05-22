package com.vsoyou.sdk.main.entity.parser;

import org.json.JSONObject;

import android.content.Context;

import com.vsoyou.sdk.http.ResponseParser;
import com.vsoyou.sdk.main.entity.InitEntity;
import com.vsoyou.sdk.main.entity.UrlEntity;
import com.vsoyou.sdk.util.DESCoder;
import com.vsoyou.sdk.util.LogUtil;

public class InitEntityParser implements ResponseParser<InitEntity> {
	
	public static final String TAG = "InitEntityParser";

	@Override
	public InitEntity getResponse(Context context, String response) {
		try {
			JSONObject json = new JSONObject(response);
			InitEntity initEntity = new InitEntity();
			initEntity.success = json.getBoolean("success");
			LogUtil.i("InitEntityParser.getResponse", "json-->" + response);
			if(initEntity.success){
				ResponseParser<UrlEntity> urlEntityParser = new UrlEntityParser();
				String msg = DESCoder.decryptoPriAndPub(context, json.getString("message"));
				initEntity.urlEntity = urlEntityParser.getResponse(context, msg);
			}else{
				initEntity.msg = json.getString("message");
				LogUtil.i(TAG, "initEntity.msg" + initEntity.msg);
			}
			return initEntity;
		} catch (Exception e) {
			LogUtil.e(TAG, "" + e);
			return null;
		}
	}

}
