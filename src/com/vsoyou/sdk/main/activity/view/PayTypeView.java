package com.vsoyou.sdk.main.activity.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.vsoyou.sdk.main.Constants;
import com.vsoyou.sdk.main.PayCallbackInfo;
import com.vsoyou.sdk.main.enums.PayType;
import com.vsoyou.sdk.resources.ResourceLoader;
import com.vsoyou.sdk.util.MetricUtil;
import com.vsoyou.sdk.util.ToastUtil;

public class PayTypeView extends LinearLayout implements View.OnClickListener {

	private static final String TAG = "PayTypeView";
	private Context context;
	private ImageView backImageView;

	private PayMainView mainView;
	private TextView promptTextView;

	private PayItemView alipay;
	private PayItemView tenpay;
	private PayItemView y_cardpay;
	private PayItemView l_cardpay;
	private PayItemView d_cardpay;
	private PayItemView unionpay;
	
	public TextView getPromptTextView() {
		return promptTextView;
	}

	@SuppressLint("NewApi")
	public PayTypeView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public PayTypeView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PayTypeView(Context context, PayMainView mainView) {
		super(context);
		this.context = context;
		this.mainView = mainView;
		initView();
		addListener();
	}

	private void addListener() {
		backImageView.setOnClickListener(this);
		alipay.setOnClickListener(this);
		tenpay.setOnClickListener(this);
		y_cardpay.setOnClickListener(this);
		l_cardpay.setOnClickListener(this);
		d_cardpay.setOnClickListener(this);
		unionpay.setOnClickListener(this);
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

		int payItemMargin = MetricUtil.getDip(context, 10.0F);

		if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) { // 竖屏

			int payItemPortroitMargin = MetricUtil.getDip(context, 5.0F);

			LinearLayout linerLayout_1 = new LinearLayout(context);
			linerLayout_1.setGravity(Gravity.CENTER);
			linerLayout_1.setOrientation(LinearLayout.HORIZONTAL);
			LinearLayout.LayoutParams linerLayout_1Params = new LinearLayout.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			linerLayout_1Params.setMargins(MetricUtil.getDip(context, 20.0F),
					0, MetricUtil.getDip(context, 20.0F), 0);
			allLinearLayout.addView(linerLayout_1, linerLayout_1Params);
			alipay = new PayItemView(context, PayType.ALIPAY, this);
			tenpay = new PayItemView(context, PayType.TENPAY, this);
			tenpay.getOwnParams().setMargins(payItemPortroitMargin, 0, 0, 0);
			linerLayout_1.addView(alipay);
			linerLayout_1.addView(tenpay);

			LinearLayout linerLayout_2 = new LinearLayout(context);
			linerLayout_2.setGravity(Gravity.CENTER);
			linerLayout_2.setOrientation(LinearLayout.HORIZONTAL);
			LinearLayout.LayoutParams linerLayout_2Params = new LinearLayout.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			linerLayout_2Params.setMargins(MetricUtil.getDip(context, 20.0F),
					MetricUtil.getDip(context, 10.0F),
					MetricUtil.getDip(context, 20.0F), 0);
			allLinearLayout.addView(linerLayout_2, linerLayout_2Params);
			unionpay = new PayItemView(context, PayType.UNIONPAY, this);
			y_cardpay = new PayItemView(context, PayType.Y_CARDPAY, this);
			y_cardpay.getOwnParams().setMargins(payItemPortroitMargin, 0, 0, 0);
			linerLayout_2.addView(unionpay);
			linerLayout_2.addView(y_cardpay);

			LinearLayout linerLayout_3 = new LinearLayout(context);
			linerLayout_3.setGravity(Gravity.CENTER);
			linerLayout_3.setOrientation(LinearLayout.HORIZONTAL);
			LinearLayout.LayoutParams linerLayout_3Params = new LinearLayout.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			linerLayout_3Params.setMargins(MetricUtil.getDip(context, 20.0F),
					MetricUtil.getDip(context, 10.0F),
					MetricUtil.getDip(context, 20.0F), 0);
			allLinearLayout.addView(linerLayout_3, linerLayout_3Params);
			l_cardpay = new PayItemView(context, PayType.L_CARDPAY, this);
			d_cardpay = new PayItemView(context, PayType.D_CARDPAY, this);
			d_cardpay.getOwnParams().setMargins(payItemPortroitMargin, 0, 0, 0);
			linerLayout_3.addView(l_cardpay);
			linerLayout_3.addView(d_cardpay);
		} else {// 橫屏
			LinearLayout linerLayout_1 = new LinearLayout(context);
			linerLayout_1.setGravity(Gravity.CENTER);
			linerLayout_1.setOrientation(LinearLayout.HORIZONTAL);
			LinearLayout.LayoutParams linerLayout_1Params = new LinearLayout.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			linerLayout_1Params.setMargins(MetricUtil.getDip(context, 20.0F),
					0, MetricUtil.getDip(context, 20.0F), 0);
			allLinearLayout.addView(linerLayout_1, linerLayout_1Params);
			alipay = new PayItemView(context, PayType.ALIPAY, this);
			tenpay = new PayItemView(context, PayType.TENPAY, this);
			tenpay.getOwnParams().setMargins(payItemMargin, 0, 0, 0);
			unionpay = new PayItemView(context, PayType.UNIONPAY, this);
			unionpay.getOwnParams().setMargins(payItemMargin, 0, 0, 0);
			linerLayout_1.addView(alipay);
			linerLayout_1.addView(tenpay);
			linerLayout_1.addView(unionpay);

			LinearLayout linerLayout_2 = new LinearLayout(context);
			linerLayout_2.setGravity(Gravity.CENTER);
			linerLayout_2.setOrientation(LinearLayout.HORIZONTAL);
			LinearLayout.LayoutParams linerLayout_2Params = new LinearLayout.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			linerLayout_2Params.setMargins(MetricUtil.getDip(context, 20.0F),
					MetricUtil.getDip(context, 10.0F),
					MetricUtil.getDip(context, 20.0F), 0);
			allLinearLayout.addView(linerLayout_2, linerLayout_2Params);

			y_cardpay = new PayItemView(context, PayType.Y_CARDPAY, this);
			l_cardpay = new PayItemView(context, PayType.L_CARDPAY, this);
			l_cardpay.getOwnParams().setMargins(payItemMargin, 0, 0, 0);
			d_cardpay = new PayItemView(context, PayType.D_CARDPAY, this);
			d_cardpay.getOwnParams().setMargins(payItemMargin, 0, 0, 0);
			linerLayout_2.addView(y_cardpay);
			linerLayout_2.addView(l_cardpay);
			linerLayout_2.addView(d_cardpay);
		}

		promptTextView = new TextView(context);
		promptTextView.setBackgroundDrawable(ResourceLoader
				.getNinePatchDrawable("edittext_bg.9.png"));
		promptTextView
				.setText("充值帮助：\n1、1元人名币=10元宝，一般1-10分钟即可到帐，安全方便。\n2、银联卡充值支持大部分信用卡以及部分借记卡。\n3、充值卡充值请选择好正确的面额，并仔细核对卡号和密码。\n4、如需帮助请联系客服，电话：4000799588，QQ：4000799588");
		int serviceTTPadding = MetricUtil.getDip(context, 10.0F);
		promptTextView.setPadding(serviceTTPadding, serviceTTPadding,
				serviceTTPadding, serviceTTPadding);
		promptTextView.setTextColor(Color.parseColor("#8c8c8c"));
		promptTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14.0F);
		LinearLayout.LayoutParams promptParams = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		promptParams.setMargins(MetricUtil.getDip(context, 20.0F),
				MetricUtil.getDip(context, 20.0F),
				MetricUtil.getDip(context, 20.0F),
				MetricUtil.getDip(context, 20.0F));
		allLinearLayout.addView(promptTextView, promptParams);

	}

	@Override
	public void onClick(View v) {
		if (v == backImageView) {
			Activity activity = (Activity)context;
			activity.finish();
			PayCallbackInfo payCallbackInfo = mainView.getPayCallbackInfo();
			payCallbackInfo.statusCode = Constants.CANCLE_TAG;
			payCallbackInfo.statusDes = ToastUtil.NO_SELECT_PAY_TYPE;
			mainView.getPayListener().onPayCallback(payCallbackInfo);
		}
		if (v == alipay) {
			if(mainView.getAlipayEntity() == null){
				ToastUtil.showToast(context, ToastUtil.NO_PAY_TYPE);
				return;
			}
			mainView.getOtherPayView().updateData(PayType.ALIPAY, mainView.getAlipayEntity());
			mainView.showOtherPayView();
		}
		if (v == tenpay) {
			if(mainView.getTenpayEntity() == null){
				ToastUtil.showToast(context, ToastUtil.NO_PAY_TYPE);
				return;
			}
			mainView.getOtherPayView().updateData(PayType.TENPAY, mainView.getTenpayEntity());
			mainView.showOtherPayView();
		}
		if (v == y_cardpay) {
			if(mainView.getCardpayEntity() == null){
				ToastUtil.showToast(context, ToastUtil.NO_PAY_TYPE);
				return;
			}
			mainView.getCzkPayView().updateData(PayType.Y_CARDPAY, mainView.getCardpayEntity());
			if(!mainView.isCanUpdatePrice() && !mainView.getCzkPayView().havaPrice()){
				ToastUtil.showToast(context, ToastUtil.NO_PAY_PRICE);
				return;
			}
			mainView.showCzkPayView();
		}
		if (v == l_cardpay) {
			if(mainView.getCardpayEntity() == null){
				ToastUtil.showToast(context, ToastUtil.NO_PAY_TYPE);
				return;
			}
			mainView.getCzkPayView().updateData(PayType.L_CARDPAY, mainView.getCardpayEntity());
			if(!mainView.isCanUpdatePrice() && !mainView.getCzkPayView().havaPrice()){
				ToastUtil.showToast(context, ToastUtil.NO_PAY_PRICE);
				return;
			}
			mainView.showCzkPayView();
		}
		if (v == d_cardpay) {
			if(mainView.getCardpayEntity() == null){
				ToastUtil.showToast(context, ToastUtil.NO_PAY_TYPE);
				return;
			}
			mainView.getCzkPayView().updateData(PayType.D_CARDPAY, mainView.getCardpayEntity());
			if(!mainView.isCanUpdatePrice() && !mainView.getCzkPayView().havaPrice()){
				ToastUtil.showToast(context, ToastUtil.NO_PAY_PRICE);
				return;
			}
			mainView.showCzkPayView();
		}
		if (v == unionpay) {
			if(mainView.getUnionpayEntity() == null){
				ToastUtil.showToast(context, ToastUtil.NO_PAY_TYPE);
				return;
			}
			mainView.getOtherPayView().updateData(PayType.UNIONPAY, mainView.getUnionpayEntity());
			mainView.showOtherPayView();
		}
	}

}
