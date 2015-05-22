package com.vsoyou.sdk.main.callback;

import com.vsoyou.sdk.main.entity.InitEntity;

public interface InitCallback {
	
	public void onInitSuccess(InitEntity initEntity);
	
	public void onFailure(int errorCode, String errorMsg);

}
