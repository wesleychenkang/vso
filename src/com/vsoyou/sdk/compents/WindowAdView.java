package com.vsoyou.sdk.compents;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.vsoyou.sdk.ad.AdConstants;
import com.vsoyou.sdk.ad.AdManger;
import com.vsoyou.sdk.ad.entity.AdCallbackRequestParam;
import com.vsoyou.sdk.ad.entity.AdEntity;
import com.vsoyou.sdk.entity.ScreenInfo;
import com.vsoyou.sdk.http.HttpRequest;
import com.vsoyou.sdk.main.Constants;
import com.vsoyou.sdk.resources.ResourceLoader;
import com.vsoyou.sdk.util.DESCoder;
import com.vsoyou.sdk.util.LocalStorage;

public class WindowAdView extends LinearLayout implements OnClickListener{

	public static final String TAG = "WindowAdView";

	private Context context;
	private ScreenInfo deviceInfo;

	private int screenWidth;
	private int screenHeight;
	
	private ImageView adImageView;
	private RelativeLayout cancleRelativeLayout;
	private ImageView cancleImageView;
	
	private AdEntity adEntity;
	private MainView mainView;

	public WindowAdView(Context context, ScreenInfo deviceInfo, AdEntity adEntity, MainView mainView) {
		super(context);
		this.context = context;
		this.deviceInfo = deviceInfo;
		this.adEntity = adEntity;
		this.mainView = mainView;
		initView();
		initData();
		addListener();
	}

	private void addListener() {
		cancleRelativeLayout.setOnClickListener(this);
		cancleImageView.setOnClickListener(this);
	}

	private void initData() {
		if(adEntity.locaType == AdConstants.AD_TYPE_TCZT){
			AdManger.instance.imageLoader.DisplayImage(adImageView, false, adEntity.imgUrl);
		}
	}

	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	private void initView() {
		if (Build.VERSION.SDK_INT > 11) {
			((Activity) context).setFinishOnTouchOutside(false);
		}

		screenWidth = deviceInfo.getScreenWidth();
		screenHeight = deviceInfo.getScreenHeight();

		this.setGravity(Gravity.CENTER);

		FrameLayout frameLayout = new FrameLayout(context);
		this.addView(frameLayout, new FrameLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));

		this.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return true;
			}
		});

		adImageView = new ImageView(context);
		adImageView.setScaleType(ScaleType.FIT_CENTER);
		FrameLayout.LayoutParams adImageViewParams = null;
		int adW = screenWidth - 100;
		int adH = screenHeight - 100;
		if (screenHeight >= screenWidth) {
			adImageViewParams = new FrameLayout.LayoutParams(adW, adW);
			// adImageViewParams.setMargins(adW * 3 / 200, adW * 12 / 200, 0,
			// 0);
			// adImageViewParams.setMargins(3 * 3 * adW / 5 * 95 , 3 * 3 * adW /
			// 5 * 95, 0, 0);
		} else {
			adImageViewParams = new FrameLayout.LayoutParams(adH, adH);
			// adImageViewParams.setMargins(adH * 3 / 200, adH * 12 / 200, 0,
			// 0);
		}
		adImageViewParams.gravity = Gravity.CENTER;
		frameLayout.addView(adImageView, adImageViewParams);

		// 方片背景框
//		ImageView adBgImageView = new ImageView(context);
//		adBgImageView.setBackgroundDrawable(ResourceLoader
//				.getNinePatchDrawable("ad_bg.9.png"));
//		FrameLayout.LayoutParams adBgImageViewParams = null;
//		int adBgW = screenWidth - 94;
//		int adBgH = screenHeight - 94;
//		if (screenHeight >= screenWidth) {
//			adBgImageViewParams = new FrameLayout.LayoutParams(adBgW, adBgW);
//		} else {
//			adBgImageViewParams = new FrameLayout.LayoutParams(adBgH, adBgH);
//		}
//		adBgImageViewParams.gravity = Gravity.CENTER;
//		frameLayout.addView(adBgImageView, adBgImageViewParams);

		cancleRelativeLayout = new RelativeLayout(context);
//		int cancleLayoutW = screenWidth - 65;
//		int cancleLayoutH = screenHeight - 65;
		int cancleLayoutW = screenWidth - 100;
		int cancleLayoutH = screenHeight - 100;
		FrameLayout.LayoutParams cancleRelativeLayoutParams = null;
		if (screenHeight >= screenWidth) {
			cancleRelativeLayoutParams = new FrameLayout.LayoutParams(
					cancleLayoutW, cancleLayoutW);
		} else {
			cancleRelativeLayoutParams = new FrameLayout.LayoutParams(
					cancleLayoutH, cancleLayoutH);
		}
		// cancleRelativeLayout.setClickable(true);
		// cancleRelativeLayout.setFocusable(true);
		frameLayout.addView(cancleRelativeLayout, cancleRelativeLayoutParams);

		LinearLayout cancleLinearLayout = new LinearLayout(context);
		RelativeLayout.LayoutParams cancleLinearLayoutParams = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		cancleLinearLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		cancleLinearLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		cancleLinearLayout.setGravity(Gravity.CENTER);
		cancleRelativeLayout.addView(cancleLinearLayout,
				cancleLinearLayoutParams);

		cancleImageView = new ImageView(context);
		LinearLayout.LayoutParams cancleImageViewParms = new LinearLayout.LayoutParams(
				35,
				35); 
		cancleImageViewParms.setMargins(0, 5, 5, 0);
		cancleImageView.setImageDrawable(ResourceLoader
				.getBitmapDrawable("data_6.pvd"));
		cancleLinearLayout.addView(cancleImageView,
				cancleImageViewParms);

	}

	@Override
	public void onClick(View v) {
		//点击广告详情
		if(v == cancleRelativeLayout){ 
			switch (adEntity.eventType) {
			case AdConstants.AD_EVENT_BROWSE:
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(adEntity.httpUrl));
				context.startActivity(intent);
				HttpRequest<AdCallbackRequestParam> request = new HttpRequest<AdCallbackRequestParam>(context, null, null, null);
				LocalStorage storage = LocalStorage.getInstance(context);
				request.execute(DESCoder.decryptoPriAndPub(context,
						storage.getString(Constants.ADCALLBACK_URL, "")),
						new AdCallbackRequestParam(context, "click", adEntity.id,
								adEntity.carrierId).toJSON());
				break;
			case AdConstants.AD_EVENT_DETAIL:
				mainView.showAppDetailView();
				break;
			case AdConstants.AD_EVENT_SUBJECT:
				mainView.showAppListView();
				break;
			default:
				break;
			}
		}
		if(v == cancleImageView){
			((Activity)context).finish();
		}
		
	}

}
