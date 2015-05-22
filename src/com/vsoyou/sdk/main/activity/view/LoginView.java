package com.vsoyou.sdk.main.activity.view;

import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.InputFilter;
import android.text.InputType;
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
import android.widget.TextView;
import android.widget.Toast;

import com.vsoyou.sdk.http.HttpCallback;
import com.vsoyou.sdk.http.HttpRequest;
import com.vsoyou.sdk.main.Constants;
import com.vsoyou.sdk.main.LoginCallbackInfo;
import com.vsoyou.sdk.main.PayManager;
import com.vsoyou.sdk.main.entity.UserLoginEntity;
import com.vsoyou.sdk.main.entity.parser.UserLoginEntityParser;
import com.vsoyou.sdk.main.entity.requestparam.LoginRequestParam;
import com.vsoyou.sdk.main.enums.InitStatus;
import com.vsoyou.sdk.resources.ResourceLoader;
import com.vsoyou.sdk.util.DESCoder;
import com.vsoyou.sdk.util.Decorator;
import com.vsoyou.sdk.util.LogUtil;
import com.vsoyou.sdk.util.MetricUtil;
import com.vsoyou.sdk.util.StringUtil;
import com.vsoyou.sdk.util.ToastUtil;
import com.vsoyou.sdk.vscenter.FloatWindowManager;
import com.vsoyou.sdk.vscenter.view.FloatManager;

public class LoginView extends LinearLayout implements View.OnClickListener {

	private static final String TAG = "LoginView";
	private Context context;
	private EditText loginNameET;
	private EditText loginPwdET;
	private TextView forgetPwdTV;

	private LinearLayout autoLoginLinearLayout;
	private TextView registerTextView;
	private TextView loginTextView;
	private TextView serviceTextView;
	private ImageView autoLoginImageView;

	private MainView mainView;
	private String userName;
	private String userPwd;
	
	public ImageView getAutoLoginImageView() {
		return autoLoginImageView;
	}

	public EditText getLoginNameET() {
		return loginNameET;
	}

	public void setLoginNameET(EditText loginNameET) {
		this.loginNameET = loginNameET;
	}

	public EditText getLoginPwdET() {
		return loginPwdET;
	}

	public void setLoginPwdET(EditText loginPwdET) {
		this.loginPwdET = loginPwdET;
	}

	public TextView getLoginTextView() {
		return loginTextView;
	}

	public void setLoginTextView(TextView loginTextView) {
		this.loginTextView = loginTextView;
	}

	@SuppressLint("NewApi")
	public LoginView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public LoginView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public LoginView(Context context, MainView mainView, boolean isDefaultBg) {
		super(context);
		this.context = context;
		this.mainView = mainView;
		initView(isDefaultBg);
		addListener();
	}

	private void addListener() {
		forgetPwdTV.setOnClickListener(this);
		autoLoginLinearLayout.setOnClickListener(this);
		loginTextView.setOnClickListener(this);
		registerTextView.setOnClickListener(this);
		serviceTextView.setOnClickListener(this);
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	private void initView(boolean isDefaultBg) {
		if(isDefaultBg){
			this.setBackgroundDrawable(ResourceLoader
					.getBitmapDrawable("login_view_bg.png"));
		}else{
			try {
				this.setBackgroundDrawable(new BitmapDrawable(BitmapFactory.decodeStream(context.getResources().getAssets().open("login_view_bg.png"))));
			} catch (IOException e) {
				LogUtil.i(TAG, "setBackground-->" + e);
				this.setBackgroundDrawable(ResourceLoader
						.getBitmapDrawable("login_view_bg.png"));
			}
		}
		this.getBackground().setAlpha(0);
		this.setGravity(Gravity.CENTER);
		this.setOrientation(LinearLayout.VERTICAL);

		LinearLayout allLinearLayout = new LinearLayout(context);
		allLinearLayout.setBackgroundDrawable(ResourceLoader
				.getNinePatchDrawable("login_bg.9.png"));
		allLinearLayout.setGravity(Gravity.CENTER);
		allLinearLayout.setOrientation(LinearLayout.VERTICAL);
		int allPadding = MetricUtil.getDip(context, 10.0F);
		int allPadding_1 = MetricUtil.getDip(context, 10.0F);
		int allPadding_5 = MetricUtil.getDip(context, 5.0F);
		LogUtil.i(TAG, "-------- 1 --------");
		allLinearLayout.setPadding(allPadding, allPadding_5, allPadding,
				allPadding_1);
		LinearLayout.LayoutParams allLinearLayoutParams = new LinearLayout.LayoutParams(
				MetricUtil.getDip(context, 280.0F), MetricUtil.getDip(context,
						280.0F));
		this.addView(allLinearLayout, allLinearLayoutParams);

		ImageView logoImageView = new ImageView(context);
		logoImageView.setImageDrawable(ResourceLoader
				.getBitmapDrawable("vsoyou_logo.png"));
		allLinearLayout.addView(logoImageView, new LinearLayout.LayoutParams(
				MetricUtil.getDip(context, 131.0F),
				MetricUtil.getDip(context, 60.0F)));

		Drawable editTextBg = ResourceLoader
				.getNinePatchDrawable("edittext_normal_bg.9.png");
		
		int padding_5 = MetricUtil.getDip(context, 5.0F);

		// 账号
		loginNameET = new EditText(context);
		loginNameET.setBackgroundDrawable(editTextBg);
		loginNameET.setHint("账号");
		loginNameET.setImeOptions(EditorInfo.IME_ACTION_NEXT);
		loginNameET.setSingleLine();
		loginNameET.setInputType(InputType.TYPE_CLASS_TEXT);
		loginNameET
		.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
				15) });
		LinearLayout.LayoutParams loginNameETParams = new LinearLayout.LayoutParams(
				MetricUtil.getDip(context, 250.0F), MetricUtil.getDip(context,
						40.0F));
		int loginNameETPadding = MetricUtil.getDip(context, 10.0F);
		loginNameET.setPadding(padding_5, loginNameETPadding,
				padding_5, loginNameETPadding);
		loginNameETParams
				.setMargins(0, allPadding_5, 0, 0);
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
		loginPwdET.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
		loginPwdET
		.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
				10) });
		LinearLayout.LayoutParams loginPwdETParams = new LinearLayout.LayoutParams(
				MetricUtil.getDip(context, 250.0F), MetricUtil.getDip(context,
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
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		relativeLayout_1Params.setMargins(MetricUtil.getDip(context, 5.0F),
				MetricUtil.getDip(context, 10.0F),
				MetricUtil.getDip(context, 5.0F), 0);
		allLinearLayout.addView(relativeLayout_1, relativeLayout_1Params);

		// 忘记密码
		forgetPwdTV = new TextView(context);
		forgetPwdTV.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		forgetPwdTV.setText("忘记密码");
		forgetPwdTV.setTextColor(Color.parseColor("#fe501b"));
		forgetPwdTV.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14.0F);
		RelativeLayout.LayoutParams forgetPwdTVParams = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		forgetPwdTVParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		relativeLayout_1.addView(forgetPwdTV, forgetPwdTVParams);

		// 自动登陆
		autoLoginLinearLayout = new LinearLayout(context);
		autoLoginLinearLayout.setGravity(Gravity.CENTER_VERTICAL);
		autoLoginLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
		RelativeLayout.LayoutParams linearLayout_1Params = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		linearLayout_1Params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		relativeLayout_1.addView(autoLoginLinearLayout, linearLayout_1Params);

		autoLoginImageView = new ImageView(context);
		autoLoginImageView.setImageDrawable(ResourceLoader
				.getBitmapDrawable("auto_login.png"));
		LinearLayout.LayoutParams autoLoginImageViewParams = new LinearLayout.LayoutParams(
				MetricUtil.getDip(context, 15.0F),
				MetricUtil.getDip(context, 15.0F));
		autoLoginImageViewParams.setMargins(0, 0,
				MetricUtil.getDip(context, 5.0F), 0);
		autoLoginLinearLayout.addView(autoLoginImageView,
				autoLoginImageViewParams);

		TextView autoLoginTV = new TextView(context);
		autoLoginTV.setText("自动登陆");
		autoLoginTV.setTextColor(Color.parseColor("#464646"));
		autoLoginTV.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14.0F);
		LinearLayout.LayoutParams autoLoginTVParams = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		autoLoginLinearLayout.addView(autoLoginTV, autoLoginTVParams);

		LinearLayout linearLayout_3 = new LinearLayout(context);
		linearLayout_3.setGravity(Gravity.CENTER_VERTICAL);
		linearLayout_3.setOrientation(LinearLayout.HORIZONTAL);
		LinearLayout.LayoutParams linearLayout_3Params = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		linearLayout_3Params.setMargins(MetricUtil.getDip(context, 5.0F),
				MetricUtil.getDip(context, 10.0F),
				MetricUtil.getDip(context, 5.0F), 0);
		allLinearLayout.addView(linearLayout_3, linearLayout_3Params);

		registerTextView = new TextView(context);
		registerTextView.setText("快速注册");
		registerTextView.setGravity(Gravity.CENTER);
		registerTextView.setTextColor(Color.WHITE);
		registerTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16.0F);
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
				ViewGroup.LayoutParams.WRAP_CONTENT, MetricUtil.getDip(context,
						40.0F));
		sureTextViewParams.weight = 1;
		linearLayout_3.addView(registerTextView, sureTextViewParams);

		loginTextView = new TextView(context);
		loginTextView.setText("立即登陆");
		loginTextView.setTextColor(Color.WHITE);
		loginTextView.setGravity(Gravity.CENTER);
		loginTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16.0F);
		Decorator.setStateImage(loginTextView,
				ResourceLoader.getNinePatchDrawable("button_yellow_normal.9.png"),
				ResourceLoader.getNinePatchDrawable("button_yellow_pressed.9.png"),
				ResourceLoader.getNinePatchDrawable("button_yellow_normal.9.png"));
		LinearLayout.LayoutParams cancleTextViewParams = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT, MetricUtil.getDip(context,
						40.0F));
		cancleTextViewParams.weight = 1;
		cancleTextViewParams.setMargins(MetricUtil.getDip(context, 20.0F), 0,
				0, 0);
		linearLayout_3.addView(loginTextView, cancleTextViewParams);

		LinearLayout serviceLinearLayout = new LinearLayout(context);
		serviceLinearLayout.setBackgroundDrawable(ResourceLoader
				.getNinePatchDrawable("service_bg.9.png"));
		serviceLinearLayout.setGravity(Gravity.CENTER);
		LinearLayout.LayoutParams serviceLinearLayoutParams = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		serviceLinearLayoutParams.setMargins(0, MetricUtil.getDip(context, 10.0F),
				0, 0);
		allLinearLayout.addView(serviceLinearLayout, serviceLinearLayoutParams);
		serviceTextView = new TextView(context);
		serviceTextView.setText("搜游支付服务协议");
		serviceTextView.setTextColor(Color.parseColor("#717896"));
		serviceTextView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
		serviceTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13.0F);
		LinearLayout.LayoutParams serviceTextViewParams = new LinearLayout.LayoutParams(
				MetricUtil.getDip(context, 125.0F),
				ViewGroup.LayoutParams.WRAP_CONTENT);
		serviceLinearLayout.addView(serviceTextView, serviceTextViewParams);
		
		userName = mainView.getLocalStorage().getString(
				Constants.USERNAME, "");
		userPwd = mainView.getLocalStorage().getString(
				Constants.USERPASSWORD, "");
		if (!StringUtil.isEmpty(userName)) {
			userName = DESCoder.decryptoPriAndPub(context, userName);
			loginNameET.setText(userName);
		}
		if (!StringUtil.isEmpty(userPwd)) {
			userPwd = DESCoder.decryptoPriAndPub(context, userPwd);
			loginPwdET.setText(userPwd);
		} 

	}

	@Override
	public void onClick(View v) {
		if(mainView.getInitStatus() == InitStatus.initFailure){
			ToastUtil.showToast(context, ToastUtil.INIT_FAILURE);
			return;
		}
		if (v == forgetPwdTV) {
			mainView.getFindPwdView().showLoginName();
			mainView.showFindPwdView();
		}
		if (v == autoLoginLinearLayout) {
			if(mainView.getLocalStorage().getBoolean(Constants.AUTO_LOGIN, true)){
				getAutoLoginImageView().setImageDrawable(ResourceLoader
						.getBitmapDrawable("cancle_auto_login.png"));
				mainView.getLocalStorage().putBoolean(Constants.AUTO_LOGIN, false);
			}else{
				getAutoLoginImageView().setImageDrawable(ResourceLoader
						.getBitmapDrawable("auto_login.png"));
				mainView.getLocalStorage().putBoolean(Constants.AUTO_LOGIN, true);
			}
		}
		if (v == loginTextView) {
			startLogin();
		}
		if (v == registerTextView) {
			mainView.showRegisterView();
		}
		if (v == serviceTextView) {
			mainView.getServiceView().setFormWhere(1);
			mainView.showServiceView();
		}

	}
	
	protected void startLogin() {
		mainView.getmHandler().sendEmptyMessage(1);
		HttpRequest<UserLoginEntity> request = new HttpRequest<UserLoginEntity>(
				context, null, new UserLoginEntityParser(),
				new LoginHttpCallback());
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
				.getLocalStorage().getString(Constants.USERLOGIN_URL, "")),
				new LoginRequestParam(context, userName, userPwd).toJson());
	}

	private class LoginHttpCallback implements HttpCallback<UserLoginEntity> {

		@Override
		public void onSuccess(UserLoginEntity object) {
			mainView.getmHandler().sendEmptyMessage(2);
			if(object.success && object.loginId > 0){
				saveInfo(object);
//				if(object.status == 0){ //未绑定邮箱
//					if(!StringUtil.isEmpty(object.eMail)){
//						mainView.getBindingEmailView().setEmail(object.eMail);
//					}
//					mainView.showBindingView(Constants.LOGIN_TAG);
//				}else{
					Activity activity = (Activity) context;
					FloatManager.startFloatWindow(activity);
					activity.finish();
					mainView.getLoginListener().onLoginCallback(
							new LoginCallbackInfo(object.id, object.userName,
									Constants.SUCCESS_TAG,
									ToastUtil.LOGIN_SUCCESS,Constants.LOGIN_TAG));
//				}
			}else{
				ToastUtil.showToast(context, -1, object.message);
				String uName = mainView.getLocalStorage().getString(
						Constants.USERNAME, "");
				if (!StringUtil.isEmpty(uName)) {
					uName = DESCoder.decryptoPriAndPub(context, uName);
				}
				mainView.getLoginListener().onLoginCallback(
						new LoginCallbackInfo(mainView.getLocalStorage()
								.getLong(Constants.USER_ID, 0), uName,
								Constants.FAILURE_TAG,
								object.message,Constants.LOGIN_TAG));
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
					new LoginCallbackInfo(mainView.getLocalStorage()
							.getLong(Constants.USER_ID, 0), uName,
							Constants.FAILURE_TAG,
							ToastUtil.CHECK_NET, Constants.LOGIN_TAG));
		}

	}

	public void saveInfo(UserLoginEntity object) {
		mainView.getLocalStorage().putString(Constants.USERNAME, DESCoder.encryptoPubAndPri(context, object.userName));
		mainView.getLocalStorage().putString(Constants.USERPASSWORD, DESCoder.encryptoPubAndPri(context, object.passWord));
		mainView.getLocalStorage().putLong(Constants.SESSION_ID, object.loginId);
		mainView.getLocalStorage().putLong(Constants.USER_ID, object.id);
	}

}
