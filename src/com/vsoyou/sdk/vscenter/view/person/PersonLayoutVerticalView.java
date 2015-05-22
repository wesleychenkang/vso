package com.vsoyou.sdk.vscenter.view.person;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

import com.vsoyou.sdk.vscenter.ParamChain;
import com.vsoyou.sdk.vscenter.ParamChain.KeyGlobal;
import com.vsoyou.sdk.vscenter.util.BitmapCache;
import com.vsoyou.sdk.vscenter.util.MetricUtil;
import com.vsoyou.sdk.vscenter.view.person.ILayoutHost.KeyILayoutHost;

public class PersonLayoutVerticalView extends BaseLayout {
	private String[] img = { "phone.png", "email.png", "password.png",
			"customer.png", "record.png" };
	private String[] dll = { "手机", "邮箱", "修改密码", "联系客服", "充值记录" };

	public PersonLayoutVerticalView(Context context, ParamChain chain) {
		super(context, chain);
		super.initUI(context);
	}

	@Override
	protected void onInitUI(Context ctx) {
		FrameLayout sub = (FrameLayout) getView_subject(ctx);
		LinearLayout all = new LinearLayout(ctx);
		sub.addView(all, new FrameLayout.LayoutParams(LP_MM));
		all.setOrientation(VERTICAL);
		createLeftView(all, ctx);
		createCenterLine(all, ctx);
		createRightView(all, ctx);

	}

	@Override
	protected void initEnv(Context ctx, ParamChain env) {
	}

	private void createCenterLine(LinearLayout all, Context ctx) {
		LinearLayout l = new LinearLayout(ctx);
		l.setPadding(MetricUtil.getDip(ctx, 20), MetricUtil.getDip(ctx, 20),
				MetricUtil.getDip(ctx, 20), MetricUtil.getDip(ctx, 20));
		View view = new View(ctx);
		view.setBackgroundColor(Color.rgb(235, 235, 235));
		LayoutParams lp_view = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		lp_view.height = 3;
		lp_view.gravity = Gravity.CENTER;
		l.addView(view, lp_view);
		all.addView(l, lp_view);
	}

	private void createLeftView(LinearLayout all, Context ctx) {
		LinearLayout lay = new LinearLayout(ctx);
		lay.setOrientation(HORIZONTAL);
		LayoutParams lp_lay = new LayoutParams(LP_MM);
		lp_lay.weight = 0.8f;
		all.addView(lay, lp_lay);

		LinearLayout l_imge = new LinearLayout(ctx);
		l_imge.setGravity(Gravity.CENTER);

		LayoutParams lp_img = new LayoutParams(LP_MM);
		lp_img.weight = 0.6f;
		lay.addView(l_imge, lp_img);

		ImageView img_icon = new ImageView(ctx);
		img_icon.setBackgroundDrawable(BitmapCache.getDrawable(ctx, "icon.png"));
		LayoutParams lp_icon = new LayoutParams(LP_WW);
		lp_icon.height = MetricUtil.getDip(ctx, 75);
		lp_icon.width = MetricUtil.getDip(ctx, 75);
		l_imge.addView(img_icon, lp_icon);

		LinearLayout ly_name = new LinearLayout(ctx);
		ly_name.setOrientation(VERTICAL);
		ly_name.setGravity(Gravity.CENTER | Gravity.LEFT);
		LayoutParams lp_name = new LayoutParams(LP_MM);
		lp_name.weight = 0.4f;
		lay.addView(ly_name, lp_name);
		TextView txt_name = new TextView(ctx);
		txt_name.setText("威搜游账号登录");
		txt_name.setPadding(0, MetricUtil.getDip(ctx, 15), 0,
				MetricUtil.getDip(ctx, 10));
		txt_name.setTextColor(Color.parseColor("#484848"));
		ly_name.addView(txt_name, new LayoutParams(LP_WW));

		TextView txt_count = new TextView(ctx);
		txt_count.setText("13632570627");
		txt_count.setPadding(0, MetricUtil.getDip(ctx, 10), 0,
				MetricUtil.getDip(ctx, 10));
		txt_count.setTextColor(Color.parseColor("#484848"));
		txt_count.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(ctx,
				"gray_block.9.png"));
		ly_name.addView(txt_count, new LayoutParams(LP_WW));

	}

	private void createRightView(LinearLayout rv, Context ctx) {
		ScrollView scroll = new ScrollView(ctx);
		LayoutParams lp_lay = new LayoutParams(LP_MM);
		lp_lay.weight = 0.2f;
		rv.addView(scroll, lp_lay);
		rv.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(ctx,
				"person_black.9.png"));
		scroll.setPadding(MetricUtil.getDip(ctx, 0),
				MetricUtil.getDip(ctx, 30), MetricUtil.getDip(ctx, 0),
				MetricUtil.getDip(ctx, 30));

		LinearLayout all = new LinearLayout(ctx);
		all.setOrientation(VERTICAL);
		LinearLayout all_content = new LinearLayout(ctx);
		all_content.setOrientation(VERTICAL);
		LayoutParams llll = new LayoutParams(LP_MW);
		all.addView(all_content, new LayoutParams(LP_MW));
		all.setPadding(MetricUtil.getDip(ctx, 10), MetricUtil.getDip(ctx, 0),
				MetricUtil.getDip(ctx, 10), MetricUtil.getDip(ctx, 0));
		all_content.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(ctx,
				"person_black.9.png"));
		scroll.addView(all, llll);
		for (int i = 0; i < img.length; i++) {
			{
				LinearLayout l_phone = new LinearLayout(ctx);
				// l_phone.setBackgroundColor(Color.parseColor("#FFFACD"));
				l_phone.setId(i + 20000);
				l_phone.setOnClickListener(this);
				l_phone.setOrientation(HORIZONTAL);
				l_phone.setPadding(15, 10, 15, 10);
				all_content.addView(l_phone, new LayoutParams(LP_MW));

				// 个人手机 左边图像区域
				LinearLayout l_phone_left = new LinearLayout(ctx);
				// l_phone_left.setBackgroundColor(Color.RED);
				l_phone_left.setOrientation(HORIZONTAL);
				l_phone_left.setGravity(Gravity.LEFT | Gravity.CENTER);

				LayoutParams lp_left = new LayoutParams(LP_MW);
				lp_left.weight = 0.4f;
				l_phone.addView(l_phone_left, lp_left);

				ImageView imag_phone = new ImageView(ctx);
				imag_phone.setBackgroundDrawable(BitmapCache.getDrawable(ctx,
						img[i]));
				LayoutParams lp_imag_phone = new LayoutParams(LP_WW);
				l_phone_left.addView(imag_phone, lp_imag_phone);

				TextView txt_phone = new TextView(ctx);
				txt_phone.setText(dll[i]);
				txt_phone.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
				txt_phone.setTextColor(Color.parseColor("#151515"));
				txt_phone.setPadding(MetricUtil.getDip(ctx, 15),
						MetricUtil.getDip(ctx, 10), 0,
						MetricUtil.getDip(ctx, 10));
				LayoutParams lp_txt_phone = new LayoutParams(LP_WW);
				lp_txt_phone.gravity = Gravity.CENTER;
				// lp_txt_phone.leftMargin = 10;
				l_phone_left.addView(txt_phone, lp_txt_phone);

				LinearLayout l_phone_right = new LinearLayout(ctx);
				l_phone_right.setOrientation(HORIZONTAL);
				l_phone_right.setPadding(0, MetricUtil.getDip(ctx, 10),
						MetricUtil.getDip(ctx, 15), MetricUtil.getDip(ctx, 10));
				l_phone_right.setGravity(Gravity.RIGHT | Gravity.CENTER);
				LayoutParams lp_right = new LayoutParams(LP_MW);
				lp_right.weight = 0.6f;
				l_phone.addView(l_phone_right, lp_right);

				if (i < 2) {
					TextView text = new TextView(ctx);
					text.setText("未绑定");
					text.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
					text.setPadding(0, 0, 10, 0);
					text.setTextColor(Color.parseColor("#151515"));
					l_phone_right.addView(text);
				}
				ImageView img_open = new ImageView(ctx);
				img_open.setBackgroundDrawable(BitmapCache.getDrawable(ctx,
						"open.png"));
				LayoutParams lp_imag = new LayoutParams(LP_WW);
				lp_imag.gravity = Gravity.CENTER;
				l_phone_right.addView(img_open, lp_imag);
				if (i < img.length - 1) {
					View view = new View(ctx);
					view.setBackgroundColor(Color.rgb(235, 235, 235));
					LayoutParams lp_view = new LayoutParams(LP_MW);
					lp_view.height = 3;
					all_content.addView(view, lp_view);
				}
			}
		}

	}

	@Override
	public void onClick(View v) {
		super.onClick(v);

		switch (v.getId()) {
		case 20000:
			tryEntryOtherView();
			break;
		case 20001:
			tryEnteyEmailView();
			break;
		case 20002:
			// Toast.makeText(getContext(), "2", Toast.LENGTH_SHORT).show();
			tryEnteyPassWordView();
			break;
		case 20003:
			tryEnteyCustumerView();
			break;
		case 20004:
			// 客服
			tryEnteyQuetionView();
			break;
		case 20005:
			Toast.makeText(getContext(), "5", Toast.LENGTH_SHORT).show();
			break;

		}

	}

	private void tryEntryOtherView() {
		ParamChain env = getEnv();
		ILayoutHost host = env.get(KeyILayoutHost.K_HOST, ILayoutHost.class);
		host.enter(env, ((Object) this).getClass().getClassLoader(),
				PhoneLayoutView.class.getName());

	}

	private void tryEnteyCustumerView() {

		ParamChain env = getEnv();
		ILayoutHost host = env.get(KeyILayoutHost.K_HOST, ILayoutHost.class);
		host.enter(env, ((Object) this).getClass().getClassLoader(),
				CustomerLayout.class.getName());
	}

	private void tryEnteyPassWordView() {

		ParamChain env = getEnv();
		ILayoutHost host = env.get(KeyILayoutHost.K_HOST, ILayoutHost.class);
		host.enter(env, ((Object) this).getClass().getClassLoader(),
				PasswordLayout.class.getName());
	}

	private void tryEnteyEmailView() {

		ParamChain env = getEnv();
		ILayoutHost host = env.get(KeyILayoutHost.K_HOST, ILayoutHost.class);
		host.enter(env, ((Object) this).getClass().getClassLoader(),
				EmailLayoutView.class.getName());
	}

	private void tryEnteyQuetionView() {
		ParamChain env = getEnv();
		ILayoutHost host = env.get(KeyILayoutHost.K_HOST, ILayoutHost.class);
		host.enter(env, ((Object) this).getClass().getClassLoader(),
				QuetionLayoutView.class.getName());
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

}
