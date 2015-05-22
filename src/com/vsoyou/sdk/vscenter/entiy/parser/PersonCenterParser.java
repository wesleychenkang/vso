package com.vsoyou.sdk.vscenter.entiy.parser;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.widget.Toast;

import com.vsoyou.sdk.http.ResponseParser;
import com.vsoyou.sdk.util.DESCoder;
import com.vsoyou.sdk.vscenter.entiy.PersonCetnerResult;

public class PersonCenterParser implements ResponseParser<PersonCetnerResult> {

	@Override
	public PersonCetnerResult getResponse(Context context, String response) {
		try {
			JSONObject json = new JSONObject(response);
			PersonCetnerResult result = new PersonCetnerResult();
			result.success = json.getBoolean("success");
			if (result.success) {
				JSONObject messageJson = new JSONObject(
						DESCoder.decryptoPriAndPub(context,
								json.getString("message")));
				result.phoneNumber = messageJson.getString("phoneNumber");
				result.phoneNumberStatus = messageJson.getBoolean("phoneNumberStatus");
				result.email = messageJson.getString("email");
				result.emailStatus = messageJson.getBoolean("emailStatus");
				result.serviceInfo = messageJson.getString("serviceInfo");
			} else {

				Toast.makeText(context, "请求出错", Toast.LENGTH_SHORT).show();

			}
          return result;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		
	}

}
