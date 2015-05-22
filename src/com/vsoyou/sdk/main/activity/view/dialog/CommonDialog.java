package com.vsoyou.sdk.main.activity.view.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vsoyou.sdk.resources.ResourceLoader;
import com.vsoyou.sdk.util.Decorator;
import com.vsoyou.sdk.util.MetricUtil;

public class CommonDialog extends AlertDialog implements View.OnClickListener {

	private Context context;
	private TextView sureTextView;
	private LinearLayout linearLayout_all;

	public CommonDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		// TODO Auto-generated constructor stub
	}

	public CommonDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
	}

	public CommonDialog(Context context) {
		super(context);
		getWindow().setBackgroundDrawable(new BitmapDrawable());
		this.context = context;
		initView();
	}

	private void initView() {
		linearLayout_all = new LinearLayout(context);
		linearLayout_all.setGravity(Gravity.CENTER);
		linearLayout_all.setOrientation(LinearLayout.VERTICAL);
		linearLayout_all.setBackgroundDrawable(ResourceLoader
				.getNinePatchDrawable("login_bg.9.png"));
		int margin_10 = MetricUtil.getDip(context, 15.0F);
		linearLayout_all.setPadding(margin_10, margin_10, margin_10, margin_10);
		this.setContentView(linearLayout_all, new LinearLayout.LayoutParams(
				MetricUtil.getDip(context, 300.0F),
				ViewGroup.LayoutParams.MATCH_PARENT));

		LinearLayout linearLayout_2 = new LinearLayout(context);
		linearLayout_2.setOrientation(LinearLayout.HORIZONTAL);
		linearLayout_all.addView(linearLayout_2, new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));
		
		TextView msgTextView = new TextView(context);
		msgTextView.setText("充值正在进行中，请稍候在游戏中查看，一般1-10分钟到帐。如未到账，请联系客服，祝您游戏愉快！");
		msgTextView.setTextColor(Color.parseColor("#2a2a2a"));
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
		linearLayout_all.addView(linearLayout_1, linearLayout_1Params);
		
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
			activity.finish();
		}
		
	}
	
	@Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (this==null||!this.isShowing()) {
            return super.dispatchTouchEvent(event);
        }
        boolean isOut=isOutOfBounds(event);
        if (event.getAction()==MotionEvent.ACTION_DOWN&&isOut) {
            this.dismiss();
            return true;
        }
        return false;
    }

    /**
     * 计算是否在PopupWindow外面点击
     *
     * @param event
     * @return
     */
    private boolean isOutOfBounds(MotionEvent event) {
        final int x=(int) event.getX();
        final int y=(int) event.getY();
        final int slop=ViewConfiguration.get(context).getScaledWindowTouchSlop();
        final View decorView= linearLayout_all;
        return (x<-slop)||(y<-slop)
            ||(x>(decorView.getWidth()+slop))
            ||(y>(decorView.getHeight()+slop));
    }




}
