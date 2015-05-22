package com.vsoyou.sdk.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import com.vsoyou.sdk.ad.AdConstants;
import com.vsoyou.sdk.ad.AdManger;
import com.vsoyou.sdk.ad.entity.AdCallbackRequestParam;
import com.vsoyou.sdk.ad.entity.AdEntity;
import com.vsoyou.sdk.ad.entity.parser.AdEntityParser;
import com.vsoyou.sdk.compents.MainView;
import com.vsoyou.sdk.http.HttpRequest;
import com.vsoyou.sdk.main.Constants;
import com.vsoyou.sdk.resources.ResourceLoader;
import com.vsoyou.sdk.util.DESCoder;
import com.vsoyou.sdk.util.LocalStorage;
import com.vsoyou.sdk.util.LogUtil;

public class PayManagerActivity extends Activity {

	public static final String TAG = "PayManagerActivity";
	private int adType;
	private int eventType;
	private AdEntity adEntity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogUtil.i(TAG, "onCreate");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(
				ResourceLoader.getBitmapDrawable("data_23.pvd"));
		AdManger.isShow = true;
		adType = getIntent().getIntExtra(AdConstants.AD_TYPE, 10);
		eventType = getIntent().getIntExtra(AdConstants.EVENT_TYPE, 10);
		LocalStorage localStorage = LocalStorage.getInstance(this);
		AdEntityParser adEntityParser = new AdEntityParser();
		adEntity = adEntityParser.getResponse(this,
				localStorage.getString(AdConstants.AD_CONTENT, ""));
		if (adEntity != null) {
			if(adType == 10 && eventType == 0){ //直接跳转到Url
				LogUtil.i(TAG, "forward to Url");
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(adEntity.httpUrl));
				this.startActivity(intent);
				HttpRequest<AdCallbackRequestParam> request = new HttpRequest<AdCallbackRequestParam>(this, null, null, null);
				LocalStorage storage = LocalStorage.getInstance(this);
				request.execute(DESCoder.decryptoPriAndPub(this,
						storage.getString(Constants.ADCALLBACK_URL, "")),
						new AdCallbackRequestParam(this, "click", adEntity.id,
								adEntity.carrierId).toJSON());
				finish();
			}
			LogUtil.i(TAG, "window' ad.");
			MainView mainView = new MainView(this, adEntity);
			if (adType != 10) { // 弹窗居中广告
				mainView.showWindowAdView();
			} else {
				switch (eventType) {
				case 1: // 1详细窗口
					mainView.showAppDetailView();
					break;

				case 2: // 2专栏窗口
					mainView.showAppListView();
					break;

				default:
					break;
				}
			}
			setContentView(mainView, new LinearLayout.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT));
		} else {
			finish();
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (KeyEvent.KEYCODE_HOME == keyCode) {
		}
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN
				&& isOutOfBounds(this, event)) {
			return true;
		}
		return super.onTouchEvent(event);
	}

	private boolean isOutOfBounds(Activity context, MotionEvent event) {
		final int x = (int) event.getX();
		final int y = (int) event.getY();
		final int slop = ViewConfiguration.get(context)
				.getScaledWindowTouchSlop();
		final View decorView = context.getWindow().getDecorView();
		return (x < -slop) || (y < -slop)
				|| (x > (decorView.getWidth() + slop))
				|| (y > (decorView.getHeight() + slop));
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		AdManger.isShow = false;
	}

}
