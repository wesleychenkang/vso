package com.vsoyou.sdk.main.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.vsoyou.sdk.main.LoginListener;
import com.vsoyou.sdk.main.PayManager;
import com.vsoyou.sdk.main.activity.view.MainView;
import com.vsoyou.sdk.main.activity.view.dialog.ProgressDialog;
import com.vsoyou.sdk.main.enums.InitStatus;
import com.vsoyou.sdk.util.LogUtil;

@SuppressLint("HandlerLeak")
public class LoginActivity extends Activity {

	private static final String TAG = "LoginActivity";

	private MainView mainView;

	public static LoginListener loginListener;
	public static InitStatus initStatus;
	
	private ProgressDialog progressDialog;
	
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				showProgressDialog(LoginActivity.this, false);
				break;

			case 2:
				dismissProgressDialog();
				break;
			};
		}
	};

	public static void toLoginActivity(Context context, LoginListener listener,
			InitStatus pInitStatus, boolean isDefaultBg) {
		loginListener = listener;
		initStatus = pInitStatus;
		Intent toLoginActivityIntent = new Intent(context, LoginActivity.class);
		toLoginActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		toLoginActivityIntent.putExtra("isDefaultBg", isDefaultBg);
		context.startActivity(toLoginActivityIntent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogUtil.i("LoginActivity", "onCreate");
		LogUtil.i("LoginActivity", "LoginListener-->" + loginListener);
		boolean isDefaultBg = getIntent().getBooleanExtra("isDefaultBg", true);
		LogUtil.i("LoginActivity", "initStatus-->" + initStatus);
		mainView = new MainView(this, loginListener, initStatus, isDefaultBg, mHandler);
		setContentView(mainView);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (initStatus == InitStatus.initFailure) {
			PayManager.recycle(this);
		}
	}
	
	public void showProgressDialog(Context context, boolean payViewUp) {
		try {
			if (progressDialog != null) {
				if (progressDialog.isShowing()) {
					progressDialog.dismiss();
				}
				progressDialog = null;
			}
			progressDialog = new ProgressDialog(context, payViewUp);
			progressDialog.show();
		} catch (Exception e) {
			LogUtil.e(TAG, "showProgressDialog.e-->" + e);
		}

	}

	public void dismissProgressDialog() {
		try {
			if (progressDialog == null)
				return;
			if (!progressDialog.isShowing())
				return;
			progressDialog.dismiss();
			if (progressDialog.payViewUp) {
				Activity activity = (Activity) progressDialog.context;
				activity.finish();
			}
			progressDialog = null;
		} catch (Exception e) {
			LogUtil.e(TAG, "dismissProgressDialog.e-->" + e);
		}

	}

}
