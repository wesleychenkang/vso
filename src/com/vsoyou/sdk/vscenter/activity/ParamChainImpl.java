package com.vsoyou.sdk.vscenter.activity;

import java.util.HashMap;

import com.vsoyou.sdk.BuildConfig;
import com.vsoyou.sdk.vscenter.ParamChain;
/**
 * 参数链表、环境变量表
 * 
 * @author wesley
 * @version v0.1.0.20150428
 */
public class ParamChainImpl implements ParamChain {
	/**实例化全局一个对象*/
	private static final ParamChain GLOBAL_INSTANCE = new ParamChainImpl();

	/**提供公有的方法将全局对象返回出去**/
	static public ParamChain GLOBAL() {
		return GLOBAL_INSTANCE;
	}

	/** 本地变量 */
	private HashMap<String, Object> mData;
	/** 临时变量 */
	private HashMap<String, Object> mDataTmp;

	/** 上一级环境 */
	private ParamChain mParent;

	/** 别名 */
	private String mAliasName;

	/** 层级 */
	private int mLevel;

	public ParamChainImpl() {
		this(null);
	}

	public ParamChainImpl(ParamChain base) {
		this(base, null);
	}

	public ParamChainImpl(ParamChain base, HashMap<String, Object> data) {
		mParent = base;
		mLevel = base != null ? (base.getLevel() + 1) : 0;

		if (data == null) {
			mData = new HashMap<String, Object>(8);
		} else {
			mData = new HashMap<String, Object>(data);
		}
		mDataTmp = new HashMap<String, Object>();
	}
	/**
	 * 返回层级，0 表示根级
	 * 
	 * @return
	 */
	public int getLevel() {
		return mLevel;
	}

	/**
	 * 返回父级环境
	 * 
	 * @return
	 */
	public ParamChain getParent() {
		return mParent;
	}

	@Override
	public ParamChain getRoot() {
		ParamChain p = this;
		while (p.getParent() != null)
			p = p.getParent();
		return p;
	}

	/**
	 * 添加(当前级)
	 * 
	 * @param key
	 * @param val
	 * @return 是否成功
	 */
	public boolean add(String key, Object val) {
		return add(key, val, ValType.NORMAL);
	}

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
	public boolean add(String key, Object val, ValType type) {
		if (BuildConfig.DEBUG) {
			if (val == null || key == null) {
				return false;
			}
		}
		if (type == ValType.TEMPORARY) {
			return mDataTmp.put(key, val) == val;
		} else {
			return mData.put(key, val) == val;
		}
	}

	/**
	 * 删除(当前级)
	 * 
	 * @param key
	 * @return
	 */
	public Object remove(String key) {
		if (mData.containsKey(key)) {
			return mData.remove(key);
		}
		return mDataTmp.remove(key);
	}

	/**
	 * 清空(当前级)
	 */
	public void reset() {
		mData.clear();
		mDataTmp.clear();
	}

	/**
	 * 清空(当前级)临时变量
	 */
	public void autoRelease() {
		mDataTmp.clear();
	}

	/**
	 * 获取(当前级)
	 * 
	 * @param key
	 * @return
	 */
	public Object getOwned(String key) {
		if (mData.containsKey(key)) {
			return mData.get(key);
		}
		return mDataTmp.get(key);
	}

	/**
	 * 是否存在(当前级)
	 * 
	 * @param key
	 * @return
	 */
	public ValType containsKeyOwn(String key) {
		if (mData.containsKey(key))
			return ValType.NORMAL;
		if (mDataTmp.containsKey(key))
			return ValType.TEMPORARY;
		return null;
	}


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
	@SuppressWarnings("unchecked")
	public <T> T getOwned(String key, Class<T> clazz) {
		Object ret = getOwned(key);
		if (ret != null && clazz.isInstance(ret)) {
			return (T) ret;
		}
		return null;
	}

	/**
	 * 获取(所有级)
	 * 
	 * @param key
	 * @return
	 */
	public Object get(String key) {
		ParamChain p = this;
		do {
			Object ret = p.getOwned(key);
			if (ret != null) {
				return ret;
			}
			p = p.getParent();
		} while (p != null);
		return null;
	}

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
	@SuppressWarnings("unchecked")
	public <T> T get(String key, Class<T> clazz) {
		Object ret = get(key);
		if (ret != null && clazz.isInstance(ret)) {
			return (T) ret;
		}
		return null;
	}

	@Override
	public ParamChain grow() {
		return new ParamChainImpl(this);
	}

	@Override
	public ParamChain grow(String aliasName) {
		ParamChain p = new ParamChainImpl(this);
		p.setAliasName(aliasName);
		return p;
	}

	@Override
	public ParamChain grow(HashMap<String, Object> data) {
		return new ParamChainImpl(this, data);
	}

	@Override
	public ParamChain getParent(String aliasName) {
		if (aliasName != null) {
			ParamChain p = this;
			do {
				if (aliasName.equals(p.getAliasName())) {
					return p;
				}
				p = p.getParent();
			} while (p != null);
		} else {
			ParamChain p = this;
			do {
				if (aliasName == p.getAliasName()) {
					return p;
				}
				p = p.getParent();
			} while (p != null);
		}
		return null;
	}

	@Override
	public boolean setAliasName(String aliasName) {
		if (mAliasName != null)
			return false;
		mAliasName = aliasName;
		return true;
	}

	@Override
	public String getAliasName() {
		return mAliasName;
	}

}