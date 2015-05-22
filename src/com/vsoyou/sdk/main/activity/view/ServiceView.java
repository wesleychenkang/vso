package com.vsoyou.sdk.main.activity.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.vsoyou.sdk.main.Constants;
import com.vsoyou.sdk.util.DESCoder;
import com.vsoyou.sdk.util.MetricUtil;

public class ServiceView extends LinearLayout implements View.OnClickListener {

	private static final String TAG = "ServiceView";
	private Context context;
	private ImageView backImageView;

	private MainView mainView;
	private WebView webView;
	
	private int formWhere = 0;// 1 = loginView; 2 = registerView;
	
	public void setFormWhere(int formWhere) {
		this.formWhere = formWhere;
	}

	@SuppressLint("NewApi")
	public ServiceView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public ServiceView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ServiceView(Context context, MainView mainView) {
		super(context);
		this.context = context;
		this.mainView = mainView;
		initView();
		addListener();
	}

	private void addListener() {
		backImageView.setOnClickListener(this);
	}

	@SuppressLint({ "NewApi", "SetJavaScriptEnabled" })
	private void initView() {
		this.setGravity(Gravity.CENTER);
		this.setBackgroundColor(Color.parseColor("#ffffff"));
		this.setOrientation(LinearLayout.VERTICAL);
		BackView backView = new BackView(context);
		backImageView = backView.getBackImageView();
		this.addView(backView, new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));

		ScrollView scrollView = new ScrollView(context);
		scrollView.setFadingEdgeLength(MetricUtil.getDip(context, 5.0F));
		scrollView.setBackgroundColor(Color.parseColor("#ffffff"));
		LinearLayout.LayoutParams scrollViewParams = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		scrollViewParams.setMargins(0, MetricUtil.getDip(context, 5.0F), 0, 0);
		this.addView(scrollView, scrollViewParams);

		LinearLayout allLinearLayout = new LinearLayout(context);
		allLinearLayout.setGravity(Gravity.CENTER);
		allLinearLayout.setOrientation(LinearLayout.VERTICAL);
		scrollView.addView(allLinearLayout, new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));

		webView = new WebView(context);
		webView.getSettings().setJavaScriptEnabled(true);
//		int serviceTTPadding = MetricUtil.getDip(context, 20.0F);
//		webView.setPadding(serviceTTPadding, serviceTTPadding,
//				serviceTTPadding, serviceTTPadding);
		allLinearLayout.addView(webView, new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));

	}

	@Override
	public void onClick(View v) {
		if (v == backImageView) {
			switch (formWhere) {
			case 1:
				mainView.showLoginView();
				break;
				
			case 2:
				mainView.showRegisterView();
				break;

			default:
				break;
			}
		}
	}

	public void updateData() {
		webView.loadUrl(DESCoder.decryptoPriAndPub(context, mainView.getLocalStorage().getString(Constants.USERTERMS_URL, "")));
	}

}
