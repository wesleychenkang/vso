package com.vsoyou.sdk.vscenter.view;

import java.lang.reflect.Field;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import com.vsoyou.sdk.vscenter.service.FloatCenterService;
import com.vsoyou.sdk.vscenter.service.FloatWindowService;
import com.vsoyou.sdk.vscenter.util.BitmapCache;
import com.vsoyou.sdk.vscenter.util.MetricUtil;

public class FloatCenterRightView extends LinearLayout implements
		OnClickListener {
	private WindowManager.LayoutParams lp;
	private int statusBarHeight;
	private float lastX, lastY;
	private ImageView imag;
	private ImageView imag_person;
	private ImageView image_forum;
	private TextView txt_forum;
	private TextView txt_person;
	private TextView txt_quetion;

	public FloatCenterRightView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public FloatCenterRightView(Context context) {
		super(context);
		initView(context);
	}

	private void initView(Context ctx) {
		setVisibility(View.GONE);
		FrameLayout all = new FrameLayout(ctx);
		LayoutParams lp_ll = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		lp_ll.width = MetricUtil.getDip(ctx, 185);
		addView(all, lp_ll);
		LinearLayout l_left = new LinearLayout(ctx);
		l_left.setBackgroundDrawable(BitmapCache.getDrawable(ctx,
				"float_right.png"));
		FrameLayout.LayoutParams lp_lleft = new FrameLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		lp_lleft.leftMargin = MetricUtil.getDip(ctx, 45);
		lp_lleft.topMargin = MetricUtil.getDip(ctx, 7);
		lp_lleft.gravity = Gravity.LEFT;
		all.addView(l_left, lp_lleft);
		l_left.setOrientation(HORIZONTAL);

		Drawable d = BitmapCache.getDrawable(ctx, "forum.png");
		d.setBounds(MetricUtil.getDip(ctx, 10), MetricUtil.getDip(ctx, 10),
				MetricUtil.getDip(ctx, 10), MetricUtil.getDip(ctx, 10));
		txt_forum = new TextView(ctx);
		txt_forum.setText("论坛");
		txt_forum.setPadding(MetricUtil.getDip(ctx,21),
				MetricUtil.getDip(ctx, 5), MetricUtil.getDip(ctx, 8),
				MetricUtil.getDip(ctx, 5));
		txt_forum.setOnClickListener(this);
		txt_forum.setTextSize(TypedValue.COMPLEX_UNIT_DIP,12f);
		txt_forum.setTextColor(Color.parseColor("#2d2d2d"));
		txt_forum.setCompoundDrawablesWithIntrinsicBounds(null, d, null, null);
		txt_forum.setId(1000021);
		LayoutParams lp_txt_forum = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		l_left.addView(txt_forum, lp_txt_forum);

		LayoutParams ll_forum_prams = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		ll_forum_prams.weight = 0.3f;
		// l_left.addView(ll_forum, ll_forum_prams);
		
		
		Drawable d_quetion = BitmapCache.getDrawable(ctx, "quetion.png");
		d_quetion.setBounds(MetricUtil.getDip(ctx, 10),
				MetricUtil.getDip(ctx, 10), MetricUtil.getDip(ctx, 10),
				MetricUtil.getDip(ctx, 10));
		txt_quetion = new TextView(ctx);
		txt_quetion.setText("提问");
		txt_quetion.setPadding(MetricUtil.getDip(ctx, 5),
				MetricUtil.getDip(ctx, 5), MetricUtil.getDip(ctx, 5),
				MetricUtil.getDip(ctx, 5));
		txt_quetion.setOnClickListener(this);
		txt_quetion.setTextColor(Color.parseColor("#2d2d2d"));
		txt_quetion.setTextSize(TypedValue.COMPLEX_UNIT_DIP,12f);
		txt_quetion.setCompoundDrawablesWithIntrinsicBounds(null, d_quetion,
				null, null);

		LayoutParams ll_txt_quetion = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		// ll_txt_person.gravity = Gravity.CENTER;
		l_left.addView(txt_quetion, ll_txt_quetion);


		// 个人中心

		Drawable d_person = BitmapCache.getDrawable(ctx, "person.png");
		d_person.setBounds(MetricUtil.getDip(ctx, 10),
				MetricUtil.getDip(ctx, 10), MetricUtil.getDip(ctx, 10),
				MetricUtil.getDip(ctx, 10));
		txt_person = new TextView(ctx);
		txt_person.setText("我");
		txt_person.setGravity(Gravity.CENTER);
		txt_person.setPadding(MetricUtil.getDip(ctx, 10),
				MetricUtil.getDip(ctx, 5), MetricUtil.getDip(ctx, 5),
				MetricUtil.getDip(ctx, 5));
		txt_person.setOnClickListener(this);
		txt_person.setTextColor(Color.parseColor("#2d2d2d"));
		txt_person.setTextSize(TypedValue.COMPLEX_UNIT_DIP,12f);
		txt_person.setCompoundDrawablesWithIntrinsicBounds(null, d_person,
				null, null);

		LayoutParams ll_txt_person = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		// ll_txt_person.gravity = Gravity.CENTER;
		l_left.addView(txt_person, ll_txt_person);

		

		

		FrameLayout frame_logo = new FrameLayout(ctx);
		ImageView back_round = new ImageView(ctx);
		back_round.setBackgroundDrawable(BitmapCache.getDrawable(ctx,
				"cricle.png"));
		frame_logo.addView(back_round);

		imag = new ImageView(ctx);
		imag.setBackgroundDrawable(BitmapCache.getDrawable(ctx, "logo.png"));
		frame_logo.addView(imag, new FrameLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
				Gravity.CENTER));
		imag.setOnClickListener(this);

		FrameLayout.LayoutParams l_logo = new FrameLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		l_logo.gravity = Gravity.LEFT;
		all.addView(frame_logo, l_logo);

	}

	public void setMangerLayParams(WindowManager.LayoutParams lp) {

		this.lp = lp;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		float x = event.getX();
		float y = event.getY() - getStatusBarHeight();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			lastX = x;
			lastY = y - getStatusBarHeight();
			break;
		case MotionEvent.ACTION_MOVE:
			float newx = event.getX();
			float newy = event.getY();
			float movex = newx - lastX;
			float movey = newy - lastY;
			if (Math.abs(movex) > 3 || Math.abs(movey) > 3) {
				updatePostion();
			}
			lp.x += (int) (x - lastX);
			lp.y += (int) (y - lastY);
		case MotionEvent.ACTION_UP:

			break;

		}
		return false;

	}

	private void updatePostion() {
		FloatWindowService.disPlayCenterView(getContext(), 0);
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
		if (arg0 == txt_person) {
			FloatWindowService.hideFloatView(getContext());
			FloatCenterService.getInstance().startPersonCenter(getContext());
		} else if (arg0 == imag) {
			updatePostion();
		} else if (arg0 == txt_forum) {
			FloatWindowService.hideFloatView(getContext());
			FloatCenterService.getInstance().startForumCenter(getContext());
		} else if (arg0 == txt_quetion) {
			FloatWindowService.hideFloatView(getContext());
			FloatCenterService.getInstance().startQuetionCenter(getContext());
		}
	}
}
