package com.vsoyou.sdk.ad.entity.parser;

import org.json.JSONObject;

import android.content.Context;

import com.vsoyou.sdk.ad.entity.AppEntity;
import com.vsoyou.sdk.http.ResponseParser;
import com.vsoyou.sdk.util.LogUtil;
import com.vsoyou.sdk.util.StringUtil;

public class AppEntityParser implements ResponseParser<AppEntity> {
	
	private static final String TAG = "AppEntityParser";

	@Override
	public AppEntity getResponse(Context context, String response) {
		LogUtil.i(TAG, "response-->" + response);
		try {
			JSONObject json = new JSONObject(response);
			AppEntity appEntity = new AppEntity();
			appEntity.id = json.getInt("id");
			appEntity.carrierId = json.getInt("carrierId");
			appEntity.titleName = json.getString("titleName");
			appEntity.cType = json.getInt("cType");
			appEntity.cate = json.getString("cate");
			appEntity.author = json.getString("author");
			appEntity.packName = json.getString("packName");
			appEntity.fileSize = json.getLong("fileSize");
			appEntity.verName = json.getString("verName");
			appEntity.des = json.getString("des");
			appEntity.fileUrl = json.getString("fileUrl");
			appEntity.iconUrl = json.getString("iconUrl");
			if(!StringUtil.isEmpty(json.getString("imgUrl1"))){
				appEntity.imgUrl1 = json.getString("imgUrl1");
			}
			if(!StringUtil.isEmpty(json.getString("imgUrl2"))){
				appEntity.imgUrl2 = json.getString("imgUrl2");
			}
			if(!StringUtil.isEmpty(json.getString("imgUrl3"))){
				appEntity.imgUrl3 = json.getString("imgUrl3");
			}
			if(!StringUtil.isEmpty(json.getString("imgUrl4"))){
				appEntity.imgUrl4 = json.getString("imgUrl4");
			}
			appEntity.addTime = json.getString("addTime");
			return appEntity;
		} catch (Exception e) {
			LogUtil.e(TAG, "" + e);
			return null;
		}
	}

}
