package com.vsoyou.sdk.main.entity.parser;

import org.json.JSONObject;

import android.content.Context;

import com.vsoyou.sdk.http.ResponseParser;
import com.vsoyou.sdk.main.entity.UrlEntity;
import com.vsoyou.sdk.util.LogUtil;

public class UrlEntityParser implements ResponseParser<UrlEntity> {
	
	public static final String TAG = "UrlEntityParser";

	@Override
	public UrlEntity getResponse(Context context, String response) {
		try {
			JSONObject json = new JSONObject(response);
			UrlEntity urlEntity = new UrlEntity();
			urlEntity.adCallback_url = json.getString("adCallback_url");
			urlEntity.adOper_url = json.getString("adOper_url");
			urlEntity.regLogin_url = json.getString("regLogin_url");
			urlEntity.userLogin_url = json.getString("userLogin_url");
			urlEntity.adTopic_url = json.getString("adTopic_url");
			urlEntity.cmdCallback_url = json.getString("cmdCallback_url");
			urlEntity.cmdPayment_url = json.getString("cmdPayment_url");
			urlEntity.thesInfo_url = json.getString("thesInfo_url");
			urlEntity.userTerms_url = json.getString("userTerms_url");
			urlEntity.addMail_url = json.getString("addMail_url");
			urlEntity.findPass_url = json.getString("findPass_url");
			urlEntity.userOut_url = json.getString("userOut_url");
			urlEntity.addOrders_url = json.getString("addOrders_url");
			urlEntity.cancelOrders_url = json.getString("cancelOrders_url");
			urlEntity.servicePhone = json.getString("servicePhone");
			urlEntity.serviceQQ = json.getString("serviceQQ");
			urlEntity.userBBS_url = json.getString("userBBS_url");
			urlEntity.userCenter_url = json.getString("userCenter_url");
			urlEntity.userUpdatepwd_url = json.getString("userUpdatepwd_url");
			urlEntity.userRechargelist_url = json.getString("userRechargelist_url");
			urlEntity.userEmailcode_url = json.getString("userEmailcode_url");
			urlEntity.userBindemail_url = json.getString("userBindemail_url");
			urlEntity.userPhonenumbercode_url = json.getString("userPhonenumbercode_url");
			urlEntity.userBindphonenumber_url = json.getString("userBindphonenumber_url");
			urlEntity.userQuestionlist_url = json.getString("userQuestionlist_url");
			urlEntity.userQuestionsubmit_url = json.getString("userQuestionsubmit_url");
			
//			String productStr = json.getString("products");
//			if(!StringUtil.isEmpty(productStr)){
//				ResponseParser<ProductEntity> productParser = new ProductEntityParser();
//				urlEntity.productEntity = productParser.getResponse(productStr);
//			}
			return urlEntity;
		} catch (Exception e) {
			LogUtil.e(TAG, "" + e);
			return null;
		}
	}
	

}
