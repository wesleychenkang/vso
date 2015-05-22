package com.vsoyou.sdk.main.activity.view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.vsoyou.sdk.main.Constants;
import com.vsoyou.sdk.main.LoginListener;
import com.vsoyou.sdk.main.enums.InitStatus;
import com.vsoyou.sdk.resources.ResourceLoader;
import com.vsoyou.sdk.util.LocalStorage;
import com.vsoyou.sdk.util.LogUtil;
import com.vsoyou.sdk.util.ToastUtil;

public class MainView extends FrameLayout{
	
	private static final String TAG	= "MainView";
	private Context context;
	private boolean isDefaultBg;
	private LoginView loginView;
	private AutoLoginView autoLoginView;
	private BindingEmailView bindingEmailView;
	private RegisterView registerView;
	private FindPwdView findPwdView;
	private ServiceView serviceView;
	private LogoView logoView;
	private LocalStorage localStorage;
	
	private LoginListener loginListener;
	private InitStatus initStatus;
	private Handler mHandler;
	
	public LocalStorage getLocalStorage() {
		return localStorage;
	}
	
	public LoginListener getLoginListener() {
		return loginListener;
	}
	
	public InitStatus getInitStatus() {
		return initStatus;
	}
	
	public LogoView getLogoView() {
		return logoView;
	}
	
	public ServiceView getServiceView() {
		return serviceView;
	}
	
	public FindPwdView getFindPwdView() {
		return findPwdView;
	}
	
	public RegisterView getRegisterView() {
		return registerView;
	}
	
	public LoginView getLoginView() {
		return loginView;
	}
	
	public AutoLoginView getAutoLoginView() {
		return autoLoginView;
	}
	
	public BindingEmailView getBindingEmailView() {
		return bindingEmailView;
	}
	
	public Handler getmHandler() {
		return mHandler;
	}

	public MainView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MainView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MainView(Context context, LoginListener loginListener, InitStatus initStatus, boolean isDefaultBg, Handler handler) {
		super(context);
		this.context = context;
		this.isDefaultBg = isDefaultBg;
		this.loginListener = loginListener;
		this.initStatus =  initStatus;
		this.localStorage = LocalStorage.getInstance(context);
		this.mHandler = handler;
		initView();
	}

	private void initView() {
		loginView = new LoginView(context, this, isDefaultBg);
		autoLoginView = new AutoLoginView(context, this);
		bindingEmailView = new BindingEmailView(context, this);
		registerView = new RegisterView(context, this);
		findPwdView = new FindPwdView(context, this);
		serviceView = new ServiceView(context, this);
		logoView = new LogoView(context, this);
		FrameLayout.LayoutParams commonParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		this.addView(loginView, commonParams);
		this.addView(autoLoginView, commonParams);
		this.addView(bindingEmailView, commonParams);
		this.addView(registerView, commonParams);
		this.addView(findPwdView, commonParams);
		this.addView(serviceView, commonParams);
		this.addView(logoView, commonParams);
//		autoLoginView.setVisibility(View.GONE);
//		loginView.setVisibility(View.GONE);
//		bindingEmailView.setVisibility(View.GONE);
//		registerView.setVisibility(View.GONE);
//		findPwdView.setVisibility(View.GONE);
//		serviceView.setVisibility(View.GONE);
		LogUtil.i(TAG, "initStatus-->" + initStatus);
		if(initStatus == InitStatus.initSuccess){
			if(localStorage.getBoolean(Constants.AUTO_LOGIN, true)){
				LogUtil.i(TAG, "showLoginViewAndAutoLoginView");
				showLoginViewAndAutoLoginView();
				getAutoLoginView().initData();
			}else{
				LogUtil.i(TAG, "showLoginView");
				showLoginView();
				getLoginView().getAutoLoginImageView().setImageDrawable(ResourceLoader
						.getBitmapDrawable("cancle_auto_login.png"));
			}
		}
		if(initStatus == InitStatus.initFailure){
			showLoginView();
			ToastUtil.showToast(context, ToastUtil.INIT_FAILURE);
		}
	}
	
	public void showBindingView(int type){
		getAutoLoginView().setVisibility(View.GONE);
		getLoginView().setVisibility(View.GONE);
		getRegisterView().setVisibility(View.GONE);
		getFindPwdView().setVisibility(View.GONE);
		getServiceView().setVisibility(View.GONE);
		getLogoView().setVisibility(View.GONE);
		getBindingEmailView().setLoginType(type);
		getBindingEmailView().setVisibility(View.VISIBLE);
	}
	
	public void showRegisterView(){
		getAutoLoginView().setVisibility(View.GONE);
		getLoginView().setVisibility(View.GONE);
		getBindingEmailView().setVisibility(View.GONE);
		getFindPwdView().setVisibility(View.GONE);
		getServiceView().setVisibility(View.GONE);
		getLogoView().setVisibility(View.GONE);
		getRegisterView().setVisibility(View.VISIBLE);
	}

	public void showLoginViewAndAutoLoginView() {
		getAutoLoginView().setVisibility(View.VISIBLE);
		getBindingEmailView().setVisibility(View.GONE);
		getRegisterView().setVisibility(View.GONE);
		getFindPwdView().setVisibility(View.GONE);
		getServiceView().setVisibility(View.GONE);
		getLogoView().setVisibility(View.GONE);
		getLoginView().setVisibility(View.VISIBLE);
	}
	
	public void showLoginView() {
		getAutoLoginView().setVisibility(View.GONE);
		getBindingEmailView().setVisibility(View.GONE);
		getRegisterView().setVisibility(View.GONE);
		getFindPwdView().setVisibility(View.GONE);
		getServiceView().setVisibility(View.GONE);
		getLogoView().setVisibility(View.GONE);
		getLoginView().setVisibility(View.VISIBLE);
	}
	
	public void showFindPwdView() {
		getBindingEmailView().setVisibility(View.GONE);
		getRegisterView().setVisibility(View.GONE);
		getLoginView().setVisibility(View.GONE);
		getServiceView().setVisibility(View.GONE);
		getLogoView().setVisibility(View.GONE);
		getFindPwdView().setVisibility(View.VISIBLE);
		getAutoLoginView().setVisibility(View.VISIBLE);
	}
	
	public void showServiceView() {
		getAutoLoginView().setVisibility(View.GONE);
		getBindingEmailView().setVisibility(View.GONE);
		getRegisterView().setVisibility(View.GONE);
		getLoginView().setVisibility(View.GONE);
		getFindPwdView().setVisibility(View.GONE);
		getLogoView().setVisibility(View.GONE);
		getServiceView().setVisibility(View.VISIBLE);
		getServiceView().updateData();
	}
	
	public void showLogoView() {
		getAutoLoginView().setVisibility(View.GONE);
		getBindingEmailView().setVisibility(View.GONE);
		getRegisterView().setVisibility(View.GONE);
		getLoginView().setVisibility(View.GONE);
		getFindPwdView().setVisibility(View.GONE);
		getServiceView().setVisibility(View.GONE);
		getLogoView().setVisibility(View.VISIBLE);
	}

}
