package com.vsoyou.sdk.vscenter.entiy.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.vsoyou.sdk.http.ResponseParser;
import com.vsoyou.sdk.util.DESCoder;
import com.vsoyou.sdk.vscenter.entiy.Quetion;
import com.vsoyou.sdk.vscenter.entiy.QuetionsResult;

public class QuetionListParse implements ResponseParser<QuetionsResult> {

	@Override
	public QuetionsResult getResponse(Context context, String response) {
		try {
			JSONObject obj = new JSONObject(response);
			QuetionsResult result = new QuetionsResult();
			result.success = obj.getBoolean("success");
			if (result.success) {
				JSONObject message = new JSONObject(DESCoder.decryptoPriAndPub(
						context, obj.getString("message")));
			   result.totalAll = message.getInt("totalAll");
			   result.totalWai = message.getInt("totalWai");
			   result.totalYes = message.getInt("totalYes");
			   result.total = message.getInt("total");
			   result.pageNum = message.getInt("pageNum");
			   JSONArray array = message.getJSONArray("lists");
			   JSONObject item;
			   Quetion quetion;
			   for(int i=0;i<array.length();i++){
				   item = array.getJSONObject(i);
				   quetion = new Quetion();
				   quetion.Id = item.getLong("id");
				   quetion.userId = item.getLong("userId");
				   quetion.appName = item.getString("appName");
				   quetion.questionContent = item.getString("questionContent");
				   quetion.reply = item.getString("reply");
				   quetion.status = item.getInt("status");
				   quetion.addTime = item.getString("addTime");
				   quetion.repTime = item.getString("repTime");
				   result.list.add(quetion);
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
