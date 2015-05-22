package com.vsoyou.sdk;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.vsoyou.sdk.main.PayCallbackInfo;
import com.vsoyou.sdk.main.PayListener;
import com.vsoyou.sdk.main.PayManager;
import com.vsoyou.sdk.util.LogUtil;
import com.vsoyou.sdk.vscenter.FloatWindowManager;

public class GameActivity extends Activity implements OnClickListener {

	private Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_activity);
		button = (Button) findViewById(R.id.buy);
		button.setOnClickListener(this);
		FloatWindowManager.getSdkManagerInstance(this).onActivityCreate(this, FloatWindowManager.GRAVITY_LEFT_TOP);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.buy:
			PayManager.showPayView(GameActivity.this, 1000000001, 1000,
					getOrderNum()
//					"15379201406131234567"
					, 0, "", new PayListener() {

						@Override
						public void onPayCallback(
								PayCallbackInfo payCallbackInfo) {
							LogUtil.i("GameActivity", "payCallbackInfo-->"
									+ payCallbackInfo);
						}

					});
			
			break;

		default:
			break;
		}
	}

	private String getOrderNum() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String key = format.format(date);

		int uPwdNum = new Random().nextInt(10);
		key += uPwdNum;
		LogUtil.i("GameActivity", "outTradeNo: " + key);
		return key;
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
    	super.onStop();
    	FloatWindowManager.getSdkManagerInstance(this).onActivityPause(this);
    	
    }
	@Override
	protected void onDestroy() {
		super.onDestroy();
		LogUtil.i("GameActivity", "onDestroy");
		FloatWindowManager.getSdkManagerInstance(this).onActivityDestory(this);
		PayManager.recycle(this);
	}

}
