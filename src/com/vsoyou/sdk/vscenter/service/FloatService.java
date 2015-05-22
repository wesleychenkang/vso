package com.vsoyou.sdk.vscenter.service;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class FloatService extends Service {

	@Override
	public IBinder onBind(Intent inent) {
		// TODO Auto-generated method stub
		return null;
	}
  
	@Override
	public void onCreate() {
		super.onCreate();
	FloatWindowService.createCenterView(getApplicationContext());
	}
}
