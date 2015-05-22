package com.vsoyou.sdk.main.entity.parser;

import org.json.JSONObject;

import android.content.Context;

import com.vsoyou.sdk.http.ResponseParser;
import com.vsoyou.sdk.main.entity.WapEntity;
import com.vsoyou.sdk.util.LogUtil;

public class WapEntityParser implements ResponseParser<WapEntity> {

	@Override
	public WapEntity getResponse(Context context, String response) {
		try {
			JSONObject json = new JSONObject(response);
			WapEntity wapEntity = new WapEntity();
			wapEntity.wType = json.getInt("wType");
			wapEntity.wapUrl = json.getString("wapUrl");
			wapEntity.sKey = json.getString("sKey");
			wapEntity.startKey = json.getString("startKey");
			wapEntity.endKey = json.getString("endKey");
			return wapEntity;
		} catch (Exception e) {
			LogUtil.e("WapEntityParser", "" + e);
			return null;
		}
	}

}
