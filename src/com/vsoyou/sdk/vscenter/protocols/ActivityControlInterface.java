package com.vsoyou.sdk.vscenter.protocols;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;


/**
 * 窗体事件接管，规则：
 * <ul>
 * <li><b>返回值:</b><br/>
 * 所有的返回值为 {@link Object} 类型，如 {@link Boolean}等，null 表示无处理，否则表示被接管</li>
 * <li>如果返回值为 void ({@link Void})，则表示无法接管</li>
 * </ul>
 * 
 * @author nxliao
 * 
 */
public abstract interface ActivityControlInterface {
	public abstract void onRestoreInstanceStateControl(Bundle paramBundle);

	public abstract void onPostCreateControl(Bundle paramBundle);

	public abstract void onCreateControl(Bundle paramBundle);

	public abstract void onStartControl();

	public abstract void onRestartControl();

	public abstract void onResumeControl();

	public abstract void onPostResumeControl();

	public abstract void onNewIntentControl(Intent paramIntent);

	public abstract void onSaveInstanceStateControl(Bundle paramBundle);

	public abstract void onPauseControl();

	public abstract void onUserLeaveHintControl();

	public abstract void onStopControl();

	public abstract void onLowMemoryControl();

	public abstract boolean onKeyLongPressControl(int paramInt,
			KeyEvent paramKeyEvent);

	public abstract boolean onKeyUpControl(int paramInt, KeyEvent paramKeyEvent);

	public abstract boolean onKeyMultipleControl(int paramInt1, int paramInt2,
			KeyEvent paramKeyEvent);

	public abstract boolean onTouchEventControl(MotionEvent paramMotionEvent);

	public abstract boolean onTrackballEventControl(MotionEvent paramMotionEvent);

	public abstract void onUserInteractionControl();

	public abstract void onWindowFocusChangedControl(boolean paramBoolean);

	public abstract void onPrepareDialogControl(int paramInt, Dialog paramDialog);

	public abstract void onDestroyControl();

	/** 褰撹繑鍥為敭琚寜涓嬶紝濡傛灉杩斿洖 true 琛ㄧず鎷︽埅 */
	public abstract Boolean onKeyDownControl(int keyCode, KeyEvent event);

	public abstract void onContentChangedControl();

	public abstract void onDetachedFromWindowControl();

	public abstract View onCreatePanelViewControl(int paramInt);

	public abstract boolean onCreatePanelMenuControl(int paramInt,
			Menu paramMenu);

	public abstract boolean onCreateOptionsMenuControl(Menu paramMenu);

	public abstract void onCreateContextMenuControl(
			ContextMenu paramContextMenu, View paramView,
			ContextMenu.ContextMenuInfo paramContextMenuInfo);

	public abstract boolean onContextItemSelectedControl(MenuItem paramMenuItem);

	public abstract void onContextMenuClosedControl(Menu paramMenu);

	public abstract Dialog onCreateDialogControl(int paramInt);

	/** 灞忓箷鍙戠敓鏃嬭浆 */
	public abstract boolean onConfigurationChangedControl(
			Configuration paramConfiguration);

	/** 褰撹繑鍥為敭琚寜涓嬶紝濡傛灉杩斿洖 true 琛ㄧず鎷︽埅 */
	public abstract boolean onBackPressedControl();

	public abstract void onAttachedToWindowControl();

	public abstract void onApplyThemeResourceControl(
			Resources.Theme paramTheme, int paramInt, boolean paramBoolean);

	public abstract void onTitleChangedControl(CharSequence paramCharSequence,
			int paramInt);

	public abstract void onChildTitleChangedControl(Activity paramActivity,
			CharSequence paramCharSequence);

	public abstract View onCreateViewControl(String paramString,
			Context paramContext, AttributeSet paramAttributeSet);

	public abstract boolean onCreateThumbnailControl(Bitmap paramBitmap,
			Canvas paramCanvas);

	public abstract CharSequence onCreateDescriptionControl();

	public abstract Object onRetainNonConfigurationInstanceControl();

	public abstract void onWindowAttributesChangedControl(
			WindowManager.LayoutParams paramLayoutParams);

	public abstract boolean onPreparePanelControl(int paramInt, View paramView,
			Menu paramMenu);

	public abstract boolean onMenuOpenedControl(int paramInt, Menu paramMenu);

	public abstract boolean onMenuItemSelectedControl(int paramInt,
			MenuItem paramMenuItem);

	public abstract void onPanelClosedControl(int paramInt, Menu paramMenu);

	public abstract boolean onPrepareOptionsMenuControl(Menu paramMenu);

	public abstract boolean onOptionsItemSelectedControl(MenuItem paramMenuItem);

	public abstract void onOptionsMenuClosedControl(Menu paramMenu);

	public abstract boolean onSearchRequestedControl();

	/** 澶勭悊瀛愮獥浣撶殑杩斿洖鍊硷紝濡傛灉杩斿洖true琛ㄧず鎷︽埅 */
	public abstract boolean onActivityResultControl(int requestCode,
			int resultCode, Intent data);

	public abstract void overridePendingTransitionControl(int paramInt1,
			int paramInt2);

	public abstract boolean dispatchKeyEventControl(KeyEvent paramKeyEvent);

	public abstract boolean dispatchTouchEventControl(
			MotionEvent paramMotionEvent);

	public abstract int getRequestedOrientationControl();

	public abstract boolean hasWindowFocusControl();
}

/*
 * Location: D:\workspace\android\svn\androidclient\ZZSdk_360\libs\360SDK.jar
 * Qualified Name: com.qihoopay.sdk.protocols.ActivityControlInterface JD-Core
 * Version: 0.6.2
 */