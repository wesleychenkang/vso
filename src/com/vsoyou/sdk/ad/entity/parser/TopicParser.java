package com.vsoyou.sdk.ad.entity.parser;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.vsoyou.sdk.ad.entity.AppEntity;
import com.vsoyou.sdk.ad.entity.Topic;
import com.vsoyou.sdk.http.ResponseParser;
import com.vsoyou.sdk.util.DESCoder;
import com.vsoyou.sdk.util.LogUtil;

public class TopicParser implements ResponseParser<Topic> {
	public static final String TAG = "TopicParser";

	@Override
	public Topic getResponse(Context context, String response) {
		LogUtil.i(TAG, "response-->" + response);
		try {
			JSONObject jsonObject = new JSONObject(response);
			boolean success = jsonObject.getBoolean("success");
			String message = jsonObject.getString("message");
			Topic topic = null;
			if(success){
				JSONObject json = new JSONObject(DESCoder.decryptoPriAndPub(context,message));
				topic = new Topic();
				topic.pageNum = json.getInt("pageNum");
				topic.total = json.getInt("total");
				JSONArray jsonArray = new JSONArray(json.getString("lists"));
				for (int i = 0; i < jsonArray.length(); i++) {
					AppEntity appEntity = new AppEntityParser().getResponse(context, jsonArray.getString(i));
					appEntity.pageNum = topic.pageNum;
					appEntity.total = topic.total;
					topic.appList.add(appEntity);
				}
			}
			return topic;
		} catch (Exception e) {
			LogUtil.e("TopicParser.getResponse", "" + e);
			return null;
		}
	}

}
