package com.vsoyou.sdk.ad.callback;

import android.graphics.Bitmap;

public interface GetImageCallBack {
	
	public void OnGetImageSuccess(Bitmap bitmap);

	public void onFailure(int errorCode, String errorMsg);

}
