package com.vsoyou.sdk.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class PackUtil {

	public static boolean checkPackage(Context context, String packageName) {
		if (packageName == null || "".equals(packageName))
			return false;
		try {
			context.getPackageManager().getApplicationInfo(packageName,
					PackageManager.GET_UNINSTALLED_PACKAGES);
			return true;
		} catch (NameNotFoundException e) {
			return false;
		}
	}

}
