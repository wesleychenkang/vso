package com.vsoyou.sdk.main.entity.requestparam;

import android.content.Context;

import com.vsoyou.sdk.main.Constants;
import com.vsoyou.sdk.util.ConfigUtil;

public class CommonRequestParam {
	
	public int sdkCode;
	public int appId;
	
	public CommonRequestParam(Context context) {
		sdkCode = Constants.SDK_CODE;
		appId = ConfigUtil.getAppID(context);
	}

}
