package com.vsoyou.sdk.main.entity.parser;

import org.json.JSONObject;

import android.content.Context;

import com.vsoyou.sdk.http.ResponseParser;
import com.vsoyou.sdk.main.entity.PayTypeEntity;
import com.vsoyou.sdk.util.LogUtil;

public class PayTypeEntityParser implements ResponseParser<PayTypeEntity> {
	
	private static final String TAG = "PayTypeEntityParser";

	@Override
	public PayTypeEntity getResponse(Context context, String response) {
		try {
			JSONObject json = new JSONObject(response);
			PayTypeEntity payTypeEntity = new PayTypeEntity();
			payTypeEntity.id = json.getInt("id");
			payTypeEntity.titleName = json.getString("titleName");
			payTypeEntity.enName = json.getString("enName");
			payTypeEntity.pid = json.getString("pid");
			payTypeEntity.seller_id = json.getString("seller_id");
			payTypeEntity.keyType = json.getString("keyType");
			payTypeEntity.public_Key = json.getString("public_Key");
			if("ALIPAY".equals(payTypeEntity.enName)){
				payTypeEntity.private_Key = json.getString("private_Key");
			}else{
				payTypeEntity.request_url = json.getString("request_url");
			}
			payTypeEntity.notify_url = json.getString("notify_url");
			return payTypeEntity;
		} catch (Exception e) {
			LogUtil.e(TAG, "" + e);
			return null;
		}
	}

}
