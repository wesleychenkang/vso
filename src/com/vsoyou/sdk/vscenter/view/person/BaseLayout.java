package com.vsoyou.sdk.vscenter.view.person;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.vsoyou.sdk.vscenter.ParamChain;
import com.vsoyou.sdk.vscenter.ParamChain.KeyGlobal;
import com.vsoyou.sdk.vscenter.protocols.ActivityControlInterface;
import com.vsoyou.sdk.vscenter.util.BitmapCache;
import com.vsoyou.sdk.vscenter.util.MetricUtil;
import com.vsoyou.sdk.vscenter.view.person.ILayoutHost.KeyILayoutHost;
public abstract class BaseLayout extends LinearLayout implements
		OnClickListener, ILayoutView {
	/** 横向根据内容填充，竖向填满 */
	protected final static LinearLayout.LayoutParams LP_WM = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
	/** 横向填满，竖向填满 */
	protected final static  LinearLayout.LayoutParams LP_MM = new  LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
	/** 横向根据内容填充，竖向根据内容填充 */
	protected final static  LinearLayout.LayoutParams LP_WW = new  LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
	/** 横向填满，竖向根据内容填充 */
	protected final static  LinearLayout.LayoutParams LP_MW = new  LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

	private ImageButton back;

	private ImageButton exit;

	private FrameLayout frame_sub;

	private TextView txt_title;

	private ParamChain mEnv;

	private ProgressDialog dialog;
	private ActivityControlInterface mActivityControl;

	public BaseLayout(Context context, AttributeSet attrs, ParamChain env) {

		super(context, attrs);
		initEnv(context, env);
	}

	public BaseLayout(Context context, ParamChain env) {
		super(context);
		init(context, env);
	}

	private void init(Context ctx, ParamChain env) {
		mEnv = env.grow(((Object) this).getClass().getName());
		initEnv(ctx, mEnv);
	}

	protected abstract void onInitUI(Context ctx);

	protected abstract void initEnv(Context ctx, ParamChain env);

	protected void initUI(Context context) {
		setOrientation(VERTICAL);
		createView(context, this);
		onInitUI(context);
	}

	private void createView(Context context, LinearLayout rv) {
		setBackgroundColor(Color.WHITE);
		FrameLayout title = new FrameLayout(context);

		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		rv.addView(title, lp);

		title.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(context,
				"toolbar.9.png"));
		txt_title = new TextView(context);
		txt_title.setText("个人中心");
		txt_title.setPadding(0, 10, 0, 10);
		txt_title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25f);
		// txt_title.setTextColor(Color.rgb(245, 245, 245));
		txt_title.setTextColor(Color.parseColor("#016ADE"));
		// tv.setTextColor(Color.parseColor("#fe501b"));
		txt_title.setGravity(Gravity.CENTER);
		txt_title.setSingleLine();
		title.addView(txt_title, new FrameLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
				Gravity.CENTER));

		// 返回
		back = new ImageButton(context);
		Drawable drawable = BitmapCache.getDrawable(context, "back.png");
		back.setBackgroundDrawable(drawable);
		back.setPadding(MetricUtil.getDip(context, 10.0F),
				MetricUtil.getDip(context, 10.0F),
				MetricUtil.getDip(context, 10.0F),
				MetricUtil.getDip(context, 10.0F));
		back.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		back.setClickable(true);
		back.setOnClickListener(this);
		FrameLayout.LayoutParams lp_back = new FrameLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
				Gravity.LEFT | Gravity.CENTER);
		lp_back.leftMargin = MetricUtil.getDip(context, 10);
		title.addView(back, lp_back);

		// 退出
		exit = new ImageButton(context);
		exit.setBackgroundDrawable(BitmapCache.getDrawable(context, "exit.png"));
		exit.setPadding(MetricUtil.getDip(context, 10.0F),
				MetricUtil.getDip(context, 10.0F),
				MetricUtil.getDip(context, 10.0F),
				MetricUtil.getDip(context, 10.0F));
		exit.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		exit.setClickable(true);
		exit.setOnClickListener(this);
		FrameLayout.LayoutParams lp_exit = new FrameLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
				Gravity.RIGHT | Gravity.CENTER);
		lp_exit.rightMargin = MetricUtil.getDip(context, 10);
		title.addView(exit, lp_exit);

		{
			// 主体区域
			FrameLayout frame_main = new FrameLayout(context);
			frame_main.setBackgroundDrawable(BitmapCache.getDrawable(context,
					"buttom_back.9.png"));
			rv.addView(frame_main, new FrameLayout.LayoutParams(LP_MM));
			View view = createViewSubject(context);
			frame_main
					.addView(view, new LayoutParams(LayoutParams.MATCH_PARENT,
							LayoutParams.MATCH_PARENT, 1.0f));
		}

		// 下标题栏
		FrameLayout title_buttom = new FrameLayout(context);
		FrameLayout.LayoutParams lp_buttom = new FrameLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT,
				Gravity.BOTTOM);
		lp_buttom.height = 50;
		title_buttom.setBackgroundColor(Color.RED);
		title_buttom.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(
				context, "top.9.png"));
		rv.addView(title_buttom, lp_buttom);
	}

	public View createViewSubject(Context context) {
		frame_sub = new FrameLayout(context);
		return frame_sub;
	}

	protected View getView_subject(Context context) {

		return frame_sub;
	}

	protected void setTitleText(String title) {
		txt_title.setText(title);
	}

	@Override
	public void onClick(View v) {
		if (back == v) {
			host_back();
		} else if (exit == v) {
			host_exit();
		}

	}

	@Override
	public boolean onEnter() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean onPause() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onResume() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getMainView() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public ParamChain getEnv() {
		// TODO Auto-generated method stub
		return mEnv;
	}

	@Override
	public boolean onExit() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isExitEnabled(boolean isBack) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAlive() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 返回
	 */
	protected void host_back() {
		ILayoutHost host = get_host();
		host.back();
	}

	/**
	 * 退出
	 */
	protected void host_exit() {
		ILayoutHost host = get_host();
		host.exit();
	}

	private ILayoutHost get_host() {
		if (mEnv != null) {
			return mEnv.get(KeyILayoutHost.K_HOST, ILayoutHost.class);
		}
		return null;
	}

	/**
	 * 获取Activity
	 * 
	 * @return
	 */
	protected Activity getActivity() {

		return getEnv().get(KeyGlobal.BASE_ACTIVITY, Activity.class);
	}

	protected void setActivityControlInterface(
			ActivityControlInterface controlInterface) {
		removeActivityControlInterface();
		mActivityControl = controlInterface;
		enableActivityControlInterface();
	}

	protected void enableActivityControlInterface() {
		if (null != mActivityControl) {
			ILayoutHost host = get_host();
			if (host != null) {
				host.addActivityControl(mActivityControl);
			}
		}

	}

	protected void removeActivityControlInterface() {
		if (mActivityControl != null) {
			disableActivityControlInterface();
			mActivityControl = null;
		}
	}

	private void disableActivityControlInterface() {
		if (null != mActivityControl) {
			ILayoutHost host = get_host();
			host.removeActivityControl(mActivityControl);
		}

	}

	protected void showDialog() {
		dialog = new ProgressDialog(getActivity());
		dialog.show();

	}

	protected void closeDialog() {
		dialog.dismiss();

	}
}
