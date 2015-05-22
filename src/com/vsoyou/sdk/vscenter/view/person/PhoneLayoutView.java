package com.vsoyou.sdk.vscenter.view.person;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vsoyou.sdk.entity.Result;
import com.vsoyou.sdk.entity.parser.ResultParser;
import com.vsoyou.sdk.http.HttpCallback;
import com.vsoyou.sdk.http.HttpRequest;
import com.vsoyou.sdk.main.Constants;
import com.vsoyou.sdk.util.DESCoder;
import com.vsoyou.sdk.util.LocalStorage;
import com.vsoyou.sdk.vscenter.ParamChain;
import com.vsoyou.sdk.vscenter.entiy.PersonCetnerResult;
import com.vsoyou.sdk.vscenter.entiy.requestparam.PhoneBindRequstParam;
import com.vsoyou.sdk.vscenter.entiy.requestparam.PhoneCodeRequestParam;
import com.vsoyou.sdk.vscenter.util.BitmapCache;
import com.vsoyou.sdk.vscenter.util.MetricUtil;
import com.vsoyou.sdk.vscenter.util.RegularUtil;

public class PhoneLayoutView extends BaseLayout {
	private Button btn_token;
	private EditText edit_phone;
	private EditText edit_token;
	private Button btn_confirm;
	private Context context;
	private PersonCetnerResult result;
	private Timer timePromptTimer;
	private int promptTime = 61;

	public PhoneLayoutView(Context context, ParamChain chain) {
		super(context, chain);
		super.initUI(context);
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initEnv(Context ctx, ParamChain env) {
		result = env.get(PersonCetnerResult.KEY, PersonCetnerResult.class);
	}

	@Override
	protected void onInitUI(Context ctx) {
		// TODO Auto-generated method stub
		setTitleText("手机绑定");
		FrameLayout sub = (FrameLayout) getView_subject(ctx);
		LinearLayout all = new LinearLayout(ctx);
		all.setPadding(MetricUtil.getDip(ctx, 20), MetricUtil.getDip(ctx, 30),
				MetricUtil.getDip(ctx, 20), MetricUtil.getDip(ctx, 20));
		all.setOrientation(VERTICAL);
		sub.addView(all, new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		// createTopView
		if (result.phoneNumberStatus) {
			createBindTopView(ctx, all);
		} else {
			createTopView(ctx, all);
		}

		// 确定按钮

		LinearLayout lin_buttom = new LinearLayout(ctx);
		lin_buttom.setGravity(Gravity.CENTER);
		btn_confirm = new Button(ctx);
		btn_confirm.setText("确定");
		btn_confirm.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
		btn_confirm.setOnClickListener(this);
		btn_confirm.setBackgroundDrawable(BitmapCache.getDrawable(ctx,
				"btn_blue.png"));
		LayoutParams lp_confirm = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		lp_confirm.gravity = Gravity.CENTER;
		lin_buttom.addView(btn_confirm, lp_confirm);
		LayoutParams lp_buttom = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		all.addView(lin_buttom, lp_buttom);
	}

	private void createTopView(Context ctx, LinearLayout all) {
		LinearLayout top = new LinearLayout(ctx);
		top.setOrientation(VERTICAL);
		top.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(ctx,
				"person_black.9.png"));
		LayoutParams lp_top = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		all.addView(top, lp_top);

		LinearLayout top_phone = new LinearLayout(ctx);
		top_phone.setOrientation(HORIZONTAL);
		top.addView(top_phone, new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));

		LinearLayout top_phone_left = new LinearLayout(ctx);
		LayoutParams lp_left = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		lp_left.weight = 0.4f;
		top_phone.addView(top_phone_left, lp_left);

		LayoutParams lp_edit_phone = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lp_edit_phone.gravity = Gravity.CENTER | Gravity.LEFT;
		ImageView phone = new ImageView(ctx);
		phone.setBackgroundDrawable(BitmapCache.getDrawable(ctx,
				"phone_block.png"));
		lp_edit_phone.leftMargin = MetricUtil.getDip(ctx, 6);
		top_phone_left.addView(phone, lp_edit_phone);

		edit_phone = new EditText(ctx);
		edit_phone.setHint("请输入手机号码");
		edit_phone.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
		edit_phone.setTextColor(Color.BLACK);
		edit_phone.setImeOptions(EditorInfo.IME_ACTION_DONE);
		edit_phone.setInputType(InputType.TYPE_CLASS_PHONE);
		edit_phone.setBackgroundColor(Color.WHITE);
		edit_phone.setPadding(MetricUtil.getDip(ctx, 6),
				MetricUtil.getDip(ctx, 20), 0, MetricUtil.getDip(ctx, 20));
		LayoutParams lp_edit = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		top_phone_left.addView(edit_phone, lp_edit);

		LinearLayout l_btn_token = new LinearLayout(ctx);
		l_btn_token.setGravity(Gravity.RIGHT | Gravity.CENTER);
		LayoutParams lp_token = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		lp_token.weight = 0.6f;
		lp_token.gravity = Gravity.CENTER;

		btn_token = new Button(ctx);
		btn_token.setGravity(Gravity.CENTER);
		btn_token.setText("获取验证码");
		btn_token.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17);
		btn_token.setBackgroundDrawable(BitmapCache.getDrawable(ctx,
				"get_token.png"));
		btn_token.setOnClickListener(this);
		btn_token.setTextColor(Color.parseColor("#4C4C4C"));
		LayoutParams lp_btn_token = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		lp_btn_token.rightMargin = MetricUtil.getDip(ctx, 6);
		lp_btn_token.height = MetricUtil.getDip(ctx, 40);
		lp_btn_token.width = MetricUtil.getDip(ctx, 120);
		l_btn_token.addView(btn_token, lp_btn_token);

		top_phone.addView(l_btn_token, lp_token);

		// 线条

		View line = new View(ctx);
		line.setBackgroundColor(Color.rgb(235, 235, 235));
		LayoutParams lp_line = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		lp_line.height = 1;
		top.addView(line, lp_line);

		// 密码

		LinearLayout top_token = new LinearLayout(ctx);
		top_phone.setOrientation(HORIZONTAL);
		top_token.setGravity(Gravity.CENTER | Gravity.LEFT);
		top.addView(top_token, lp_top);

		ImageView token = new ImageView(ctx);
		token.setBackgroundDrawable(BitmapCache.getDrawable(ctx,
				"password_block.png"));
		LayoutParams lp_img_token = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		lp_img_token.leftMargin = MetricUtil.getDip(ctx, 6);
		top_token.addView(token, lp_img_token);

		edit_token = new EditText(ctx);
		edit_token.setHint("请输入验证码");
		edit_token.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
		edit_token.setTextColor(Color.BLACK);
		edit_token.setInputType(InputType.TYPE_CLASS_PHONE);
		edit_token.setPadding(MetricUtil.getDip(ctx, 6),
				MetricUtil.getDip(ctx, 20), 0, MetricUtil.getDip(ctx, 20));
		edit_token.setBackgroundColor(Color.WHITE);
		LayoutParams lp_edit_token = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		top_token.addView(edit_token, lp_edit_token);

	}

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				if (promptTime > 0) {
					btn_token.setClickable(false);
					btn_token.setText(""+promptTime);
					
				} else {
					promptTime = 61;
					timePromptTimer.cancel();
					btn_token.setText("获取验证码");
					btn_token.setClickable(true);
				}

			}

		};

	};

	private void createBindTopView(Context ctx, LinearLayout all) {

		LinearLayout top = new LinearLayout(ctx);
		top.setOrientation(HORIZONTAL);
		top.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(ctx,
				"person_black.9.png"));
		LayoutParams lp_top = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		lp_top.topMargin = MetricUtil.getDip(ctx, 20);
		all.addView(top, lp_top);

		TextView txt_name = new TextView(ctx);
		txt_name.setText("绑定号码:");
		txt_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
		txt_name.setPadding(MetricUtil.getDip(ctx, 10),
				MetricUtil.getDip(ctx, 15), MetricUtil.getDip(ctx, 20),
				MetricUtil.getDip(ctx, 15));
		top.addView(txt_name);

		TextView txt_number = new TextView(ctx);
		txt_number.setText(result.phoneNumber);
		txt_number.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
		top.addView(txt_number);

	}

	private String getNumber() {
		return edit_phone.getText().toString();
	}

	private String getToken() {
		return edit_token.getText().toString();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		if (v == btn_token) {
			if (RegularUtil.checkPhoneNumber(getNumber(), getContext())) {
				// 进行获取验证码
				showDialog();
				requestCode(context);
			}
		}
		if (v == btn_confirm) {
			if (!result.phoneNumberStatus) {
				if (RegularUtil.checkToken(getToken(), getContext())) {
					showDialog();
					bindPhoneNumber(context);
				}
			} else {
				host_back();

			}

		}
	}

	private void startTimer() {
		timePromptTimer = new Timer();
		timePromptTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				promptTime--;
				mHandler.sendEmptyMessage(1);
			}
		}, 0, 1000);
	}

	private void requestCode(Context ctx) {
		HttpRequest<Result> request = new HttpRequest<Result>(ctx, null,
				new ResultParser(), new RequestCodeHttpCallback());
		PhoneCodeRequestParam code = new PhoneCodeRequestParam(ctx, getNumber());
		LocalStorage storage = LocalStorage.getInstance(ctx);
		String url = DESCoder.decryptoPriAndPub(ctx,
				storage.getString(Constants.USERPHONENUMBERCODE_URL, ""));
		request.execute(url, code.toJson());
	}

	private void bindPhoneNumber(Context ctx) {
		HttpRequest<Result> request = new HttpRequest<Result>(ctx, null,
				new ResultParser(), new RequestCodeHttpCallback());
		PhoneBindRequstParam param = new PhoneBindRequstParam(ctx, getNumber(),
				getToken());
		LocalStorage storage = LocalStorage.getInstance(ctx);
		String url = DESCoder.decryptoPriAndPub(ctx,
				storage.getString(Constants.USERBINDPHONENUMBER_URL, ""));
		request.execute(url, param.toJson());
	}

	class RequestCodeHttpCallback implements HttpCallback<Result> {

		@Override
		public void onSuccess(Result object) {
			if (object.success) {
				closeDialog();
				Toast.makeText(context, "验证成功", Toast.LENGTH_SHORT).show();
				startTimer();
			} else {

				closeDialog();
				Toast.makeText(context, "获取验证码失败", Toast.LENGTH_SHORT).show();
			}
			 
		}

		@Override
		public void onFailure(int errorCode, String errorMessage) {
			// TODO Auto-generated method stub
			closeDialog();
			Toast.makeText(context, "访问网络出错", Toast.LENGTH_SHORT).show();
		}

	}

}
