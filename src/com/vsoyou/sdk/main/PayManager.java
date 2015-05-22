package com.vsoyou.sdk.main;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;

import com.vsoyou.sdk.ad.AdManger;
import com.vsoyou.sdk.compents.progress.ProgressView;
import com.vsoyou.sdk.entity.DeviceInfo;
import com.vsoyou.sdk.http.HttpCallback;
import com.vsoyou.sdk.http.HttpRequest;
import com.vsoyou.sdk.main.activity.LoginActivity;
import com.vsoyou.sdk.main.activity.PayActivity;
import com.vsoyou.sdk.main.activity.view.dialog.ProgressDialog;
import com.vsoyou.sdk.main.callback.GetThesInfoCallback;
import com.vsoyou.sdk.main.callback.InitCallback;
import com.vsoyou.sdk.main.entity.InitEntity;
import com.vsoyou.sdk.main.entity.ThesInfoResult;
import com.vsoyou.sdk.main.entity.parser.InitEntityParser;
import com.vsoyou.sdk.main.entity.parser.ThesInfoResultParser;
import com.vsoyou.sdk.main.entity.requestparam.GetPayCateRequestParam;
import com.vsoyou.sdk.main.entity.requestparam.UserExitRequestParam;
import com.vsoyou.sdk.main.enums.InitStatus;
import com.vsoyou.sdk.main.third.alipay.Result;
import com.vsoyou.sdk.util.DESCoder;
import com.vsoyou.sdk.util.LocalStorage;
import com.vsoyou.sdk.util.LogUtil;
import com.vsoyou.sdk.util.StringUtil;
import com.vsoyou.sdk.util.ToastUtil;

@SuppressLint("HandlerLeak")
public class PayManager implements InitCallback, GetThesInfoCallback {

	private static final String TAG = "PayManger";

	public static PayManager instance;

	private final static Lock lock = new ReentrantLock();

	private Context context;

	private PayListener payListener;
	private int productId;
	private int price;
	private boolean canUpdatePrice;
	private String orderNo;
	private int serviceId;
	private String resultParam;
//	private InitProgressView initProgressView;
	private ProgressDialog progressDialog;
	private boolean isDefaultBg;

	private InitStatus initStatus = InitStatus.noInit;
	private boolean initEndShowLoginView = false;
	private LoginListener loginListener;
	
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				if(instance != null){
					instance.showProgressDialog(instance.context, false);
				}
				break;

			case 2:
				if(instance != null){
					instance.dismissProgressDialog();
				}
				break;
			};
		}
	};
	
	public Handler getmHandler() {
		LogUtil.i(TAG, "getmHandler");
		return instance.mHandler;
	}
	
	public PayManager(Context context) {
		this.context = context;
	}
	
	public void showProgressDialog(Context context, boolean payViewUp) {
		try {
			if (instance.progressDialog != null) {
				if (instance.progressDialog.isShowing()) {
					instance.progressDialog.dismiss();
				}
				instance.progressDialog = null;
			}
			instance.progressDialog = new ProgressDialog(context, payViewUp);
			instance.progressDialog.show();
		} catch (Exception e) {
			LogUtil.e(TAG, "showProgressDialog.e-->" + e);
		}

	}

	public void dismissProgressDialog() {
		try {
			if (instance.progressDialog == null)
				return;
			if (!instance.progressDialog.isShowing())
				return;
			instance.progressDialog.dismiss();
			if (instance.progressDialog.payViewUp) {
				Activity activity = (Activity) instance.progressDialog.context;
				activity.finish();
			}
			instance.progressDialog = null;
		} catch (Exception e) {
			LogUtil.e(TAG, "dismissProgressDialog.e-->" + e);
		}

	}

	/**
	 * 回收
	 */
	public static void recycle(Context context) {
		getInstance(context);
		instance.startRecycle();
	}

	private void startRecycle() {
		LogUtil.i(TAG, "startRecycle");
		if(instance != null){
			HttpRequest<Result> request = new HttpRequest<Result>(instance.context,
					null, null, null);
			LocalStorage localStorage = LocalStorage.getInstance(instance.context);
			request.execute(
					DESCoder.decryptoPriAndPub(instance.context,
							localStorage.getString(Constants.USEROUT_URL, "")),
					new UserExitRequestParam(instance.context, localStorage
							.getLong(Constants.SESSION_ID, 0L)).toJson());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
		instance = null;
	}

	/**
	 * SDK初始化
	 * 
	 * @param context
	 *            当前Activity
	 * @param appId
	 *            APPID
	 * @param channelId
	 *            渠道ID
	 * @param appKey
	 *            APP密钥
	 */
	public static void initSdk(Context context, int appId, int channelId,
			String appKey) {
		saveInfo(context, appId, channelId, appKey);
		getInstance(context);
		instance.initalize();
	}

	/**
	 * 显示登陆界面
	 * 
	 * @param context
	 *            当前activity
	 * @param isDefaultBg
	 *            是否显示默认背景
	 * @param loginListener
	 *            登陆回调监听
	 */
	public static void showLoginView(Context context, boolean isDefaultBg, 
			LoginListener loginListener) {
		getInstance(context);
		instance.startShowLoginView(context, isDefaultBg, loginListener);
	}

	private void startShowLoginView(Context context, boolean isDefaultBg, 
			LoginListener loginListener) {
		instance.isDefaultBg = isDefaultBg;
		instance.loginListener = loginListener;
		instance.initEndShowLoginView = true;
		if (instance.initStatus == InitStatus.initing) {
			instance.mHandler.sendEmptyMessage(1);
		} else if (instance.initStatus == InitStatus.initSuccess
				|| instance.initStatus == InitStatus.initFailure) {
			instance.loginShow();
		} else if (instance.initStatus == InitStatus.noInit) {
			instance.initalize();
		}
	}

	/**
	 * 显示支付界面
	 * 
	 * @param context
	 * @param productId
	 *            产品ID
	 * @param defaultPrice
	 *            金额
	 * @param canUpdatePrice
	 *            是否可以修改传入的默认金额
	 * @param orderNo
	 *            订单号
	 * @param serviceId
	 *            服务器分区ID
	 * @param extra
	 *            结果参数
	 * @param payListener
	 *            支付回调监听
	 */
	public static void showPayView(Context context, int productId, int defaultPrice, 
			String orderNo, int serviceId, String extra, PayListener payListener) {
		getInstance(context);
		instance.payListener = payListener;
		instance.productId = productId;
		instance.price = defaultPrice;
		instance.canUpdatePrice = false;
		instance.orderNo = orderNo;
		instance.serviceId = serviceId;
		instance.resultParam = extra; 
		instance.payShow();
	}

	private void payShow() {
		PayManager.instance.getmHandler().sendEmptyMessage(1);
		HttpRequest<ThesInfoResult> request = new HttpRequest<ThesInfoResult>(
				instance.context, null,
				new ThesInfoResultParser(), new GetPayTypeListener());
		String url = LocalStorage.getInstance(instance.context).getString(
				Constants.THESINFO_URL, "");
		if (StringUtil.isEmpty(url)) {
			ToastUtil.showToast(instance.context, ToastUtil.INIT_FAILURE);
		} else {
			PayManager.instance.getmHandler().sendEmptyMessage(1);
			request.execute(DESCoder.decryptoPriAndPub(instance.context, url),
					new GetPayCateRequestParam(instance.context,
							instance.productId).toJson());
		}
	}

	private class GetPayTypeListener implements HttpCallback<ThesInfoResult> {

		@Override
		public void onSuccess(ThesInfoResult object) {
			PayManager.instance.getmHandler().sendEmptyMessage(2);
			LogUtil.i(TAG, "" + object.success);
			if (object.success) {
				onGetThesInfoSuccess(object);
			} else {
				onGetThesInfoFailure(object.code, object.message);
			}

		}

		@Override
		public void onFailure(int errorCode, String errorMessage) {
			PayManager.instance.getmHandler().sendEmptyMessage(2);
			onGetThesInfoFailure(errorCode, errorMessage);

		}

	}

	private void loginShow() {
		LoginActivity.toLoginActivity(instance.context, instance.loginListener,
				instance.initStatus, instance.isDefaultBg);
	}

	/**
	 * 支付
	 * 
	 * @param context
	 * @param productId
	 *            商品ID
	 * @param price
	 *            传入固定金额
	 * @param orderNo
	 *            订单号
	 * @param payListener
	 */
	@SuppressWarnings("unused")
	private static void startPay(Context context, int productId, int price, String orderNo,
			PayListener payListener) {
		pay(context, productId, price, orderNo, payListener);
	}

	private static void pay(Context context, int productId, int price, String orderNo,
			PayListener payListener) {
		LogUtil.i(TAG, "pay");
		getInstance(context);
		instance.payMent(context, productId, price, orderNo, payListener);

	}

	private void payMent(Context context, int productId, int price, String orderNo,
			PayListener payListener) {
		instance.payListener = payListener;
		instance.productId = productId;
		instance.price = price;
		instance.orderNo = orderNo;
	}

	private static PayManager getInstance(Context context) {
		try {
			lock.lock();
			if (instance == null) {
				instance = new PayManager(context);
			}else{
				instance.context = context;
			}
			return instance;
		} finally {
			lock.unlock();
		}
	}

	private void initalize() {
		init(this, null);
	}

	private void init(InitCallback initCallBack, ProgressView progressView) {
		LogUtil.i(TAG, "init");
		HttpRequest<InitEntity> request = new HttpRequest<InitEntity>(
				instance.context, progressView, new InitEntityParser(),
				new InitListener(initCallBack));
		initStatus = InitStatus.initing;
		request.execute(Constants.INIT_URL,
				new DeviceInfo(instance.context).toJSON());
	}

	private class InitListener implements HttpCallback<InitEntity> {

		private InitCallback initCallback;

		public InitListener(InitCallback initCallback) {
			this.initCallback = initCallback;
		}

		@Override
		public void onSuccess(InitEntity object) {
			LogUtil.i(TAG,
					"InitListener.onSuccess_object-->" + object.toString());
			if (object.success) { // 初始化成功
				LocalStorage storage = LocalStorage.getInstance(context);
				
				storage.putString(Constants.INIT_TAG, "true");
				storage.putString(Constants.USERLOGIN_URL, DESCoder
						.encryptoPubAndPri(((PayManager) initCallback).context,
								object.urlEntity.userLogin_url));
				storage.putString(Constants.REGLOGIN_URL, DESCoder
						.encryptoPubAndPri(((PayManager) initCallback).context,
								object.urlEntity.regLogin_url));
				storage.putString(Constants.ADCALLBACK_URL, DESCoder
						.encryptoPubAndPri(((PayManager) initCallback).context,
								object.urlEntity.adCallback_url));
				storage.putString(Constants.ADOPER_URL, DESCoder
						.encryptoPubAndPri(((PayManager) initCallback).context,
								object.urlEntity.adOper_url));
				storage.putString(Constants.ADTOPIC_URL, DESCoder
						.encryptoPubAndPri(((PayManager) initCallback).context,
								object.urlEntity.adTopic_url));
				LogUtil.i(TAG, "" + object.urlEntity.cmdPayment_url);
				storage.putString(Constants.CMDPAYMENT_URL, DESCoder
						.encryptoPubAndPri(((PayManager) initCallback).context,
								object.urlEntity.cmdPayment_url));
				LogUtil.i(TAG, "" + object.urlEntity.cmdCallback_url);
				storage.putString(Constants.CMDCALLBACK_URL, DESCoder
						.encryptoPubAndPri(((PayManager) initCallback).context,
								object.urlEntity.cmdCallback_url));
				storage.putString(Constants.THESINFO_URL, DESCoder
						.encryptoPubAndPri(((PayManager) initCallback).context,
								object.urlEntity.thesInfo_url));

				storage.putString(Constants.USERTERMS_URL, DESCoder
						.encryptoPubAndPri(((PayManager) initCallback).context,
								object.urlEntity.userTerms_url));
				storage.putString(Constants.ADDMAIL_URL, DESCoder
						.encryptoPubAndPri(((PayManager) initCallback).context,
								object.urlEntity.addMail_url));
				storage.putString(Constants.FINDPASS_URL, DESCoder
						.encryptoPubAndPri(((PayManager) initCallback).context,
								object.urlEntity.findPass_url));
				storage.putString(Constants.USEROUT_URL, DESCoder
						.encryptoPubAndPri(((PayManager) initCallback).context,
								object.urlEntity.userOut_url));
				storage.putString(Constants.ADDORDERS_URL, DESCoder
						.encryptoPubAndPri(((PayManager) initCallback).context,
								object.urlEntity.addOrders_url));
				storage.putString(Constants.CANCELORDERS_URL, DESCoder
						.encryptoPubAndPri(((PayManager) initCallback).context,
								object.urlEntity.cancelOrders_url));
				storage.putString(Constants.SERVICEPHONE, DESCoder
						.encryptoPubAndPri(((PayManager) initCallback).context,
								object.urlEntity.servicePhone));
				storage.putString(Constants.SERVICEQQ, DESCoder
						.encryptoPubAndPri(((PayManager) initCallback).context,
								object.urlEntity.serviceQQ));
				
				storage.putString(Constants.USERBBS_URL, DESCoder
						.encryptoPubAndPri(((PayManager) initCallback).context,
								object.urlEntity.userBBS_url));
				storage.putString(Constants.USERCENTER_URL, DESCoder
						.encryptoPubAndPri(((PayManager) initCallback).context,
								object.urlEntity.userCenter_url));
				storage.putString(Constants.USERUPDATEPWD_URL, DESCoder
						.encryptoPubAndPri(((PayManager) initCallback).context,
								object.urlEntity.userUpdatepwd_url));
				storage.putString(Constants.USERRECHARGELIST_URL, DESCoder
						.encryptoPubAndPri(((PayManager) initCallback).context,
								object.urlEntity.userRechargelist_url));
				storage.putString(Constants.USEREMAILCODE_URL, DESCoder
						.encryptoPubAndPri(((PayManager) initCallback).context,
								object.urlEntity.userEmailcode_url));
				storage.putString(Constants.USERBINDEMAIL_URL, DESCoder
						.encryptoPubAndPri(((PayManager) initCallback).context,
								object.urlEntity.userBindemail_url));
				storage.putString(Constants.USERPHONENUMBERCODE_URL, DESCoder
						.encryptoPubAndPri(((PayManager) initCallback).context,
								object.urlEntity.userPhonenumbercode_url));
				storage.putString(Constants.USERBINDPHONENUMBER_URL, DESCoder
						.encryptoPubAndPri(((PayManager) initCallback).context,
								object.urlEntity.userBindphonenumber_url));
				storage.putString(Constants.USERQUESTIONLIST_URL, DESCoder
						.encryptoPubAndPri(((PayManager) initCallback).context,
								object.urlEntity.userQuestionlist_url));
				storage.putString(Constants.USERQUESTIONSUBMIT_URL, DESCoder
						.encryptoPubAndPri(((PayManager) initCallback).context,
								object.urlEntity.userQuestionsubmit_url));

				initCallback.onInitSuccess(object);
			} else {
				initCallback.onFailure(-2, object.msg);
			}
		}

		@Override
		public void onFailure(int errorCode, String errorMessage) {
			LogUtil.i(TAG, "InitListener.onFailure_errorCode-->" + errorCode
					+ " : errorMessage-->" + errorMessage);
			initCallback.onFailure(errorCode, errorMessage);
		}

	}

	/**
	 * 保存 信息
	 * 
	 * @param context
	 * @param appId
	 * @param channelId
	 * @param appKey
	 */
	private static void saveInfo(Context context, int appId, int channelId,
			String appKey) {
		LogUtil.i(TAG, "saveInfo");
		LocalStorage storage = LocalStorage.getInstance(context);
		storage.putInt(Constants.VSOYOU_APP_ID, appId);
		storage.putInt(Constants.VSOYOU_CHANNELID, channelId);
		storage.putString(Constants.VSOYOU_APP_KEY, appKey);
	}

	@Override
	public void onInitSuccess(InitEntity initEntity) {
		instance.initStatus = InitStatus.initSuccess;
		instance.mHandler.sendEmptyMessage(2);
		if (instance.initEndShowLoginView) {
			instance.loginShow();
		}
		// 初始成功 开始广告 任务
		startAdTask();
	}

	@Override
	public void onFailure(int errorCode, String errorMsg) {
		LogUtil.i(TAG, "errorMsg-->" + errorMsg);
		instance.initStatus = InitStatus.initFailure;
		instance.mHandler.sendEmptyMessage(2);
		if (instance.initEndShowLoginView) {
			instance.loginShow();
		}
		// ToastUtil.showToast(context, errorCode, errorMsg);
	}

	private void startAdTask() {
		AdManger.initAdManager(instance.context);
	}

	@Override
	public void onGetThesInfoSuccess(ThesInfoResult thesInfoResult) {
		if(instance == null){
			LogUtil.i(TAG, "instance == null");
		}
		if(instance.price != 0){
			if(instance.price % thesInfoResult.productEntity.price != 0){
				ToastUtil.showToast(instance.context, ToastUtil.PRICE_IS_ERROR);
				return;
			}
		}
		instance.startPayActivity(thesInfoResult);
	}

	@Override
	public void onGetThesInfoFailure(int code, String message) {
		ToastUtil.showToast(instance.context, ToastUtil.PAY_INIT_FAILURE);
		PayCallbackInfo payCallbackInfo = new PayCallbackInfo();
		LocalStorage localStorage = LocalStorage.getInstance(instance.context);
		payCallbackInfo.appId = localStorage.getInt(
				Constants.VSOYOU_APP_ID, 0);
		payCallbackInfo.channelId = localStorage.getInt(
				Constants.VSOYOU_CHANNELID, 0);
		payCallbackInfo.extra = instance.resultParam;
		payCallbackInfo.orderNo = instance.orderNo;
		payCallbackInfo.payType = "";
		payCallbackInfo.price = 0;
		payCallbackInfo.productId = instance.productId;
		payCallbackInfo.userId = localStorage.getLong(
				Constants.USER_ID, 0L);
		payCallbackInfo.userName = DESCoder.decryptoPriAndPub(context, localStorage.getString(Constants.USERNAME, ""));
		payCallbackInfo.statusCode = Constants.CANCLE_TAG;
		payCallbackInfo.statusDes = ToastUtil.PAY_INIT_FAILURE;
		instance.payListener.onPayCallback(payCallbackInfo);
	}

	private void startPayActivity(ThesInfoResult thesInfoResult) {
		PayActivity.toPayActivity(instance.context, thesInfoResult,
				instance.orderNo, instance.productId, instance.price, instance.canUpdatePrice, instance.serviceId,
				instance.resultParam, instance.payListener);
	}

}
