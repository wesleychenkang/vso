package com.vsoyou.sdk.main.activity.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.InputType;
import android.text.Spanned;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.vsoyou.sdk.http.HttpCallback;
import com.vsoyou.sdk.http.HttpRequest;
import com.vsoyou.sdk.main.Constants;
import com.vsoyou.sdk.main.PayManager;
import com.vsoyou.sdk.main.activity.view.progress.FindPwdProgressView;
import com.vsoyou.sdk.main.entity.FindPwdEntity;
import com.vsoyou.sdk.main.entity.parser.FindPwdEntityParser;
import com.vsoyou.sdk.main.entity.requestparam.FindPwdRequestParam;
import com.vsoyou.sdk.resources.ResourceLoader;
import com.vsoyou.sdk.util.DESCoder;
import com.vsoyou.sdk.util.Decorator;
import com.vsoyou.sdk.util.LogUtil;
import com.vsoyou.sdk.util.MetricUtil;
import com.vsoyou.sdk.util.StringUtil;
import com.vsoyou.sdk.util.ToastUtil;

public class FindPwdView extends LinearLayout implements View.OnClickListener {

	private static final String TAG = "RegisterView";
	private Context context;
	private ImageView backImageView;

	private MainView mainView;
	private TextView emailFindTT;
	private TextView phoneFindTT;
	private LinearLayout emailFindLL;
	private LinearLayout phoneFindLL;

	private TextView findTextView;
	private EditText loginNameET;

	private boolean isLeft = true;
	private Drawable leftNormal;
	private Drawable leftSelect;
	private Drawable rightNormal;
	private Drawable rightSelect;

	@SuppressLint("NewApi")
	public FindPwdView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public FindPwdView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FindPwdView(Context context, MainView mainView) {
		super(context);
		this.context = context;
		this.mainView = mainView;
		initDrawble();
		initView();
		addListener();
	}

	private void initDrawble() {
		leftNormal = ResourceLoader.getBitmapDrawable("find_left_bg.png");
		leftSelect = ResourceLoader.getBitmapDrawable("find_left_select_bg.png");
		rightNormal = ResourceLoader.getBitmapDrawable("find_right_bg.png");
		rightSelect = ResourceLoader.getBitmapDrawable("find_right_select_bg.png");

	}

	private void addListener() {
		backImageView.setOnClickListener(this);
		emailFindTT.setOnClickListener(this);
		phoneFindTT.setOnClickListener(this);
		findTextView.setOnClickListener(this);
	}

	@SuppressLint("NewApi")
	private void initView() {
		this.setGravity(Gravity.CENTER);
		this.setBackgroundColor(Color.parseColor("#ffffff"));
		this.setOrientation(LinearLayout.VERTICAL);
		BackView backView = new BackView(context);
		backImageView = backView.getBackImageView();
		this.addView(backView, new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));

		LinearLayout linearLayout_1 = new LinearLayout(context);
		linearLayout_1.setGravity(Gravity.CENTER);
		linearLayout_1.setOrientation(LinearLayout.HORIZONTAL);
		LinearLayout.LayoutParams li_1Params = new LinearLayout.LayoutParams(
				MetricUtil.getDip(context, 300.0F), MetricUtil.getDip(context,
						45.0F));
		li_1Params.setMargins(0, MetricUtil.getDip(context, 10.0F), 0, 0);
		this.addView(linearLayout_1, li_1Params);

		isLeft = true;
		int padding_5 = MetricUtil.getDip(context, 5.0F);
		emailFindTT = new TextView(context);
		emailFindTT.setText("绑定邮箱找回");
		emailFindTT.setTextColor(Color.parseColor("#ffffff"));
		emailFindTT.setGravity(Gravity.CENTER);
		emailFindTT.setBackgroundDrawable(leftSelect);
		emailFindTT.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18.0F);
		emailFindTT.setPadding(padding_5, padding_5, padding_5, padding_5);
		linearLayout_1.addView(emailFindTT, new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));

		phoneFindTT = new TextView(context);
		phoneFindTT.setText("账号申诉找回");
		phoneFindTT.setTextColor(Color.parseColor("#6b6b6b"));
		phoneFindTT.setGravity(Gravity.CENTER);
		phoneFindTT.setBackgroundDrawable(rightNormal);
		phoneFindTT.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18.0F);
		phoneFindTT.setPadding(padding_5, padding_5, padding_5, padding_5);
		linearLayout_1.addView(phoneFindTT, new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));

		ScrollView scrollView = new ScrollView(context);
		scrollView.setFadingEdgeLength(MetricUtil.getDip(context, 5.0F));
		scrollView.setBackgroundColor(Color.parseColor("#ffffff"));
		LinearLayout.LayoutParams scrollViewParams = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		scrollViewParams.setMargins(0, MetricUtil.getDip(context, 10.0F), 0, 0);
		this.addView(scrollView, scrollViewParams);

		LinearLayout allLinearLayout = new LinearLayout(context);
		allLinearLayout.setGravity(Gravity.CENTER);
		allLinearLayout.setOrientation(LinearLayout.VERTICAL);
		scrollView.addView(allLinearLayout, new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));

		emailFindLL = new LinearLayout(context);
		emailFindLL.setGravity(Gravity.CENTER);
		emailFindLL.setOrientation(LinearLayout.VERTICAL);
		allLinearLayout.addView(emailFindLL, new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));

		TextView textView_1 = new TextView(context);
		textView_1.setText("您可以使用绑定邮箱找回密码");
		textView_1.setGravity(Gravity.CENTER);
		textView_1.setTextColor(Color.parseColor("#525252"));
		textView_1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16.0F);
		emailFindLL.addView(textView_1, new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));

		LinearLayout linearLayout_2 = new LinearLayout(context);
		linearLayout_2.setGravity(Gravity.CENTER);
		linearLayout_2.setOrientation(LinearLayout.HORIZONTAL);
		LinearLayout.LayoutParams linearLayout_2Params = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		linearLayout_2Params.setMargins(0, MetricUtil.getDip(context, 10.0F),
				0, 0);
		emailFindLL.addView(linearLayout_2, linearLayout_2Params);

		TextView cardPwdTextView = new TextView(context);
		cardPwdTextView.setGravity(Gravity.CENTER_VERTICAL);
		cardPwdTextView.setText("账号：");
		cardPwdTextView.setTextColor(Color.parseColor("#666666"));
		cardPwdTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20.0F);
		LinearLayout.LayoutParams cardPwdTextViewParams = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		linearLayout_2.addView(cardPwdTextView, cardPwdTextViewParams);

		Drawable editTextBg = ResourceLoader
				.getNinePatchDrawable("edittext_normal_bg.9.png");
		loginNameET = new EditText(context);
		loginNameET.setBackgroundDrawable(editTextBg);
		loginNameET.setHint("账号");
		loginNameET.setImeOptions(EditorInfo.IME_ACTION_DONE);
		loginNameET.setSingleLine();
		loginNameET.setInputType(InputType.TYPE_CLASS_TEXT);
		LinearLayout.LayoutParams loginNameETParams = new LinearLayout.LayoutParams(
				MetricUtil.getDip(context, 200.0F), MetricUtil.getDip(context,
						40.0F));
		int loginNameETPadding = MetricUtil.getDip(context, 10.0F);
		loginNameET.setPadding(padding_5, loginNameETPadding, padding_5,
				loginNameETPadding);
		Drawable login_user = ResourceLoader
				.getBitmapDrawable("login_user.png");
		login_user.setBounds(0, 0, MetricUtil.getDip(context, 30.0F),
				MetricUtil.getDip(context, 30.0F));
		Drawable login_pull_down = ResourceLoader
				.getBitmapDrawable("login_pull_down.png");
		login_pull_down.setBounds(0, 0, MetricUtil.getDip(context, 12.0F),
				MetricUtil.getDip(context, 12.0F));
		loginNameET.setCompoundDrawables(login_user, null, login_pull_down,
				null);
		loginNameET
				.setCompoundDrawablePadding(MetricUtil.getDip(context, 5.0F));
		linearLayout_2.addView(loginNameET, loginNameETParams);

		findTextView = new TextView(context);
		findTextView.setText("找回");
		findTextView.setGravity(Gravity.CENTER);
		findTextView.setTextColor(Color.WHITE);
		findTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14.0F);
		Decorator
				.setStateImage(
						findTextView,
						ResourceLoader
								.getNinePatchDrawable("button_blue_normal.9.png"),
						ResourceLoader
								.getNinePatchDrawable("buttom_blue_pressed.9.png"),
						ResourceLoader
								.getNinePatchDrawable("button_blue_normal.9.png"));
		LinearLayout.LayoutParams findTextViewParams = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		int dip_5 = MetricUtil.getDip(context, 6.0F);
		findTextViewParams.setMargins(dip_5, 0, 0, 0);
		findTextView.setPadding(dip_5, dip_5, dip_5, dip_5);
		linearLayout_2.addView(findTextView, findTextViewParams);

		Spanned spanned = null;
		try {
			String phoneStr = DESCoder.decryptoPriAndPub(context, mainView
					.getLocalStorage().getString(Constants.SERVICEPHONE, ""));
			String qqStr = DESCoder.decryptoPriAndPub(context, mainView
					.getLocalStorage().getString(Constants.SERVICEQQ, ""));
			spanned = Html.fromHtml("客服电话：<font color='#5251f7'>" + phoneStr
					+ "</font><br>客服 QQ：<font color='#5251f7'>" + qqStr
					+ "</font>");

		} catch (Exception e) {
			LogUtil.e(TAG, "get phoneStr and qqStr exception:" + e);
		}

		TextView textView_3 = new TextView(context);
		if (spanned != null) {
			textView_3.setText(spanned);
		} else {
			textView_3.setText("客服电话：xxxxxxxx\n客服 QQ：xxxxxxxx");
		}
		textView_3.setGravity(Gravity.CENTER);
		textView_3.setTextColor(Color.parseColor("#525252"));
		textView_3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16.0F);
		LinearLayout.LayoutParams textView_3Params = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		textView_3Params.setMargins(0, MetricUtil.getDip(context, 15.0F), 0, 0);
		emailFindLL.addView(textView_3, textView_3Params);

		TextView textView_4 = new TextView(context);
		textView_4.setText("绑定邮箱账号安全，享受更多贴心服务！");
		textView_4.setGravity(Gravity.CENTER);
		textView_4.setTextColor(Color.parseColor("#b6b6b6"));
		textView_4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16.0F);
		LinearLayout.LayoutParams textView_4Params = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		textView_4Params.setMargins(0, MetricUtil.getDip(context, 15.0F), 0, 0);
		emailFindLL.addView(textView_4, textView_4Params);

		phoneFindLL = new LinearLayout(context);
		phoneFindLL.setGravity(Gravity.CENTER);
		phoneFindLL.setOrientation(LinearLayout.VERTICAL);
		allLinearLayout.addView(phoneFindLL, new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));

		TextView textView_5 = new TextView(context);
		textView_5.setText("      您的账号未绑定邮箱，请通过以下联系方式联系我们客服进行账号申诉。");
		textView_5.setGravity(Gravity.CENTER);
		textView_5.setTextColor(Color.parseColor("#525252"));
		textView_5.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16.0F);
		textView_5.setPadding(MetricUtil.getDip(context, 10.0F), 0,
				MetricUtil.getDip(context, 10.0F), 0);
		phoneFindLL.addView(textView_5, new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));

		TextView textView_6 = new TextView(context);
		if (spanned != null) {
			textView_6.setText(spanned);
		} else {
			textView_6.setText("客服电话：xxxxxxxx\n客服 QQ：xxxxxxxx");
		}
		textView_6.setGravity(Gravity.CENTER);
		textView_6.setTextColor(Color.parseColor("#525252"));
		textView_6.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16.0F);
		LinearLayout.LayoutParams textView_6Params = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		textView_6Params.setMargins(0, MetricUtil.getDip(context, 15.0F), 0, 0);
		phoneFindLL.addView(textView_6, textView_6Params);

		TextView textView_7 = new TextView(context);
		textView_7.setText("绑定邮箱账号安全，享受更多贴心服务！");
		textView_7.setGravity(Gravity.CENTER);
		textView_7.setTextColor(Color.parseColor("#b6b6b6"));
		textView_7.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16.0F);
		LinearLayout.LayoutParams textView_7Params = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		textView_7Params.setMargins(0, MetricUtil.getDip(context, 15.0F), 0, 0);
		phoneFindLL.addView(textView_7, textView_7Params);

		phoneFindLL.setVisibility(View.GONE);

	}

	@Override
	public void onClick(View v) {
		if (v == backImageView) {
			mainView.showLoginView();
		}
		if (v == emailFindTT) {
			emailFindLL.setVisibility(View.VISIBLE);
			phoneFindLL.setVisibility(View.GONE);
			if(!isLeft){
				changeBg();
			}
		}
		if (v == phoneFindTT) {
			emailFindLL.setVisibility(View.GONE);
			phoneFindLL.setVisibility(View.VISIBLE);
			if(isLeft){
				changeBg();
			}
		}
		if (v == findTextView) {
			String loginName = loginNameET.getText().toString().trim();
			if (!StringUtil.isEmpty(loginName)) {
				startFind(loginName);
			} else {
				ToastUtil.showToast(context, "用户名不能为空！");
			}
		}
	}

	private void changeBg() {
		if(isLeft){
			isLeft = false;
			emailFindTT.setBackgroundDrawable(leftNormal);
			phoneFindTT.setBackgroundDrawable(rightSelect);
			emailFindTT.setTextColor(Color.parseColor("#6b6b6b"));
			phoneFindTT.setTextColor(Color.parseColor("#ffffff"));
		}else{
			isLeft = true;
			emailFindTT.setBackgroundDrawable(leftSelect);
			phoneFindTT.setBackgroundDrawable(rightNormal);
			emailFindTT.setTextColor(Color.parseColor("#ffffff"));
			phoneFindTT.setTextColor(Color.parseColor("#6b6b6b"));
		}
	}

	private void startFind(String loginName) {
		mainView.getmHandler().sendEmptyMessage(1);
		HttpRequest<FindPwdEntity> request = new HttpRequest<FindPwdEntity>(
				context, null,
				new FindPwdEntityParser(), new FindPwdHttpCallback());
		request.execute(DESCoder.decryptoPriAndPub(context, mainView
				.getLocalStorage().getString(Constants.FINDPASS_URL, "")),
				new FindPwdRequestParam(context, loginName).toJson());
	}

	private class FindPwdHttpCallback implements HttpCallback<FindPwdEntity> {

		@Override
		public void onSuccess(FindPwdEntity object) {
			mainView.getmHandler().sendEmptyMessage(2);
			if (object != null && !StringUtil.isEmpty(object.eMail)) {
				ToastUtil.showToast(context, "密码找回已发送到" + object.eMail + "邮箱");
			} else {
				ToastUtil.showToast(context, object.message);
			}

		}

		@Override
		public void onFailure(int errorCode, String errorMessage) {
			mainView.getmHandler().sendEmptyMessage(2);
			ToastUtil.showToast(context, errorMessage);
		}

	}

	void showLoginName() {
		String loginName = mainView.getLocalStorage().getString(
				Constants.USERNAME, "");
		if (!StringUtil.isEmpty(loginName)) {
			loginNameET.setText(DESCoder.decryptoPriAndPub(context, loginName));
		}
	}

}
