package com.vsoyou.sdk.vscenter.view.person;

import android.R.color;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
import com.vsoyou.sdk.vscenter.entiy.User;
import com.vsoyou.sdk.vscenter.entiy.requestparam.UpdatePwdRequestParam;
import com.vsoyou.sdk.vscenter.util.BitmapCache;
import com.vsoyou.sdk.vscenter.util.MetricUtil;

public class PasswordLayout extends BaseLayout {
	private EditText old_pass;
	private EditText new_pass;
	private EditText rnew_pass;
	private Button btn_submit;
    private Context context;
    private User user;
	public PasswordLayout(Context context, ParamChain env) {
		super(context, env);
		super.initUI(context);
		this.context = context;
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void initEnv(Context ctx, ParamChain env) {
		// TODO Auto-generated method stub
	}
	@Override
	protected void onInitUI(Context ctx) {
		setTitleText("修改密码");
		FrameLayout sub = (FrameLayout) getView_subject(ctx);
		ScrollView scroll = new ScrollView(ctx);
		sub.addView(scroll, new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));

		LinearLayout all = new LinearLayout(ctx);
		scroll.addView(all);
		all.setOrientation(VERTICAL);
		all.setPadding(MetricUtil.getDip(ctx, 20.0F),
				MetricUtil.getDip(ctx, 10.0F), MetricUtil.getDip(ctx, 20.0F), 0);
		// 账号
		TextView txt_name = new TextView(ctx);
		user = new User(ctx);
		txt_name.setText("威搜游账号: " + user.getUserName());
		txt_name.setTextColor(Color.parseColor("#151515"));
		txt_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
		txt_name.setPadding(0, MetricUtil.getDip(ctx, 10.0F), 0,
				MetricUtil.getDip(ctx, 10.0F));
		all.addView(txt_name);

		// 密码布局
		LinearLayout password = new LinearLayout(ctx);
		password.setBackgroundDrawable(BitmapCache.getNinePatchDrawable(ctx,
				"person_black.9.png"));
		password.setOrientation(VERTICAL);
		all.addView(password);

		Drawable drawalbe = BitmapCache.getDrawable(ctx, "password_block.png");
		drawalbe.setBounds(0, 0, MetricUtil.getDip(ctx, 25.0F),
				MetricUtil.getDip(ctx, 25.0F));
		old_pass = new EditText(ctx);
		old_pass.setBackgroundColor(color.transparent);
		old_pass.setHint("请输入旧的密码");
		old_pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		old_pass.setTextColor(Color.parseColor("#cbcdcb"));
		old_pass.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
		old_pass.setPadding(MetricUtil.getDip(ctx, 6f),
				MetricUtil.getDip(ctx, 15f), 0, MetricUtil.getDip(ctx, 15f));
		old_pass.setCompoundDrawables(drawalbe, null, null, null);
		old_pass.setCompoundDrawablePadding(MetricUtil.getDip(ctx, 10.0F));
		password.addView(old_pass);

		View line = createLine(ctx);
		LayoutParams lp_line = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		lp_line.height = MetricUtil.getDip(ctx, 1.0F);
		password.addView(line, lp_line);

		new_pass = new EditText(ctx);
		new_pass.setBackgroundColor(color.transparent);
		new_pass.setHint("请输入新的密码");
		new_pass.setTextColor(Color.parseColor("#cbcdcb"));
		new_pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		new_pass.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
		new_pass.setPadding(MetricUtil.getDip(ctx, 6f),
				MetricUtil.getDip(ctx, 15f), 0, MetricUtil.getDip(ctx, 15f));
		new_pass.setCompoundDrawables(drawalbe, null, null, null);
		new_pass.setCompoundDrawablePadding(MetricUtil.getDip(ctx, 10.0F));
		password.addView(new_pass);

		View line2 = createLine(ctx);
		password.addView(line2, lp_line);

		rnew_pass = new EditText(ctx);
		rnew_pass.setBackgroundColor(color.transparent);
		rnew_pass.setHint("请再次输入新的密码");
		rnew_pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		rnew_pass.setTextColor(Color.parseColor("#cbcdcb"));
		rnew_pass.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
		rnew_pass.setPadding(MetricUtil.getDip(ctx, 6f),
				MetricUtil.getDip(ctx, 15f), 0, MetricUtil.getDip(ctx, 15f));
		rnew_pass.setCompoundDrawables(drawalbe, null, null, null);
		rnew_pass.setCompoundDrawablePadding(MetricUtil.getDip(ctx, 10.0F));
		password.addView(rnew_pass);

		btn_submit = new Button(ctx);
		btn_submit.setOnClickListener(this);
		btn_submit.setBackgroundDrawable(BitmapCache.getDrawable(ctx,
				"btn_blue.png"));
		btn_submit.setText("提交");
		btn_submit.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
		LayoutParams lp_submit = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		lp_submit.topMargin = MetricUtil.getDip(ctx, 15.0F);
		lp_submit.gravity = Gravity.CENTER;
		all.addView(btn_submit, lp_submit);
		//
	}

	private View createLine(Context ctx) {
		View line = new View(ctx);
		line.setBackgroundColor(Color.rgb(235, 235, 235));
		return line;
	}

	private String getOldPwd(){
		return old_pass.getText().toString().trim();
	}
	
	private  String getNewPwd(){
		
		return new_pass.getText().toString().trim();
	}
	
	private String getRNewPwd(){
		
		return rnew_pass.getText().toString().trim();
	}
	
	private boolean  checkPwd(){
		if(TextUtils.isEmpty(getOldPwd())){
			Toast.makeText(context, "密码不能为空", Toast.LENGTH_SHORT).show();
			return false;
		}else if(TextUtils.isEmpty(getNewPwd())||TextUtils.isEmpty(getRNewPwd())){
			Toast.makeText(context, "新的密码不能为空", Toast.LENGTH_SHORT).show();
			return false;
		}else if(!checkOldPwd()){
			return false;
			
		}else if(!getNewPwd().equals(getRNewPwd())){
			Toast.makeText(context, "两次输入的密码需要一致", Toast.LENGTH_SHORT).show();
			return false;
		
		}
		return true;
	}
	
	private boolean checkOldPwd(){
		LocalStorage storage =  LocalStorage.getInstance(context);
		String password = DESCoder.decryptoPriAndPub(context,
				storage.getString(Constants.USERPASSWORD, ""));
		if(!password.equals(getOldPwd())){
			Toast.makeText(context, "旧的密码与原始密码不一致", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
	
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		if(v==btn_submit){
			if(checkPwd()){
				//访问网络
				updatePwd(context);
			}
		}

	}
	
	private void updatePwd(Context ctx) {
		showDialog();
		HttpRequest<Result> request = new HttpRequest<Result>(ctx, null,
				new ResultParser(), new RequestCodeHttpCallback());
 		UpdatePwdRequestParam param = new UpdatePwdRequestParam(ctx,getOldPwd(),getNewPwd());
		LocalStorage storage = LocalStorage.getInstance(ctx);
		String url = DESCoder.decryptoPriAndPub(ctx,
				storage.getString(Constants.USERUPDATEPWD_URL, ""));
		request.execute(url, param.toJson());
	}

	class RequestCodeHttpCallback implements HttpCallback<Result> {

		@Override
		public void onSuccess(Result object) {
			if (object.success) {
				closeDialog();
				user.updatePassWord(context, getNewPwd());
				Toast.makeText(context, "修改密码成功", Toast.LENGTH_SHORT).show();
			} else {

				closeDialog();
				Toast.makeText(context, "修改密码失败", Toast.LENGTH_SHORT).show();
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
