package com.vsoyou.sdk.vscenter.view;

import java.lang.reflect.Field;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.vsoyou.sdk.vscenter.service.FloatWindowService;
import com.vsoyou.sdk.vscenter.util.BitmapCache;

public class FloatCenter extends LinearLayout implements OnClickListener {
	private static WindowManager.LayoutParams lp;
	private int statusBarHeight;
	private int oldoffsetX;
	private int oldoffsetY;
	private int tag = 0;
	private float lastX, lastY;
	private ImageView imag;

	public FloatCenter(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public FloatCenter(Context context) {
		super(context);
		initView(context);
	}

	private void initView(Context ctx) {
		setOrientation(HORIZONTAL);
		FrameLayout lay = new FrameLayout(ctx);
		ImageView back_round = new ImageView(ctx);
		back_round.setBackgroundDrawable(BitmapCache.getDrawable(ctx,
				"cricle.png"));
		lay.addView(back_round);

		imag = new ImageView(ctx);
		imag.setBackgroundDrawable(BitmapCache.getDrawable(ctx, "logo.png"));
		lay.addView(imag, new FrameLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
				Gravity.CENTER));
		addView(lay);
	}

	public void setMangerLayParams(WindowManager.LayoutParams lp) {

		this.lp = lp;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		oldoffsetX = lp.x;
		oldoffsetY = lp.y - getStatusBarHeight();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			lastX = event.getX();
			lastY = event.getY() - getStatusBarHeight();
			break;
		case MotionEvent.ACTION_MOVE:
			float x = event.getX();
			float y = event.getY() - getStatusBarHeight();
			int moveX = (int) (x - lastX);
			int moveY = (int) (y-lastY);
			
			tag = 1;
			if (Math.abs(moveX) > 5&&Math.abs(moveY)>5){
			lp.x += (int) (x - lastX);
			lp.y += (int) (y - lastY);
				updatePostion();
			}
			break;
		case MotionEvent.ACTION_UP:
			float newoffsetX = event.getX();
			float newoffsetY = event.getY() - getStatusBarHeight();
			if (lastX == newoffsetX && lastY == newoffsetY) {
				updateView(oldoffsetX, oldoffsetY);
			}
			break;

		}
		return false;
	}

	private void updateView(int x, int y) {
		if (lp.x > 0) {
			FloatWindowService.disPlayLeftView(getContext());
		} else {
			FloatWindowService.disPlayRightView(getContext());
		}

	}

	private void updatePostion() {
		WindowManager manager = (WindowManager) getContext().getSystemService(
				Context.WINDOW_SERVICE);
		manager.updateViewLayout(this, lp);

	}

	private int getStatusBarHeight() {
		if (statusBarHeight == 0) {
			try {
				Class<?> c = Class.forName("com.android.internal.R$dimen");
				Object o = c.newInstance();
				Field field = c.getField("status_bar_height");
				int x = (Integer) field.get(o);
				statusBarHeight = getResources().getDimensionPixelSize(x);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return statusBarHeight;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

}
