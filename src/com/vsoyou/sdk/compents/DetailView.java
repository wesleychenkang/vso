package com.vsoyou.sdk.compents;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.format.Formatter;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.vsoyou.sdk.ad.AdConstants;
import com.vsoyou.sdk.ad.AdManger;
import com.vsoyou.sdk.ad.adapter.ScreenShotAdapter;
import com.vsoyou.sdk.ad.cache.ImageLoader;
import com.vsoyou.sdk.ad.entity.AdCallbackRequestParam;
import com.vsoyou.sdk.ad.entity.AdEntity;
import com.vsoyou.sdk.ad.entity.AppEntity;
import com.vsoyou.sdk.ad.entity.UrlImage;
import com.vsoyou.sdk.entity.ScreenInfo;
import com.vsoyou.sdk.http.HttpRequest;
import com.vsoyou.sdk.main.Constants;
import com.vsoyou.sdk.resources.ResourceLoader;
import com.vsoyou.sdk.util.BrowseUtil;
import com.vsoyou.sdk.util.DESCoder;
import com.vsoyou.sdk.util.Decorator;
import com.vsoyou.sdk.util.LocalStorage;
import com.vsoyou.sdk.util.LogUtil;
import com.vsoyou.sdk.util.StringUtil;

public class DetailView extends RelativeLayout implements OnClickListener,
		OnItemSelectedListener {

	public static final String TAG = "DetailView";

	private ImageView imageView;
	private TextView introContentTextView;
	private ImageView contentArrowImageView;
	private TextView appNameTextView;
	private TextView appCategoryTextView;
	private TextView appAutherTextView;
	private TextView verAndSizeTextView;
	private RelativeLayout contentArrowRelativeLayout;
	private TextView downloadTextView;
	private BackView backView;

	private Context context;
	private ScreenInfo deviceInfo;

	private MyGallery picGallery;
	private LinearLayout picSelectedLayout;
	private int currentScreenShotPage;

	private ScreenShotAdapter screenShotAdapter;
	private boolean haveScale = false;
	private boolean arrowIsUp = false;
	private ImageLoader imageLoader;
	private MainView mainView;

	private AdEntity adEntity;
	private ArrayList<UrlImage> urlImageList;
	private String introStr = "";

	public DetailView(Context context, ScreenInfo deviceInfo,
			AdEntity adEntity, MainView mainView) {
		super(context);
		this.context = context;
		this.deviceInfo = deviceInfo;
		this.adEntity = adEntity;
		this.mainView = mainView;
		imageLoader = AdManger.instance.imageLoader;
		initView();
		initScreenshotPage();
		initData();
		addListener();
	}

	private void addListener() {
//		if (adEntity.eventType == AdConstants.AD_EVENT_DETAIL) {
			contentArrowRelativeLayout.setOnClickListener(this);
			downloadTextView.setOnClickListener(this);
			picGallery.setOnItemSelectedListener(this);
			backView.backTextView.setOnClickListener(this);
//		}
	}

	private void initData() {
		LogUtil.i(TAG, "initData");
		if (adEntity.eventType == AdConstants.AD_EVENT_DETAIL) {
			int screenW = 0;
			if (deviceInfo.getScreenHeight() > deviceInfo.getScreenWidth()) {
				screenW = deviceInfo.getScreenWidth();
			} else {
				screenW = deviceInfo.getScreenHeight();
			}
			screenShotAdapter = new ScreenShotAdapter(context,
					urlImageList, screenW, imageLoader);
			picGallery.setAdapter(screenShotAdapter);
			picGallery.setSelection(1000);

			imageLoader.DisplayImage(imageView, false, adEntity.carriers.iconUrl);
			appNameTextView.setText(adEntity.carriers.titleName);
			appCategoryTextView.setText("类别：" + adEntity.carriers.cate);
			appAutherTextView.setText("作者：" + adEntity.carriers.author);
			verAndSizeTextView.setText(adEntity.carriers.verName
					+ "版   / "
					+ Formatter.formatFileSize(context,
							adEntity.carriers.fileSize));
			introStr = Html.fromHtml(adEntity.carriers.des).toString();
			if (introStr.length() > 100) {
				introContentTextView.setText("    "
						+ introStr.substring(0, 100) + "...");
				haveScale = true;
			} else {
				introContentTextView.setText("    " + introStr);
			}
		}
	}

	public void updateData(final AppEntity nowAppEntity) {
		LogUtil.i(TAG, "updateData");
		int screenW = 0;
		if (deviceInfo.getScreenHeight() > deviceInfo.getScreenWidth()) {
			screenW = deviceInfo.getScreenWidth();
		} else {
			screenW = deviceInfo.getScreenHeight();
		}
		if (urlImageList == null) {
			urlImageList = new ArrayList<UrlImage>();
		}
		if (urlImageList.size() > 0) {
			urlImageList.clear();
			urlImageList = null;
			urlImageList = new ArrayList<UrlImage>();
		}

		if (!StringUtil.isEmpty(nowAppEntity.imgUrl1)) {
			urlImageList.add(new UrlImage(nowAppEntity.imgUrl1));
		}
		if (!StringUtil.isEmpty(nowAppEntity.imgUrl2)) {
			urlImageList.add(new UrlImage(nowAppEntity.imgUrl2));
		}
		if (!StringUtil.isEmpty(nowAppEntity.imgUrl3)) {
			urlImageList.add(new UrlImage(nowAppEntity.imgUrl3));
		}
		if (!StringUtil.isEmpty(nowAppEntity.imgUrl4)) {
			urlImageList.add(new UrlImage(nowAppEntity.imgUrl4));
		}
		
		currentScreenShotPage = 0;
		picSelectedLayout.removeAllViews();
		int screenShotListSize = urlImageList.size();
		if (screenShotListSize > 0) {
			for (int i = 0; i < screenShotListSize; i++) {

				ImageView locationImage = new ImageView(context);
				if (i == currentScreenShotPage) {
					locationImage
							.setImageDrawable(ResourceLoader
									.getBitmapDrawable("data_10.pvd"));
				} else {
					locationImage
							.setImageDrawable(ResourceLoader
									.getBitmapDrawable("data_11.pvd"));
				}
				if (i != screenShotListSize - 1) {
					locationImage.setPadding(0, 0, 5, 0);
				}
				picSelectedLayout.addView(locationImage);
			}
		}
		
		screenShotAdapter = new ScreenShotAdapter(context,
				urlImageList, screenW, imageLoader);
		picGallery.setAdapter(screenShotAdapter);
		picGallery.setSelection(1000);

		imageLoader.DisplayImage(imageView, false, nowAppEntity.iconUrl);
		appNameTextView.setText(nowAppEntity.titleName);
		appCategoryTextView.setText("类别：" + nowAppEntity.cate);
		appAutherTextView.setText("作者：" + nowAppEntity.author);
		verAndSizeTextView
				.setText(nowAppEntity.verName
						+ "版   / "
						+ Formatter.formatFileSize(context,
								nowAppEntity.fileSize));
		introStr = Html.fromHtml(nowAppEntity.des).toString();
		if (introStr.length() > 100) {
			introContentTextView.setText("    " + introStr.substring(0, 100) + "...");
			haveScale = true;
		} else {
			introContentTextView.setText("    " + introStr);
		}
		downloadTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				BrowseUtil.openBrowser(context, nowAppEntity.fileUrl);
				HttpRequest<AdCallbackRequestParam> request = new HttpRequest<AdCallbackRequestParam>(context, null, null, null);
				LocalStorage storage = LocalStorage.getInstance(context);
				request.execute(DESCoder.decryptoPriAndPub(context,
						storage.getString(Constants.ADCALLBACK_URL, "")),
						new AdCallbackRequestParam(context, "click", adEntity.id,
								nowAppEntity.carrierId).toJSON());
			}
		});
		backView.backTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mainView.showAppListView();
			}
		});
	}

	private void initScreenshotPage() {
		LogUtil.i(TAG, "initScreenshotPage");
		urlImageList = new ArrayList<UrlImage>();
		if (adEntity.eventType == AdConstants.AD_EVENT_DETAIL) {
			if (!StringUtil.isEmpty(adEntity.carriers.imgUrl1)) {
				urlImageList.add(new UrlImage(adEntity.carriers.imgUrl1));
			}
			if (!StringUtil.isEmpty(adEntity.carriers.imgUrl2)) {
				urlImageList.add(new UrlImage(adEntity.carriers.imgUrl2));
			}
			if (!StringUtil.isEmpty(adEntity.carriers.imgUrl3)) {
				urlImageList.add(new UrlImage(adEntity.carriers.imgUrl3));
			}
			if (!StringUtil.isEmpty(adEntity.carriers.imgUrl4)) {
				urlImageList.add(new UrlImage(adEntity.carriers.imgUrl4));
			}
			currentScreenShotPage = 0;
			picSelectedLayout.removeAllViews();
			int screenShotListSize = urlImageList.size();
			if (screenShotListSize > 0) {
				for (int i = 0; i < screenShotListSize; i++) {

					ImageView locationImage = new ImageView(context);
					if (i == currentScreenShotPage) {
						locationImage
								.setImageDrawable(ResourceLoader
										.getBitmapDrawable("data_10.pvd"));
					} else {
						locationImage
								.setImageDrawable(ResourceLoader
										.getBitmapDrawable("data_11.pvd"));
					}
					if (i != screenShotListSize - 1) {
						locationImage.setPadding(0, 0, 5, 0);
					}
					picSelectedLayout.addView(locationImage);
				}
			}
		}
	}

	private void updateScreenshotPage() {
		int screenShotListSize = urlImageList.size();
		if (screenShotListSize > 0) {
			for (int i = 0; i < screenShotListSize; i++) {
				ImageView locationImage = (ImageView) picSelectedLayout
						.getChildAt(i);
				if (i == currentScreenShotPage) {
					locationImage.setImageDrawable(ResourceLoader
							.getBitmapDrawable("data_10.pvd"));
				} else {
					locationImage
							.setImageDrawable(ResourceLoader
									.getBitmapDrawable("data_11.pvd"));
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	private void initView() {
		this.setBackgroundColor(Color.parseColor("#246370"));

		backView = new BackView(context, "详情");
		backView.setId(88);
		RelativeLayout.LayoutParams backViewParams = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		backViewParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		this.addView(backView, backViewParams);

		ScrollView scrollView = new ScrollView(context);
		RelativeLayout.LayoutParams scrollViewParams = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		scrollViewParams.addRule(RelativeLayout.BELOW, backView.getId());
		this.addView(scrollView, scrollViewParams);

		LinearLayout allLinearlayout = new LinearLayout(context);
		allLinearlayout.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams allLinearlayoutParams = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		scrollView.addView(allLinearlayout, allLinearlayoutParams);

		// 游戏标题部分
		LinearLayout title1LinearLayout = new LinearLayout(context);
		title1LinearLayout.setBackgroundDrawable(ResourceLoader
				.getNinePatchDrawable("data_2.pvd"));
		title1LinearLayout.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams title1LinearLayoutParams = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		title1LinearLayoutParams.setMargins(10, 10, 10, 10);
		allLinearlayout.addView(title1LinearLayout, title1LinearLayoutParams);

		LinearLayout title2LinearLayout = new LinearLayout(context);
		title2LinearLayout.setOrientation(LinearLayout.HORIZONTAL);
		LinearLayout.LayoutParams title2LinearLayoutParams = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		title2LinearLayoutParams.setMargins(10, 10, 10, 0);
		title1LinearLayout
				.addView(title2LinearLayout, title2LinearLayoutParams);

		// app icon
		imageView = new ImageView(context);
		imageView.setImageDrawable(ResourceLoader.getBitmapDrawable("data_24.pvd"));
		title2LinearLayout.addView(imageView, new LinearLayout.LayoutParams(80,
				80));

		// linearLayout_3
		LinearLayout title3LinearLayout = new LinearLayout(context);
		title3LinearLayout.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams title3LinearLayoutParams = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		title3LinearLayout.setGravity(Gravity.CENTER_VERTICAL);
		title3LinearLayoutParams.setMargins(10, 0, 0, 0);
		title2LinearLayout
				.addView(title3LinearLayout, title3LinearLayoutParams);
		// app名称
		appNameTextView = new TextView(context);
		// appNameTextView.setText("第一滴血");
		appNameTextView.setTextSize(18f);
		appNameTextView.setTextColor(Color.parseColor("#333333"));
		title3LinearLayout.addView(appNameTextView,
				new LinearLayout.LayoutParams(
						ViewGroup.LayoutParams.WRAP_CONTENT,
						ViewGroup.LayoutParams.WRAP_CONTENT));
		// app category
		appCategoryTextView = new TextView(context);
		// appCategoryTextView.setText("类别：休闲益智");
		appCategoryTextView.setTextSize(11f);
		appCategoryTextView.setTextColor(Color.parseColor("#999999"));
		LinearLayout.LayoutParams appCategoryTextViewParams = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		appCategoryTextViewParams.setMargins(0, 5, 0, 0);
		title3LinearLayout.addView(appCategoryTextView,
				appCategoryTextViewParams);
		// app auther
		appAutherTextView = new TextView(context);
		// appAutherTextView.setText("作者：叱诧风云OMG");
		appAutherTextView.setTextSize(11f);
		appAutherTextView.setTextColor(Color.parseColor("#999999"));
		LinearLayout.LayoutParams appAutherTextViewParams = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		appAutherTextViewParams.setMargins(0, 5, 0, 0);
		title3LinearLayout.addView(appAutherTextView, appAutherTextViewParams);

		// 分割线
		TextView lineTextView = new TextView(context);
		lineTextView.setBackgroundDrawable(ResourceLoader
				.getBitmapDrawable("data_3.pvd"));
		LinearLayout.LayoutParams lineTextViewParams = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, 1);
		lineTextViewParams.setMargins(10, 10, 10, 10);
		title1LinearLayout.addView(lineTextView, lineTextViewParams);

		RelativeLayout relativeLayoutLayout_1 = new RelativeLayout(context);
		LinearLayout.LayoutParams relativeLayoutLayout_1Params = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, 20);
		relativeLayoutLayout_1Params.setMargins(10, 0, 10, 10);
		title1LinearLayout.addView(relativeLayoutLayout_1,
				relativeLayoutLayout_1Params);

		ImageView securityImage = new ImageView(context);
		RelativeLayout.LayoutParams securityImageParams = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		securityImageParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		securityImage.setImageDrawable(ResourceLoader
				.getBitmapDrawable("data_21.pvd"));
		relativeLayoutLayout_1.addView(securityImage, securityImageParams);

		// 版本 和 大小
		verAndSizeTextView = new TextView(context);
		RelativeLayout.LayoutParams verAndSizeTextViewParams = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		verAndSizeTextView.setGravity(Gravity.CENTER);
		verAndSizeTextViewParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		// verAndSizeTextView.setText("1.0.5版   / 43.32M");
		verAndSizeTextView.setTextColor(Color.parseColor("#b9b9b9"));
		verAndSizeTextView.setTextSize(10f);
		relativeLayoutLayout_1.addView(verAndSizeTextView,
				verAndSizeTextViewParams);

		// 简介和截图模块
		LinearLayout titleLinearLayout_4 = new LinearLayout(context);
		titleLinearLayout_4.setBackgroundDrawable(ResourceLoader
				.getNinePatchDrawable("data_2.pvd"));
		titleLinearLayout_4.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams titleLinearLayout_4Params = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		titleLinearLayout_4Params.setMargins(10, 10, 10, 80);
		allLinearlayout.addView(titleLinearLayout_4, titleLinearLayout_4Params);

		TextView introTitleTextView = new TextView(context);
		introTitleTextView.setText("简介:");
		introTitleTextView.setTextColor(Color.parseColor("#333333"));
		introTitleTextView.setTextSize(12f);
		LinearLayout.LayoutParams introTitleTextViewParams = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		introTitleTextViewParams.setMargins(10, 10, 10, 10);
		titleLinearLayout_4.addView(introTitleTextView,
				introTitleTextViewParams);

		// 简介内容
		introContentTextView = new TextView(context);
		introContentTextView.setTextColor(Color.parseColor("#8b8c8c"));
		introContentTextView.setTextSize(12f);
		LinearLayout.LayoutParams introContentTextViewParams = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		introContentTextViewParams.setMargins(10, 0, 10, 10);
		introContentTextView.setLineSpacing(3f, 1.2f);
		// introTitleTextView.setMaxLines(10);
		titleLinearLayout_4.addView(introContentTextView,
				introContentTextViewParams);
		// introContentTextView.setLines(4);

		contentArrowRelativeLayout = new RelativeLayout(context);
		LinearLayout.LayoutParams contentArrowRelativeLayoutParams = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		contentArrowRelativeLayoutParams.setMargins(10, 0, 10, 10);
		titleLinearLayout_4.addView(contentArrowRelativeLayout,
				contentArrowRelativeLayoutParams);
		contentArrowRelativeLayout.setVisibility(View.VISIBLE);
		// 内容箭头View
		contentArrowImageView = new ImageView(context);
		contentArrowImageView.setImageDrawable(ResourceLoader
				.getBitmapDrawable("data_16.pvd"));
		RelativeLayout.LayoutParams contentArrowImageViewParams = new RelativeLayout.LayoutParams(
				25, 25);
		contentArrowImageViewParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		contentArrowRelativeLayout.addView(contentArrowImageView,
				contentArrowImageViewParams);

		// 截图
		TextView picTitleTextView = new TextView(context);
		picTitleTextView.setText("截图:");
		picTitleTextView.setTextColor(Color.parseColor("#333333"));
		picTitleTextView.setTextSize(12f);
		LinearLayout.LayoutParams picTitleTextViewParams = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		picTitleTextViewParams.setMargins(10, 10, 10, 10);
		titleLinearLayout_4.addView(picTitleTextView, picTitleTextViewParams);

		RelativeLayout picRelativeLayout = new RelativeLayout(context);
		LinearLayout.LayoutParams picRelativeLayoutParams = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		picRelativeLayoutParams.setMargins(0, 0, 0, 5);
		picRelativeLayout.setGravity(Gravity.CENTER);
		titleLinearLayout_4.addView(picRelativeLayout, picRelativeLayoutParams);

		LinearLayout picLinearLayout = new LinearLayout(context);
		picLinearLayout.setGravity(Gravity.CENTER);
		picLinearLayout.setId(8);
		picRelativeLayout.addView(picLinearLayout,
				new LinearLayout.LayoutParams(
						ViewGroup.LayoutParams.MATCH_PARENT,
						ViewGroup.LayoutParams.WRAP_CONTENT));

		// 截图Gallery
		picGallery = new MyGallery(context);
		LinearLayout.LayoutParams picGalleryParams = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		picGalleryParams.setMargins(0, 2, 0, 0);
		picGallery.setSpacing(50);
		picGallery
				.setPersistentDrawingCache(ViewGroup.PERSISTENT_ANIMATION_CACHE);
		picLinearLayout.addView(picGallery, picGalleryParams);

		picSelectedLayout = new LinearLayout(context);
		RelativeLayout.LayoutParams picLinearLayout_2Params = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		picLinearLayout_2Params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		picLinearLayout_2Params.addRule(RelativeLayout.BELOW,
				picLinearLayout.getId());
		picLinearLayout_2Params.setMargins(0, 10, 0, 10);
		picSelectedLayout.setGravity(Gravity.CENTER);
		picRelativeLayout.addView(picSelectedLayout, picLinearLayout_2Params);
		LogUtil.i(TAG, "4");

		// 下載模块
		LinearLayout titleLinearLayout_5 = new LinearLayout(context);
		titleLinearLayout_5.setBackgroundDrawable(ResourceLoader
				.getNinePatchDrawable("data_2.pvd"));
		titleLinearLayout_5.setOrientation(LinearLayout.VERTICAL);
		RelativeLayout.LayoutParams titleLinearLayout_5Params = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				75);
		titleLinearLayout_5Params.setMargins(0, 10, 0, 0);
		titleLinearLayout_5.setGravity(Gravity.CENTER);
		titleLinearLayout_5Params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		this.addView(titleLinearLayout_5, titleLinearLayout_5Params);
		titleLinearLayout_5.getBackground().setAlpha(30);

		downloadTextView = new TextView(context);
		downloadTextView.setText("下载");
		downloadTextView.setTextColor(Color.parseColor("#ffffff"));
		downloadTextView.setGravity(Gravity.CENTER);
		downloadTextView.setTextSize(14f);
		Decorator
				.setStateImage(
						downloadTextView,
						ResourceLoader
								.getNinePatchDrawable("data_18.pvd"),
						ResourceLoader
								.getNinePatchDrawable("data_19.pvd"),
						ResourceLoader
								.getNinePatchDrawable("data_18.pvd"));
		LinearLayout.LayoutParams downloadTextViewParams = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, 60);
		downloadTextViewParams.setMargins(20, 0, 20, 0);
		titleLinearLayout_5.addView(downloadTextView, downloadTextViewParams);

	}

	@Override
	public void onClick(View v) {
		if (v == contentArrowRelativeLayout) {
			LogUtil.i(TAG, "contentArrowRelativeLayout.setOnClickListener");
			synchronized (TAG) {
				if (!arrowIsUp) {
					contentArrowImageView.setImageDrawable(ResourceLoader
							.getBitmapDrawable("data_17.pvd"));
					arrowIsUp = true;
				} else {
					contentArrowImageView.setImageDrawable(ResourceLoader
							.getBitmapDrawable("data_16.pvd"));
					arrowIsUp = false;
				}

				if (introStr.length() > 100) {
					if (haveScale) {
						introContentTextView.setText("    " + introStr);
						haveScale = false;
					} else {
						introContentTextView.setText("    "
								+ introStr.substring(0, 100) + "...");
						haveScale = true;
					}
				}
			}
		}

		if (v == downloadTextView) {
			LogUtil.i(TAG, "downloadTextView.setOnClickListener");
			if (!StringUtil.isEmpty(adEntity.carriers.fileUrl)) {
				BrowseUtil.openBrowser(context, adEntity.carriers.fileUrl);
				HttpRequest<AdCallbackRequestParam> request = new HttpRequest<AdCallbackRequestParam>(context, null, null, null);
				LocalStorage storage = LocalStorage.getInstance(context);
				request.execute(DESCoder.decryptoPriAndPub(context,
						storage.getString(Constants.ADCALLBACK_URL, "")),
						new AdCallbackRequestParam(context, "click", adEntity.id,
								adEntity.carrierId).toJSON());
			}
		}
		if (v == backView.backTextView) {
			LogUtil.i(TAG, "backView.backTextView.setOnClickListener");
			if (adEntity.locaType == AdConstants.AD_TYPE_TCZT) {
				if (adEntity.eventType == AdConstants.AD_EVENT_DETAIL) {
					mainView.showWindowAdView();
				} else if (adEntity.eventType == AdConstants.AD_EVENT_SUBJECT) {
					mainView.showAppListView();
				}
			} else {
				((Activity) context).finish();
			}
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		currentScreenShotPage = position % urlImageList.size();
		updateScreenshotPage();
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

}
