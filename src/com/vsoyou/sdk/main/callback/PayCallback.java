package com.vsoyou.sdk.main.callback;


public interface PayCallback {
	
	public void onPaySuccess();
	public void onFailure(int code, String message);

}
