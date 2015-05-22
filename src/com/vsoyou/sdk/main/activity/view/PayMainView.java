package com.vsoyou.sdk.main.activity.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.text.Html;
import android.text.Spanned;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.vsoyou.sdk.main.Constants;
import com.vsoyou.sdk.main.PayCallbackInfo;
import com.vsoyou.sdk.main.PayListener;
import com.vsoyou.sdk.main.entity.PayTypeEntity;
import com.vsoyou.sdk.main.entity.ProductEntity;
import com.vsoyou.sdk.main.entity.ThesInfoResult;
import com.vsoyou.sdk.main.enums.PayType;
import com.vsoyou.sdk.util.DESCoder;
import com.vsoyou.sdk.util.LocalStorage;
import com.vsoyou.sdk.util.LogUtil;

public class PayMainView extends FrameLayout {

	private static final String TAG = "PayMainView";
	private Context context;
	private PayTypeView payTypeView;
	private CzkPayView czkPayView;
	private OtherPayView otherPayView;

	private PayTypeEntity alipayEntity;
	private PayTypeEntity tenpayEntity;
	private PayTypeEntity unionpayEntity;
	private PayTypeEntity cardpayEntity;

	private String orderNum;
	private ProductEntity productEntity;
	private int productId;
	private int serviceId;
	private String resultParam;
	private String phoneStr;
	private String qqStr;
	private int inPrice; // 传入的固定金额
	private boolean canUpdatePrice; //是否能修改金额 true=能修改 false=不能修改
	private LocalStorage localStorage;

	private PayListener payListener;
	private Handler mHandler;
	
	public boolean isCanUpdatePrice() {
		return canUpdatePrice;
	}

	public int getInPrice() {
		return inPrice;
	}

	public PayListener getPayListener() {
		return payListener;
	}

	public String getPhoneStr() {
		return phoneStr;
	}

	public String getQqStr() {
		return qqStr;
	}

	public int getServiceId() {
		return serviceId;
	}

	public String getResultParam() {
		return resultParam;
	}

	public int getProductId() {
		return productId;
	}

	public LocalStorage getLocalStorage() {
		return localStorage;
	}

	public ProductEntity getProductEntity() {
		return productEntity;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public PayTypeEntity getAlipayEntity() {
		return alipayEntity;
	}

	public PayTypeEntity getTenpayEntity() {
		return tenpayEntity;
	}

	public PayTypeEntity getUnionpayEntity() {
		return unionpayEntity;
	}

	public PayTypeEntity getCardpayEntity() {
		return cardpayEntity;
	}

	public PayTypeView getPayTypeView() {
		return payTypeView;
	}

	public CzkPayView getCzkPayView() {
		return czkPayView;
	}

	public OtherPayView getOtherPayView() {
		return otherPayView;
	}
	
	public Handler getmHandler() {
		return mHandler;
	}

	@SuppressLint("NewApi")
	public PayMainView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public PayMainView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PayMainView(Context context, ThesInfoResult thesInfoResult,
			String orderNum, int productId, int defaultPrice, boolean canUpdatePrice, int serviceId, String resultParam,
			PayListener payListener, Handler handler) {
		super(context);
		this.context = context;
		this.orderNum = orderNum;
		this.productEntity = thesInfoResult.productEntity;
		this.productId = productId;
		LogUtil.i(TAG, "defaultPrice-->" + defaultPrice);
		if(defaultPrice != 0){
			this.inPrice = defaultPrice / 100;
		}else{
			this.inPrice = productEntity.price / 100;
		}
		this.canUpdatePrice = canUpdatePrice;
		this.serviceId = serviceId;
		this.resultParam = resultParam;
		this.payListener = payListener;
		this.localStorage = LocalStorage.getInstance(context);
		this.mHandler = handler;
		intPayTypeData(thesInfoResult);
		initPhoneAndQQ();
		initView();
	}

	private void initPhoneAndQQ() {
		phoneStr = DESCoder.decryptoPriAndPub(context,
				localStorage.getString(Constants.SERVICEPHONE, ""));
		qqStr = DESCoder.decryptoPriAndPub(context,
				localStorage.getString(Constants.SERVICEQQ, ""));
	}

	private void intPayTypeData(ThesInfoResult thesInfoResult) {
		int payTypeSize = thesInfoResult.payTypeList.size();
		if (payTypeSize > 0) {
			for (int i = 0; i < payTypeSize; i++) {
				PayTypeEntity temp = thesInfoResult.payTypeList.get(i);
				if ("ALIPAY".equals(temp.enName)) {
					alipayEntity = temp;
				} else if ("TENPAY".equals(temp.enName)) {
					tenpayEntity = temp;
				} else if ("CARDPAY".equals(temp.enName)) {
					cardpayEntity = temp;
				} else if ("UNIONPAY".equals(temp.enName)) {
					unionpayEntity = temp;
				}
			}
		}
	}

	private void initView() {
		payTypeView = new PayTypeView(context, this);
		czkPayView = new CzkPayView(context, this);
		otherPayView = new OtherPayView(context, this);
		FrameLayout.LayoutParams commonParams = new FrameLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		this.addView(payTypeView, commonParams);
		this.addView(czkPayView, commonParams);
		this.addView(otherPayView, commonParams);
		czkPayView.setVisibility(View.GONE);
		otherPayView.setVisibility(View.GONE);
		int yuanPrice = productEntity.price / 100;
		Spanned spanned = Html
				.fromHtml("充值帮助：<br>1、<font color='#ff5c03'>"
						+ yuanPrice
						+ "元人民币="
						+ productEntity.titleName
						+ "</font>，一般1-10分钟即可到帐，安全方便。<br>2、银联卡充值支持大部分信用卡以及部分借记卡。<br>3、充值卡充值请选择好正确的面额，并仔细核对卡号和密码。<br>4、如需帮助请联系客服，电话：<font color='#ff5c03'>"
						+ phoneStr + "</font>，QQ：<font color='#ff5c03'>"
						+ qqStr + "</font>");
		payTypeView.getPromptTextView().setText(spanned);
	}

	public void showMainView() {
		czkPayView.setVisibility(View.GONE);
		otherPayView.setVisibility(View.GONE);
		payTypeView.setVisibility(View.VISIBLE);
	}

	public void showCzkPayView() {
		otherPayView.setVisibility(View.GONE);
		payTypeView.setVisibility(View.GONE);
		czkPayView.setVisibility(View.VISIBLE);
	}

	public void showOtherPayView() {
		czkPayView.setVisibility(View.GONE);
		payTypeView.setVisibility(View.GONE);
		otherPayView.setVisibility(View.VISIBLE);
	}
	
	public void showSureDialog(PayCallbackInfo callbackInfo){
		AlertDialog dialog = new AlertDialog.Builder(context).create();
		dialog.setTitle("提示");
		dialog.setIcon(android.R.drawable.ic_dialog_info);
		dialog.setView(new DialogView(context, callbackInfo, payListener, dialog), 0, 0, 0, 0);
		dialog.setCancelable(false);
		dialog.show();
	}
	
	public PayCallbackInfo getPayCallbackInfo() {
		PayCallbackInfo payCallbackInfo = new PayCallbackInfo();
		payCallbackInfo.appId = getLocalStorage().getInt(Constants.VSOYOU_APP_ID, 0);
		payCallbackInfo.channelId = getLocalStorage().getInt(Constants.VSOYOU_CHANNELID, 0);
		payCallbackInfo.extra = getResultParam();
		payCallbackInfo.orderNo = getOrderNum();
		if(getOtherPayView().getPayType() == null){
			payCallbackInfo.payType = "";
		}else{
			payCallbackInfo.payType = "" + getOtherPayView().getPayType();
		}
		payCallbackInfo.price = getOtherPayView().getTotalPrice();
		payCallbackInfo.productId = getProductId();
		payCallbackInfo.userId = getLocalStorage().getLong(Constants.USER_ID, 0L);
		payCallbackInfo.userName = DESCoder.decryptoPriAndPub(context, getLocalStorage().getString(Constants.USERNAME, ""));
		return payCallbackInfo;
	}

}
