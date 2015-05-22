package com.vsoyou.sdk.main.activity.view;

import com.vsoyou.sdk.resources.ResourceLoader;
import com.vsoyou.sdk.util.Decorator;
import com.vsoyou.sdk.util.MetricUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BackView extends LinearLayout {

	private static final String TAG = "BackView";
	
	private ImageView backImageView;
	private Context context;

	public ImageView getBackImageView() {
		return backImageView;
	}

	public BackView(Context context) {
		super(context);
		this.context = context;
		initView();
	}

	public BackView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@SuppressLint("NewApi")
	public BackView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	private void initView() {
		this.setBackgroundColor(Color.parseColor("#efefef"));
		this.setGravity(Gravity.CENTER);
		this.setOrientation(LinearLayout.VERTICAL);

		RelativeLayout relativeLayout_1 = new RelativeLayout(context);
//		relativeLayout_1.setBackgroundColor(Color.parseColor("#fcfcfc"));
		relativeLayout_1.setBackgroundDrawable(ResourceLoader.getBitmapDrawable("back_bg.png"));
//		relativeLayout_1.setGravity(Gravity.CENTER_HORIZONTAL);
		this.addView(relativeLayout_1, new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, MetricUtil.getDip(context, 60.0F)));

		LinearLayout linearLayout_1 = new LinearLayout(context);
		linearLayout_1.setOrientation(LinearLayout.VERTICAL);
		linearLayout_1.setGravity(Gravity.CENTER);
		RelativeLayout.LayoutParams linearLayout_1Params = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		linearLayout_1Params.setMargins(MetricUtil.getDip(context, 10.0F), 0, 0, 0);
		linearLayout_1Params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		relativeLayout_1.addView(linearLayout_1, linearLayout_1Params);

		backImageView = new ImageView(context);
		Decorator.setStateImage(backImageView,
				ResourceLoader.getBitmapDrawable("back_normal.png"),
				ResourceLoader.getBitmapDrawable("back_pressed.png"),
				ResourceLoader.getBitmapDrawable("back_normal.png"));
		linearLayout_1.addView(backImageView, new LinearLayout.LayoutParams(
				MetricUtil.getDip(context, 35.0F),
				MetricUtil.getDip(context, 35.0F)));
		
		
		LinearLayout linearLayout_2 = new LinearLayout(context);
		linearLayout_2.setOrientation(LinearLayout.VERTICAL);
		linearLayout_2.setGravity(Gravity.CENTER);
		RelativeLayout.LayoutParams linearLayout_2Params = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		linearLayout_2Params.addRule(RelativeLayout.CENTER_IN_PARENT);
		relativeLayout_1.addView(linearLayout_2, linearLayout_2Params);
		
		ImageView logoImageView = new ImageView(context);
		logoImageView.setImageDrawable(ResourceLoader.getBitmapDrawable("vsoyou_logo.png"));
		linearLayout_2.addView(logoImageView, new LinearLayout.LayoutParams(
				MetricUtil.getDip(context, 131.0F),
				MetricUtil.getDip(context, 60.0F)));
		
//		TextView lineTextView = new TextView(context);
//		lineTextView.setBackgroundDrawable(ResourceLoader.getBitmapDrawable("blue_line.png"));
//		this.addView(lineTextView, new LinearLayout.LayoutParams(
//				ViewGroup.LayoutParams.MATCH_PARENT,
//				1));

	}

}
