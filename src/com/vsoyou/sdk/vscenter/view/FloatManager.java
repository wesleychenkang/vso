package com.vsoyou.sdk.vscenter.view;

import com.vsoyou.sdk.vscenter.service.FloatService;

import android.app.Activity;
import android.content.Intent;

public class FloatManager {
	public static void startFloatWindow(Activity activity) {
		Intent intent = new Intent(activity, FloatService.class);
		activity.startService(intent);
	}
}
