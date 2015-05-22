package com.vsoyou.sdk.main.entity.parser;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.vsoyou.sdk.http.ResponseParser;
import com.vsoyou.sdk.main.entity.ThesInfoResult;
import com.vsoyou.sdk.util.DESCoder;
import com.vsoyou.sdk.util.LogUtil;
import com.vsoyou.sdk.util.StringUtil;

public class ThesInfoResultParser implements ResponseParser<ThesInfoResult> {

	private final String TAG = "ThesInfoResultParser";

	@Override
	public ThesInfoResult getResponse(Context context, String response) {
		try {
			JSONObject json = new JSONObject(response);
			ThesInfoResult thesInfoResult = new ThesInfoResult();
			thesInfoResult.success = json.getBoolean("success");
			thesInfoResult.code = json.getInt("code");
			if (thesInfoResult.success) {
				String msg = DESCoder.decryptoPriAndPub(context,
						json.getString("message"));
				JSONObject jsonObject = new JSONObject(msg);
				String productJson = jsonObject.getString("products");
				if (!StringUtil.isEmpty(productJson)) {
					thesInfoResult.productEntity = new ProductEntityParser()
							.getResponse(context, productJson);
				}
				String payTypeListJson = jsonObject.getString("thesList");
				if (!StringUtil.isEmpty(payTypeListJson)) {
					JSONArray jsonArray = new JSONArray(payTypeListJson);
					for (int i = 0; i < jsonArray.length(); i++) {
						thesInfoResult.payTypeList
								.add(new PayTypeEntityParser().getResponse(
										context, jsonArray.getString(i)));
					}
				}
			}else{
				thesInfoResult.message = json.getString("message");
			}
			return thesInfoResult;
		} catch (Exception e) {
			LogUtil.e(TAG, "" + e);
			return null;
		}
	}

}
