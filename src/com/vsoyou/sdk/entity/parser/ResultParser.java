package com.vsoyou.sdk.entity.parser;

import org.json.JSONObject;

import android.content.Context;

import com.vsoyou.sdk.entity.Result;
import com.vsoyou.sdk.http.ResponseParser;
import com.vsoyou.sdk.util.LogUtil;

public class ResultParser implements ResponseParser<Result>{

	@Override
	public Result getResponse(Context context, String response) {
		try {
			JSONObject json = new JSONObject(response);
			Result result = new Result();
			result.success = json.getBoolean("success");
			result.message = json.getString("message");
			try {
				result.code = json.getInt("code");
			} catch (Exception e) {
				LogUtil.e("ResultParser.getResponse_1", "" + e);
			}
			return result;
		} catch (Exception e) {
			LogUtil.e("ResultParser.getResponse", "" + e);
			return null;
		}
	}

}
