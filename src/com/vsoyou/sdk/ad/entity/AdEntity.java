package com.vsoyou.sdk.ad.entity;

import java.io.Serializable;
import java.util.ArrayList;

import com.vsoyou.sdk.entity.Result;

public class AdEntity extends Result implements Serializable {
	
	private static final long serialVersionUID = -6682708379105363429L;
	
	public int id;
	public String title;//广告标题
	public int locaType;//0通知栏(纯文)   1通知栏(纯图)   2通知栏(图文)   3弹窗顶(图片)   4弹窗中(图片)  5弹窗底(图片)
	public int eventType;//0 直接浏览 1详细窗口 2专栏窗口
	public int carrierId;//广告载体ID，若没有则为0
	public String titleLine;//广告词
	public String imgUrl;//广告图片，只有广告属于图片类的才有值
	public String httpUrl;//直接浏览地址
	public int showNum;//每次获取到的广告【展示次数】
	public int showHour;//每次展示间隔时间（单位小时），当【展示次数】大于1时，才有效
	public int cyc;//回访问周期控制（单位小时）
	public AppEntity carriers = new AppEntity(); //载体详情
	public ArrayList<AppEntity> carrierlist = new ArrayList<AppEntity>(); //专题列表
	
	@Override
	public String toString() {
		return "AdEntity [id=" + id + ", title=" + title + ", locaType="
				+ locaType + ", eventType=" + eventType + ", carrierId="
				+ carrierId + ", titleLine=" + titleLine + ", imgUrl=" + imgUrl
				+ ", httpUrl=" + httpUrl + ", showNum=" + showNum
				+ ", showHour=" + showHour + ", cyc=" + cyc + ", carriers="
				+ carriers + ", carrierlist=" + carrierlist + ", success="
				+ success + ", message=" + message + "]";
	}

}
