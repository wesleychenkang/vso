package com.vsoyou.sdk.vscenter.service;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

import com.vsoyou.sdk.util.LocalStorage;
import com.vsoyou.sdk.vscenter.util.MetricUtil;
import com.vsoyou.sdk.vscenter.view.FloatCenter;
import com.vsoyou.sdk.vscenter.view.FloatCenterLeftView;
import com.vsoyou.sdk.vscenter.view.FloatCenterRightView;

public class FloatWindowService {
	private static FloatCenter center;
	private static FloatCenterLeftView centerLeft;
	private static WindowManager.LayoutParams centerParams;
	private static WindowManager.LayoutParams leftParams;
	private static WindowManager.LayoutParams rightParams;
	private static FloatCenterRightView centerRight;
	private static WindowManager.LayoutParams personParams;
	private static int x;
	private static int y;
	private static final int OFFSET = 63;

	/**
	 * 创建浮窗页面
	 * 
	 * @param ctx
	 */
	public static void createCenterView(Context ctx) {

		if (center == null) {
			center = new FloatCenter(ctx);
			createCenterParams(ctx);
			WindowManager manager = getWindowManger(ctx);
			center.setMangerLayParams(centerParams);
			createCenterRightView(ctx);
			createCenterLeftView(ctx);
			manager.addView(center, centerParams);
		}
	}

	private static void createCenterParams(Context ctx){
//		DisplayMetrics dm = new DisplayMetrics();
//		manager.getDefaultDisplay().getMetrics(dm);
//		int screenWidth = dm.widthPixels;
//		int screenHeight = dm.heightPixels;
		if (centerParams == null) {
			centerParams = new WindowManager.LayoutParams();
			centerParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
					| LayoutParams.FLAG_NOT_FOCUSABLE;
			centerParams.format = PixelFormat.RGBA_8888;
			centerParams.type = LayoutParams.TYPE_PHONE;
			centerParams.width = LayoutParams.WRAP_CONTENT;
			centerParams.height = LayoutParams.WRAP_CONTENT;

		}

//		if (centerParams.x == 0 && centerParams.y == 0) {
//			centerParams.x = screenWidth / 2;
//			centerParams.y = -screenHeight / 2;
//		} else {
//
//			x = centerParams.x;
//			y = centerParams.y;
//		}

	}

	/**
	 * 创建左边显示的页面
	 * 
	 * @param ctx
	 */
	public static void createCenterLeftView(Context ctx) {
		WindowManager manager = getWindowManger(ctx);
		centerLeft = new FloatCenterLeftView(ctx);
		leftParams = new WindowManager.LayoutParams();
		leftParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
				| LayoutParams.FLAG_NOT_FOCUSABLE;
		leftParams.format = PixelFormat.RGBA_8888;
		leftParams.type = LayoutParams.TYPE_PHONE;
		leftParams.width = LayoutParams.WRAP_CONTENT;
		leftParams.height = LayoutParams.WRAP_CONTENT;
		leftParams.x = centerParams.x - MetricUtil.getDip(ctx, 100);
		leftParams.y = centerParams.y;
		centerLeft.setMangerLayParams(leftParams);
		manager.addView(centerLeft, leftParams);

	}

	/**
	 * 创建右边显示的页面
	 * 
	 * @param ctx
	 */
	public static void createCenterRightView(Context ctx) {
		WindowManager manager = getWindowManger(ctx);
		if (centerRight == null) {
			centerRight = new FloatCenterRightView(ctx);
			rightParams = new WindowManager.LayoutParams();
			rightParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
					| LayoutParams.FLAG_NOT_FOCUSABLE;
			rightParams.format = PixelFormat.RGBA_8888;
			rightParams.type = LayoutParams.TYPE_PHONE;
			rightParams.width = LayoutParams.WRAP_CONTENT;
			rightParams.height = LayoutParams.WRAP_CONTENT;
			rightParams.x = centerParams.x;
			rightParams.y = centerParams.y;

		}
		centerRight.setMangerLayParams(rightParams);
		manager.addView(centerRight, rightParams);

	}

	public static void disPlayRightView(Context ctx) {
		WindowManager manager = getWindowManger(ctx);
		centerLeft.setVisibility(View.GONE);
		centerRight.setVisibility(View.VISIBLE);
		center.setVisibility(View.GONE);
		centerParams.x = centerParams.x + MetricUtil.getDip(ctx, OFFSET);
		;
		manager.updateViewLayout(centerRight, centerParams);

	}

	public static void disPlayLeftView(Context ctx) {
		WindowManager manager = getWindowManger(ctx);
		centerRight.setVisibility(View.INVISIBLE);
		centerLeft.setVisibility(View.VISIBLE);
		center.setVisibility(View.INVISIBLE);
		centerParams.x = centerParams.x - MetricUtil.getDip(ctx, OFFSET);
		manager.updateViewLayout(centerLeft, centerParams);

	}

	/**
	 * 显示左边浮窗或者右边浮窗
	 * 
	 * @param ctx
	 * @param place
	 *            1，代表左边 ， 0 代表右边,其它值表示只显示centerView
	 */
	public static void disPlayCenterView(Context ctx, int place) {

		WindowManager manager = getWindowManger(ctx);
		centerRight.setVisibility(View.GONE);
		centerLeft.setVisibility(View.GONE);
		center.setVisibility(View.VISIBLE);
		if (place == 0) {
			centerParams.x = centerParams.x - MetricUtil.getDip(ctx, OFFSET);
		} else if (place == 1) {
			centerParams.x = centerParams.x + MetricUtil.getDip(ctx, OFFSET);
		} else {
		}
		manager.updateViewLayout(center, centerParams);

	}

	/**
	 * 显示浮窗
	 * 
	 * @param ctx
	 */
	public static void disPlayCenterView(Context ctx) {
		if (null != center && center.getVisibility() == View.GONE) {
			disPlayCenterView(ctx, -1);
		}
	}

	/**
	 * 隐藏浮窗
	 * 
	 * @param ctx
	 */
	public static void hideFloatView(Context ctx) {
		if (center != null && center.getVisibility() == View.VISIBLE) {
			center.setVisibility(View.GONE);
		}
		if (centerLeft != null && centerLeft.getVisibility() == View.VISIBLE) {
			centerLeft.setVisibility(View.GONE);
		}
		if (centerRight != null && centerRight.getVisibility() == View.VISIBLE)
			centerRight.setVisibility(View.GONE);
	}

	/**
	 * 清除windowManager上面所有的视图
	 * 
	 * @param ctx
	 */
	public static void removeAllView(Context ctx) {
		WindowManager manager = getWindowManger(ctx);
		if (center != null) {
			manager.removeView(center);
			center = null;
		}
		if (centerLeft != null) {
			manager.removeView(centerLeft);
			centerLeft = null;
		}
		if (centerRight != null) {
			manager.removeView(centerRight);
			centerRight = null;
		}
	}

	/**
	 * 浮窗完全退出
	 * 
	 * @param ctx
	 */
	public static void clean(Context ctx) {
		removeAllView(ctx);
		Intent service = new Intent(
				"com.vsoyou.sdk.vscenter.service.FloatService");
		ctx.stopService(service);
	}

	/**
	 * 浮窗初次显示的位置
	 * 
	 * @param postion
	 */
	private static void updateFloatPostion(Context ctx, int direction) {
		if (centerParams == null) {
			createCenterParams(ctx);
		   }
			WindowManager manager = getWindowManger(ctx);
			DisplayMetrics dm = new DisplayMetrics();
			manager.getDefaultDisplay().getMetrics(dm);
			int screenWidth = dm.widthPixels;
			int screenHeight = dm.heightPixels;
			switch (direction) {
			// 左上角
			case 1:
				centerParams.x = -screenWidth / 2;
				centerParams.y = -screenHeight / 2;
				break;
			// 左下角
			case 2:
				centerParams.x = -screenWidth / 2;
				centerParams.y = screenHeight / 2;
				break;
			// 右上角
			case 3:
				centerParams.x = screenWidth / 2;
				centerParams.y = -screenHeight / 2;
				break;
			// 右下角
			case 4:
				centerParams.x = screenWidth / 2;
				centerParams.y = screenHeight / 2;
				break;
			}
		
	}

	/**
	 * 恢复窗体的位置
	 * 
	 * @param x
	 * @param y
	 */
	public static void revertFloatPostion(Context ctx, int direction) {
		
		LocalStorage storage = LocalStorage.getInstance(ctx);
		int x = storage.getInt("centerX", 100000);
		int y = storage.getInt("centerY", 100000);
		
		if (x != 100000 && y != 100000 && centerParams != null) {
			// 说明里面有保存数据
			centerParams.x = x;
			centerParams.y = y;
			System.out.println("刷新后的X的值" + x + "刷新后Y的值" + y);
			WindowManager manager = getWindowManger(ctx);
			manager.updateViewLayout(center, centerParams);
		} else {
			updateFloatPostion(ctx, direction);

		}
	}

	/**
	 * 保存浮窗的具体位置
	 * 
	 */
	public static void savaFloatPostion(Context ctx) {
		if (centerParams != null) {
			// 保存centreParams的坐标

			int x = centerParams.x;
			int y = centerParams.y;
			System.out.println("保存了坐标" + x + "保存了坐标" + y);
			LocalStorage storage = LocalStorage.getInstance(ctx);
			storage.putInt("centerX", x);
			storage.putInt("centerY", y);
		}
	}

	private static WindowManager getWindowManger(Context ctx) {

		return (WindowManager) ctx.getApplicationContext().getSystemService(
				Context.WINDOW_SERVICE);
	}

	public static WindowManager.LayoutParams getWindowLayoutParams() {
		return new WindowManager.LayoutParams();
	}

}
