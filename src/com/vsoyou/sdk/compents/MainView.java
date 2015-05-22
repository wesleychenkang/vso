package com.vsoyou.sdk.compents;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.vsoyou.sdk.ad.entity.AdEntity;
import com.vsoyou.sdk.entity.ScreenInfo;
import com.vsoyou.sdk.util.DeviceInfoUtil;
import com.vsoyou.sdk.util.LogUtil;

public class MainView extends LinearLayout {
	
	private static final String TAG = "MainView";
	
	private Context context;
	private AdEntity adEntity;
	private ScreenInfo screenInfo;
	
	private WindowAdView windowAdView;
	public DetailView detailView;
	private ListsView listsView;

	public MainView(Context context, AdEntity adEntity) {
		super(context);
		this.context = context;
		this.adEntity = adEntity;
		screenInfo = DeviceInfoUtil.getScreenInfo(context);
		initView();
	}

	private void initView() {
		windowAdView = new WindowAdView(context, screenInfo, adEntity, this);
		detailView = new DetailView(context, screenInfo, adEntity, this);
		listsView = new ListsView(context, adEntity, this);
		this.addView(windowAdView,new LinearLayout.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT));
		this.addView(detailView, new LinearLayout.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT));
		this.addView(listsView, new LinearLayout.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT));
	}
	
	public void showWindowAdView(){
		LogUtil.i(TAG, "showWindowAdView");
		windowAdView.setVisibility(View.VISIBLE);
		detailView.setVisibility(View.GONE);
		listsView.setVisibility(View.GONE);
	}
	
	public void showAppDetailView(){
		LogUtil.i(TAG, "showAppDetailView");
		windowAdView.setVisibility(View.GONE);
		detailView.setVisibility(View.VISIBLE);
		listsView.setVisibility(View.GONE);
	}
	
	public void showAppListView(){
		LogUtil.i(TAG, "showAppListView");
		windowAdView.setVisibility(View.GONE);
		detailView.setVisibility(View.GONE);
		listsView.setVisibility(View.VISIBLE);
	}

}
