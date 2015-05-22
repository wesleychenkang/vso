package com.vsoyou.sdk.ad;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.vsoyou.sdk.ad.cache.ImageLoader;
import com.vsoyou.sdk.ad.callback.GetAdCallBack;
import com.vsoyou.sdk.ad.callback.GetImageCallBack;
import com.vsoyou.sdk.ad.entity.AdCallBackResult;
import com.vsoyou.sdk.ad.entity.AdCallbackRequestParam;
import com.vsoyou.sdk.ad.entity.AdEntity;
import com.vsoyou.sdk.ad.entity.parser.AdEntityParser;
import com.vsoyou.sdk.entity.DeviceInfo;
import com.vsoyou.sdk.http.HttpCallback;
import com.vsoyou.sdk.http.HttpRequest;
import com.vsoyou.sdk.http.ImageDownloadTask;
import com.vsoyou.sdk.main.Constants;
import com.vsoyou.sdk.main.activity.PayManagerActivity;
import com.vsoyou.sdk.util.ConfigUtil;
import com.vsoyou.sdk.util.DESCoder;
import com.vsoyou.sdk.util.LocalStorage;
import com.vsoyou.sdk.util.LogUtil;
import com.vsoyou.sdk.util.NetUtil;
import com.vsoyou.sdk.util.NotificationUtil;
import com.vsoyou.sdk.util.PackUtil;
import com.vsoyou.sdk.util.StringUtil;

public class AdManger implements GetAdCallBack, GetImageCallBack {

	private static final String TAG = "AdManger";

	private Context context;
	private Timer timer;
	public static boolean isShow = false;

	private final static Lock lock = new ReentrantLock();

	public static AdManger instance;
	public ImageLoader imageLoader;
	private boolean haveSchedule = false;
	
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				instance.getShowAd();
				break;

			case 2:
				if (!AdManger.isShow) {
					instance.context.sendBroadcast(new Intent("com.ad.push"));
				}
				break;

			default:
				break;
			}
		};
	};

	TimerTask task = new TimerTask() {
		public void run() {
			Message message = new Message();
			message.what = 1;
			instance.handler.sendMessage(message);
		}
	};

	public AdManger(Context context) {
		this.context = context;
		imageLoader = new ImageLoader(context);
	}

	private static AdManger getInstance(Context context) {
		try {
			lock.lock();
			if (instance == null) {
				instance = new AdManger(context.getApplicationContext());
			}
			return instance;
		} finally {
			lock.unlock();
		}
	}

	public static void initAdManager(Context context) {
		getInstance(context);
		instance.initalize();
	}

	private void initalize() {
		init();
	}

	private void init() {
		LogUtil.i(TAG, "init");
		instance.registerReceiver(context);
		instance.showWindow();
	}

	public void registerReceiver(Context context) {
		context.registerReceiver(new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				Log.i("registerReceiver", "onReceive");
				popAd(context);
			}

		}, new IntentFilter("com.ad.push"));
	}

	/**
	 * 弹出广告
	 * 
	 * @param context
	 */
	protected void popAd(Context context) {
		LogUtil.i(TAG, "popAd");
		// 设置广告弹出时间
		ConfigUtil.setAdShowTime(context);
		// 设置广告弹出次数
		int showCount = ConfigUtil.getAdShowCount(context) + 1;
		ConfigUtil.setAdShowCount(context, showCount);

		LocalStorage storage = LocalStorage.getInstance(context);
		String adJson = storage.getString(AdConstants.AD_CONTENT, "");
		AdEntityParser adEntityParser = new AdEntityParser();
		AdEntity adEntity = adEntityParser.getResponse(context, adJson);
		if (adEntity != null) {
			switch (adEntity.locaType) {
			case AdConstants.AD_TYPE_CW: // 0通知栏(纯文)
				NotificationUtil.setMsgNotification(context, adEntity);
				break;

			case AdConstants.AD_TYPE_CT:// 1通知栏(纯图)
//				NotificationUtil.setMsgNotification(context, adEntity);
				break;

			case AdConstants.AD_TYPE_TW:// 2通知栏(图文)
//				NotificationUtil.setMsgNotification(context, adEntity);
				break;

			case AdConstants.AD_TYPE_TCDINGT:// 3弹窗顶(图片)
				break;

			case AdConstants.AD_TYPE_TCZT:// 4弹窗中(图片)
				Intent intent = new Intent(instance.context,
						PayManagerActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra(AdConstants.EVENT_TYPE, adEntity.eventType);
				intent.putExtra(AdConstants.AD_TYPE, adEntity.locaType);
				context.startActivity(intent);
				break;

			case AdConstants.AD_TYPE_TCDIT:// 5弹窗底(图片)
				break;

			default:
				break;
			}
			// 显示回调统计
			HttpRequest<AdCallBackResult> request = new HttpRequest<AdCallBackResult>(
					context, null, null, null);
			request.execute(DESCoder.decryptoPriAndPub(context,
					storage.getString(Constants.ADCALLBACK_URL, "")),
					new AdCallbackRequestParam(context, "show", adEntity.id,
							adEntity.carrierId).toJSON());
		}
	}

	public void showWindow() {
		instance.timer = new Timer(true);
		if(!instance.haveSchedule){
			instance.timer.schedule(instance.task, 1 * 60 * 1000, 5 * 60 * 1000);
			instance.haveSchedule = true;
		}
	}

	protected void getShowAd() {
		LogUtil.i(TAG, "getShowAd");
		if (!NetUtil.isConnected(instance.context)) {
			LogUtil.i(TAG, "net is not connect");
			return;
		}
		LocalStorage storage = LocalStorage.getInstance(instance.context);
		String adUrl = DESCoder.decryptoPriAndPub(
				instance.context,storage.getString(Constants.ADOPER_URL, ""));
		LogUtil.i(TAG, "adUrl-->" + adUrl);
		String adJson = storage.getString(AdConstants.AD_CONTENT, "");
		if (!StringUtil.isEmpty(adUrl)) {
			if (StringUtil.isEmpty(adJson)) { // 没有广告
				LogUtil.i(TAG, "adJson is null");
				excuteAdRequest(adUrl);
			} else {
				AdEntity adEntity = new AdEntityParser().getResponse(
						instance.context, adJson);
				if (adEntity == null) { // 广告解析为空
					LogUtil.i(TAG, "adJson is not null, but adEntity is null");
					excuteAdRequest(adUrl);
				} else {// 有广告
						// 判断广告展示时间、展示次数是否满足
						// 满足则弹出广告，展示次数达到最大 则删除广告
					LogUtil.i(TAG, "adEntity --> " + adEntity);
					int adShowCount = ConfigUtil
							.getAdShowCount(instance.context);
					LogUtil.i(TAG, "adShowCount-->" + adShowCount);
					if (adShowCount >= adEntity.showNum) { // 删除广告json
						storage.putString(AdConstants.AD_CONTENT, "");
						return;
					}
					if (adShowTimeMeet()
							&& (ConfigUtil.getAdShowCount(instance.context) < adEntity.showNum)) {
						// 通知弹出广告
						Message message = new Message();
						message.what = 2;
						instance.handler.sendMessage(message);
					}
				}
			}
		} else {
			LogUtil.i(TAG, "payment is no init. not find adUrl");
		}
	}

	/**
	 * 是否满足广告请求时间
	 * 
	 * @return
	 */
	private boolean adRequestTimeMeet() {
		long currentTime = System.currentTimeMillis();
		long timeLong = currentTime
				- ConfigUtil.getAdRequestTime(instance.context);
		return (timeLong > ConfigUtil.getAdCyc(instance.context) * 60 * 60 * 1000)
				|| (timeLong < 0);
	}

	/**
	 * 是否满足广告请求时间
	 * 
	 * @return
	 */
	private boolean adShowTimeMeet() {
		long currentTime = System.currentTimeMillis();
		long timeLong = currentTime
				- ConfigUtil.getAdShowTime(instance.context);
		return (timeLong > ConfigUtil.getAdShowCyc(instance.context) * 60 * 60 * 1000)
				|| (timeLong < 0);
	}

	private void excuteAdRequest(String adUrl) {
		LogUtil.i(TAG, "excuteAdRequest-->" + adUrl);
		if (adRequestTimeMeet()) {
			HttpRequest<AdEntity> request = new HttpRequest<AdEntity>(
					instance.context, null, new AdEntityParser(),
					new GetAdListener(instance));
			request.execute(adUrl, new DeviceInfo(context).toJSON());
			ConfigUtil.setAdRequestTime(context);
		}
	}

	// 获取广告监听
	private class GetAdListener implements HttpCallback<AdEntity> {

		private GetAdCallBack getAdCallBack;

		public GetAdListener(GetAdCallBack getAdCallBack) {
			this.getAdCallBack = getAdCallBack;
		}

		@Override
		public void onSuccess(AdEntity object) {
			LogUtil.i(TAG, "GetAdListener.onSuccess-->" + object);
			getAdCallBack.OnGetAdSuccess(object);
		}

		@Override
		public void onFailure(int errorCode, String errorMessage) {
			LogUtil.i(TAG, "GetAdListener.onFailure-->" + errorCode + ":"
					+ errorMessage);
			getAdCallBack.onFailure(errorCode, errorMessage);
		}

	}

	// 获取广告图片监听
	private class GetImageListener implements HttpCallback<Bitmap> {

		private GetImageCallBack getImageCallBack;

		public GetImageListener(GetImageCallBack getImageCallBack) {
			this.getImageCallBack = getImageCallBack;
		}

		@Override
		public void onSuccess(Bitmap object) {
			getImageCallBack.OnGetImageSuccess(object);
		}

		@Override
		public void onFailure(int errorCode, String errorMessage) {
			// TODO Auto-generated method stub

		}

	}

	@Override
	public void OnGetAdSuccess(AdEntity adEntity) {
		// instance.handler.sendEmptyMessage(2);
		if (adEntity != null) {
			//获取新的广告，重置广告显示次数
			ConfigUtil.setAdShowCount(context, 0);
			if (adEntity.carriers != null
					&& !StringUtil.isEmpty(adEntity.carriers.packName)) {
				if (PackUtil.checkPackage(instance.context,
						adEntity.carriers.packName)) {
					LogUtil.i(TAG, "this ad's packName is installed, delete this adJson");
					// 此广告app对应的包已经安装，删除广告
					LocalStorage storage = LocalStorage.getInstance(context);
					storage.putString(AdConstants.AD_CONTENT, "");
					return;
				}
			}
			if (adEntity.locaType != AdConstants.AD_TYPE_CW) {
				LogUtil.i(TAG, "start ad img download.");
				ImageDownloadTask request = new ImageDownloadTask(
						instance.context, new GetImageListener(instance));
				request.execute(adEntity.imgUrl);
			} else {
				instance.handler.sendEmptyMessage(2);
			}
		}
	}

	@Override
	public void onFailure(int errorCode, String errorMsg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnGetImageSuccess(Bitmap bitmap) {
		if (bitmap != null) {
			instance.handler.sendEmptyMessage(2);
		}

	}

}
