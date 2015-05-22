package com.vsoyou.sdk.vscenter.view.person;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

public class ProgressDialog extends Dialog {
	
	public Context context;
	public boolean payViewUp;

	public ProgressDialog(Context context) {
		super(context);
		this.context = context;
		init();
	}

	private void init() {
		ProgressBar progressBar = new ProgressBar(context);
		this.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.getWindow().setBackgroundDrawable(
				new ColorDrawable(Color.TRANSPARENT));
		this.setContentView(progressBar);
		this.setCancelable(false);
	}
	
	

}