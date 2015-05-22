package com.vsoyou.sdk.vscenter.entiy.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.vsoyou.sdk.http.ResponseParser;
import com.vsoyou.sdk.util.DESCoder;
import com.vsoyou.sdk.vscenter.entiy.Record;
import com.vsoyou.sdk.vscenter.entiy.RecordListResult;

public class RecordListParse implements ResponseParser<RecordListResult> {

	@Override
	public RecordListResult getResponse(Context context, String response) {
		try {
			JSONObject obj = new JSONObject(response);
			RecordListResult result = new RecordListResult();
			result.success =obj.getBoolean("success");
			if(result.success){
			JSONObject message = new JSONObject(DESCoder.decryptoPriAndPub(context,
					obj.getString("message")));
			result.pageNum = message.getInt("pageNum");
			result.total = message.getInt("total");
			JSONArray array = message.getJSONArray("lists");
			Record record;
			for (int i = 0; i < array.length(); i++) {
				record = new Record();
				JSONObject json = array.getJSONObject(i);
				record.Id = json.getLong("id");
				record.userId = json.getLong("userId");
				record.userName =json.getString("userName");
				record.appName = json.getString("appName");
				record.productName = json.getString("productName");
				record.serviceId = json.getString("serviceId");
				record.theName = json.getString("theName");
				record.orderNo = json.getString("orderNo");
				record.price = json.getInt("totalPrice");
				record.status = json.getInt("status");
				record.addTime = json.getString("addTime");
				result.lists.add(record);
			}
			
			return result;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		return null;
	}

}
