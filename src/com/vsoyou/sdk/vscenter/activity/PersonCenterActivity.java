package com.vsoyou.sdk.vscenter.activity;

import java.util.Stack;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.vsoyou.sdk.http.HttpCallback;
import com.vsoyou.sdk.http.HttpRequest;
import com.vsoyou.sdk.main.Constants;
import com.vsoyou.sdk.util.DESCoder;
import com.vsoyou.sdk.util.LocalStorage;
import com.vsoyou.sdk.vscenter.ParamChain;
import com.vsoyou.sdk.vscenter.ParamChain.KeyGlobal;
import com.vsoyou.sdk.vscenter.entiy.PersonCetnerResult;
import com.vsoyou.sdk.vscenter.entiy.parser.PersonCenterParser;
import com.vsoyou.sdk.vscenter.entiy.requestparam.PersonCenterRequestParam;
import com.vsoyou.sdk.vscenter.protocols.ActivityControlInterface;
import com.vsoyou.sdk.vscenter.service.FloatWindowService;
import com.vsoyou.sdk.vscenter.view.person.ILayoutHost;
import com.vsoyou.sdk.vscenter.view.person.ILayoutHost.KeyILayoutHost;
import com.vsoyou.sdk.vscenter.view.person.ILayoutView;
import com.vsoyou.sdk.vscenter.view.person.LayoutFactory;
import com.vsoyou.sdk.vscenter.view.person.ProgressDialog;

public class PersonCenterActivity extends Activity {
	private static ParamChain ROOT_ENV;
	private Stack<ILayoutView> mStack = new Stack<ILayoutView>();
	private ParamChain root;
	private ProgressDialog dialog;
	private LayoutType type;

	public static final synchronized ParamChain GET_GLOBAL_PARAM_CHAIN() {
		if (ROOT_ENV == null) {
			ROOT_ENV = ParamChainImpl.GLOBAL().grow(
					PersonCenterActivity.class.getName());
		}
		return ROOT_ENV;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// setContentView(new PersonLayoutView(getApplicationContext(),
		// ROOT_ENV));
		initActivity(this);
	}

	private void initActivity(Activity activity) {
		String name = getIntent().getStringExtra(KeyGlobal.KEY_UINAME);
		ParamChain env = (ParamChain) GET_GLOBAL_PARAM_CHAIN().getParent(
				PersonCenterActivity.class.getName()).remove(name);
		root = env.grow();
		root.add(KeyGlobal.BASE_ACTIVITY, activity);
		root.add(KeyILayoutHost.K_HOST, new ILayoutHost() {

			@Override
			public void back() {
				popViewFromStack();
			}

			@Override
			public void exit() {
				// TODO Auto-generated method stub
				finish();
			}

			@Override
			public void enter(ParamChain chain, ClassLoader loader,
					String classname) {
				tryEnterView(chain, loader, classname);
			}

			@Override
			public void enter(ParamChain chain, LayoutType type) {
				// TODO Auto-generated method stub

				tryEnterView(chain, type);

			}

			@Override
			public void addActivityControl(ActivityControlInterface control) {
				// TODO Auto-generated method stub

			}

			@Override
			public void removeActivityControl(ActivityControlInterface control) {
				// TODO Auto-generated method stub

			}

		});
		type = root.get(KeyGlobal.LAYOUT_TYPE, LayoutType.class);

		if (type.equals(LayoutType.PERSON_CENTER)) {
			requestData(root, type);
		} else {
			tryEnterView(root, type);
		}
	}

	private void tryEnterView(ParamChain chain, ClassLoader loader,
			String classname) {

		ILayoutView layout = LayoutFactory.createLayout(
				getApplicationContext(), classname, loader, chain);
		pushViewToStack(layout);

	}

	private void tryEnterView(ParamChain chain, LayoutType type) {

		ILayoutView layout = LayoutFactory.createLayoutView(
				getApplicationContext(), type, chain);
		pushViewToStack(layout);

	}

	private View checkPopExitView(boolean isBack) {

		if (mStack.size() > 0) {
			ILayoutView layout = mStack.peek();
			if (!layout.isExitEnabled(isBack)) {
				View top = layout.getMainView();
				return top;
			}

		}
		return null;
	}

	private View popViewFromStack() {
		View v = checkPopExitView(true);
		if (v != null) {
			return v;
		}

		if (mStack.size() > 1) {
			ILayoutView layout = mStack.pop();
			if (layout.isAlive()) {
				layout.onExit();
			}
			layout = mStack.peek();
			layout.onResume();
			View cur = layout.getMainView();
			setContentView(cur);
			cur.requestFocus();
			return cur;
		} else {
			finish();
			return null;
		}

	}

	private boolean pushViewToStack(ILayoutView layout) {
		if (mStack.size() > 0) {
			ILayoutView lvOld = mStack.peek();
			if (lvOld.isAlive()) {
				View old = lvOld.getMainView();
				old.clearFocus();
			}
			lvOld.onPause();
		}

		mStack.push(layout);
		layout.onEnter();
		View cur = layout.getMainView();
		setContentView(cur);
		cur.requestFocus();
		return true;
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		FloatWindowService.disPlayCenterView(getApplicationContext(), -1);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		FloatWindowService.disPlayCenterView(getApplicationContext(), -1);
	}

	private void requestData(ParamChain chain, LayoutType type) {
		dialog = new ProgressDialog(this);
		dialog.show();
		LocalStorage storage = LocalStorage
				.getInstance(getApplicationContext());
		String url = DESCoder.decryptoPriAndPub(this,
				storage.getString(Constants.USERCENTER_URL, ""));
		long userId = storage.getLong(Constants.USER_ID, -1);
		String userName = DESCoder.decryptoPriAndPub(this,
				storage.getString(Constants.USERNAME, ""));
		HttpRequest<PersonCetnerResult> request = new HttpRequest<PersonCetnerResult>(
				getApplicationContext(), null, new PersonCenterParser(),
				new PersonCenterCallback(chain));
		PersonCenterRequestParam param = new PersonCenterRequestParam(
				getApplicationContext(), userId, userName);
		request.execute(url, param.toJson());

	}

	private class PersonCenterCallback implements
			HttpCallback<PersonCetnerResult> {
		private ParamChain chain;

		public PersonCenterCallback(ParamChain chain) {
			this.chain = chain;
		}

		@Override
		public void onSuccess(PersonCetnerResult object) {
			// TODO Auto-generated method stub
			dialog.dismiss();
			if (object.success) {

				chain.add(PersonCetnerResult.KEY, object);
				tryEnterView(chain, type);
			} else {
				Toast.makeText(getApplicationContext(), "服务器返回错误",
						Toast.LENGTH_LONG).show();
			}
		}

		@Override
		public void onFailure(int errorCode, String errorMessage) {
			// TODO Auto-generated method stub
			Toast.makeText(getApplicationContext(), "访问服务器出错,请检查网络",
					Toast.LENGTH_LONG).show();
			dialog.dismiss();
			PersonCenterActivity.this.finish();
		}

	}

}
