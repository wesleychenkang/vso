package com.vsoyou.sdk.compents;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vsoyou.sdk.resources.ResourceLoader;
import com.vsoyou.sdk.util.Decorator;

public class BackView extends RelativeLayout {
	
	public static final String TAG = "BackView";
	
	private Context context;
	private String titleName;
	public TextView backTextView;

	public BackView(Context context,String titleName) {
		super(context);
		this.context = context;
		this.titleName = titleName;
		initView();
	}

	private void initView() {
		this.setBackgroundDrawable(ResourceLoader
				.getBitmapDrawable("data_1.pvd"));
//		this.setLayoutParams(new RelativeLayout.LayoutParams(
//				ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		// 返回
		LinearLayout backLinearLayout = new LinearLayout(context);
		backLinearLayout.setGravity(Gravity.CENTER);
		backLinearLayout.setOrientation(LinearLayout.VERTICAL);
		RelativeLayout.LayoutParams backLinearLayoutParams = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT, 60);
		backLinearLayoutParams.setMargins(10, 0, 0, 0);
		this.addView(backLinearLayout, backLinearLayoutParams);

		backTextView = new TextView(context);
		Decorator.setStateImage(backTextView,
				ResourceLoader.getBitmapDrawable("data_4.pvd"),
				ResourceLoader.getBitmapDrawable("data_5.pvd"),
				ResourceLoader.getBitmapDrawable("data_4.pvd"));
		backLinearLayout.addView(backTextView, new LinearLayout.LayoutParams(
				45,
				38));

		// 应用介绍
		LinearLayout appTypeLinearLayout = new LinearLayout(context);
		appTypeLinearLayout.setGravity(Gravity.CENTER);
		appTypeLinearLayout.setOrientation(LinearLayout.VERTICAL);
		RelativeLayout.LayoutParams appTypeLinearLayoutParams = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT, 60);
		// appTypeLinearLayoutParams.setMargins(10, 0, 0, 0);
		appTypeLinearLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		this.addView(appTypeLinearLayout,
				appTypeLinearLayoutParams);

		TextView appTypeTextView = new TextView(context);
		appTypeTextView.setText(titleName);
		appTypeTextView.setTextColor(Color.parseColor("#3d3d3d"));
		appTypeTextView.setTextSize(20);
		appTypeLinearLayout.addView(appTypeTextView,
				new LinearLayout.LayoutParams(
						ViewGroup.LayoutParams.WRAP_CONTENT,
						ViewGroup.LayoutParams.WRAP_CONTENT));
	}

}
