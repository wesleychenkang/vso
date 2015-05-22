package com.vsoyou.sdk.ad.entity.parser;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.vsoyou.sdk.ad.AdConstants;
import com.vsoyou.sdk.ad.entity.AdEntity;
import com.vsoyou.sdk.ad.entity.AppEntity;
import com.vsoyou.sdk.http.ResponseParser;
import com.vsoyou.sdk.util.DESCoder;
import com.vsoyou.sdk.util.LocalStorage;
import com.vsoyou.sdk.util.LogUtil;
import com.vsoyou.sdk.util.StringUtil;

public class AdEntityParser implements ResponseParser<AdEntity> {

	private static final String TAG = "AdEntityParser";

	@Override
	public AdEntity getResponse(Context context, String response) {
		LogUtil.i(TAG, "response-->" + response);
		try {
			JSONObject jsonResult = new JSONObject(response);
			boolean success = jsonResult.getBoolean("success");
			String message = jsonResult.getString("message");
			AdEntity adEntity = null;
			if(success){
				JSONObject json = new JSONObject(DESCoder.decryptoPriAndPub(context, message));
				LogUtil.i(TAG, "json-->" + json.toString());
				adEntity = new AdEntity();
				adEntity.id = json.getInt("id");
				adEntity.locaType = json.getInt("locaType");
				switch (adEntity.locaType) {
				case AdConstants.AD_TYPE_CW: // 0通知栏(纯文)
					adEntity.title = json.getString("titleName");
					adEntity.titleLine = json.getString("titleLine");
					break;

				case AdConstants.AD_TYPE_CT:// 1通知栏(纯图)
					adEntity.imgUrl = json.getString("imgUrl");
					break;

				case AdConstants.AD_TYPE_TW:// 2通知栏(图文)
					adEntity.title = json.getString("titleName");
					adEntity.imgUrl = json.getString("imgUrl");
					adEntity.titleLine = json.getString("titleLine");
					break;

				case AdConstants.AD_TYPE_TCDINGT:// 3弹窗顶(图片)
					adEntity.imgUrl = json.getString("imgUrl");
					break;

				case AdConstants.AD_TYPE_TCZT:// 4弹窗中(图片)
					adEntity.imgUrl = json.getString("imgUrl");
					break;

				case AdConstants.AD_TYPE_TCDIT:// 5弹窗底(图片)
					adEntity.imgUrl = json.getString("imgUrl");
					break;

				default:
					break;
				}

				adEntity.eventType = json.getInt("eventType");
				switch (adEntity.eventType) {
				case AdConstants.AD_EVENT_BROWSE:
					adEntity.httpUrl = json.getString("httpUrl");
					break;
				case AdConstants.AD_EVENT_DETAIL:
					LogUtil.i(TAG, "5");
					adEntity.carrierId = json.getInt("carrierId");
					if (!StringUtil.isEmpty(json.getString("carriers"))) {
						AppEntityParser adAppEntityParser = new AppEntityParser();
						adEntity.carriers = adAppEntityParser.getResponse(context,
								json.getString("carriers"));
					}
					break;
				case AdConstants.AD_EVENT_SUBJECT:
					LogUtil.i(TAG, "4");
					if (!StringUtil.isEmpty(json.getString("carrierlist"))) {
						JSONObject jsonObject = new JSONObject(json.getString("carrierlist"));
						int total = jsonObject.getInt("total");
						int pageNum = jsonObject.getInt("pageNum");
						JSONArray jsonArray = new JSONArray(jsonObject.getString("lists"));
						int jsonArrayLength = jsonArray.length();
						AppEntityParser adAppEntityParser = new AppEntityParser();
						for (int i = 0; i < jsonArrayLength; i++) {
							AppEntity appEntity = adAppEntityParser.getResponse(
									context, jsonArray.getString(i));
							appEntity.total = total;
							appEntity.pageNum = pageNum;
							adEntity.carrierlist.add(appEntity);
						}
					}
					break;

				default:
					break;
				}
				LogUtil.i(TAG, "1");
				adEntity.showNum = json.getInt("showNum");
				adEntity.showHour = json.getInt("showHour");
				adEntity.cyc = json.getInt("cyc");
				LogUtil.i(TAG, "2");
				LocalStorage localStorage = LocalStorage.getInstance(context);
				localStorage.putString(AdConstants.AD_CONTENT, response);
				localStorage.putInt(AdConstants.AD_SHOW_CYC, adEntity.showHour);
				localStorage.putInt(AdConstants.AD_CYC, adEntity.cyc);
				LogUtil.i(TAG, "3");
			}
			
			return adEntity;
		} catch (Exception e) {
			LogUtil.e(TAG, "" + e);
			
			return null;
		}
	}

}
