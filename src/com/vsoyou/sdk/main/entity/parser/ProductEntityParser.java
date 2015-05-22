package com.vsoyou.sdk.main.entity.parser;

import org.json.JSONObject;

import android.content.Context;

import com.vsoyou.sdk.http.ResponseParser;
import com.vsoyou.sdk.main.entity.ProductEntity;
import com.vsoyou.sdk.util.LogUtil;

public class ProductEntityParser implements ResponseParser<ProductEntity>{
	
	public static final String TAG = "ProductEntityParser";

	@Override
	public ProductEntity getResponse(Context context, String response) {
		try {
			JSONObject json = new JSONObject(response);
			ProductEntity productEntity = new ProductEntity();
			productEntity.id = json.getInt("id");
			productEntity.appName = json.getString("appName");
			productEntity.appId = json.getInt("appId");
			productEntity.price = json.getInt("price");
			productEntity.status = json.getInt("status");
			productEntity.titleName = json.getString("titleName");
			productEntity.addTime = json.getString("addTime");
			return productEntity;
		} catch (Exception e) {
			LogUtil.e(TAG, "" + e);
			return null;
		}
	}


}
