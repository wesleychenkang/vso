package com.vsoyou.sdk.main.activity.view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import android.widget.ScrollView;
import android.widget.TextView;

import com.vsoyou.sdk.entity.Result;
import com.vsoyou.sdk.entity.parser.ResultParser;
import com.vsoyou.sdk.http.HttpCallback;
import com.vsoyou.sdk.http.HttpRequest;
import com.vsoyou.sdk.main.Constants;
import com.vsoyou.sdk.main.LoginCallbackInfo;
import com.vsoyou.sdk.main.PayManager;
import com.vsoyou.sdk.main.activity.view.progress.BindingProgressView;
import com.vsoyou.sdk.main.entity.requestparam.AddEmailRequetParam;
import com.vsoyou.sdk.resources.ResourceLoader;
import com.vsoyou.sdk.util.DESCoder;
import com.vsoyou.sdk.util.Decorator;
import com.vsoyou.sdk.util.MetricUtil;
import com.vsoyou.sdk.util.StringUtil;
import com.vsoyou.sdk.util.ToastUtil;

public class BindingEmailView extends LinearLayout implements
		View.OnClickListener {

	private static final String TAG = "BindingEmailView";
	private Context context;
	private ImageView backImageView;
	private EditText emailEditText;
	
	private TextView bindingTextView;
	private TextView unBindingTextView;
	
	private MainView mainView;
	private String email;
	private int type; //登陆或注册

	@SuppressLint("NewApi")
	public BindingEmailView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public BindingEmailView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public BindingEmailView(Context context, MainView mainView) {
		super(context);
		this.context = context;
		this.mainView = mainView;
		initView();
		addListener();
	}

	private void addListener() {
		backImageView.setOnClickListener(this);
		bindingTextView.setOnClickListener(this);
		unBindingTextView.setOnClickListener(this);
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

		LinearLayout linearLayout_1 = new LinearLayout(context);
		linearLayout_1.setGravity(Gravity.CENTER);
		linearLayout_1.setOrientation(LinearLayout.HORIZONTAL);
		allLinearLayout.addView(
				linearLayout_1,
				new LinearLayout.LayoutParams(MetricUtil
						.getDip(context, 300.0F), MetricUtil.getDip(context,
						45.0F)));

		TextView textView_1 = new TextView(context);
		textView_1.setText("账号绑定邮箱");
		textView_1.setTextColor(Color.parseColor("#009cff"));
		textView_1.setGravity(Gravity.CENTER);
		textView_1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20.0F);
		linearLayout_1.addView(textView_1, new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT));
		
		//邮箱
		LinearLayout linearLayout_6 = new LinearLayout(context);
		linearLayout_6.setGravity(Gravity.CENTER);
		linearLayout_6.setOrientation(LinearLayout.HORIZONTAL);
		LinearLayout.LayoutParams linearLayout_6Params = new LinearLayout.LayoutParams(
				MetricUtil.getDip(context, 310.0F), MetricUtil.getDip(context,
						45.0F));
		linearLayout_6Params.setMargins(0, MetricUtil.getDip(context, 10.0F),
				0, 0);
		allLinearLayout.addView(linearLayout_6, linearLayout_6Params);

		TextView cardPwdTextView = new TextView(context);
		cardPwdTextView.setGravity(Gravity.CENTER_VERTICAL);
		cardPwdTextView.setText("邮箱：");
		cardPwdTextView.setTextColor(Color.parseColor("#666666"));
		cardPwdTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20.0F);
		LinearLayout.LayoutParams cardPwdTextViewParams = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		linearLayout_6.addView(cardPwdTextView, cardPwdTextViewParams);

		Drawable editTextBg = ResourceLoader
		.getNinePatchDrawable("edittext_normal_bg.9.png");
		int padding_5 = MetricUtil.getDip(context, 5.0F);
		emailEditText = new EditText(context);
		emailEditText.setBackgroundDrawable(editTextBg);
		emailEditText.setHint("请输入邮箱");
		emailEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
		emailEditText.setSingleLine();
		emailEditText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
		emailEditText.setPadding(padding_5, padding_5, padding_5, padding_5);
		LinearLayout.LayoutParams cardPwdEditTextParams = new LinearLayout.LayoutParams(
				MetricUtil.getDip(context, 220.0F), MetricUtil.getDip(context,
						40.0F));
		cardPwdEditTextParams.setMargins(MetricUtil.getDip(context, 1.0F),
				MetricUtil.getDip(context, 1.0F),
				MetricUtil.getDip(context, 1.0F),
				MetricUtil.getDip(context, 1.0F));
		linearLayout_6.addView(emailEditText, cardPwdEditTextParams);
		
		LinearLayout linearLayout_3 = new LinearLayout(context);
		linearLayout_3.setGravity(Gravity.CENTER_VERTICAL);
		linearLayout_3.setOrientation(LinearLayout.HORIZONTAL);
		LinearLayout.LayoutParams linearLayout_3Params = new LinearLayout.LayoutParams(
				MetricUtil.getDip(context, 280.0F),
				ViewGroup.LayoutParams.WRAP_CONTENT);
		linearLayout_3Params.setMargins(MetricUtil.getDip(context, 5.0F),
				MetricUtil.getDip(context, 20.0F),
				MetricUtil.getDip(context, 5.0F), MetricUtil.getDip(context, 10.0F));
		allLinearLayout.addView(linearLayout_3, linearLayout_3Params);

		bindingTextView = new TextView(context);
		bindingTextView.setText("立即绑定");
		bindingTextView.setGravity(Gravity.CENTER);
		bindingTextView.setTextColor(Color.WHITE);
		bindingTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16.0F);
		Decorator
				.setStateImage(
						bindingTextView,
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
		linearLayout_3.addView(bindingTextView, sureTextViewParams);

		unBindingTextView = new TextView(context);
		unBindingTextView.setText("暂不绑定");
		unBindingTextView.setTextColor(Color.WHITE);
		unBindingTextView.setGravity(Gravity.CENTER);
		unBindingTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16.0F);
		Decorator.setStateImage(unBindingTextView,
				ResourceLoader.getNinePatchDrawable("button_yellow_normal.9.png"),
				ResourceLoader.getNinePatchDrawable("button_yellow_pressed.9.png"),
				ResourceLoader.getNinePatchDrawable("button_yellow_normal.9.png"));
		LinearLayout.LayoutParams cancleTextViewParams = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT, MetricUtil.getDip(context,
						40.0F));
		cancleTextViewParams.weight = 1;
		cancleTextViewParams.setMargins(MetricUtil.getDip(context, 20.0F), 0,
				0, 0);
		linearLayout_3.addView(unBindingTextView, cancleTextViewParams);

	}

	@Override
	public void onClick(View v) {
		if(v == bindingTextView){
			startBinding();
		}
		if(v ==  unBindingTextView || v == backImageView){
			Activity activity = (Activity) context;
			activity.finish();
			String uName = mainView.getLocalStorage().getString(
					Constants.USERNAME, "");
			if (!StringUtil.isEmpty(uName)) {
				uName = DESCoder.decryptoPriAndPub(context, uName);
			}
			mainView.getLoginListener().onLoginCallback(
					new LoginCallbackInfo(mainView.getLocalStorage()
							.getLong(Constants.USER_ID, 0), uName,
							Constants.SUCCESS_TAG,
							ToastUtil.LOGIN_SUCCESS, type));
		}
	}
	
	protected void startBinding() {
		mainView.getmHandler().sendEmptyMessage(1);
		HttpRequest<Result> request = new HttpRequest<Result>(
				context, null, new ResultParser(),
				new BindingEmailHttpCallback());
		if (StringUtil.isEmpty(emailEditText.getText().toString().trim())) {
			ToastUtil.showToast(context, "邮箱不能为空！");
			return;
		} else {
			email = emailEditText.getText().toString().trim();
		}
		if(!checkEmail(email)){
			ToastUtil.showToast(context, "邮箱格式不正确！");
			return;
		}
		request.execute(DESCoder.decryptoPriAndPub(context, mainView
				.getLocalStorage().getString(Constants.ADDMAIL_URL, "")),
				new AddEmailRequetParam(context, mainView.getLocalStorage().getLong(
						Constants.USER_ID, 0), DESCoder.decryptoPriAndPub(context, mainView.getLocalStorage().getString(
				Constants.USERNAME, "")), email).toJson());
	}
	
	private boolean checkEmail(String email){
		  boolean flag = false;
		  try{
		   String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		   Pattern regex = Pattern.compile(check);
		   Matcher matcher = regex.matcher(email);
		   flag = matcher.matches();
		  }catch(Exception e){
		   flag = false;
		  }
		  return flag;
	}

	private class BindingEmailHttpCallback implements HttpCallback<Result> {

		@Override
		public void onSuccess(Result object) {
			mainView.getmHandler().sendEmptyMessage(2);
			if(object.success){
				ToastUtil.showToast(context, "已发送邮件到" + email + ",请登录邮箱激活绑定！");
			}else{
				ToastUtil.showToast(context, object.message);
			}
			Activity activity = (Activity) context;
			activity.finish();
			String uName = mainView.getLocalStorage().getString(
					Constants.USERNAME, "");
			if (!StringUtil.isEmpty(uName)) {
				uName = DESCoder.decryptoPriAndPub(context, uName);
			}
			mainView.getLoginListener().onLoginCallback(
					new LoginCallbackInfo(mainView.getLocalStorage()
							.getLong(Constants.USER_ID, 0), uName,
							Constants.SUCCESS_TAG,
							ToastUtil.LOGIN_SUCCESS, type));
		}

		@Override
		public void onFailure(int errorCode, String errorMessage) { 
			mainView.getmHandler().sendEmptyMessage(2);
			ToastUtil.showToast(context, ToastUtil.BINDING_EMAIL_FAILURA);
		}

	}

	public void setEmail(String email) {
		if(StringUtil.isEmpty(email) || "null".equals(email)){
			return;
		}
		emailEditText.setText(email);
	}

	public void setLoginType(int type) {
		this.type = type;
	}


}
