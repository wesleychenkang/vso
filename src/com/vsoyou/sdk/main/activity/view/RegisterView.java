package com.vsoyou.sdk.main.activity.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
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
import com.vsoyou.sdk.main.LoginCallbackInfo;
import com.vsoyou.sdk.main.PayManager;
import com.vsoyou.sdk.main.activity.view.progress.RegisterProgressView;
import com.vsoyou.sdk.main.entity.UserLoginEntity;
import com.vsoyou.sdk.main.entity.parser.UserLoginEntityParser;
import com.vsoyou.sdk.main.entity.requestparam.LoginRequestParam;
import com.vsoyou.sdk.resources.ResourceLoader;
import com.vsoyou.sdk.util.DESCoder;
import com.vsoyou.sdk.util.Decorator;
import com.vsoyou.sdk.util.MetricUtil;
import com.vsoyou.sdk.util.StringUtil;
import com.vsoyou.sdk.util.ToastUtil;

public class RegisterView extends LinearLayout implements View.OnClickListener {

	private static final String TAG = "RegisterView";
	private Context context;
	private ImageView backImageView;

	private MainView mainView;

	private EditText loginNameET;
	private EditText loginPwdET;
	private LinearLayout showPwdLinearLayout;
	private TextView registerTextView;

	private TextView serviceTextView;
	private ImageView autoLoginImageView;

	private boolean showPwd = false;

	@SuppressLint("NewApi")
	public RegisterView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public RegisterView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public RegisterView(Context context, MainView mainView) {
		super(context);
		this.context = context;
		this.mainView = mainView;
		initView();
		addListener();
	}

	private void addListener() {
		backImageView.setOnClickListener(this);
		showPwdLinearLayout.setOnClickListener(this);
		registerTextView.setOnClickListener(this);
		serviceTextView.setOnClickListener(this);
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
						25.0F));
		li_1Params.setMargins(0, MetricUtil.getDip(context, 10.0F), 0, 0);
		this.addView(linearLayout_1, li_1Params);

		TextView textView_1 = new TextView(context);
		textView_1.setText("用户注册");
		textView_1.setTextColor(Color.parseColor("#009cff"));
		textView_1.setGravity(Gravity.CENTER);
		textView_1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20.0F);
		linearLayout_1.addView(textView_1, new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT));

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

		Drawable editTextBg = ResourceLoader
				.getNinePatchDrawable("edittext_normal_bg.9.png");
		int padding_5 = MetricUtil.getDip(context, 5.0F);
		loginNameET = new EditText(context);
		loginNameET.setBackgroundDrawable(editTextBg);
		loginNameET.setHint("账号");
		loginNameET.setImeOptions(EditorInfo.IME_ACTION_NEXT);
		loginNameET.setSingleLine();
		loginNameET.setInputType(InputType.TYPE_CLASS_TEXT);
		loginNameET.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable edt) {
				try {
					String temp = edt.toString();
					String tem = temp.substring(temp.length() - 1,
							temp.length());
					char[] temC = tem.toCharArray();
					int mid = temC[0];
					if (mid >= 33 && mid <= 64) {// 数字 符号
						return;
					}
					if (mid >= 65 && mid <= 90) {// 大写字母
						return;
					}
					if (mid > 97 && mid <= 122) {// 小写字母
						return;
					}
					edt.delete(temp.length() - 1, temp.length());
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		loginNameET
				.setFilters(new InputFilter[] { new InputFilter.LengthFilter(15) });
		LinearLayout.LayoutParams loginNameETParams = new LinearLayout.LayoutParams(
				MetricUtil.getDip(context, 295.0F), MetricUtil.getDip(context,
						40.0F));
		int loginNameETPadding = MetricUtil.getDip(context, 10.0F);
		loginNameET.setPadding(padding_5, loginNameETPadding,
				padding_5, loginNameETPadding);
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
		allLinearLayout.addView(loginNameET, loginNameETParams);

		// 密码
		loginPwdET = new ClearEditText(context);
		loginPwdET.setBackgroundDrawable(editTextBg);
		loginPwdET.setHint("密码");
		loginPwdET.setImeOptions(EditorInfo.IME_ACTION_DONE);
		loginPwdET.setSingleLine();
		loginPwdET.setInputType(InputType.TYPE_CLASS_TEXT
				| InputType.TYPE_TEXT_VARIATION_PASSWORD);
		loginPwdET.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable edt) {
				try {
					String temp = edt.toString();
					String tem = temp.substring(temp.length() - 1,
							temp.length());
					char[] temC = tem.toCharArray();
					int mid = temC[0];
					if (mid >= 48 && mid <= 57) {// 数字
						return;
					}
					if (mid >= 65 && mid <= 90) {// 大写字母
						return;
					}
					if (mid > 97 && mid <= 122) {// 小写字母
						return;
					}
					edt.delete(temp.length() - 1, temp.length());
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		loginPwdET.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
				15) });
		LinearLayout.LayoutParams loginPwdETParams = new LinearLayout.LayoutParams(
				MetricUtil.getDip(context, 295.0F), MetricUtil.getDip(context,
						40.0F));
		int loginPwdETPadding = MetricUtil.getDip(context, 10.0F);
		loginPwdET.setPadding(padding_5, loginPwdETPadding,
				padding_5, loginPwdETPadding);
		loginPwdETParams.setMargins(0, MetricUtil.getDip(context, 10.0F), 0, 0);
		Drawable login_key = ResourceLoader.getBitmapDrawable("login_key.png");
		login_key.setBounds(0, 0, MetricUtil.getDip(context, 30.0F),
				MetricUtil.getDip(context, 30.0F));
		Drawable login_edite_delete = ResourceLoader
				.getBitmapDrawable("login_edite_delete.png");
		login_edite_delete.setBounds(0, 0, MetricUtil.getDip(context, 12.0F),
				MetricUtil.getDip(context, 12.0F));
		loginPwdET.setCompoundDrawables(login_key, null, login_edite_delete,
				null);
		loginPwdET.setCompoundDrawablePadding(MetricUtil.getDip(context, 5.0F));
		allLinearLayout.addView(loginPwdET, loginPwdETParams);

		RelativeLayout relativeLayout_1 = new RelativeLayout(context);
		LinearLayout.LayoutParams relativeLayout_1Params = new LinearLayout.LayoutParams(
				MetricUtil.getDip(context, 295.0F), MetricUtil.getDip(context,
						40.0F));
		relativeLayout_1Params.setMargins(MetricUtil.getDip(context, 5.0F),
				MetricUtil.getDip(context, 10.0F),
				MetricUtil.getDip(context, 5.0F), 0);
		allLinearLayout.addView(relativeLayout_1, relativeLayout_1Params);

		// 显示密码
		showPwdLinearLayout = new LinearLayout(context);
		showPwdLinearLayout.setGravity(Gravity.CENTER_VERTICAL);
		showPwdLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
		RelativeLayout.LayoutParams linearLayout_1Params = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		linearLayout_1Params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		relativeLayout_1.addView(showPwdLinearLayout, linearLayout_1Params);

		autoLoginImageView = new ImageView(context);
		autoLoginImageView.setImageDrawable(ResourceLoader
				.getBitmapDrawable("cancle_auto_login.png"));
		LinearLayout.LayoutParams autoLoginImageViewParams = new LinearLayout.LayoutParams(
				MetricUtil.getDip(context, 15.0F), MetricUtil.getDip(context,
						15.0F));
		autoLoginImageViewParams.setMargins(0, 0,
				MetricUtil.getDip(context, 5.0F), 0);
		showPwdLinearLayout.addView(autoLoginImageView,
				autoLoginImageViewParams);

		TextView autoLoginTV = new TextView(context);
		autoLoginTV.setText("显示密码");
		autoLoginTV.setTextColor(Color.parseColor("#464646"));
		autoLoginTV.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14.0F);
		LinearLayout.LayoutParams autoLoginTVParams = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		showPwdLinearLayout.addView(autoLoginTV, autoLoginTVParams);

		registerTextView = new TextView(context);
		registerTextView.setText("立即注册");
		registerTextView.setGravity(Gravity.CENTER);
		registerTextView.setTextColor(Color.WHITE);
		registerTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20.0F);
		Decorator
				.setStateImage(
						registerTextView,
						ResourceLoader
								.getNinePatchDrawable("button_blue_normal.9.png"),
						ResourceLoader
								.getNinePatchDrawable("buttom_blue_pressed.9.png"),
						ResourceLoader
								.getNinePatchDrawable("button_blue_normal.9.png"));
		LinearLayout.LayoutParams sureTextViewParams = new LinearLayout.LayoutParams(
				MetricUtil.getDip(context, 295.0F), MetricUtil.getDip(context,
						40.0F));
		sureTextViewParams.setMargins(0, MetricUtil.getDip(context, -10.0F), 0,
				0);
		allLinearLayout.addView(registerTextView, sureTextViewParams);

		LinearLayout linearLayout_8 = new LinearLayout(context);
		linearLayout_8.setGravity(Gravity.CENTER);
		linearLayout_8.setOrientation(LinearLayout.HORIZONTAL);
		LinearLayout.LayoutParams linearLayout_8Params = new LinearLayout.LayoutParams(
				MetricUtil.getDip(context, 295.0F),
				ViewGroup.LayoutParams.WRAP_CONTENT);
		linearLayout_8Params.setMargins(0, MetricUtil.getDip(context, 10.0F),
				0, MetricUtil.getDip(context, 10.0F));
		allLinearLayout.addView(linearLayout_8, linearLayout_8Params);

		ImageView serviceImageView = new ImageView(context);
		serviceImageView.setImageDrawable(ResourceLoader
				.getBitmapDrawable("auto_login.png"));
		LinearLayout.LayoutParams serviceImageViewParams = new LinearLayout.LayoutParams(
				MetricUtil.getDip(context, 15.0F), MetricUtil.getDip(context,
						15.0F));
		autoLoginImageViewParams.setMargins(0, 0,
				MetricUtil.getDip(context, 5.0F), 0);
		linearLayout_8.addView(serviceImageView, serviceImageViewParams);

		serviceTextView = new TextView(context);
		serviceTextView.setText("搜游支付服务协议");
		serviceTextView.setTextColor(Color.parseColor("#037cff"));
		serviceTextView.setGravity(Gravity.CENTER);
		serviceTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16.0F);
		linearLayout_8.addView(serviceTextView, new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));

	}

	@Override
	public void onClick(View v) {
		if (v == backImageView) {
			mainView.showLoginView();
		}
		if (v == serviceTextView) {
			mainView.getServiceView().setFormWhere(2);
			mainView.showServiceView();
		}
		if (v == registerTextView) {
			startRegister();
		}
		if (v == showPwdLinearLayout) {
			if (showPwd) {
				loginPwdET.setInputType(InputType.TYPE_CLASS_TEXT
						| InputType.TYPE_TEXT_VARIATION_PASSWORD);
				autoLoginImageView.setImageDrawable(ResourceLoader
						.getBitmapDrawable("cancle_auto_login.png"));
				showPwd = false;
			} else {
				loginPwdET
						.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
				autoLoginImageView.setImageDrawable(ResourceLoader
						.getBitmapDrawable("auto_login.png"));
				showPwd = true;
			}
		}
	}

	private void startRegister() {
		mainView.getmHandler().sendEmptyMessage(1);
		String userName = "";
		String userPwd = "";
		HttpRequest<UserLoginEntity> request = new HttpRequest<UserLoginEntity>(
				context, null,
				new UserLoginEntityParser(), new LoginHttpCallback());
		if (StringUtil.isEmpty(loginNameET.getText().toString().trim())) {
			ToastUtil.showToast(context, "用戶名不能为空！");
			return;
		} else {
			userName = loginNameET.getText().toString().trim();
		}
		if (StringUtil.isEmpty(loginPwdET.getText().toString().trim())) {
			ToastUtil.showToast(context, "密码不能为空！");
			return;
		} else {
			userPwd = loginPwdET.getText().toString().trim();
		}
		request.execute(DESCoder.decryptoPriAndPub(context, mainView
				.getLocalStorage().getString(Constants.REGLOGIN_URL, "")),
				new LoginRequestParam(context, userName, userPwd).toJson());
	}

	private class LoginHttpCallback implements HttpCallback<UserLoginEntity> {

		@Override
		public void onSuccess(UserLoginEntity object) {
			mainView.getmHandler().sendEmptyMessage(2);
			if (object.success && object.loginId > 0) {
				saveInfo(object);
//				if (object.status == 0) { // 未绑定邮箱
//					mainView.showBindingView(Constants.REGISTER_TAG);
//				} else {
					Activity activity = (Activity) context;
					activity.finish();
					mainView.getLoginListener().onLoginCallback(
							new LoginCallbackInfo(object.id, object.userName,
									Constants.SUCCESS_TAG,
									ToastUtil.LOGIN_SUCCESS,Constants.REGISTER_TAG));
//				}
			} else {
				ToastUtil.showToast(context, object.message);
				String uName = mainView.getLocalStorage().getString(
						Constants.USERNAME, "");
				if (!StringUtil.isEmpty(uName)) {
					uName = DESCoder.decryptoPriAndPub(context, uName);
				}
				mainView.getLoginListener().onLoginCallback(
						new LoginCallbackInfo(mainView.getLocalStorage()
								.getLong(Constants.USER_ID, 0), uName,
								Constants.FAILURE_TAG, object.message,Constants.REGISTER_TAG));
			}
		}

		@Override
		public void onFailure(int errorCode, String errorMessage) {
			mainView.getmHandler().sendEmptyMessage(2);
			ToastUtil.showToast(context, ToastUtil.CHECK_NET);
			String uName = mainView.getLocalStorage().getString(
					Constants.USERNAME, "");
			if (!StringUtil.isEmpty(uName)) {
				uName = DESCoder.decryptoPriAndPub(context, uName);
			}
			mainView.getLoginListener().onLoginCallback(
					new LoginCallbackInfo(mainView.getLocalStorage().getLong(
							Constants.USER_ID, 0), uName,
							Constants.FAILURE_TAG, ToastUtil.CHECK_NET,Constants.REGISTER_TAG));
		}

	}

	public void saveInfo(UserLoginEntity object) {
		mainView.getLocalStorage().putString(Constants.USERNAME,
				DESCoder.encryptoPubAndPri(context, object.userName));
		mainView.getLocalStorage().putString(Constants.USERPASSWORD,
				DESCoder.encryptoPubAndPri(context, object.passWord));
		mainView.getLocalStorage()
				.putLong(Constants.SESSION_ID, object.loginId);
		mainView.getLocalStorage().putLong(Constants.USER_ID, object.id);
		if (object.status == 1) { // 已绑定邮箱
			mainView.getLocalStorage().putString(Constants.USER_EMAIL,
					object.eMail);
		}
	}

}
