package com.vsoyou.sdk.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetUtil {
	
	/**
	 * 获取网络连接状态。
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isConnected(Context context) {
		if (null != context) {
			ConnectivityManager conMgr = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (null != conMgr) {
				NetworkInfo info = conMgr.getActiveNetworkInfo();
				if (null != info && info.isConnected()) {
					if (NetworkInfo.State.CONNECTED == info.getState()) {
						return true;
					}
				}
			}
		}
		return false;
	}

}
