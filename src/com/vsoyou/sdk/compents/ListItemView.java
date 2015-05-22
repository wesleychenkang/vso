package com.vsoyou.sdk.compents;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vsoyou.sdk.resources.ResourceLoader;
import com.vsoyou.sdk.util.Decorator;

public class ListItemView extends LinearLayout {

	public static final String TAG = "ListItemView";
	private Context context;

	public ImageView iconImageView;
	public TextView appNameTextView;
	public TextView appSizeTextView;
	public TextView publishDateTextView;
	public TextView versionTextView;
	public Button downloadButton;
	public ProgressBar progressBar;

	public ListItemView(Context context) {
		super(context);
		this.context = context;
		initView();
	}

	private void initView() {
		this.setGravity(Gravity.CENTER);
		this.setOrientation(LinearLayout.VERTICAL);
		this.setLayoutParams(new AbsListView.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, 100));
		Decorator.setStateImage(this,
				ResourceLoader.getBitmapDrawable("data_13.pvd"),
				ResourceLoader.getBitmapDrawable("data_14.pvd"),
				ResourceLoader.getBitmapDrawable("data_13.pvd"));

		RelativeLayout relativeLayout_1 = new RelativeLayout(context);
		RelativeLayout.LayoutParams relativeLayout_1Params = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		relativeLayout_1Params.setMargins(5, 5, 5, 5);
		this.addView(relativeLayout_1, relativeLayout_1Params);

		LinearLayout linearLayout_1 = new LinearLayout(context);
		linearLayout_1.setGravity(Gravity.CENTER_VERTICAL);
		linearLayout_1.setOrientation(LinearLayout.HORIZONTAL);
		relativeLayout_1.addView(linearLayout_1, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));

		// icon
		iconImageView = new ImageView(context);
		iconImageView.setScaleType(ScaleType.FIT_XY);
		LinearLayout.LayoutParams iconImageViewParams = new LinearLayout.LayoutParams(
				72, 72);
		iconImageViewParams.setMargins(5, 0, 0, 0);
		iconImageView.setImageDrawable(ResourceLoader
				.getBitmapDrawable("data_6.pvd"));
		linearLayout_1.addView(iconImageView, iconImageViewParams);

		LinearLayout linearLayout_2 = new LinearLayout(context);
		LinearLayout.LayoutParams linearLayout_2Params = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		linearLayout_2Params.setMargins(7, 0, 0, 0);
		linearLayout_2.setOrientation(LinearLayout.VERTICAL);
		linearLayout_1.addView(linearLayout_2, linearLayout_2Params);

		// 应用名称
		appNameTextView = new TextView(context);
		appNameTextView.setTextSize(16f);
		appNameTextView.setTextColor(Color.parseColor("#029dde"));
		appNameTextView.setSingleLine();
		appNameTextView.setText("VSOYOU");
		linearLayout_2.addView(appNameTextView, new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));

		LinearLayout linearLayout_3 = new LinearLayout(context);
		linearLayout_3.setGravity(Gravity.CENTER_VERTICAL);
		linearLayout_3.setOrientation(LinearLayout.HORIZONTAL);
		LinearLayout.LayoutParams linearLayout_3Params = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		linearLayout_3Params.setMargins(0, 0, 0, 0);
		linearLayout_2.addView(linearLayout_3, linearLayout_3Params);

		// 评级
		ImageView starImageView = new ImageView(context);
		LinearLayout.LayoutParams starImageViewParams = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		starImageViewParams.gravity = Gravity.CENTER_VERTICAL;
		starImageView.setBackgroundDrawable(ResourceLoader
				.getBitmapDrawable("data_22.pvd"));
		linearLayout_3.addView(starImageView, starImageViewParams);

		// 大小
		appSizeTextView = new TextView(context);
		appSizeTextView.setGravity(Gravity.CENTER);
		appSizeTextView.setTextSize(10f);
		appSizeTextView.setText("大小：20M");
		LinearLayout.LayoutParams appSizeTextViewParams = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		appSizeTextViewParams.gravity = Gravity.CENTER_VERTICAL;
		appSizeTextViewParams.setMargins(10, 0, 0, 0);
		linearLayout_3.addView(appSizeTextView, appSizeTextViewParams);

		LinearLayout linearLayout_4 = new LinearLayout(context);
		LinearLayout.LayoutParams llinearLayout_4Params = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		llinearLayout_4Params.setMargins(0, 0, 0, 0);
		linearLayout_4.setOrientation(LinearLayout.HORIZONTAL);
		linearLayout_2.addView(linearLayout_4, llinearLayout_4Params);

		// 发布日期
		publishDateTextView = new TextView(context);
		publishDateTextView.setText("发布日期：2013-06-05 17:04");
		publishDateTextView.setTextSize(10f);
		linearLayout_4.addView(publishDateTextView,
				new LinearLayout.LayoutParams(
						ViewGroup.LayoutParams.WRAP_CONTENT,
						ViewGroup.LayoutParams.WRAP_CONTENT));
		// 版本
		versionTextView = new TextView(context);
		versionTextView.setText("版本：1.0");
		versionTextView.setTextSize(10f);
		versionTextView.setSingleLine();
		LinearLayout.LayoutParams versionTextViewParams = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		versionTextViewParams.setMargins(5, 0, 0, 0);
		linearLayout_4.addView(versionTextView, versionTextViewParams);

		LinearLayout linearLayout_5 = new LinearLayout(context);
		RelativeLayout.LayoutParams linearLayout_5Params = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		linearLayout_5Params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		linearLayout_5.setOrientation(LinearLayout.VERTICAL);
		linearLayout_5.setGravity(Gravity.CENTER);
		relativeLayout_1.addView(linearLayout_5, linearLayout_5Params);

		// 下载Button
		downloadButton = new Button(context);
		downloadButton.setFocusable(false);
		Decorator
				.setStateImage(
						downloadButton,
						ResourceLoader
								.getBitmapDrawable("data_8.pvd"),
						ResourceLoader
								.getBitmapDrawable("data_9.pvd"),
						ResourceLoader
								.getBitmapDrawable("data_8.pvd"));
		linearLayout_5.addView(downloadButton,  new LinearLayout.LayoutParams(
				75,
				67));
		
		progressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleLarge);
		LinearLayout.LayoutParams progressBarParams = new LinearLayout.LayoutParams(27,27); 
		progressBarParams.gravity = Gravity.CENTER;
		progressBar.setVisibility(View.GONE);
		this.addView(progressBar, progressBarParams);
	}
}
