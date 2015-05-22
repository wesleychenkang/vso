package com.vsoyou.sdk.vscenter.view.person;

import android.view.View;

import com.vsoyou.sdk.vscenter.ParamChain;

public interface ILayoutView {
	/***
	 * 进入，此时可启动初始化代码
	 * 
	 * @return 是否成功响应
	 */
	public boolean onEnter();

	/**
	 * @return 是否成功响应暂停
	 */
	public boolean onPause();

	/**
	 * @return 是否成功响应恢复
	 */
	public boolean onResume();

	/**
	 * 是否允许被关闭
	 * 
	 * @param isBack
	 *            true表示仅返回， false表示想要完全退出
	 * @return true 表示允许关闭， false 表示继续停留在这个界面
	 */
	public boolean isExitEnabled(boolean isBack);

	/**
	 * @return 是否成功响应被关闭
	 */
	public boolean onExit();

	/**
	 * @return 获取环境变量
	 */
	public ParamChain getEnv();

	/***
	 * 是否有效
	 * 
	 * @return 是否有效
	 */
	public boolean isAlive();

	/**
	 * 获取主视图，用于窗体显示
	 * 
	 * @return 主视图
	 */
	public View getMainView();


}
