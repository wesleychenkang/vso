package com.vsoyou.sdk.vscenter;

import java.util.HashMap;

/**
 * 参数列表、环境变量管理
 * 
 * @author kangzhi.chen
 * 
 */
public interface ParamChain {
	/**
	 * 全局变量名
	 * <p>
	 * 子级变量名定义规则：
	 * 
	 * <pre>
	 * // 示例，二级变量组  KeyLayout，名为 layout，定义一个变量 K_YOUR_NAME  
	 * public static interface <font color="#ff0000">KeyLayout</font> extends <b>KeyGlobal</b> {
	 *  	static final String _TAG_ = <b>KeyGlobal._TAG_</b> + "layout" + _SEPARATOR_;
	 *  
	 *  	<i>/** 键：自定义名, 类型 {@link String}，其它说明 *</i><i>/</i>
	 *  	public static final String K_YOUR_NAME = _TAG_ + "myName";
	 * }
	 * </pre>
	 * 
	 * @author kangzhi.chen
	 * 
	 */
	public static interface KeyGlobal {
		static final String _SEPARATOR_ = ".";
		static final String _TAG_ = "global" + _SEPARATOR_;
		/** 主窗体 */
		static final String BASE_ACTIVITY = _TAG_ + "mainActivity";
		/** 页面类型 */
		static final String LAYOUT_TYPE = _TAG_ + "personLayout";
	    
		/**窗体名称*/
		static final String KEY_UINAME =_TAG_+"ui_activity_name";
	   
		static final String KEY_URL = _TAG_+"url_http";
	}

	public static enum ValType {
		/** 普通变量 */
		NORMAL,
		/** 临时变量，在调用 {@link ParamChain#autoRelease()} 后立即回收 */
		TEMPORARY;
	}

	/**
	 * 构造出一个子级变量环境
	 * 
	 * @return
	 */
	public ParamChain grow();

	/**
	 * 构造出一个子级变量环境
	 * 
	 * @param aliasName
	 *            别名
	 * @return
	 */
	public ParamChain grow(String aliasName);

	/**
	 * 构造出一个子级变量环境
	 * 
	 * @param data
	 *            附加的变量表
	 * @return
	 */
	public ParamChain grow(HashMap<String, Object> data);

	/**
	 * 返回层级，0 表示根级
	 * 
	 * @return
	 */
	public int getLevel();

	/**
	 * 返回父级环境
	 * 
	 * @return
	 */
	public ParamChain getParent();

	/**
	 * 设置别名，仅可设置一次
	 * 
	 * @param aliasName
	 *            别名
	 * @return 是否设置成功
	 */
	public boolean setAliasName(String aliasName);

	/**
	 * 获取别名
	 * 
	 * @return
	 */
	public String getAliasName();

	/**
	 * 根据别名返回父级环境，有可能返回自己
	 * 
	 * @param aliasName
	 *            别名
	 * @return
	 */
	public ParamChain getParent(String aliasName);

	/**
	 * 返回根级环境
	 * 
	 * @return
	 */
	public ParamChain getRoot();

	/**
	 * 添加(当前级)
	 * 
	 * @param key
	 * @param val
	 * @return 是否成功
	 */
	public boolean add(String key, Object val);

	/**
	 * 添加(当前级)
	 * 
	 * @param key
	 *            变量名
	 * @param val
	 *            值
	 * @param type
	 *            类型
	 * @return 是否成功
	 */
	public boolean add(String key, Object val, ValType type);

	/**
	 * 删除(当前级)
	 * 
	 * @param key
	 * @return
	 */
	public Object remove(String key);

	/**
	 * 清空(当前级)
	 */
	public void reset();

	/**
	 * 清空(当前级)临时变量
	 */
	public void autoRelease();

	/**
	 * 获取(当前级)
	 * 
	 * @param key
	 * @return
	 */
	public Object getOwned(String key);

	/**
	 * 获取指定类型的变量（当前级）
	 * 
	 * @param key
	 *            变量名
	 * @param clazz
	 *            将返回值的类型
	 * @return 如果包含变量并且类型符合，则返回之，否则返回 null
	 * 
	 */
	public <T> T getOwned(String key, Class<T> clazz);

	/**
	 * 获取(所有级)
	 * 
	 * @param key
	 * @return
	 */
	public Object get(String key);

	/**
	 * 获取指定类型的变量（所有级），示例
	 * <P>
	 * Double amount = env.get({@link KeyGlobal#K_PAY_AMOUNT}, {@link Double
	 * Double.class});
	 * 
	 * @param key
	 *            变量名
	 * @param clazz
	 *            将返回值的类型
	 * @return 如果包含变量并且类型符合，则返回之，否则返回 null
	 * 
	 */
	public <T> T get(String key, Class<T> clazz);

}
