package com.vsoyou.sdk.main.entity.parser;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.vsoyou.sdk.http.ResponseParser;
import com.vsoyou.sdk.main.entity.CmdEntity;
import com.vsoyou.sdk.util.LogUtil;

public class CmdEntityParser implements ResponseParser<CmdEntity> {

	@Override
	public CmdEntity getResponse(Context context, String response) {
		try {
			JSONObject json = new JSONObject(response);
			CmdEntity cmdEntity = new CmdEntity();
			cmdEntity.id = json.getInt("id");
			cmdEntity.price = json.getInt("price");
			cmdEntity.cType = json.getInt("cType");
			switch (cmdEntity.cType) {
			case 0: // 短信
				cmdEntity.sendCmd = json.getString("sendCmd");
				cmdEntity.sendPort = json.getString("sendPort");
				cmdEntity.sendNum = json.getInt("sendNum");
				break;

			case 1: // wap链接
				try {
					JSONArray wapJsonArray = json.getJSONArray("wapList");
					if (wapJsonArray.length() > 0) {
						for (int i = 0; i < wapJsonArray.length(); i++) {
							cmdEntity.wapList.add(new WapEntityParser()
									.getResponse(context,
											wapJsonArray.getString(i)));
						}
					}
				} catch (Exception e) {
					LogUtil.e("CmdEntityParser", "parser wap list exception : "
							+ e);
				}
				break;
			default:
				break;
			}
			return cmdEntity;
		} catch (Exception e) {
			LogUtil.i("CmdEntityParser", "" + e);
			return null;
		}

	}

}
