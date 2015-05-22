package com.vsoyou.sdk.vscenter.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

public class RegularUtil {
	public static boolean checkPhoneNumber(String phone, Context ctx) {
		if (TextUtils.isEmpty(phone)) {
			Toast.makeText(ctx, "请输入手机号码", Toast.LENGTH_SHORT).show();
			return false;
		}
		boolean flag;
		try {

			Pattern regex = Pattern
					.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
			Matcher matcher = regex.matcher(phone);
			flag = matcher.matches();
			if (!flag) {
				Toast.makeText(ctx, "请输入正确格式的手机号码", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			flag = false;
		}

		return flag;
	}

	public static boolean checkEmail(String email, Context ctx) {
		if (TextUtils.isEmpty(email)) {
			Toast.makeText(ctx, "请输入邮箱号码", Toast.LENGTH_SHORT).show();
			return false;
		}
		boolean flag;
		try {
			String regExp = "^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
			Pattern p = Pattern.compile(regExp);
			Matcher m = p.matcher(email);
			flag = m.matches();
			if (!flag) {
				Toast.makeText(ctx, "请输入正确的邮箱", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			flag = false;
		}

		return flag;
	}

	public static boolean checkToken(String token, Context ctx) {
		if (TextUtils.isEmpty(token)) {
			Toast.makeText(ctx, "验证码不能为空", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (token.trim().length() > 8 || token.trim().length() < 3) {
			Toast.makeText(ctx, "验证码长度不正确", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
}
