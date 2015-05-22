package com.vsoyou.sdk.main.activity.view;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vsoyou.sdk.http.HttpCallback;
import com.vsoyou.sdk.http.HttpRequest;
import com.vsoyou.sdk.main.Constants;
import com.vsoyou.sdk.main.LoginCallbackInfo;
import com.vsoyou.sdk.main.entity.UserLoginEntity;
import com.vsoyou.sdk.main.entity.parser.UserLoginEntityParser;
import com.vsoyou.sdk.main.entity.requestparam.LoginRequestParam;
import com.vsoyou.sdk.resources.ResourceLoader;
import com.vsoyou.sdk.util.DESCoder;
import com.vsoyou.sdk.util.Decorator;
import com.vsoyou.sdk.util.MetricUtil;
import com.vsoyou.sdk.util.StringUtil;
import com.vsoyou.sdk.util.ToastUtil;
import com.vsoyou.sdk.vscenter.FloatWindowManager;
import com.vsoyou.sdk.vscenter.view.FloatManager;

public class AutoLoginView extends LinearLayout implements View.OnClickListener {

	private static final String TAG = "AutoLoginView";
	private Context context;
	private TextView timeTextView;
	private TextView cancleTextView;
	private ImageView imageview_1;
	private ImageView imageview_2;
	private ImageView imageview_3;
	private ImageView imageview_4;

	private Timer imgSelectTimer;
	private Timer timePromptTimer;
	private int imgLocation;
	private int promptTime = 3;
	private Drawable imageSelected;
	private Drawable imageUnSelected;

	private MainView mainView;
	private String userName;
	private String userPwd;

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				imageview_1.setImageDrawable(imageUnSelected);
				imageview_2.setImageDrawable(imageSelected);
				break;
			case 1:
				imageview_2.setImageDrawable(imageUnSelected);
				imageview_3.setImageDrawable(imageSelected);
				break;
			case 2:
				imageview_3.setImageDrawable(imageUnSelected);
				imageview_4.setImageDrawable(imageSelected);
				break;
			case 3:
				imageview_4.setImageDrawable(imageUnSelected);
				imageview_1.setImageDrawable(imageSelected);
				break;

			case 10:
				if (promptTime <= -1) {
					timeTextView.setText("正在登陆...");
					cancleTimePromptTimer();
					cancleTextView.setClickable(false);
					// 开始注册登录
					startRegisterLogin();
				} else {
					timeTextView.setText("剩下" + promptTime + "秒自动登陆");
				}

				break;

			default:
				break;
			}
		};
	};

	@SuppressLint("NewApi")
	public AutoLoginView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public AutoLoginView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AutoLoginView(Context context, MainView mainView) {
		super(context);
		this.context = context;
		this.mainView = mainView;
		initView();
		addListener();
	}

	private void addListener() {
		cancleTextView.setOnClickListener(this);
	}

	private void initView() {
		 ColorDrawable dw = new ColorDrawable(Color.parseColor("#b0000000"));
		 this.setBackgroundDrawable(dw);
//		this.setBackgroundDrawable(ResourceLoader
//				.getBitmapDrawable("auto_login_bg.png"));
//		this.getBackground().setAlpha(180);
		this.setGravity(Gravity.CENTER);
		this.setOrientation(LinearLayout.VERTICAL);

		LinearLayout allLinearLayout = new LinearLayout(context);
		allLinearLayout.setBackgroundDrawable(ResourceLoader
				.getNinePatchDrawable("login_bg.9.png"));
		allLinearLayout.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams allLinearLayoutParams = new LinearLayout.LayoutParams(
				MetricUtil.getDip(context, 220.0F), MetricUtil.getDip(context,
						180.0F));
		this.addView(allLinearLayout, allLinearLayoutParams);

		timeTextView = new TextView(context);
		timeTextView.setBackgroundColor(Color.parseColor("#007aff"));
		timeTextView.setText("剩下2秒自动登陆");
		timeTextView.setTextColor(Color.WHITE);
		timeTextView.setGravity(Gravity.CENTER);
		timeTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18.0F);
		timeTextView.setBackgroundDrawable(ResourceLoader.getNinePatchDrawable("login_top_bg.9.png"));
		allLinearLayout.addView(
				timeTextView,
				new LinearLayout.LayoutParams(
						ViewGroup.LayoutParams.MATCH_PARENT, MetricUtil.getDip(
								context, 42.0F)));

		LinearLayout linearLayout_1 = new LinearLayout(context);
		linearLayout_1.setGravity(Gravity.CENTER);
		linearLayout_1.setOrientation(LinearLayout.HORIZONTAL);
		LinearLayout.LayoutParams linearLayout_1Params = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		linearLayout_1Params.setMargins(0, MetricUtil.getDip(context, 42.0F),
				0, 0);
		allLinearLayout.addView(linearLayout_1, linearLayout_1Params);

		imageSelected = ResourceLoader.getBitmapDrawable("data_10.pvd");
		imageUnSelected = ResourceLoader.getBitmapDrawable("data_11.pvd");
		imageview_1 = new ImageView(context);
		imageview_1.setImageDrawable(imageSelected);
		linearLayout_1.addView(imageview_1, new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));

		imageview_2 = new ImageView(context);
		imageview_1.setImageDrawable(imageUnSelected);
		LinearLayout.LayoutParams imageView_2Params = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		imageView_2Params
				.setMargins(MetricUtil.getDip(context, 15.0F), 0, 0, 0);
		linearLayout_1.addView(imageview_2, imageView_2Params);

		imageview_3 = new ImageView(context);
		imageview_3.setImageDrawable(imageUnSelected);
		linearLayout_1.addView(imageview_3, imageView_2Params);

		imageview_4 = new ImageView(context);
		imageview_4.setImageDrawable(imageUnSelected);
		linearLayout_1.addView(imageview_4, imageView_2Params);

		LinearLayout linearLayout_2 = new LinearLayout(context);
		linearLayout_2.setGravity(Gravity.CENTER);
		linearLayout_2.setOrientation(LinearLayout.HORIZONTAL);
		LinearLayout.LayoutParams linearLayout_2Params = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		linearLayout_2Params.setMargins(0, MetricUtil.getDip(context, 42.0F),
				0, 0);
		allLinearLayout.addView(linearLayout_2, linearLayout_2Params);

		cancleTextView = new TextView(context);
		cancleTextView.setTextColor(Color.WHITE);
		cancleTextView.setText("取    消");
		cancleTextView.setGravity(Gravity.CENTER);
		cancleTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16.0F);
		Decorator
				.setStateImage(
						cancleTextView,
						ResourceLoader
								.getNinePatchDrawable("button_blue_normal.9.png"),
						ResourceLoader
								.getNinePatchDrawable("buttom_blue_pressed.9.png"),
						ResourceLoader
								.getNinePatchDrawable("button_blue_normal.9.png"));
		linearLayout_2.addView(
				cancleTextView,
				new LinearLayout.LayoutParams(
						MetricUtil.getDip(context, 80.0F), MetricUtil.getDip(
								context, 35.0F)));

	}

	public void initData() {
		imgSelectTimer = new Timer();
		imgSelectTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				switch (imgLocation) {
				case 0:
					mHandler.sendEmptyMessage(0);
					break;
				case 1:
					mHandler.sendEmptyMessage(1);
					break;
				case 2:
					mHandler.sendEmptyMessage(2);
					break;
				case 3:
					mHandler.sendEmptyMessage(3);
					break;

				default:
					break;
				}
				imgLocation++;
				if (imgLocation == 4) {
					imgLocation = 0;
				}
			}
		}, 5 * 100, 5 * 100);

		timePromptTimer = new Timer();
		timePromptTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				promptTime--;
				mHandler.sendEmptyMessage(10);
			}
		}, 1000, 1000);
	}

	@Override
	public void onClick(View v) {
		if (v == cancleTextView) {
			this.setVisibility(View.GONE);
			cancleTimePromptTimer();
			cancleTimer();
		}
	}

	private void cancleTimePromptTimer() {
		timePromptTimer.cancel();
		promptTime = 3;
	}

	private void cancleTimer() {
		imgSelectTimer.cancel();
		imgLocation = 0;
	}

	protected void startRegisterLogin() {
		boolean isLogin = false;
		userName = mainView.getLocalStorage().getString(Constants.USERNAME, "");
		userPwd = mainView.getLocalStorage().getString(Constants.USERPASSWORD,
				"");
		if (StringUtil.isEmpty(userName)) {
			StringBuffer uNameBuffer = new StringBuffer();
			uNameBuffer.append("vsy_");
			for (int i = 0; i < 8; i++) {
				int uNameNum = new Random().nextInt(10);
				uNameBuffer.append("" + uNameNum);
			}
			userName = uNameBuffer.toString();
		} else {
			isLogin = true;
			userName = DESCoder.decryptoPriAndPub(context, userName);
			mainView.getLoginView().getLoginNameET().setText(userName);
		}
		if (StringUtil.isEmpty(userPwd)) {
			StringBuffer uPwdBuffer = new StringBuffer();
			for (int i = 0; i < 6; i++) {
				int uPwdNum = new Random().nextInt(10);
				uPwdBuffer.append("" + uPwdNum);
			}
			userPwd = uPwdBuffer.toString();
		} else {
			userPwd = DESCoder.decryptoPriAndPub(context, userPwd);
			mainView.getLoginView().getLoginPwdET().setText(userPwd);
		}
		String url = "";
		if (isLogin) {
			url = DESCoder.decryptoPriAndPub(context, mainView
					.getLocalStorage().getString(Constants.USERLOGIN_URL, ""));
		} else {
			url = DESCoder.decryptoPriAndPub(context, mainView
					.getLocalStorage().getString(Constants.REGLOGIN_URL, ""));
		}
		HttpRequest<UserLoginEntity> request = new HttpRequest<UserLoginEntity>(
				context, null, new UserLoginEntityParser(),
				new LoginHttpCallback(isLogin));
		request.execute(url,
				new LoginRequestParam(context, userName, userPwd).toJson());
	}

	private class LoginHttpCallback implements HttpCallback<UserLoginEntity> {
		
		private int type;
		
		public LoginHttpCallback(boolean isLogin) {
			if(isLogin){
				this.type = Constants.LOGIN_TAG;
			}else{
				this.type = Constants.REGISTER_TAG;
			}
		}

		@Override
		public void onSuccess(UserLoginEntity object) {
			if (object.success && object.loginId > 0) {
				saveInfo(object);
				cancleTimer();
//				if (object.status == 0) { // 未绑定邮箱
//					if (!StringUtil.isEmpty(object.eMail)) {
//						mainView.getBindingEmailView().setEmail(object.eMail);
//					}
//					mainView.showBindingView(type);
//				} else {
					Activity activity = (Activity) context;
					FloatManager.startFloatWindow(activity);
					activity.finish();
					mainView.getLoginListener().onLoginCallback(
							new LoginCallbackInfo(object.id, object.userName,
									Constants.SUCCESS_TAG,
									ToastUtil.LOGIN_SUCCESS, type));
//				}
			} else {
				ToastUtil.showToast(context, object.message);
				cancleTimer();
				mainView.showLoginView();
				String uName = mainView.getLocalStorage().getString(
						Constants.USERNAME, "");
				if (!StringUtil.isEmpty(uName)) {
					uName = DESCoder.decryptoPriAndPub(context, uName);
				}
				mainView.getLoginListener().onLoginCallback(
						new LoginCallbackInfo(mainView.getLocalStorage()
								.getLong(Constants.USER_ID, 0), uName,
								Constants.FAILURE_TAG, object.message, type));
			}
		}

		@Override
		public void onFailure(int errorCode, String errorMessage) {
			cancleTimer();
			mainView.showLoginView();
			ToastUtil.showToast(context, ToastUtil.CHECK_NET);
			String uName = mainView.getLocalStorage().getString(
					Constants.USERNAME, "");
			if (!StringUtil.isEmpty(uName)) {
				uName = DESCoder.decryptoPriAndPub(context, uName);
			}
			mainView.getLoginListener().onLoginCallback(
					new LoginCallbackInfo(mainView.getLocalStorage().getLong(
							Constants.USER_ID, 0), uName,
							Constants.FAILURE_TAG, ToastUtil.CHECK_NET, type));
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
	}

}
