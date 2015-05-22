package com.vsoyou.sdk.vscenter.util;

import android.util.Log;
public class LogUtil {
	
	public static boolean LOG_SWITCH = false;
	
	public static void i(String tag, String msg){
		if(LOG_SWITCH){
			Log.i(tag, msg);
		}
	}
	
	public static void e(String tag, String msg){
		if(LOG_SWITCH){
			Log.e(tag, msg);
		}
	}

}
