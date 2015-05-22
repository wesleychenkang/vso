package com.vsoyou.sdk.main.activity.view.progress;

import com.vsoyou.sdk.compents.BasicWindowView;
import com.vsoyou.sdk.compents.progress.ProgressContentView;
import com.vsoyou.sdk.compents.progress.ProgressView;
import com.vsoyou.sdk.util.MetricUtil;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class InitProgressView extends BasicWindowView implements ProgressView {

    private ProgressContentView progressContentView;

    public InitProgressView(Context context) {
        super(context);
    }

    public InitProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InitProgressView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void init(Context context) {

        setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        setBackgroundColor(Color.argb(50, 40, 40, 40));
        setGravity(Gravity.CENTER);
        setClickable(true);

        progressContentView = new ProgressContentView(context);
        progressContentView.setLayoutParams(new LinearLayout.LayoutParams(MetricUtil.getDip(context, 200.0F), LayoutParams.WRAP_CONTENT));
        progressContentView.setText("初始化中...");

        addView(progressContentView);
        this.show = false;

    }

    protected void onShow() {
        progressContentView.show();
    }

    protected void onClose() {
        progressContentView.close();
    }

}
