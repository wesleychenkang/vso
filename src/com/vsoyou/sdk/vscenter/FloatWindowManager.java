package com.vsoyou.sdk.vscenter;

import com.vsoyou.sdk.vscenter.service.FloatWindowService;
import android.app.Activity;

public class FloatWindowManager {
	private FloatWindowManager() {
	}

	public static final int GRAVITY_LEFT_TOP = 1;
	public static final int GRAVITY_LEFT_BOTTOM = 2;
	public static final int GRAVITY_RIGHT_TOP = 3;
	public static final int GRAVITY_RIGHT_BOTTOM = 4;
	private static FloatWindowManager manager;
	private static Activity a;

	public static FloatWindowManager getSdkManagerInstance(Activity activity) {
		if (manager == null) {
			manager = new FloatWindowManager();
			a = activity;
		}
		return manager;
	}

	/**
	 * 主动显示浮窗 在Activity的onReume()方法中调用
	 * 
	 * @param activity
	 */
	public void disPlayFloatWindow(Activity activity) {
		FloatWindowService.disPlayCenterView(activity);
	}

	/**
	 * 主动隐藏浮窗 Activity的onPause()中调用
	 * 
	 * @param activity
	 */

	public void hideFloatWindow(Activity activity) {

	}

	/**
	 * 在游戏窗体的onCreate方法中调用
	 * 
	 * @param activity
	 *            窗体
	 * @param postion
	 *            浮窗显示的位置
	 *            {@code GRAVITY_LEFT_TOP GRAVITY_LEFT_BOTTOM GRAVITY_RIGHT_TOP GRAVITY_RIGHT_BOTTOM}
	 */
	public void onActivityCreate(Activity activity, int postion) {
		FloatWindowService.revertFloatPostion(activity, postion);
	}

	/**
	 * 
	 * 在游戏窗体中 onResume方法中调用
	 * 
	 * @param activity
	 */

	public void onActivityResume(Activity activity) {
		FloatWindowService.disPlayCenterView(activity);
	}

	/**
	 * 在游戏窗体中onPause方法中调用
	 * 
	 * @param activity
	 */
	public void onActivityPause(Activity activity) {
		FloatWindowService.hideFloatView(activity);
	}

	/**
	 * 在游戏窗体中的onDestory方法中调用
	 * 
	 * @param activity
	 */
	public void onActivityDestory(Activity activity) {
		FloatWindowService.savaFloatPostion(activity);
	}
}
