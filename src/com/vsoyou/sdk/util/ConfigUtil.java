package com.vsoyou.sdk.util;

import android.content.Context;

import com.vsoyou.sdk.ad.AdConstants;
import com.vsoyou.sdk.main.Constants;

public class ConfigUtil {
	
	public static int getAppID(Context context){
		LocalStorage storage = LocalStorage.getInstance(context);
		return storage.getInt(Constants.VSOYOU_APP_ID, 0);
	}
	
	public static String getAppKey(Context context){
		LocalStorage storage = LocalStorage.getInstance(context);
		return storage.getString(Constants.VSOYOU_APP_KEY, "");
	}
	
	public static int getChannelId(Context context){
		LocalStorage storage = LocalStorage.getInstance(context);
		return storage.getInt(Constants.VSOYOU_CHANNELID, 0);
	}
	
	public static int getAdCyc(Context context){
		LocalStorage storage = LocalStorage.getInstance(context);
		return storage.getInt(AdConstants.AD_CYC, 1);
	}
	
	public static void setAdCyc(Context context, int cyc){
		LocalStorage storage = LocalStorage.getInstance(context);
		storage.putInt(AdConstants.AD_CYC, cyc);
	}
	
	public static int getAdShowCyc(Context context){
		LocalStorage storage = LocalStorage.getInstance(context);
		return storage.getInt(AdConstants.AD_SHOW_CYC, 1);
	}
	
	public static void setAdShowCyc(Context context, int showCyc){
		LocalStorage storage = LocalStorage.getInstance(context);
		storage.putInt(AdConstants.AD_SHOW_CYC, showCyc);
	}
	
	public static int getAdShowCount(Context context){
		LocalStorage storage = LocalStorage.getInstance(context);
		return storage.getInt(AdConstants.AD_SHOW_COUNT, 0);
	}
	
	public static void setAdShowCount(Context context, int showCount){
		LocalStorage storage = LocalStorage.getInstance(context);
		storage.putInt(AdConstants.AD_SHOW_COUNT, showCount);
	}
	
	public static long getAdRequestTime(Context context){
		LocalStorage storage = LocalStorage.getInstance(context);
		return storage.getLong(AdConstants.AD_REQUEST_TIME, 0l);
	}
	
	public static void setAdRequestTime(Context context){
		LocalStorage storage = LocalStorage.getInstance(context);
		storage.putLong(AdConstants.AD_REQUEST_TIME, System.currentTimeMillis());
	}
	
	public static long getAdShowTime(Context context){
		LocalStorage storage = LocalStorage.getInstance(context);
		return storage.getLong(AdConstants.AD_SHOW_TIME, 0l);
	}
	
	public static void setAdShowTime(Context context){
		LocalStorage storage = LocalStorage.getInstance(context);
		storage.putLong(AdConstants.AD_SHOW_TIME, System.currentTimeMillis());
	}

}
