package com.vsoyou.sdk.vscenter.view.person;

import com.vsoyou.sdk.vscenter.ParamChain;
import com.vsoyou.sdk.vscenter.ParamChain.KeyGlobal;
import com.vsoyou.sdk.vscenter.activity.LayoutType;
import com.vsoyou.sdk.vscenter.protocols.ActivityControlInterface;

public interface ILayoutHost {
	public static class KeyILayoutHost implements KeyGlobal {
		public static final String Tag = KeyGlobal._TAG_ + "key_layout_host"
				+ _SEPARATOR_;
		public static final String K_HOST = Tag + "host";
	}

	/**
	 * 返回上一界面
	 */
	public void back();

	/**
	 * 退出
	 */
	public void exit();

	/**
	 * 进入页面
	 */
	public void enter(ParamChain chain, ClassLoader loader, String name);

	public void enter(ParamChain chain, LayoutType type);

	public void addActivityControl(ActivityControlInterface control);

	public void removeActivityControl(ActivityControlInterface control);

}
