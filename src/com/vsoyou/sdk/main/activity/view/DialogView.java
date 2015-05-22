package com.vsoyou.sdk.main.activity.view;

import com.vsoyou.sdk.main.PayCallbackInfo;
import com.vsoyou.sdk.main.PayListener;
import com.vsoyou.sdk.resources.ResourceLoader;
import com.vsoyou.sdk.util.Decorator;
import com.vsoyou.sdk.util.MetricUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DialogView extends LinearLayout implements View.OnClickListener {
	
	private Context context;
	private TextView sureTextView;
	private PayCallbackInfo callbackInfo;
	private PayListener payListener;
	private AlertDialog dialog;

	@SuppressLint("NewApi")
	public DialogView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public DialogView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public DialogView(Context context, PayCallbackInfo callbackInfo, PayListener payListener, AlertDialog dialog) {
		super(context);
		this.context = context;
		this.callbackInfo = callbackInfo;
		this.payListener = payListener;
		this.dialog = dialog;
		initView();
	}

	private void initView() {
		this.setGravity(Gravity.CENTER);
		this.setOrientation(LinearLayout.VERTICAL);
//		this.setBackgroundColor(Color.parseColor("#ffffff"));
		int margin_10 = MetricUtil.getDip(context, 15.0F);
		this.setPadding(margin_10, margin_10, margin_10, margin_10);
		this.setLayoutParams(new LinearLayout.LayoutParams(
				MetricUtil.getDip(context, 300.0F),
				ViewGroup.LayoutParams.MATCH_PARENT));

		LinearLayout linearLayout_2 = new LinearLayout(context);
		linearLayout_2.setOrientation(LinearLayout.HORIZONTAL);
		this.addView(linearLayout_2, new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));
		
		TextView msgTextView = new TextView(context);
		msgTextView.setText(callbackInfo.statusDes);
//		msgTextView.setTextColor(Color.parseColor("#2a2a2a"));
		msgTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18.0F);
		linearLayout_2.addView(msgTextView, new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));

		LinearLayout linearLayout_1 = new LinearLayout(context);
		linearLayout_1.setGravity(Gravity.CENTER);
		linearLayout_1.setOrientation(LinearLayout.HORIZONTAL);
		LinearLayout.LayoutParams linearLayout_1Params = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		linearLayout_1Params.setMargins(0, MetricUtil.getDip(context, 10.0F), 0, 0);
		this.addView(linearLayout_1, linearLayout_1Params);
		
		sureTextView = new TextView(context);
		sureTextView.setTextColor(Color.WHITE);
		sureTextView.setText("确   定");
		sureTextView.setGravity(Gravity.CENTER);
		sureTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16.0F);
		Decorator
				.setStateImage(
						sureTextView,
						ResourceLoader
								.getNinePatchDrawable("button_blue_normal.9.png"),
						ResourceLoader
								.getNinePatchDrawable("buttom_blue_pressed.9.png"),
						ResourceLoader
								.getNinePatchDrawable("button_blue_normal.9.png"));
		linearLayout_1.addView(
				sureTextView,
				new LinearLayout.LayoutParams(
						MetricUtil.getDip(context, 80.0F), MetricUtil.getDip(
								context, 35.0F)));
		sureTextView.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v == sureTextView){
			Activity activity = (Activity) context;
			dialog.cancel();
			activity.finish();
			payListener.onPayCallback(callbackInfo);
		}
	}

}
