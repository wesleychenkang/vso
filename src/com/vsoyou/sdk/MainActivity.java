package com.vsoyou.sdk;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.vsoyou.sdk.main.LoginCallbackInfo;
import com.vsoyou.sdk.main.LoginListener;
import com.vsoyou.sdk.main.PayManager;
import com.vsoyou.sdk.vscenter.FloatWindowManager;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LinearLayout linearLayout = new LinearLayout(this);
		linearLayout.setGravity(Gravity.CENTER);
		linearLayout.setOrientation(LinearLayout.HORIZONTAL);
		linearLayout.setBackgroundColor(Color.parseColor("#ffffff"));
		ImageView imageView = new ImageView(this);
		imageView.setBackgroundResource(R.drawable.login_view_bg);
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) { // 竖屏
			linearLayout.addView(imageView, new LinearLayout.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT));
		} else { // 横屏
			linearLayout.addView(imageView, new LinearLayout.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT));
		}
		setContentView(linearLayout, new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT));
		PayManager.initSdk(this, 1000000, 1000000001,
				"fed3d36ff81e4f388ae9661022005956");
		// PayManager.initSdk(this, 1000007, 1000007001,
		// "023984ea4b52fdae6015435274e83229");
		FloatWindowManager.getSdkManagerInstance(this).onActivityCreate(this, FloatWindowManager.GRAVITY_LEFT_TOP);
		PayManager.showLoginView(MainActivity.this, true, new LoginListener() {

			@Override
			public void onLoginCallback(LoginCallbackInfo loginCallbackInfo) {
				if (loginCallbackInfo.statusCode == 0) {
					// 支付成功 做对应操作
					MainActivity.this.finish();
					MainActivity.this.startActivity(new Intent(
							MainActivity.this, GameActivity.class));
				} else if (loginCallbackInfo.statusCode == -1) {
					// 支付失败 做对应操作
				}
			}
		});
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		FloatWindowManager.getSdkManagerInstance(this).onActivityResume(this);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		FloatWindowManager.getSdkManagerInstance(this).onActivityPause(this);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		FloatWindowManager.getSdkManagerInstance(this).onActivityDestory(this);
	}
}
