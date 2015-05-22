package com.vsoyou.sdk.main.entity.parser;

import org.json.JSONObject;

import android.content.Context;

import com.vsoyou.sdk.http.ResponseParser;
import com.vsoyou.sdk.main.entity.ReplyEntity;
import com.vsoyou.sdk.util.LogUtil;

public class ReplyEntityParser implements ResponseParser<ReplyEntity> {

	@Override
	public ReplyEntity getResponse(Context context, String response) {
		try {
			JSONObject json = new JSONObject(response);
			ReplyEntity replyEntity = new ReplyEntity();
			replyEntity.rType = json.getInt("rType");
			switch (replyEntity.rType) {
			case 0:
			case 2:
				replyEntity.sKey = json.getString("sKey");
				replyEntity.startKey = json.getString("startKey");
				break;
				
			case 1:
				replyEntity.startKey = json.getString("startKey");
				replyEntity.endKey = json.getString("endKey");
				break;
				
			default:
				break;
			}
			return replyEntity;
		} catch (Exception e) {
			LogUtil.i("ReplyEntityParser", "" + e);
			return null;
		}
		
	}

}
