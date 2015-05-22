package com.vsoyou.sdk.ad.callback;

import com.vsoyou.sdk.ad.entity.AdEntity;

public interface GetAdCallBack {

	public void OnGetAdSuccess(AdEntity adEntity);

	public void onFailure(int errorCode, String errorMsg);

}
