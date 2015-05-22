package com.vsoyou.sdk.vscenter.view.person;

import android.content.Context;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.vsoyou.sdk.main.Constants;
import com.vsoyou.sdk.util.DESCoder;
import com.vsoyou.sdk.util.LocalStorage;
import com.vsoyou.sdk.vscenter.ParamChain;

public class ForumLayout extends BaseLayout {
	public ForumLayout(Context context, ParamChain env) {
		super(context, env);
		super.initUI(context);
		 
	}

	@Override
	protected void initEnv(Context ctx, ParamChain env) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onInitUI(Context ctx) {
		setTitleText("论坛");
		FrameLayout sub = (FrameLayout) getView_subject(ctx);
		final WebView web = new WebView(getActivity());
		sub.addView(web,new FrameLayout.LayoutParams(LP_MM));
        web.setWebViewClient(new WebViewClient(){

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				web.loadUrl(url);
				return true;
			}
        });
        web.getSettings().setJavaScriptEnabled(true);
        LocalStorage storge = LocalStorage.getInstance(ctx);
        web.loadUrl( DESCoder.decryptoPriAndPub(ctx,storge.getString(Constants.USERBBS_URL, "")));
	}

}
