package com.vsoyou.sdk.main.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;

import com.vsoyou.sdk.http.HttpRequest;
import com.vsoyou.sdk.main.Constants;
import com.vsoyou.sdk.main.PayCallbackInfo;
import com.vsoyou.sdk.main.PayListener;
import com.vsoyou.sdk.main.activity.view.PayMainView;
import com.vsoyou.sdk.main.activity.view.dialog.ProgressDialog;
import com.vsoyou.sdk.main.entity.ThesInfoResult;
import com.vsoyou.sdk.main.entity.requestparam.UserCancleOrder;
import com.vsoyou.sdk.main.third.alipay.Result;
import com.vsoyou.sdk.util.DESCoder;
import com.vsoyou.sdk.util.LocalStorage;
import com.vsoyou.sdk.util.LogUtil;
import com.vsoyou.sdk.util.ToastUtil;

public class PayActivity extends Activity {

	private static final String TAG = "PayActivity";

	private static PayListener payListener;
	private String orderNum;
	private PayMainView mainView;

	private ProgressDialog progressDialog;

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				showProgressDialog(PayActivity.this, false);
				break;

			case 2:
				dismissProgressDialog();
				break;
			}
			;
		}
	};

	public static void toPayActivity(Context context,
			ThesInfoResult thesInfoResult, String orderNo, int productId,
			int price, boolean canUpdatePrice, int serviceId,
			String resultParam, PayListener mPayListener) {
		payListener = mPayListener;
		Intent toPayActivityIntent = new Intent(context, PayActivity.class);
		toPayActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		toPayActivityIntent.putExtra("thesInfo", thesInfoResult);
		toPayActivityIntent.putExtra("orderNo", orderNo);
		toPayActivityIntent.putExtra("productId", productId);
		toPayActivityIntent.putExtra("price", price);
		toPayActivityIntent.putExtra("canUpdatePrice", canUpdatePrice);
		toPayActivityIntent.putExtra("serviceId", serviceId);
		toPayActivityIntent.putExtra("resultParam", resultParam);
		context.startActivity(toPayActivityIntent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ThesInfoResult infoResult = (ThesInfoResult) getIntent()
				.getSerializableExtra("thesInfo");
		LogUtil.i(TAG, "infoResult-->" + infoResult.toString());
		LogUtil.i(TAG, "orderNo-->" + getIntent().getStringExtra("orderNo"));
		LogUtil.i(TAG, "productId-->" + getIntent().getIntExtra("productId", 0));
		orderNum = getIntent().getStringExtra("orderNo");
		mainView = new PayMainView(PayActivity.this, infoResult, orderNum,
				getIntent().getIntExtra("productId", 0), getIntent()
						.getIntExtra("price", 0), getIntent().getBooleanExtra(
						"canUpdatePrice", true), getIntent().getIntExtra(
						"serviceId", 0), getIntent().getStringExtra(
						"resultParam"), payListener, mHandler);
		setContentView(mainView);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		/*************************************************
		 * 
		 * 步骤3：处理银联手机支付控件返回的支付结果
		 * 
		 ************************************************/

		PayCallbackInfo payCallbackInfo = mainView.getPayCallbackInfo();

		if (data == null) {
			payCallbackInfo.statusCode = Constants.FAILURE_TAG;
			payCallbackInfo.statusDes = ToastUtil.PAY_FAILURE;
			mainView.showSureDialog(payCallbackInfo);
			return;

		}

		/*
		 * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
		 */
		String str = data.getExtras().getString("pay_result");
		if (str.equalsIgnoreCase("success")) {
			payCallbackInfo.statusCode = Constants.SUCCESS_TAG;
			payCallbackInfo.statusDes = ToastUtil.PAY_SUCCESS;
		} else if (str.equalsIgnoreCase("fail")) {
			payCallbackInfo.statusCode = Constants.FAILURE_TAG;
			payCallbackInfo.statusDes = ToastUtil.PAY_FAILURE;
		} else if (str.equalsIgnoreCase("cancel")) {
			HttpRequest<Result> request = new HttpRequest<Result>(
					PayActivity.this, null, null, null);
			request.execute(DESCoder.decryptoPriAndPub(
					PayActivity.this,
					LocalStorage.getInstance(PayActivity.this).getString(
							Constants.CANCELORDERS_URL, "")),
					new UserCancleOrder(PayActivity.this, orderNum).toJson());
			payCallbackInfo.statusCode = Constants.CANCLE_TAG;
			payCallbackInfo.statusDes = ToastUtil.CANCEL_ORDERNO;
		}
		mainView.showSureDialog(payCallbackInfo);

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// to-do
			return true;
		}
		return super.onKeyDown(keyCode, event);
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
