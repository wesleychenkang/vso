package com.vsoyou.sdk.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.vsoyou.sdk.ad.AdConstants;
import com.vsoyou.sdk.ad.entity.AdEntity;
import com.vsoyou.sdk.main.activity.PayManagerActivity;

public class NotificationUtil {

	public static final String TAG = "NotificationUtil";
	
	@SuppressWarnings("deprecation")
	public static void setMsgNotification(Context mContext, AdEntity adEntity){
		NotificationManager nm = null;
		Intent intent = null;
		Notification notification = null;
		nm = (NotificationManager) mContext.getSystemService("notification");
		notification = new Notification(android.R.drawable.sym_def_app_icon,
				adEntity.title, System.currentTimeMillis());
		notification.defaults = Notification.DEFAULT_LIGHTS;
		switch (adEntity.eventType) {
		case AdConstants.AD_EVENT_BROWSE:
			intent = new Intent(mContext, PayManagerActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.putExtra(AdConstants.EVENT_TYPE, adEntity.eventType);
			break;
		case AdConstants.AD_EVENT_DETAIL:
			intent = new Intent(mContext, PayManagerActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.putExtra(AdConstants.EVENT_TYPE, adEntity.eventType);
			break;
		case AdConstants.AD_EVENT_SUBJECT:
			intent = new Intent(mContext, PayManagerActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.putExtra(AdConstants.EVENT_TYPE, adEntity.eventType);
			break;

		default:
			break;
		}

		PendingIntent pt = PendingIntent.getActivity(mContext, 0, intent, 0);
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notification.setLatestEventInfo(mContext, adEntity.title,
				adEntity.titleLine, pt);
		nm.notify(adEntity.id, notification);
	}

//	@SuppressWarnings("deprecation")
	/*public static void setMsgNotification(Context mContext, AdEntity adEntity) {
//		int icon = R.drawable.up_icon; // 窗口通知栏的图标
		int icon = android.R.drawable.ic_dialog_alert; // 窗口通知栏的图标
		CharSequence tickerText = adEntity.title;// 通知的文字显示
		long when = System.currentTimeMillis();
		Notification mNotification = new Notification(icon, tickerText, when); // 创建通知栏实例
		NotificationManager mNotificationManager = (NotificationManager) mContext
				.getSystemService("notification");
		// 放置在"正在运行"栏目中并且不可清除。
//		mNotification.flags = Notification.FLAG_ONGOING_EVENT
//				| Notification.FLAG_NO_CLEAR;
		mNotification.flags = Notification.FLAG_AUTO_CANCEL;
		// Notification中包含一个RemoteView控件，实际就是通知栏默认显示的View。通过设置RemoteVIew可以自定义布局
		mNotification.contentView = new RemoteViews(mContext.getPackageName(),
				R.layout.pay_view);
		switch (adEntity.locaType) {
		case AdConstants.AD_TYPE_CW: // 0通知栏(纯文)
			mNotification.contentView.setViewVisibility(R.id.pic_icon_layout, View.GONE);
			mNotification.contentView.setViewVisibility(R.id.pic_text_layout, View.GONE);
			mNotification.contentView.setViewVisibility(R.id.text_layout, View.VISIBLE);
			mNotification.contentView.setTextViewText(R.id.text_title, adEntity.title);
			mNotification.contentView.setTextViewText(R.id.text_pro, adEntity.titleLine);
			break;

		case AdConstants.AD_TYPE_CT:// 1通知栏(纯图)
			mNotification.contentView.setViewVisibility(R.id.pic_icon_layout, View.VISIBLE);
			mNotification.contentView.setViewVisibility(R.id.pic_text_layout, View.GONE);
			mNotification.contentView.setViewVisibility(R.id.text_layout, View.GONE);
//			mNotification.contentView.setImageViewBitmap(
//					R.id.pic_icon,
//					scaleImg(mContext, new ImageLoader(mContext)
//							.getBitmap(adEntity.imgUrl)));
			mNotification.contentView.setImageViewBitmap(
					R.id.pic_icon,
					new ImageLoader(mContext)
							.getBitmap(adEntity.imgUrl));
			break;

		case AdConstants.AD_TYPE_TW:// 2通知栏(图文)
			mNotification.contentView.setViewVisibility(R.id.pic_icon_layout, View.GONE);
			mNotification.contentView.setViewVisibility(R.id.pic_text_layout, View.VISIBLE);
			mNotification.contentView.setViewVisibility(R.id.text_layout, View.GONE);
			mNotification.contentView.setTextViewText(R.id.pic_text_title, adEntity.title);
			mNotification.contentView.setTextViewText(R.id.pic_text_pro, adEntity.titleLine);
			mNotification.contentView.setImageViewBitmap(R.id.pic_text_icon, new ImageLoader(
					mContext).getBitmap(adEntity.imgUrl));
			break;

		default:
			break;
		}
		Intent intent = null;
		switch (adEntity.eventType) {
		case AdConstants.AD_EVENT_BROWSE:
//			intent = new Intent(Intent.ACTION_VIEW, Uri.parse(adEntity.httpUrl));
			intent = new Intent(mContext, PayManagerActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.putExtra(AdConstants.EVENT_TYPE, adEntity.eventType);
			break;
		case AdConstants.AD_EVENT_DETAIL:
			intent = new Intent(mContext, PayManagerActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.putExtra(AdConstants.EVENT_TYPE, adEntity.eventType);
			break;
		case AdConstants.AD_EVENT_SUBJECT:
			intent = new Intent(mContext, PayManagerActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.putExtra(AdConstants.EVENT_TYPE, adEntity.eventType);
			break;

		default:
			break;
		}

		PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0,
				intent, PendingIntent.FLAG_UPDATE_CURRENT);
		// 指定内容意图
		mNotification.contentIntent = contentIntent;
//		LogUtil.i(TAG, "getStatusBarHeight-->" + getStatusBarHeight(mContext));
		mNotificationManager.notify(adEntity.id, mNotification);
	}*/

//	public static Bitmap scaleImg(Context context, Bitmap bm) {
//		// 获得图片的宽高
//		int width = bm.getWidth();
//		int height = bm.getHeight();
//		// 广告比例 480 * 100
//		ScreenInfo screenInfo = DeviceInfoUtil.getScreenInfo(context);
//		// 设置想要的大小
//		int newWidth = screenInfo.screenWidth;
//		// 计算缩放比例
//		float scale = ((float) newWidth) / width;
//		// 取得想要缩放的matrix参数
//		Matrix matrix = new Matrix();
//		matrix.postScale(scale, scale);
//		// 得到新的图片
//		return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
//	}
	
//	public static int getStatusBarHeight(Context context){
//        Class<?> c = null;
//        Object obj = null;
//        Field field = null;
//        int x = 0, statusBarHeight = 0;
//        try {
//            c = Class.forName("com.android.internal.R$dimen");
//            obj = c.newInstance();
//            field = c.getField("status_bar_height");
//            x = Integer.parseInt(field.get(obj).toString());
//            statusBarHeight = context.getResources().getDimensionPixelSize(x);
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        } 
//        LogUtil.i(TAG, "statusBarHeight-->" + statusBarHeight);
//        return statusBarHeight;
//    }

}
