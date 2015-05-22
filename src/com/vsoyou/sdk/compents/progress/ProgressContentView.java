package com.vsoyou.sdk.compents.progress;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vsoyou.sdk.resources.ResourceLoader;
import com.vsoyou.sdk.util.Decorator;
import com.vsoyou.sdk.util.MetricUtil;

public class ProgressContentView extends LinearLayout {

    private ImageView imageView;
    private TextView textView;

    public ProgressContentView(Context context) {
        super(context);
        init(context);
    }

    public ProgressContentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {

        setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        setGravity(Gravity.CENTER);
        setVisibility(GONE);
        setClickable(true);

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setMinimumWidth(MetricUtil.getDip(context, 300.0F));
        linearLayout.setMinimumHeight(MetricUtil.getDip(context, 90.0F));
        Decorator.setBackground(linearLayout, ResourceLoader.getNinePatchDrawable("data_20.pvd"));

        addView(linearLayout);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(MetricUtil.getDip(context, 25.0F), MetricUtil.getDip(context, 25.0F));
        layoutParams.rightMargin = MetricUtil.getDip(context, 8.0F);

        imageView = new ImageView(context);
        imageView.setLayoutParams(layoutParams);
        linearLayout.addView(imageView);

        textView = new TextView(context);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        textView.setTextColor(-16777216);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13.3F);
        linearLayout.addView(textView);

    }

    public final ProgressContentView setText(String text) {
        textView.setText(text);
        return this;
    }

    public final void show() {

        Decorator.setImage(imageView, ResourceLoader.getBitmapDrawable("data_15.pvd"));
        RotateAnimation localRotateAnimation = new RotateAnimation(0.0F, 360.0F, 1, 0.5F, 1, 0.5F);
        localRotateAnimation.setRepeatCount(-1);
        localRotateAnimation.setDuration(1000L);
        localRotateAnimation.setInterpolator(new LinearInterpolator());
        imageView.setAnimation(localRotateAnimation);
        setVisibility(VISIBLE);

    }

    public void show(String text) {
        setText(text);
        show();
    }

    public void close() {
        setVisibility(GONE);
        imageView.clearAnimation();
    }

}
