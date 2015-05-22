package com.vsoyou.sdk.main.activity.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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

import com.vsoyou.sdk.entity.parser.ResultParser;
import com.vsoyou.sdk.http.HttpCallback;
import com.vsoyou.sdk.http.HttpRequest;
import com.vsoyou.sdk.main.Constants;
import com.vsoyou.sdk.main.PayCallbackInfo;
import com.vsoyou.sdk.main.PayManager;
import com.vsoyou.sdk.main.activity.view.progress.GetDataProgressView;
import com.vsoyou.sdk.main.adapter.MoneyGridAdapter;
import com.vsoyou.sdk.main.entity.PayTypeEntity;
import com.vsoyou.sdk.main.entity.requestparam.AddOrderRquestParam;
import com.vsoyou.sdk.main.enums.PayType;
import com.vsoyou.sdk.resources.ResourceLoader;
import com.vsoyou.sdk.util.DESCoder;
import com.vsoyou.sdk.util.Decorator;
import com.vsoyou.sdk.util.LogUtil;
import com.vsoyou.sdk.util.MetricUtil;
import com.vsoyou.sdk.util.StringUtil;
import com.vsoyou.sdk.util.ToastUtil;

public class CzkPayView extends LinearLayout implements View.OnClickListener {

	private static final String TAG = "CzkPayView";
	private Context context;
	private ImageView backImageView;
	private TextView payTypeTextView;
	private EditText moneyEditText;
	private ImageView moreMoneyImageView;
	private TextView sureTextView;
	private TextView cancleTextView;
	private LayoutInflater inflater;
	private PopupWindow pop;
	private EditText cardNumEditText;
	private EditText cardPwdEditText;

	private PayMainView mainView;

	private PayType payType;
	private PayTypeEntity payTypeEntity;
	private int cardNumTotal;
	private int cardPwdTotal;

	private String cardNum;
	private String cardPwd;
	private int price;
	private int totalPrice;

	@SuppressLint("NewApi")
	public CzkPayView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public CzkPayView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CzkPayView(Context context, PayMainView mainView) {
		super(context);
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.mainView = mainView;
		initView();
		addListener();
	}

	private void addListener() {
		backImageView.setOnClickListener(this);
		sureTextView.setOnClickListener(this);
		cancleTextView.setOnClickListener(this);
		if(mainView.isCanUpdatePrice()){
			moreMoneyImageView.setOnClickListener(this);
		}
		cardNumEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		cardPwdEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
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
		LinearLayout.LayoutParams imageViewParams = new LinearLayout.LayoutParams(
				MetricUtil.getDip(context, 23.0F), MetricUtil.getDip(context,
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

		// 卡号
		LinearLayout linearLayout_5 = new LinearLayout(context);
		linearLayout_5.setGravity(Gravity.CENTER_VERTICAL);
		linearLayout_5.setOrientation(LinearLayout.HORIZONTAL);
		LinearLayout.LayoutParams linearLayout_5Params = new LinearLayout.LayoutParams(
				MetricUtil.getDip(context, 310.0F), MetricUtil.getDip(context,
						45.0F));
		linearLayout_5Params.setMargins(0, MetricUtil.getDip(context, 10.0F),
				0, 0);
		allLinearLayout.addView(linearLayout_5, linearLayout_5Params);

		TextView cardNumTextView = new TextView(context);
		cardNumTextView.setGravity(Gravity.CENTER_VERTICAL);
		cardNumTextView.setText("卡号：");
		cardNumTextView.setTextColor(Color.parseColor("#666666"));
		cardNumTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20.0F);
		LinearLayout.LayoutParams cardNumTextViewParams = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		linearLayout_5.addView(cardNumTextView, cardNumTextViewParams);

		cardNumEditText = new EditText(context);
		cardNumEditText.setBackgroundDrawable(editTextBg);
		cardNumEditText.setHint("请输入卡号（17位）");
		cardNumEditText.setImeOptions(EditorInfo.IME_ACTION_NEXT);
		cardNumEditText.setSingleLine();
		cardNumEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
		LinearLayout.LayoutParams cardNumEditTextParams = new LinearLayout.LayoutParams(
				MetricUtil.getDip(context, 200.0F), MetricUtil.getDip(context,
						40.0F));
		int moneyEditTextPadding = MetricUtil.getDip(context, 5.0F);
		cardNumEditText.setPadding(moneyEditTextPadding, moneyEditTextPadding,
				moneyEditTextPadding, moneyEditTextPadding);
		cardNumEditTextParams.setMargins(MetricUtil.getDip(context, 1.0F),
				MetricUtil.getDip(context, 1.0F),
				MetricUtil.getDip(context, 1.0F),
				MetricUtil.getDip(context, 1.0F));
		linearLayout_5.addView(cardNumEditText, cardNumEditTextParams);

		// 密码
		LinearLayout linearLayout_6 = new LinearLayout(context);
		linearLayout_6.setGravity(Gravity.CENTER_VERTICAL);
		linearLayout_6.setOrientation(LinearLayout.HORIZONTAL);
		LinearLayout.LayoutParams linearLayout_6Params = new LinearLayout.LayoutParams(
				MetricUtil.getDip(context, 310.0F), MetricUtil.getDip(context,
						45.0F));
		linearLayout_6Params.setMargins(0, MetricUtil.getDip(context, 10.0F),
				0, 0);
		allLinearLayout.addView(linearLayout_6, linearLayout_6Params);

		TextView cardPwdTextView = new TextView(context);
		cardPwdTextView.setGravity(Gravity.CENTER_VERTICAL);
		cardPwdTextView.setText("密码：");
		cardPwdTextView.setTextColor(Color.parseColor("#666666"));
		cardPwdTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20.0F);
		LinearLayout.LayoutParams cardPwdTextViewParams = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		linearLayout_6.addView(cardPwdTextView, cardPwdTextViewParams);

		cardPwdEditText = new EditText(context);
		cardPwdEditText.setBackgroundDrawable(editTextBg);
		cardPwdEditText.setHint("请输入密码（18位）");
		cardPwdEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
		cardPwdEditText.setSingleLine();
		cardPwdEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
		LinearLayout.LayoutParams cardPwdEditTextParams = new LinearLayout.LayoutParams(
				MetricUtil.getDip(context, 200.0F), MetricUtil.getDip(context,
						40.0F));
		cardPwdEditText.setPadding(moneyEditTextPadding, moneyEditTextPadding,
				moneyEditTextPadding, moneyEditTextPadding);
		cardPwdEditTextParams.setMargins(MetricUtil.getDip(context, 1.0F),
				MetricUtil.getDip(context, 1.0F),
				MetricUtil.getDip(context, 1.0F),
				MetricUtil.getDip(context, 1.0F));
		linearLayout_6.addView(cardPwdEditText, cardPwdEditTextParams);

		// 金额
		LinearLayout linearLayout_2 = new LinearLayout(context);
		linearLayout_2.setGravity(Gravity.CENTER_VERTICAL);
		linearLayout_2.setOrientation(LinearLayout.HORIZONTAL);
		LinearLayout.LayoutParams linearLayout_2Params = new LinearLayout.LayoutParams(
				MetricUtil.getDip(context, 310.0F), MetricUtil.getDip(context,
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
		moneyEditText.setText("50");
		moneyEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
		moneyEditText.setSingleLine();
		moneyEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
		moneyEditText.setFocusable(false);
		LinearLayout.LayoutParams moneyEditTextParams = new LinearLayout.LayoutParams(
				MetricUtil.getDip(context, 200.0F), MetricUtil.getDip(context,
						40.0F));
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
		Decorator.setStateImage(cancleTextView, ResourceLoader
				.getNinePatchDrawable("button_yellow_normal.9.png"),
				ResourceLoader
						.getNinePatchDrawable("button_yellow_pressed.9.png"),
				ResourceLoader
						.getNinePatchDrawable("button_yellow_normal.9.png"));
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

		TextView promptTextView = new TextView(context);
		promptTextView.setBackgroundDrawable(ResourceLoader
				.getNinePatchDrawable("edittext_bg.9.png"));
		promptTextView
				.setText("温馨提示\n请根据充值卡面金额选择正确的充值金额，并确保卡号密码输入无误，若充值失败，请选择其他支付方式。");
		promptTextView.setTextColor(Color.parseColor("#393939"));
		promptTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14.0F);
		LinearLayout.LayoutParams promptTextViewParams = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		int promptTextViewMargin = MetricUtil.getDip(context, 10.0F);
		promptTextView.setPadding(promptTextViewMargin, promptTextViewMargin,
				promptTextViewMargin, promptTextViewMargin);
		linearLayout_4.addView(promptTextView, promptTextViewParams);

	}

	private void updatePop(PayType payType) {
		moneyEditText.setText("50");
		GridView view = new MoneyGridView(context);
		final ArrayList<String> monyList = new ArrayList<String>();
		if (payType == PayType.Y_CARDPAY) {
			cardNumTotal = 17;
			cardPwdTotal = 18;
			monyList.add("10元");
			monyList.add("20元");
			monyList.add("30元");
			monyList.add("50元");
			monyList.add("100元");
			monyList.add("200元");
			monyList.add("300元");
			monyList.add("500元");
			if (mainView.getInPrice() != 0) {
				if (mainView.getInPrice() <= 10) {
					moneyEditText.setText("" + 10);
				} else if (mainView.getInPrice() > 10
						&& mainView.getInPrice() <= 20) {
					moneyEditText.setText("" + 20);
				} else if (mainView.getInPrice() > 20
						&& mainView.getInPrice() <= 30) {
					moneyEditText.setText("" + 30);
				} else if (mainView.getInPrice() > 30
						&& mainView.getInPrice() <= 50) {
					moneyEditText.setText("" + 50);
				} else if (mainView.getInPrice() > 50
						&& mainView.getInPrice() <= 100) {
					moneyEditText.setText("" + 100);
				} else if (mainView.getInPrice() > 100
						&& mainView.getInPrice() <= 200) {
					moneyEditText.setText("" + 200);
				} else if (mainView.getInPrice() > 200
						&& mainView.getInPrice() <= 300) {
					moneyEditText.setText("" + 300);
				} else if (mainView.getInPrice() > 300) {
					moneyEditText.setText("" + 500);
				}
			}
		} else if (payType == PayType.L_CARDPAY) {
			cardNumTotal = 15;
			cardPwdTotal = 19;
			monyList.add("20元");
			monyList.add("30元");
			monyList.add("50元");
			monyList.add("100元");
			monyList.add("200元");
			monyList.add("300元");
			monyList.add("500元");
			if (mainView.getInPrice() != 0) {
				if (mainView.getInPrice() <= 20) {
					moneyEditText.setText("" + 20);
				} else if (mainView.getInPrice() > 20
						&& mainView.getInPrice() <= 30) {
					moneyEditText.setText("" + 30);
				} else if (mainView.getInPrice() > 30
						&& mainView.getInPrice() <= 50) {
					moneyEditText.setText("" + 50);
				} else if (mainView.getInPrice() > 50
						&& mainView.getInPrice() <= 100) {
					moneyEditText.setText("" + 100);
				} else if (mainView.getInPrice() > 100
						&& mainView.getInPrice() <= 200) {
					moneyEditText.setText("" + 200);
				} else if (mainView.getInPrice() > 200
						&& mainView.getInPrice() <= 300) {
					moneyEditText.setText("" + 300);
				} else if (mainView.getInPrice() > 300) {
					moneyEditText.setText("" + 500);
				}
			}
		} else if (payType == PayType.D_CARDPAY) {
			cardNumTotal = 19;
			cardPwdTotal = 18;
			monyList.add("20元");
			monyList.add("30元");
			monyList.add("50元");
			monyList.add("100元");
			if (mainView.getInPrice() != 0) {
				if (mainView.getInPrice() <= 20) {
					moneyEditText.setText("" + 20);
				} else if (mainView.getInPrice() > 20
						&& mainView.getInPrice() <= 30) {
					moneyEditText.setText("" + 30);
				} else if (mainView.getInPrice() > 30
						&& mainView.getInPrice() <= 50) {
					moneyEditText.setText("" + 50);
				} else if (mainView.getInPrice() > 50) {
					moneyEditText.setText("" + 100);
				}
			}
		}
		cardNumEditText
				.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
						cardNumTotal) });
		cardPwdEditText
				.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
						cardPwdTotal) });
		cardNumEditText.setText("");
		cardPwdEditText.setText("");
		cardNumEditText.setHint("请输入卡号（" + cardNumTotal + "位）");
		cardPwdEditText.setHint("请输入密码（" + cardPwdTotal + "位）");

		view.setAdapter(new MoneyGridAdapter(context, monyList));
		pop = new PopupWindow(view, MetricUtil.getDip(context, 202.0F),
				LayoutParams.WRAP_CONTENT, false);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setOutsideTouchable(false);
		pop.setFocusable(true);

		price = mainView.getProductEntity().price / 100;

		view.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				pop.dismiss();
				String nowPriceStr = monyList.get(position).substring(0,
						monyList.get(position).length() - 1);
				if (Integer.parseInt(nowPriceStr) >= price) {
					moneyEditText.setText(nowPriceStr);
				}
			}
		});
	}

	public boolean havaPrice(){
		Map<Integer, Integer> monyMap = new HashMap<Integer, Integer>();
		if (payType == PayType.Y_CARDPAY) {
			monyMap.put(10, 10);
			monyMap.put(20, 20);
			monyMap.put(30, 30);
			monyMap.put(50, 50);
			monyMap.put(100, 100);
			monyMap.put(200, 200);
			monyMap.put(300, 300);
			monyMap.put(500, 500);
		} else if (payType == PayType.L_CARDPAY) {
			monyMap.put(20, 20);
			monyMap.put(30, 30);
			monyMap.put(50, 50);
			monyMap.put(100, 100);
			monyMap.put(200, 200);
			monyMap.put(300, 300);
			monyMap.put(500, 500);
		} else if (payType == PayType.D_CARDPAY) {
			monyMap.put(20, 20);
			monyMap.put(30, 30);
			monyMap.put(50, 50);
			monyMap.put(100, 100);
		}
		if(monyMap.get(mainView.getInPrice()) == null || StringUtil.isEmpty("" + monyMap.get(mainView.getInPrice()))){
			return false;
		}else{
			return true;
		}
	}

	public void updateData(PayType payType, PayTypeEntity payTypeEntity) {
		this.payType = payType;
		Spanned spanned = null;
		if (payType == PayType.Y_CARDPAY) {
			spanned = Html.fromHtml("您已选择<font color=red>\"移动充值卡\"</font>支付");
		} else if (payType == PayType.L_CARDPAY) {
			spanned = Html.fromHtml("您已选择<font color=red>\"联通充值卡\"</font>支付");
		} else if (payType == PayType.D_CARDPAY) {
			spanned = Html.fromHtml("您已选择<font color=red>\"电信充值卡\"</font>支付");
		}
		payTypeTextView.setText(spanned);
		this.payTypeEntity = payTypeEntity;
		updatePop(payType);
	}

	@Override
	public void onClick(View v) {
		if (v == backImageView) {
			mainView.showMainView();
		}
		if (v == sureTextView) {
			submitOrder();
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
				pop.showAtLocation(this, Gravity.CENTER, 0, 0);
			}
		}
	}

	private void submitOrder() {
		if (check()) {
			mainView.getmHandler().sendEmptyMessage(1);
			HttpRequest<com.vsoyou.sdk.entity.Result> request = new HttpRequest<com.vsoyou.sdk.entity.Result>(
					context, null,
					new ResultParser(), new SubmitOrderCallback());
			AddOrderRquestParam addOrderRquestParam = new AddOrderRquestParam(
					context);
			addOrderRquestParam.setOrderNo(mainView.getOrderNum());
			addOrderRquestParam.setProductId(mainView.getProductId());
			addOrderRquestParam.setResultParam(mainView.getResultParam());
			addOrderRquestParam.setServiceId(mainView.getServiceId());
			LogUtil.i(TAG, "payTypeEntity.id-->" + payTypeEntity.id);
			addOrderRquestParam.setTheId(payTypeEntity.id);
			totalPrice = Integer.parseInt(moneyEditText.getText().toString()
					.trim()) * 100;
			addOrderRquestParam.setTotalPrice(totalPrice);
			addOrderRquestParam.setUserId(mainView.getLocalStorage().getLong(
					Constants.USER_ID, 0L));
			addOrderRquestParam.setUserName(DESCoder.decryptoPriAndPub(context,
					mainView.getLocalStorage()
							.getString(Constants.USERNAME, "")));
			addOrderRquestParam.setCardNum(cardNum);
			addOrderRquestParam.setCardPwd(cardPwd);
			String url = "";
			if (payType == PayType.Y_CARDPAY || payType == PayType.L_CARDPAY
					|| payType == PayType.D_CARDPAY) {
				url = mainView.getCardpayEntity().request_url;
			}
			request.execute(url, addOrderRquestParam.toJson());
		}
	}

	private class SubmitOrderCallback implements
			HttpCallback<com.vsoyou.sdk.entity.Result> {

		@Override
		public void onSuccess(com.vsoyou.sdk.entity.Result object) {
			mainView.getmHandler().sendEmptyMessage(2);
			if (object.success) {
				PayCallbackInfo payCallbackInfo = getPayCallbackInfo();
				payCallbackInfo.statusCode = Constants.SUCCESS_TAG;
				payCallbackInfo.statusDes = ToastUtil.SUBMIT_ORDER_SUCCESS;
				mainView.showSureDialog(payCallbackInfo);
			} else {
				PayCallbackInfo payCallbackInfo = getPayCallbackInfo();
				payCallbackInfo.statusCode = Constants.FAILURE_TAG;
				payCallbackInfo.statusDes = object.message;
				mainView.showSureDialog(payCallbackInfo);
			}
		}

		@Override
		public void onFailure(int errorCode, String errorMessage) {
			mainView.getmHandler().sendEmptyMessage(2);
			PayCallbackInfo payCallbackInfo = getPayCallbackInfo();
			payCallbackInfo.statusCode = Constants.FAILURE_TAG;
			payCallbackInfo.statusDes = ToastUtil.SUBMIT_ORDER_FAILURE_CHECK_NET;
			mainView.showSureDialog(payCallbackInfo);
		}

	}

	private boolean check() {
		cardNum = cardNumEditText.getText().toString().trim();
		cardPwd = cardPwdEditText.getText().toString().trim();
		if (StringUtil.isEmpty(cardNum) || StringUtil.isEmpty(cardPwd)) {
			ToastUtil.showToast(context, ToastUtil.NUM_OR_PWD_IS_NULL);
			return false;
		}
		if (cardNum.length() != cardNumTotal) {
			ToastUtil.showToast(context, ToastUtil.NUM_IS_ERROR);
			return false;
		}
		if (cardPwd.length() != cardPwdTotal) {
			ToastUtil.showToast(context, ToastUtil.PWD_IS_ERROR);
			return false;
		}
		return true;
	}

	private PayCallbackInfo getPayCallbackInfo() {
		PayCallbackInfo payCallbackInfo = new PayCallbackInfo();
		payCallbackInfo.appId = mainView.getLocalStorage().getInt(
				Constants.VSOYOU_APP_ID, 0);
		payCallbackInfo.channelId = mainView.getLocalStorage().getInt(
				Constants.VSOYOU_CHANNELID, 0);
		payCallbackInfo.extra = mainView.getResultParam();
		payCallbackInfo.orderNo = mainView.getOrderNum();
		if (payType == null) {
			payCallbackInfo.payType = "";
		} else {
			payCallbackInfo.payType = "" + payType;
		}
		payCallbackInfo.price = totalPrice;
		payCallbackInfo.productId = mainView.getProductId();
		payCallbackInfo.userId = mainView.getLocalStorage().getLong(
				Constants.USER_ID, 0L);
		payCallbackInfo.userName = DESCoder.decryptoPriAndPub(context, mainView
				.getLocalStorage().getString(Constants.USERNAME, ""));
		return payCallbackInfo;
	}

}
