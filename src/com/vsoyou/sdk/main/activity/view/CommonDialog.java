package com.vsoyou.sdk.main.activity.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.vsoyou.sdk.R;

public class CommonDialog extends Dialog {

	private static int default_width = 160; 

	private static int default_height = 120;
	
	
	private TextView forgetPwdTextView;

	public CommonDialog(Context context, View layout, int style) {
		this(context, default_width, default_height, layout, style);
		initView();
	}

	public CommonDialog(Context context, int width, int height, View layout,
			int style) {
		super(context, style);
		setContentView(layout);
		Window window = getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		params.gravity = Gravity.CENTER;
		window.setAttributes(params);
	}
	
	private void initView() {
		forgetPwdTextView = (TextView) findViewById(R.id.forgetPwdTextView);
		forgetPwdTextView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		forgetPwdTextView.setText("忘记密码");
		forgetPwdTextView.setTextColor(Color.parseColor("#fe501b"));
		
	}

}
