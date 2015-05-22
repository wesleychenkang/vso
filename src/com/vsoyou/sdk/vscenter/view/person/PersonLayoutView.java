package com.vsoyou.sdk.vscenter.view.person;

import android.annotation.SuppressLint;
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

import com.vsoyou.sdk.vscenter.ParamChain;
import com.vsoyou.sdk.vscenter.entiy.PersonCetnerResult;
import com.vsoyou.sdk.vscenter.entiy.User;
import com.vsoyou.sdk.vscenter.util.BitmapCache;
import com.vsoyou.sdk.vscenter.util.MetricUtil;
import com.vsoyou.sdk.vscenter.view.person.ILayoutHost.KeyILayoutHost;

@SuppressLint("NewApi")
public class PersonLayoutView extends BaseLayout {
	private String[] img = { "phone.png", "email.png", "password.png",
			"customer.png", "record.png" };
	private String[] dll = { "手机", "邮箱", "修改密码", "联系客服", "充值记录" };
	private String userName;
	private PersonCetnerResult result;

	public PersonLayoutView(Context context, ParamChain chain) {
		super(context, chain);
		super.initUI(context);
	}

	@Override
	protected void onInitUI(Context ctx) {
		FrameLayout sub = (FrameLayout) getView_subject(ctx);
		LinearLayout all = new LinearLayout(ctx);
		sub.addView(all, new FrameLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		all.setOrientation(HORIZONTAL);
		createLeftView(all, ctx);
		createCenterLine(all, ctx);
		createRightView(all, ctx);
	}

	@Override
	protected void initEnv(Context ctx, ParamChain env) {
		result = env.get(PersonCetnerResult.KEY, PersonCetnerResult.class);
	}

	private void createCenterLine(LinearLayout all, Context ctx) {
		LinearLayout l = new LinearLayout(ctx);
		l.setPadding(0, MetricUtil.getDip(ctx, 40), 0,
				MetricUtil.getDip(ctx, 40));
		View view = new View(ctx);
		view.setBackgroundColor(Color.rgb(235, 235, 235));
		LayoutParams lp_view = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.MATCH_PARENT);
		lp_view.width = 3;
		lp_view.gravity = Gravity.CENTER;
		l.addView(view, lp_view);
		all.addView(l, lp_view);
	}

	private void createLeftView(LinearLayout all, Context ctx) {
		LinearLayout lay = new LinearLayout(ctx);
		lay.setOrientation(VERTICAL);
		lay.setGravity(Gravity.CENTER);
		LayoutParams lp_lay = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		lp_lay.weight = 0.7f;
		all.addView(lay, lp_lay);

		ImageView img_icon = new ImageView(ctx);
		img_icon.setBackgroundDrawable(BitmapCache.getDrawable(ctx, "icon.png"));
		LayoutParams lp_icon = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		lp_icon.height = MetricUtil.getDip(ctx, 75);
		lp_icon.width = MetricUtil.getDip(ctx, 75);
		lay.addView(img_icon, lp_icon);

		TextView txt_name = new TextView(ctx);
		txt_name.setText("威搜游账号登录");
		txt_name.setPadding(0, MetricUtil.getDip(ctx, 15), 0,
				MetricUtil.getDip(ctx, 10));
		txt_name.setTextColor(Color.parseColor("#484848"));
		lay.addView(txt_name, new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));

		TextView txt_count = new TextView(ctx);
		User user = new User(ctx);
		txt_count.setText(user.getUserName());
		txt_count.setPadding(0, MetricUtil.getDip(ctx, 10), 0,
				MetricUtil.getDip(ctx, 10));
		txt_count.setTextColor(Color.parseColor("#484848"));
		txt_count.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(ctx,
				"gray_block.9.png"));
		lay.addView(txt_count, new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));

	}

	private void createRightView(LinearLayout rv, Context ctx) {
		LinearLayout all = new LinearLayout(ctx);
		LayoutParams lp_lay = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		lp_lay.weight = 0.3f;
		rv.addView(all, lp_lay);
		// rv.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(ctx,
		// "person_black.9.png"));
		all.setPadding(MetricUtil.getDip(ctx, 40), MetricUtil.getDip(ctx, 30),
				MetricUtil.getDip(ctx, 40), MetricUtil.getDip(ctx, 30));
		ScrollView scroll = new ScrollView(ctx);
		scroll.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(ctx,
				"person_black.9.png"));
		all.addView(scroll, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);

		LinearLayout all_content = new LinearLayout(ctx);
		all_content.setOrientation(VERTICAL);
		LayoutParams lp_content = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		scroll.addView(all_content, lp_content);

		for (int i = 0; i < img.length; i++) {
			{
				LinearLayout l_phone = new LinearLayout(ctx);
				// l_phone.setBackgroundColor(Color.parseColor("#FFFACD"));
				l_phone.setId(i + 20000);
				l_phone.setOnClickListener(this);
				l_phone.setOrientation(HORIZONTAL);
				l_phone.setPadding(15, 10, 15, 10);
				all_content.addView(l_phone, new LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

				// 个人手机 左边图像区域
				LinearLayout l_phone_left = new LinearLayout(ctx);
				// l_phone_left.setBackgroundColor(Color.RED);
				l_phone_left.setOrientation(HORIZONTAL);
				l_phone_left.setGravity(Gravity.LEFT | Gravity.CENTER);

				LayoutParams lp_left = new LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				lp_left.weight = 0.4f;
				l_phone.addView(l_phone_left, lp_left);

				ImageView imag_phone = new ImageView(ctx);
				imag_phone.setBackgroundDrawable(BitmapCache.getDrawable(ctx,
						img[i]));
				LayoutParams lp_imag_phone = new LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				l_phone_left.addView(imag_phone, lp_imag_phone);

				TextView txt_phone = new TextView(ctx);
				txt_phone.setText(dll[i]);
				txt_phone.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
				txt_phone.setTextColor(Color.parseColor("#151515"));
				txt_phone.setPadding(MetricUtil.getDip(ctx, 15),
						MetricUtil.getDip(ctx, 10), 0,
						MetricUtil.getDip(ctx, 10));
				LayoutParams lp_txt_phone = new LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				lp_txt_phone.gravity = Gravity.CENTER;
				// lp_txt_phone.leftMargin = 10;
				l_phone_left.addView(txt_phone, lp_txt_phone);

				LinearLayout l_phone_right = new LinearLayout(ctx);
				l_phone_right.setOrientation(HORIZONTAL);
				l_phone_right.setPadding(0, MetricUtil.getDip(ctx, 10),
						MetricUtil.getDip(ctx, 15), MetricUtil.getDip(ctx, 10));
				l_phone_right.setGravity(Gravity.RIGHT | Gravity.CENTER);
				LayoutParams lp_right = new LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				lp_right.weight = 0.6f;
				l_phone.addView(l_phone_right, lp_right);

				if (i ==0) {
					TextView text = new TextView(ctx);
					if (!result.phoneNumberStatus) {
						text.setText("未绑定");
					} else {
						text.setText("已绑定");
                        text.setTextColor(Color.RED);
					}
					text.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
					text.setPadding(0, 0, 10, 0);
					text.setTextColor(Color.parseColor("#151515"));
					l_phone_right.addView(text);
				}
				
				if(i==1){
					TextView text = new TextView(ctx);
					if (!result.emailStatus) {
						text.setText("未绑定");
					} else {
						text.setText("已绑定");
						text.setTextColor(Color.RED);

					}
					text.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
					text.setPadding(0, 0, 10, 0);
					text.setTextColor(Color.parseColor("#151515"));
					l_phone_right.addView(text);	
					
				}
				ImageView img_open = new ImageView(ctx);
				img_open.setBackgroundDrawable(BitmapCache.getDrawable(ctx,
						"open.png"));
				LayoutParams lp_imag = new LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				lp_imag.gravity = Gravity.CENTER;
				l_phone_right.addView(img_open, lp_imag);
				if (i < img.length - 1) {
					View view = new View(ctx);
					view.setBackgroundColor(Color.rgb(235, 235, 235));
					LayoutParams lp_view = new LayoutParams(
							LayoutParams.MATCH_PARENT,
							LayoutParams.WRAP_CONTENT);
					lp_view.height = 1;
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
				RecordLayoutView.class.getName());
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
