package com.vsoyou.sdk.main.callback;

import com.vsoyou.sdk.main.entity.ThesInfoResult;

public interface GetThesInfoCallback {
	
	public void onGetThesInfoSuccess(ThesInfoResult thesInfoResult);
	public void onGetThesInfoFailure(int code, String message);

}
