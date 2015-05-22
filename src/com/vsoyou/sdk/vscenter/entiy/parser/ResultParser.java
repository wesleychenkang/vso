package com.vsoyou.sdk.vscenter.entiy.parser;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.vsoyou.sdk.entity.Result;
import com.vsoyou.sdk.http.ResponseParser;

public class ResultParser implements ResponseParser<Result> {

	@Override
	public Result getResponse(Context context, String response) {
		// TODO Auto-generated method stub
		try {
		JSONObject obj = new JSONObject(response);
		Result result = new Result();
		result.success = obj.getBoolean("success");
		return result;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
		 e.printStackTrace();
		}
		return null;
	}

}
