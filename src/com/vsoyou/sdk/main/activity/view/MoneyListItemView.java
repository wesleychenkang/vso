package com.vsoyou.sdk.main.activity.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.TextView;

import com.vsoyou.sdk.util.MetricUtil;

public class MoneyListItemView extends TextView {
	
	private Context context;

	@SuppressLint("NewApi")
	public MoneyListItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MoneyListItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MoneyListItemView(Context context) {
		super(context);
		this.context = context;
		initView();
	}

	private void initView() {
		this.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		this.setBackgroundColor(Color.parseColor("#deefff"));
		this.setGravity(Gravity.CENTER);
		this.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16.0F);
		this.setTextColor(Color.parseColor("#4e4e4e"));
		int dip_5 = MetricUtil.getDip(context, 5.0F);
		this.setPadding(dip_5, dip_5, dip_5, dip_5);
	}

}
