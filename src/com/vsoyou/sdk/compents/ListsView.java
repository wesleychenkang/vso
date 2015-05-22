package com.vsoyou.sdk.compents;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.vsoyou.sdk.ad.AdConstants;
import com.vsoyou.sdk.ad.adapter.ListAdapter;
import com.vsoyou.sdk.ad.entity.AdEntity;
import com.vsoyou.sdk.ad.entity.AppEntity;
import com.vsoyou.sdk.resources.ResourceLoader;

public class ListsView extends LinearLayout implements OnClickListener, OnItemClickListener {

	public static final String TAG = "ListsView";

	private Context context;
	public ListView appListView;
	
	private AdEntity adEntity;
	private MainView mainView;
	private BackView backView;
	private ListAdapter adapter;

	public ListsView(Context context, AdEntity adEntity, MainView mainView) {
		super(context);
		this.context = context;
		this.adEntity = adEntity;
		this.mainView = mainView;
		initView();
		addListener();
		initData();
	}

	private void initData() {
		if(adEntity.carrierlist != null && adEntity.carrierlist.size() > 0){
			adapter = new ListAdapter(context, adEntity);
			appListView.setAdapter(adapter);
		}
	}

	private void addListener() {
		backView.backTextView.setOnClickListener(this);
		appListView.setOnItemClickListener(this);
	}

	private void initView() {
		this.setOrientation(LinearLayout.VERTICAL);
		this.setBackgroundColor(Color.parseColor("#ffffff"));
		this.setLayoutParams(new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT));

		backView = new BackView(context, "列表");
		this.addView(backView,
				new RelativeLayout.LayoutParams(
						ViewGroup.LayoutParams.MATCH_PARENT,
						ViewGroup.LayoutParams.WRAP_CONTENT));

		appListView = new ListView(context);
		appListView.setBackgroundColor(Color.parseColor("#ffffff"));
		appListView.setCacheColorHint(Color.parseColor("#000000"));
		AbsListView.LayoutParams appListViewParams = new AbsListView.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		appListView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		appListView.setDividerHeight(1);
		appListView.setDivider(ResourceLoader.getBitmapDrawable("data_12.pvd"));
		this.addView(appListView, appListViewParams);

	}

	@Override
	public void onClick(View v) {
		if(v == backView.backTextView){
			if(adEntity.locaType == AdConstants.AD_TYPE_TCZT){
				if(adEntity.eventType == AdConstants.AD_EVENT_SUBJECT){
					mainView.showWindowAdView();
				}
			}else{
				((Activity)context).finish();
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		AppEntity nowAppEntity = adEntity.carrierlist.get(position);
		if(nowAppEntity != null){
			mainView.detailView.updateData(nowAppEntity);
			mainView.showAppDetailView();
		}
	}

}
