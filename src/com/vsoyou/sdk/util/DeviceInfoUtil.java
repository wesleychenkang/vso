package com.vsoyou.sdk.util;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.WindowManager;

import com.vsoyou.sdk.entity.DeviceInfo;
import com.vsoyou.sdk.entity.ScreenInfo;

public class DeviceInfoUtil {
	public static final String TAG = "DeviceInfoUtil";

	public static String getImsi(Context context) {
		String imsi = "";
		try {
			TelephonyManager telephonyManager = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			if (telephonyManager != null) {
				imsi = telephonyManager.getSubscriberId();
			}
		} catch (Exception e) {
			LogUtil.e(TAG, "" + e);
		}
		return imsi;
	}

	public static DeviceInfo getDeviceInfo(Context context) {

		if (context == null)
			return null;

		try {

			DeviceInfo info = new DeviceInfo(context);

			info.channelId = ConfigUtil.getChannelId(context);

			WifiManager wifiMgr = (WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);
			WifiInfo wifiInfo = (wifiMgr == null ? null : wifiMgr
					.getConnectionInfo());
			if (wifiInfo != null) {
				info.mac = wifiInfo.getMacAddress();
			}

			TelephonyManager telephonyManager = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			if (telephonyManager != null) {
				info.imei = telephonyManager.getDeviceId();
				info.imsi = telephonyManager.getSubscriberId();
			}
			info.model = Build.MODEL;
			info.sdkVer = Build.VERSION.RELEASE;

			return info;

		} catch (Exception ex) {
			LogUtil.e(TAG, "getDeviceInfo-->" + ex);
			return null;
		}

	}

	public static ScreenInfo getScreenInfo(Context context) {
		if (context == null) {
			return null;
		}
		try {
			ScreenInfo screenInfo = new ScreenInfo();
			// TelephonyManager telephonyManager = (TelephonyManager) context
			// .getSystemService(Context.TELEPHONY_SERVICE);
			// if (telephonyManager != null) {
			// switch (telephonyManager.getNetworkType()) {
			// case TelephonyManager.NETWORK_TYPE_1xRTT:
			// case TelephonyManager.NETWORK_TYPE_CDMA:
			// case TelephonyManager.NETWORK_TYPE_EDGE:
			// case TelephonyManager.NETWORK_TYPE_GPRS:
			// case TelephonyManager.NETWORK_TYPE_IDEN:
			// case TelephonyManager.NETWORK_TYPE_UNKNOWN:
			// screenInfo.setNetworkType(2);
			// break;
			// case TelephonyManager.NETWORK_TYPE_EVDO_0:
			// case TelephonyManager.NETWORK_TYPE_EVDO_A:
			// case TelephonyManager.NETWORK_TYPE_HSDPA:
			// case TelephonyManager.NETWORK_TYPE_HSPA:
			// case TelephonyManager.NETWORK_TYPE_HSUPA:
			// case TelephonyManager.NETWORK_TYPE_UMTS:
			// screenInfo.setNetworkType(3);
			// break;
			// default:
			// screenInfo.setNetworkType(5);
			// }
			// String proxyHost = android.net.Proxy.getDefaultHost();
			// if (proxyHost != null)
			// screenInfo.setNetworkType(4); // Wap上网
			// }
			//
			// ConnectivityManager connectivityManager = (ConnectivityManager)
			// context
			// .getSystemService(Context.CONNECTIVITY_SERVICE);
			// if (connectivityManager != null) {
			// NetworkInfo networkInfo = connectivityManager
			// .getActiveNetworkInfo();// 获取网络的连接情况
			// if (networkInfo != null) {
			// if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
			// screenInfo.setNetworkType(1);
			// }
			// }
			// }

			WindowManager windowManager = (WindowManager) context
					.getSystemService(Context.WINDOW_SERVICE);
			if (windowManager != null) {
				Display display = windowManager.getDefaultDisplay();
				screenInfo.setScreenWidth(display.getWidth());
				screenInfo.setScreenHeight(display.getHeight());
			}
			return screenInfo;
		} catch (Exception e) {
			LogUtil.i(TAG, "" + e);
			return null;
		}

	}

}
