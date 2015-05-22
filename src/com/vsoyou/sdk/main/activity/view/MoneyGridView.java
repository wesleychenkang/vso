package com.vsoyou.sdk.main.activity.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;

import com.vsoyou.sdk.util.MetricUtil;

public class MoneyGridView extends GridView {

	private Context context;

	@SuppressLint("NewApi")
	public MoneyGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MoneyGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MoneyGridView(Context context) {
		super(context);
		this.context = context;
		initView();
	}

	private void initView() {
		this.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		int dip_1 = MetricUtil.getDip(context, 1.0F);
		this.setVerticalSpacing(dip_1);
		this.setHorizontalSpacing(dip_1);
		this.setGravity(Gravity.CENTER);
		this.setCacheColorHint(Color.parseColor("#000000"));
		this.setBackgroundColor(Color.parseColor("#0068d9"));
		this.setSelector(new ColorDrawable(Color.TRANSPARENT));
		this.setNumColumns(2);
		this.setPadding(dip_1, dip_1, dip_1, dip_1);
	}
}
