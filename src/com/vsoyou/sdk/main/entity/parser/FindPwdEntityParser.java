package com.vsoyou.sdk.main.entity.parser;

import org.json.JSONObject;

import android.content.Context;

import com.vsoyou.sdk.http.ResponseParser;
import com.vsoyou.sdk.main.entity.FindPwdEntity;
import com.vsoyou.sdk.util.DESCoder;
import com.vsoyou.sdk.util.LogUtil;

public class FindPwdEntityParser implements ResponseParser<FindPwdEntity> {
	
	private static final String TAG = "FindPwdEntityParser";

	@Override
	public FindPwdEntity getResponse(Context context, String response) {
		try {
			JSONObject json = new JSONObject(response);
			FindPwdEntity findPwdEntity = new FindPwdEntity();
			findPwdEntity.success = json.getBoolean("success");
			findPwdEntity.code = json.getInt("code");
			if(findPwdEntity.success){
				String msg = DESCoder.decryptoPriAndPub(context, json.getString("message"));
				JSONObject json1 = new JSONObject(msg);
				JSONObject json2 = new JSONObject(json1.getString("users"));
				findPwdEntity.status = json2.getInt("status");
				if(findPwdEntity.status == 1){
					findPwdEntity.eMail = json2.getString("eMail");
				}
			}else{
				findPwdEntity.message = json.getString("message");
			}
			return findPwdEntity;
		} catch (Exception e) {
			LogUtil.e(TAG, "" + e);
			return null;
		}
	}

}
