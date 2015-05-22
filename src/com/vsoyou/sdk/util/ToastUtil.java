package com.vsoyou.sdk.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
	
	public static final String NO_PAY_TYPE = "暂未开放此支付方式，请选择其他方式支付！";
	public static final String NO_PAY_PRICE = "没有对应的支付金额，请选择其他方式支付！";
	public static final String CHECK_NET = "登陆失败，请检查网络连接！";
	public static final String BINDING_EMAIL_FAILURA = "绑定邮箱失败！";
	public static final String LOGIN_SUCCESS = "登陆成功。";
	public static final String INIT_FAILURE = "初始化失败，请检查网络并重新启动游戏！";
	public static final String CANCEL_ORDERNO = "用户取消订单，支付失败！";
	public static final String PAY_SUCCESS = "支付成功，请稍候在游戏中查看，一般1-10分钟到帐。如未到账，请联系客服，祝您游戏愉快！";
	public static final String SUBMIT_ORDER_SUCCESS = "下单成功，请稍候在游戏中查看，一般1-10分钟到帐。如未到账，请联系客服，祝您游戏愉快！";
	public static final String PAY_FAILURE = "支付失败，请选择其他支付方式充值！";
	public static final String CANCEL_INSTALL = "取消安装支付插件，支付失败！";
	public static final String NO_SELECT_PAY_TYPE = "用户未选择任何支付方式支付！";
	public static final String NUM_OR_PWD_IS_NULL = "卡号或密码不能为空！";
	public static final String NUM_IS_ERROR = "卡号位数错误！";
	public static final String PWD_IS_ERROR = "密码位数错误！";
	public static final String SUBMIT_ORDER_FAILURE_CHECK_NET = "提交订单失败，请检查网络！";
	public static final String PAY_INIT_FAILURE = "支付初始化失败！";
	public static final String PRICE_IS_ERROR = "支付失败，传入默认金额必须是对应商品单价的整数倍！";
	
	public static void showToast(Context context, int code, String msg){
		Toast.makeText(context, "" + code + "," + msg, Toast.LENGTH_SHORT).show();
	}
	
	public static void showToast(Context context, String msg){
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
	
	public static void showToast(Context context, String code, String msg){
		Toast.makeText(context, "" + code + "," + msg, Toast.LENGTH_SHORT).show();
	}

}
