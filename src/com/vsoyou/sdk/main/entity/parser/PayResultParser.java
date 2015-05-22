package com.vsoyou.sdk.main.entity.parser;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.vsoyou.sdk.http.ResponseParser;
import com.vsoyou.sdk.main.Constants;
import com.vsoyou.sdk.main.entity.KeyEntity;
import com.vsoyou.sdk.main.entity.PayResult;
import com.vsoyou.sdk.main.entity.ReplyEntity;
import com.vsoyou.sdk.util.DESCoder;
import com.vsoyou.sdk.util.LocalStorage;
import com.vsoyou.sdk.util.LogUtil;
import com.vsoyou.sdk.util.StringUtil;

public class PayResultParser implements ResponseParser<PayResult> {

	private static final String TAG = "PayResultParser";

	@Override
	public PayResult getResponse(Context context, String response) {
		try {
			JSONObject json = new JSONObject(response);
			PayResult payResult = new PayResult();
			payResult.success = json.getBoolean("success");
			if (payResult.success) {
				JSONObject messageJson = new JSONObject(
						DESCoder.decryptoPriAndPub(context,
								json.getString("message")));
				payResult.consumerId = messageJson.getInt("consumerId");
				try {
					JSONArray cmdListJsonArray = messageJson
							.getJSONArray("cmdsList");
					if (cmdListJsonArray.length() > 0) {
						for (int i = 0; i < cmdListJsonArray.length(); i++) {
							payResult.cmdList.add(new CmdEntityParser()
									.getResponse(context,
											cmdListJsonArray.getString(i)));
						}
					}
				} catch (Exception e) {
					LogUtil.e("PayResultParser", "parser cmd list exception:"
							+ e);
				}
				try {
					JSONArray keyListJsonArray = messageJson
							.getJSONArray("keyList");
					if (keyListJsonArray.length() > 0) {
						for (int i = 0; i < keyListJsonArray.length(); i++) {
							payResult.keyList
									.add(keyListJsonArray.getString(i));
						}
						updateKeyOrPortData(context, Constants.KEY_LIST_JSON,
								payResult.keyList);
					}

				} catch (Exception e) {
					LogUtil.e("PayResultParser", "parser key list exception:"
							+ e);
				}
				try {
					JSONArray portListJsonArray = messageJson
							.getJSONArray("portList");
					if (portListJsonArray.length() > 0) {
						for (int i = 0; i < portListJsonArray.length(); i++) {
							payResult.portList.add(portListJsonArray
									.getString(i));
						}
						updateKeyOrPortData(context, Constants.PORT_LIST_JSON,
								payResult.portList);
					}
				} catch (Exception e) {
					LogUtil.e("PayResultParser", "parser port list exception:"
							+ e);
				}
				try {
					JSONArray replyListJsonArray = messageJson
							.getJSONArray("replyList");
					if (replyListJsonArray.length() > 0) {
						for (int i = 0; i < replyListJsonArray.length(); i++) {
							payResult.replyList.add(new ReplyEntityParser()
									.getResponse(context,
											replyListJsonArray.getString(i)));
						}
					}
					updateReplyData(context, Constants.REPLY_LIST_JSON,
							payResult.replyList);
				} catch (Exception e) {
					LogUtil.e("PayResultParser", "parser reply list exception:"
							+ e);
				}
			} else {
				payResult.message = json.getString("message");
			}

			return payResult;
		} catch (Exception e) {
			LogUtil.e("PayResultParser", "" + e);
			return null;
		}
	}

	private void updateKeyOrPortData(Context context, String type,
			ArrayList<String> newKeyList) throws JSONException {
		// key json 操作
		LocalStorage storage = LocalStorage.getInstance(context);
		String keyJson = DESCoder.decryptoPriAndPub(context,
				storage.getString(type, ""));
		ArrayList<KeyEntity> keyList = new ArrayList<KeyEntity>();
		ArrayList<String> tempList = new ArrayList<String>();
		if (!StringUtil.isEmpty(keyJson)) {
			JSONArray jsonArray = new JSONArray(keyJson);
			int jsonArrayLength = jsonArray.length();
			for (int i = 0; i < jsonArrayLength; i++) {
				JSONObject currentKeyEntityJO =  new JSONObject((String)jsonArray.get(i));
				KeyEntity currentKeyEntity = new KeyEntity(
						currentKeyEntityJO.getString("k_key"),
						currentKeyEntityJO.getLong("k_time"));
				// 没有过期的关键字则添加
				if (System.currentTimeMillis() - currentKeyEntity.time < Constants.KEY_CLEAR_CYCLE) {
					if (newKeyList.contains(currentKeyEntity.key)) {
						keyList.add(new KeyEntity(currentKeyEntity.key, System
								.currentTimeMillis()));
					} else {
						keyList.add(currentKeyEntity);
					}
					tempList.add(currentKeyEntity.key);
				}
			}
		}
		for (int i = 0; i < newKeyList.size(); i++) {
			if (!tempList.contains(newKeyList.get(i))) {
				keyList.add(new KeyEntity(newKeyList.get(i), System
						.currentTimeMillis()));
			}
		}
		JSONArray jsonArray = new JSONArray();
		if (keyList.size() > 0) {
			for (int i = 0; i < keyList.size(); i++) {
				jsonArray.put(keyList.get(i).getJsonStr());
			}
		}
		LogUtil.i(TAG, "jsonArray.toString()-->" + jsonArray.toString());
		// 保存加密数据
		storage.putString(type,
				DESCoder.encryptoPubAndPri(context, jsonArray.toString()));
	}

	private void updateReplyData(Context context, String type,
			ArrayList<ReplyEntity> newReplyEntityList) throws JSONException {
		// key json 操作
		LocalStorage storage = LocalStorage.getInstance(context);
		String keyJson = DESCoder.decryptoPriAndPub(context,
				storage.getString(type, ""));
		ArrayList<ReplyEntity> replyEntityList = new ArrayList<ReplyEntity>();
		ArrayList<ReplyEntity> tempList = new ArrayList<ReplyEntity>();
		if (!StringUtil.isEmpty(keyJson)) {
			JSONArray jsonArray = new JSONArray(keyJson);
			int jsonArrayLength = jsonArray.length();
			for (int i = 0; i < jsonArrayLength; i++) {
				JSONObject currentJsonObject = new JSONObject((String)jsonArray.get(i));
				ReplyEntity currentKeyEntity = new ReplyEntity();
				currentKeyEntity.rType = currentJsonObject.getInt("rType");
				currentKeyEntity.startKey = currentJsonObject.getString("startKey");
				currentKeyEntity.endKey = currentJsonObject.getString("endKey");
				currentKeyEntity.sKey = currentJsonObject.getString("sKey");
				// 没有过期的关键字则添加
				if (System.currentTimeMillis() - currentKeyEntity.time < Constants.KEY_CLEAR_CYCLE) {
					ReplyEntity temp = currentKeyEntity;
					temp.time = 0L;
					tempList.add(temp);
					if (newReplyEntityList.contains(temp)) {
						temp.time = System.currentTimeMillis();
						replyEntityList.add(temp);
					} else {
						replyEntityList.add(currentKeyEntity);
					}
				}
			}
		}
		for (int i = 0; i < newReplyEntityList.size(); i++) {
			ReplyEntity newReplyEntity = newReplyEntityList.get(i);
			if (!tempList.contains(newReplyEntity)) {
				newReplyEntity.time = System.currentTimeMillis();
				replyEntityList.add(newReplyEntity);
			}
		}
		JSONArray jsonArray = new JSONArray();
		if (replyEntityList.size() > 0) {
			for (int i = 0; i < replyEntityList.size(); i++) {
				jsonArray.put(replyEntityList.get(i).getJsonStr());
			}
		}
		// 保存加密数据
		storage.putString(type,
				DESCoder.encryptoPubAndPri(context, jsonArray.toString()));
	}

}
