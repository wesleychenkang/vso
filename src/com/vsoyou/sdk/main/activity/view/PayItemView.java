package com.vsoyou.sdk.main.activity.view;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vsoyou.sdk.main.enums.PayType;
import com.vsoyou.sdk.resources.ResourceLoader;
import com.vsoyou.sdk.util.Decorator;
import com.vsoyou.sdk.util.MetricUtil;

public class PayItemView extends RelativeLayout {

	private static final String TAG = "PayItemView";
	private Context context;
	private PayTypeView payTypeView;
	private PayType payType;
	
	private LinearLayout.LayoutParams ownParams;
	
	public void setOwnParams(LinearLayout.LayoutParams ownParams) {
		this.ownParams = ownParams;
	}
	
	public LinearLayout.LayoutParams getOwnParams() {
		return ownParams;
	}

	public PayItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public PayItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public PayItemView(Context context, PayType payType, PayTypeView payTypeView) {
		super(context);
		this.context = context;
		this.payType = payType;
		this.payTypeView = payTypeView;
		initView();
	}

	private void initView() {
		ownParams = new LinearLayout.LayoutParams(
				MetricUtil.getDip(context, 160.0F),
				ViewGroup.LayoutParams.WRAP_CONTENT);
		ownParams.weight = 1;
		this.setLayoutParams(ownParams);
//		this.setBackgroundDrawable(ResourceLoader
//				.getNinePatchDrawable("edittext_bg.9.png"));
		
		Decorator
		.setStateImage(
				this,
				ResourceLoader
						.getNinePatchDrawable("edittext_normal_bg.9.png"),
				ResourceLoader
						.getNinePatchDrawable("edittext_bg.9.png"),
				ResourceLoader
						.getNinePatchDrawable("edittext_normal_bg.9.png"));
		
		LinearLayout linerLayout_1 = new LinearLayout(context);
		linerLayout_1.setGravity(Gravity.CENTER);
		linerLayout_1.setOrientation(LinearLayout.HORIZONTAL);
		RelativeLayout.LayoutParams linerLayout_1Params = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT, MetricUtil.getDip(context,
						40.0F));
		linerLayout_1Params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		linerLayout_1Params.setMargins(MetricUtil.getDip(context, 10.0F), 0, 0,
				0);
		this.addView(linerLayout_1, linerLayout_1Params);

		ImageView typeLogoImageView = new ImageView(context);
		linerLayout_1.addView(typeLogoImageView, new LinearLayout.LayoutParams(
				MetricUtil.getDip(context, 20.0F),
				MetricUtil.getDip(context, 20.0F)));
		
		TextView payNameTextView = new TextView(context);
		payNameTextView.setTextColor(Color.parseColor("#4c4c4c"));
		payNameTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 19.0F);
		LinearLayout.LayoutParams payNameTextViewParams = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		payNameTextViewParams.setMargins(MetricUtil.getDip(context, 5.0F), 0, 0, 0);
		linerLayout_1.addView(payNameTextView, payNameTextViewParams);
		
		if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) { // 竖屏
			if (payType == PayType.Y_CARDPAY) {
				typeLogoImageView.setImageDrawable(ResourceLoader
						.getBitmapDrawable("yidong.png"));
				payNameTextView.setText("移动充值");
			} else if (payType == PayType.L_CARDPAY) {
				typeLogoImageView.setImageDrawable(ResourceLoader
						.getBitmapDrawable("liantong.png"));
				payNameTextView.setText("联通充值");
			} else if (payType == PayType.D_CARDPAY) {
				typeLogoImageView.setImageDrawable(ResourceLoader
						.getBitmapDrawable("dianxin.png"));
				payNameTextView.setText("电信充值");
			}
		}else{
			if (payType == PayType.Y_CARDPAY) {
				typeLogoImageView.setImageDrawable(ResourceLoader
						.getBitmapDrawable("yidong.png"));
				payNameTextView.setText("移动充值卡");
			} else if (payType == PayType.L_CARDPAY) {
				typeLogoImageView.setImageDrawable(ResourceLoader
						.getBitmapDrawable("liantong.png"));
				payNameTextView.setText("联通充值卡");
			} else if (payType == PayType.D_CARDPAY) {
				typeLogoImageView.setImageDrawable(ResourceLoader
						.getBitmapDrawable("dianxin.png"));
				payNameTextView.setText("电信充值卡");
			}
		}
		if (payType == PayType.ALIPAY) {
			typeLogoImageView.setImageDrawable(ResourceLoader
					.getBitmapDrawable("zhifubao.png"));
			payNameTextView.setText("支付宝");
		} else if (payType == PayType.TENPAY) {
			typeLogoImageView.setImageDrawable(ResourceLoader
					.getBitmapDrawable("caifutong.png"));
			payNameTextView.setText("财付通");
		} else if (payType == PayType.UNIONPAY) {
			typeLogoImageView.setImageDrawable(ResourceLoader
					.getBitmapDrawable("yinlian.png"));
			payNameTextView.setText("银行卡");
		} 
		
		LinearLayout linerLayout_2 = new LinearLayout(context);
		linerLayout_2.setGravity(Gravity.CENTER);
		linerLayout_2.setOrientation(LinearLayout.HORIZONTAL);
		RelativeLayout.LayoutParams linerLayout_2Params = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT, MetricUtil.getDip(context,
						40.0F));
		linerLayout_2Params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		linerLayout_2Params.setMargins(0, 0, MetricUtil.getDip(context, 10.0F),
				0);
		this.addView(linerLayout_2, linerLayout_2Params);
		
		ImageView arrowImageView = new ImageView(context);
		arrowImageView.setImageDrawable(ResourceLoader
					.getBitmapDrawable("right_arrow.png"));
		linerLayout_2.addView(arrowImageView, new LinearLayout.LayoutParams(
				MetricUtil.getDip(context, 11.0F),
				MetricUtil.getDip(context, 11.0F)));

	}


}
