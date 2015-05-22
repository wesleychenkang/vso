package com.vsoyou.sdk.main.activity.view;

import com.vsoyou.sdk.resources.ResourceLoader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class LogoView extends LinearLayout {

	private Context context;
	private MainView mainView;

	@SuppressLint("NewApi")
	public LogoView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public LogoView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public LogoView(Context context, MainView mainView) {
		super(context);
		this.context = context;
		this.mainView = mainView;
		initView();
	}

	private void initView() {
		this.setLayoutParams(new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT));
		this.setGravity(Gravity.CENTER);
		this.setBackgroundColor(Color.WHITE);
		this.setOrientation(LinearLayout.HORIZONTAL);

		ImageView logoImageView = new ImageView(context);
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) { 

		} else {
			
		}
		logoImageView.setImageDrawable(ResourceLoader.getBitmapDrawable("vsoyou_logo.png"));
		this.addView(logoImageView, new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));
	}

}
