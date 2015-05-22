package com.vsoyou.sdk.main.activity.view;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.android.app.sdk.AliPay;
import com.tenpay.android.service.TenpayServiceHelper;
import com.unionpay.UPPayAssistEx;
import com.unionpay.uppay.PayActivity;
import com.vsoyou.sdk.entity.parser.ResultParser;
import com.vsoyou.sdk.http.HttpCallback;
import com.vsoyou.sdk.http.HttpRequest;
import com.vsoyou.sdk.main.Constants;
import com.vsoyou.sdk.main.PayCallbackInfo;
import com.vsoyou.sdk.main.adapter.MoneyGridAdapter;
import com.vsoyou.sdk.main.entity.PayTypeEntity;
import com.vsoyou.sdk.main.entity.requestparam.AddOrderRquestParam;
import com.vsoyou.sdk.main.entity.requestparam.UserCancleOrder;
import com.vsoyou.sdk.main.enums.PayType;
import com.vsoyou.sdk.main.third.alipay.Result;
import com.vsoyou.sdk.main.third.alipay.Rsa;
import com.vsoyou.sdk.resources.ResourceLoader;
import com.vsoyou.sdk.util.DESCoder;
import com.vsoyou.sdk.util.Decorator;
import com.vsoyou.sdk.util.LogUtil;
import com.vsoyou.sdk.util.MetricUtil;
import com.vsoyou.sdk.util.StringUtil;
import com.vsoyou.sdk.util.ToastUtil;

public class OtherPayView extends LinearLayout implements View.OnClickListener {

	private static final String TAG = "OtherPayView";

	private static final int RQF_PAY = 1;
	private static final int RQF_LOGIN = 2;

	private Context context;
	private ImageView backImageView;
	private TextView payTypeTextView;
	private EditText moneyEditText;
	private ImageView moreMoneyImageView;
	private TextView sureTextView;
	private TextView cancleTextView;
	private LayoutInflater inflater;
	private PopupWindow pop;
	private TextView promptTextView;

	private PayMainView mainView;

	private PayType payType;
	private PayTypeEntity payTypeEntity;
	private int price; // 单价
	private int totalPrice = 0;
	
	public int getTotalPrice() {
		return totalPrice;
	}
	
	public PayType getPayType() {
		return payType;
	}

	@SuppressLint("NewApi")
	public OtherPayView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public OtherPayView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public OtherPayView(Context context, PayMainView mainView) {
		super(context);
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.mainView = mainView;
		initView();
		initPop();
		addListener();
	}

	private void addListener() {
		backImageView.setOnClickListener(this);
		sureTextView.setOnClickListener(this);
		cancleTextView.setOnClickListener(this);
		if(mainView.isCanUpdatePrice()){
			moreMoneyImageView.setOnClickListener(this);
		}
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
		linearLayout_1.setBackgroundDrawable(ResourceLoader
				.getNinePatchDrawable("edittext_bg.9.png"));
		linearLayout_1.setGravity(Gravity.CENTER_VERTICAL);
		linearLayout_1.setOrientation(LinearLayout.HORIZONTAL);
		allLinearLayout.addView(
				linearLayout_1,
				new LinearLayout.LayoutParams(MetricUtil
						.getDip(context, 300.0F), MetricUtil.getDip(context,
						45.0F)));

		ImageView imageView = new ImageView(context);
		imageView.setImageDrawable(ResourceLoader
				.getBitmapDrawable("select.png"));
		LinearLayout.LayoutParams imageViewParams = new LinearLayout.LayoutParams(MetricUtil.getDip(context, 23.0F), MetricUtil.getDip(context,
				23.0F));
		imageViewParams.setMargins(MetricUtil.getDip(context, 10.0F), 0, 0, 0);
		imageViewParams.gravity = Gravity.CENTER_VERTICAL;
		linearLayout_1.addView(imageView, imageViewParams);

		payTypeTextView = new TextView(context);
		payTypeTextView.setGravity(Gravity.CENTER_VERTICAL);
		payTypeTextView.setTextColor(Color.parseColor("#009cff"));
		payTypeTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20.0F);
		LinearLayout.LayoutParams payTypeTextViewParams = new LinearLayout.LayoutParams(
				MetricUtil.getDip(context, 350.0F),
				ViewGroup.LayoutParams.WRAP_CONTENT);
		payTypeTextViewParams.setMargins(MetricUtil.getDip(context, 10.0F), 0,
				0, 0);
		linearLayout_1.addView(payTypeTextView, payTypeTextViewParams);

		Drawable editTextBg = ResourceLoader
				.getNinePatchDrawable("edittext_normal_bg.9.png");

		LinearLayout linearLayout_2 = new LinearLayout(context);
		linearLayout_2.setGravity(Gravity.CENTER_VERTICAL);
		linearLayout_2.setOrientation(LinearLayout.HORIZONTAL);
		LinearLayout.LayoutParams linearLayout_2Params = new LinearLayout.LayoutParams(
				MetricUtil.getDip(context, 300.0F), MetricUtil.getDip(context,
						45.0F));
		linearLayout_2Params.setMargins(0, MetricUtil.getDip(context, 10.0F),
				0, 0);
		allLinearLayout.addView(linearLayout_2, linearLayout_2Params);

		TextView moneyTextView = new TextView(context);
		moneyTextView.setGravity(Gravity.CENTER_VERTICAL);
		moneyTextView.setText("金额：");
		moneyTextView.setTextColor(Color.parseColor("#666666"));
		moneyTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20.0F);
		LinearLayout.LayoutParams moneyTextViewParams = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		linearLayout_2.addView(moneyTextView, moneyTextViewParams);

		moneyEditText = new EditText(context);
		moneyEditText.setBackgroundDrawable(editTextBg);
		if(mainView.getInPrice() != 0){
			moneyEditText.setText("" + mainView.getInPrice());
		}else{
			moneyEditText.setText("50");
		}
		moneyEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
		moneyEditText.setSingleLine();
		moneyEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
		moneyEditText.setFilters(new  InputFilter[]{ new  InputFilter.LengthFilter(5)});
		LinearLayout.LayoutParams moneyEditTextParams = new LinearLayout.LayoutParams(
				MetricUtil.getDip(context, 200.0F), MetricUtil.getDip(context,
						40.0F));
		int moneyEditTextPadding = MetricUtil.getDip(context, 5.0F);
		moneyEditText.setPadding(moneyEditTextPadding, moneyEditTextPadding,
				moneyEditTextPadding, moneyEditTextPadding);
		moneyEditTextParams.setMargins(MetricUtil.getDip(context, 1.0F),
				MetricUtil.getDip(context, 1.0F),
				MetricUtil.getDip(context, 1.0F),
				MetricUtil.getDip(context, 1.0F));

		linearLayout_2.addView(moneyEditText, moneyEditTextParams);

		moreMoneyImageView = new ImageView(context);
		LinearLayout.LayoutParams moreMoneyImageViewParams = new LinearLayout.LayoutParams(
				MetricUtil.getDip(context, 30.0F), MetricUtil.getDip(context,
						30.0F));
		moreMoneyImageViewParams.setMargins(MetricUtil.getDip(context, 5.0F),
				0, 0, 0);
		moreMoneyImageView.setImageDrawable(ResourceLoader
				.getBitmapDrawable("more_money.png"));
		linearLayout_2.addView(moreMoneyImageView, moreMoneyImageViewParams);

		LinearLayout linearLayout_3 = new LinearLayout(context);
		linearLayout_3.setGravity(Gravity.CENTER_VERTICAL);
		linearLayout_3.setOrientation(LinearLayout.HORIZONTAL);
		LinearLayout.LayoutParams linearLayout_3Params = new LinearLayout.LayoutParams(
				MetricUtil.getDip(context, 280.0F),
				ViewGroup.LayoutParams.WRAP_CONTENT);
		linearLayout_3Params.setMargins(MetricUtil.getDip(context, 5.0F),
				MetricUtil.getDip(context, 10.0F),
				MetricUtil.getDip(context, 5.0F), 0);
		allLinearLayout.addView(linearLayout_3, linearLayout_3Params);

		sureTextView = new TextView(context);
		sureTextView.setText("确定");
		sureTextView.setGravity(Gravity.CENTER);
		sureTextView.setTextColor(Color.WHITE);
		sureTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16.0F);
		Decorator
				.setStateImage(
						sureTextView,
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
		linearLayout_3.addView(sureTextView, sureTextViewParams);

		cancleTextView = new TextView(context);
		cancleTextView.setText("取消");
		cancleTextView.setTextColor(Color.WHITE);
		cancleTextView.setGravity(Gravity.CENTER);
		cancleTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16.0F);
		Decorator.setStateImage(cancleTextView,
				ResourceLoader.getNinePatchDrawable("button_yellow_normal.9.png"),
				ResourceLoader.getNinePatchDrawable("button_yellow_pressed.9.png"),
				ResourceLoader.getNinePatchDrawable("button_yellow_normal.9.png"));
		LinearLayout.LayoutParams cancleTextViewParams = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT, MetricUtil.getDip(context,
						40.0F));
		cancleTextViewParams.weight = 1;
		cancleTextViewParams.setMargins(MetricUtil.getDip(context, 20.0F), 0,
				0, 0);
		linearLayout_3.addView(cancleTextView, cancleTextViewParams);

		LinearLayout linearLayout_4 = new LinearLayout(context);
		linearLayout_4.setGravity(Gravity.CENTER);
		linearLayout_4.setOrientation(LinearLayout.HORIZONTAL);
		LinearLayout.LayoutParams linearLayout_4Params = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		int plinearLayout_4Margin = MetricUtil.getDip(context, 10.0F);
		linearLayout_4Params.setMargins(plinearLayout_4Margin,
				plinearLayout_4Margin, plinearLayout_4Margin,
				plinearLayout_4Margin);
		allLinearLayout.addView(linearLayout_4, linearLayout_4Params);

		promptTextView = new TextView(context);
		promptTextView.setBackgroundDrawable(ResourceLoader
				.getNinePatchDrawable("edittext_bg.9.png"));
		promptTextView.setText("温馨提示\n一般1-10分钟即可到帐，请放心充值，若充值失败，请选择其他支付方式。");
		promptTextView.setTextColor(Color.parseColor("#393939"));
		promptTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14.0F);
		LinearLayout.LayoutParams promptTextViewParams = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		int promptTextViewMargin = MetricUtil.getDip(context, 10.0F);
		promptTextView.setPadding(promptTextViewMargin, promptTextViewMargin,
				promptTextViewMargin, promptTextViewMargin);
		linearLayout_4.addView(promptTextView, promptTextViewParams);

	}

	private void initPop() {
		
		price = mainView.getProductEntity().price / 100;
		
		GridView view = new MoneyGridView(context);
		final ArrayList<String> monyList = new ArrayList<String>();
		monyList.add("10元");
		monyList.add("20元");
		monyList.add("50元");
		monyList.add("100元");
		monyList.add("200元");
		monyList.add("500元");
		monyList.add("1000元");
		monyList.add("2000元");
		view.setAdapter(new MoneyGridAdapter(context, monyList));
		pop = new PopupWindow(view, MetricUtil.getDip(context, 202.0F),
				LayoutParams.WRAP_CONTENT, false);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setOutsideTouchable(false);
		pop.setFocusable(true);
		view.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				pop.dismiss();
				String nowPriceStr = monyList.get(position).substring(0,
						monyList.get(position).length() - 1);
				if(Integer.parseInt(nowPriceStr) >= price){
					moneyEditText.setText(nowPriceStr);
				}
			}
		});
	}

	public void updateData(PayType payType, PayTypeEntity payTypeEntity) {
		this.payType = payType;
		Spanned spanned = null;
		String promptStr = "温馨提示\n一般1-10分钟即可到帐，请放心充值，若充值失败，请选择其他支付方式。";
		if (payType == PayType.ALIPAY) {
			spanned = Html.fromHtml("您已选择<font color=red>\"支付宝\"</font>支付");
		} else if (payType == PayType.TENPAY) {
			spanned = Html.fromHtml("您已选择<font color=red>\"财付通\"</font>支付");
		} else if (payType == PayType.UNIONPAY) {
			spanned = Html.fromHtml("您已选择<font color=red>\"银行卡\"</font>支付");
			promptStr = "温馨提示\n支持信用卡支付\n一般1-10分钟即可到帐，请放心充值，若充值失败，请选择其他支付方式。";
		}
		payTypeTextView.setText(spanned);
		promptTextView.setText(promptStr);
		
		this.payTypeEntity = payTypeEntity;
		price = mainView.getProductEntity().price / 100;
		if(mainView.isCanUpdatePrice()){
			moneyEditText.setFocusable(true);
		}else{
			moneyEditText.setFocusable(false);
		}
		moneyEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				String priceStr = s.toString();
				LogUtil.i(TAG, "priceStr:" + priceStr);
				if(StringUtil.isEmpty(priceStr)){
					return;
				}
				boolean needUpdate = false;
				boolean tag = true;
				while(tag){
					if(priceStr.substring(0, 1).equals("0")){
						if(priceStr.length() > 1){
							priceStr = priceStr.substring(1, priceStr.length());
							LogUtil.i(TAG, "priceStr1:" + priceStr);
							needUpdate = true;
						}else{
							tag = false;
						}
					}else{
						tag = false;
					}
				}
				LogUtil.i(TAG, "priceStr2:" + priceStr);
				int nowPrice = Integer.parseInt(priceStr);
				int shengyu = nowPrice % price;
				if (shengyu == 0) {
					if(needUpdate){
						moneyEditText.setText("" + nowPrice);
					}
					if(nowPrice == 0){
						moneyEditText.setText("" + price);
					}
				} else {
					nowPrice = nowPrice + price - shengyu;
					moneyEditText.setText("" + nowPrice);
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		if (v == backImageView) {
			mainView.showMainView();
		}
		if (v == sureTextView) {
			startPay();
		}
		if (v == cancleTextView) {
			mainView.showMainView();
		}
		if (v == moreMoneyImageView) {
			Log.i("imageView", "onClick");
			if (pop.isShowing()) {
				Log.i("imageView", "pop is Showing");
				pop.dismiss();
			} else {
				Log.i("imageView", "pop not Showing");
				// pop.showAsDropDown(v);
				pop.showAtLocation(this, Gravity.CENTER, 0, 0);
			}
		}
	}

	private void startPay() {
		// 提交订单
		submitOrder();
	}

	private void submitOrder() {
		mainView.getmHandler().sendEmptyMessage(1);
		HttpRequest<com.vsoyou.sdk.entity.Result> request = new HttpRequest<com.vsoyou.sdk.entity.Result>(
				context, null, new ResultParser(),
				new SubmitOrderCallback());
		AddOrderRquestParam addOrderRquestParam = new AddOrderRquestParam(
				context);
		addOrderRquestParam.setOrderNo(mainView.getOrderNum());
		addOrderRquestParam.setProductId(mainView.getProductId());
		addOrderRquestParam.setResultParam(mainView.getResultParam());
		addOrderRquestParam.setServiceId(mainView.getServiceId());
		LogUtil.i(TAG, "payTypeEntity.id-->" + payTypeEntity.id);
		addOrderRquestParam.setTheId(payTypeEntity.id);
		totalPrice = Integer.parseInt(moneyEditText
				.getText().toString().trim()) * 100;
		addOrderRquestParam.setTotalPrice(totalPrice);
		addOrderRquestParam.setUserId(mainView.getLocalStorage().getLong(
				Constants.USER_ID, 0L));
		addOrderRquestParam.setUserName(DESCoder.decryptoPriAndPub(context,
				mainView.getLocalStorage().getString(Constants.USERNAME, "")));
		String url = "";
		if (payType == PayType.ALIPAY) {
			url = DESCoder.decryptoPriAndPub(context, mainView
					.getLocalStorage().getString(Constants.ADDORDERS_URL, ""));
		} else if (payType == PayType.TENPAY) {
			url = mainView.getTenpayEntity().request_url;
		} else if (payType == PayType.UNIONPAY) {
			url = mainView.getUnionpayEntity().request_url;
		}
		request.execute(url, addOrderRquestParam.toJson());
	}

	private class SubmitOrderCallback implements
			HttpCallback<com.vsoyou.sdk.entity.Result> {

		@Override
		public void onSuccess(com.vsoyou.sdk.entity.Result object) {
			mainView.getmHandler().sendEmptyMessage(2);
			if (object.success) {
				if (payType == PayType.ALIPAY) {
					alipayPay();
				} else if (payType == PayType.TENPAY) {
					tenpayPay(object.message);
				} else if (payType == PayType.UNIONPAY) {
					unionpayPay(object.message);
				}
			} else {
				PayCallbackInfo payCallbackInfo = mainView.getPayCallbackInfo();
				payCallbackInfo.statusCode = Constants.FAILURE_TAG;
				payCallbackInfo.statusDes = object.message;
				mainView.showSureDialog(payCallbackInfo);
			}
		}

		@Override
		public void onFailure(int errorCode, String errorMessage) {
			mainView.getmHandler().sendEmptyMessage(2);
			PayCallbackInfo payCallbackInfo = mainView.getPayCallbackInfo();
			payCallbackInfo.statusCode = Constants.FAILURE_TAG;
			payCallbackInfo.statusDes = ToastUtil.SUBMIT_ORDER_FAILURE_CHECK_NET;
			mainView.showSureDialog(payCallbackInfo);
		}

	}

	/************ 银联 **********/

	public void unionpayPay(String tn) {
		String[] data = tn.split("\\|");
		Activity activity = (Activity) context;
		UPPayAssistEx.startPayByJAR(activity, PayActivity.class, null, null,
				data[0], data[1]);
	}

	/************ 财付通支付 **********/

	private void tenpayPay(String mTokenId) {
		Activity activity = (Activity) context;
		if (mTokenId == null || mTokenId.length() <  32) {
			ToastUtil.showToast(context, "获取订单失败！");
			return;
		}
		
		TenpayServiceHelper tenpayHelper = new TenpayServiceHelper(activity);
		tenpayHelper.setLogEnabled(true); //打开log 方便debug, 发布时不需要打开。
		//判断并安装财付通安全支付服务应用
		if (!tenpayHelper.isTenpayServiceInstalled(9)) {
			tenpayHelper.installTenpayService(new DialogInterface.OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface dialog) {
					PayCallbackInfo payCallbackInfo = mainView.getPayCallbackInfo();
					payCallbackInfo.statusCode = Constants.CANCLE_TAG;
					payCallbackInfo.statusDes = ToastUtil.CANCEL_INSTALL;
					HttpRequest<Result> request = new HttpRequest<Result>(
							context, null, null, null);
					request.execute(
							DESCoder.decryptoPriAndPub(
									context,
									mainView.getLocalStorage().getString(
											Constants.CANCELORDERS_URL, "")),
							new UserCancleOrder(context, mainView.getOrderNum())
									.toJson());
					mainView.showSureDialog(payCallbackInfo);
				}
			}, "/sdcard/tenpay");
			return;
		}			
		//构造支付参数
		HashMap<String, String> payInfo = new HashMap<String, String>();
		payInfo.put("token_id", mTokenId);         //财付通订单号token_id
		payInfo.put("bargainor_id", mainView.getTenpayEntity().pid); //财付通合作商户ID,此为演示示例		
		
		//去支付
		tenpayHelper.pay(payInfo, tenpayHandler, MSG_PAY_RESULT);
	}
	
	final static int MSG_PAY_RESULT = 100;
	//接收支付返回值的Handler
	protected Handler tenpayHandler = new Handler()	{  
		  public void handleMessage(Message msg) {							
				switch (msg.what){
					case MSG_PAY_RESULT:
						String strRet = (String)msg.obj; // 支付返回值
                        
						String statusCode = null;
						String info = null;
						String result = null;
						
						JSONObject jo;
						try {
							jo = new JSONObject(strRet);
							if (jo != null) {
								statusCode = jo.getString("statusCode");
								info = jo.getString("info");
								result  = jo.getString("result");								
							}	
						} catch (JSONException e1) {			
							e1.printStackTrace();
						}		
						
						String ret = "statusCode = " + statusCode + ", info = " + info
									+ ", result = " + result;
						
						//按协议文档，解析并判断返回值，从而显示自定义的支付结果界面
						PayCallbackInfo payCallbackInfo = mainView.getPayCallbackInfo();
						if("0".equals(statusCode)){ //成功
							payCallbackInfo.statusCode = Constants.SUCCESS_TAG;
							payCallbackInfo.statusDes = ToastUtil.PAY_SUCCESS;
						}else if("66200003".equals(statusCode)){
							payCallbackInfo.statusCode = Constants.CANCLE_TAG;
							payCallbackInfo.statusDes = ToastUtil.CANCEL_ORDERNO;
							HttpRequest<Result> request = new HttpRequest<Result>(
									context, null, null, null);
							request.execute(
									DESCoder.decryptoPriAndPub(
											context,
											mainView.getLocalStorage().getString(
													Constants.CANCELORDERS_URL, "")),
									new UserCancleOrder(context, mainView.getOrderNum())
											.toJson());
						}else{
							payCallbackInfo.statusCode = Constants.FAILURE_TAG;
							payCallbackInfo.statusDes = info;
						}
						//显示窗口
						mainView.showSureDialog(payCallbackInfo);
						break; 
				}
		  }
	};	

	/************ 支付宝支付 **********/

	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			Result result = new Result((String) msg.obj, payTypeEntity);
			result.parseStatus();
			switch (msg.what) {
			case RQF_PAY:
			case RQF_LOGIN: {
				// 回调给服务器和PC
				// 之后取消订单 才返回数据给服务器
				PayCallbackInfo payCallbackInfo = mainView.getPayCallbackInfo();
				
				if (result.getResultCode().equals("6001")) { // 取消订单
					payCallbackInfo.statusCode = Constants.CANCLE_TAG;
					payCallbackInfo.statusDes = ToastUtil.CANCEL_ORDERNO;
					HttpRequest<Result> request = new HttpRequest<Result>(
							context, null, null, null);
					request.execute(
							DESCoder.decryptoPriAndPub(
									context,
									mainView.getLocalStorage().getString(
											Constants.CANCELORDERS_URL, "")),
							new UserCancleOrder(context, mainView.getOrderNum())
									.toJson());
				} else if (result.getResultCode().equals("9000")) {
					payCallbackInfo.statusCode = Constants.SUCCESS_TAG;
					payCallbackInfo.statusDes = ToastUtil.PAY_SUCCESS;
				}else{
					payCallbackInfo.statusCode = Constants.FAILURE_TAG;
					payCallbackInfo.statusDes = ToastUtil.PAY_FAILURE;
				}
				mainView.showSureDialog(payCallbackInfo);

			}
				break;
			default:
				break;
			}
		}

	};

	private void alipayPay() {
		LogUtil.i(TAG, "alipayPay");
		try {
			String info = getNewOrderInfo();
			String sign = Rsa.sign(info, payTypeEntity.private_Key);
			sign = URLEncoder.encode(sign);
			info += "&sign=\"" + sign + "\"&" + getSignType();
			LogUtil.i(TAG, "start pay");
			// start the pay.
			LogUtil.i(TAG, "info = " + info);

			final String orderInfo = info;
			new Thread() {
				public void run() {
					Activity activity = (Activity) context;
					AliPay alipay = new AliPay(activity, mHandler);

					// 设置为沙箱模式，不设置默认为线上环境
					// alipay.setSandBox(true);

					String result = alipay.pay(orderInfo);

					LogUtil.i(TAG, "result = " + result);
					Message msg = new Message();
					msg.what = RQF_PAY;
					msg.obj = result;
					mHandler.sendMessage(msg);
				}
			}.start();

		} catch (Exception ex) {
			ex.printStackTrace();
			Toast.makeText(context, "Failure calling remote service.",
					Toast.LENGTH_SHORT).show();
		}
	}

	private String getNewOrderInfo() {
		StringBuilder sb = new StringBuilder();
		sb.append("partner=\"");
		sb.append(payTypeEntity.pid);
		sb.append("\"&out_trade_no=\"");
		sb.append(mainView.getOrderNum());
		sb.append("\"&subject=\"");
		sb.append(mainView.getProductEntity().appName);
		sb.append("\"&body=\"");
		sb.append(mainView.getProductEntity().titleName);
		sb.append("\"&total_fee=\"");
		sb.append(moneyEditText.getText());
		sb.append("\"&notify_url=\"");

		// 网址需要做URL编码
		sb.append(URLEncoder.encode(payTypeEntity.notify_url));
		sb.append("\"&service=\"mobile.securitypay.pay");
		sb.append("\"&_input_charset=\"UTF-8");
		sb.append("\"&return_url=\"");
		sb.append(URLEncoder.encode("http://m.alipay.com"));
		sb.append("\"&payment_type=\"1");
		sb.append("\"&seller_id=\"");
		sb.append(payTypeEntity.seller_id);

		// 如果show_url值为空，可不传
		// sb.append("\"&show_url=\"");
		sb.append("\"&it_b_pay=\"1m");
		sb.append("\"");

		return new String(sb);
	}

	private String getSignType() {
		return "sign_type=\"" + payTypeEntity.keyType + "\"";
	}

}
