package com.vsoyou.sdk.ad.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 游戏 app 实体类
 * 
 * @author lmy 
 */
public class AppEntity implements Serializable{

	private static final long serialVersionUID = -773539061640700398L;
	
	public int id; 
	public int carrierId;
	public String titleName; // 游戏名称
	public int cType;
	public String cate;//类别
	public String author;//作者
	public String packName;
	public long fileSize;
	public String verName;
	public String des;
	public String fileUrl;
	public String iconUrl;
	public ArrayList<String> imgUrlList;
	public String imgUrl1;
	public String imgUrl2;
	public String imgUrl3;
	public String imgUrl4;
	public String addTime;
	public int total;
	public int pageNum;
	
	@Override
	public String toString() {
		return "AppEntity [id=" + id + ", carrierId=" + carrierId
				+ ", titleName=" + titleName + ", cType=" + cType + ", cate="
				+ cate + ", author=" + author + ", packName=" + packName
				+ ", fileSize=" + fileSize + ", verName=" + verName + ", des="
				+ des + ", fileUrl=" + fileUrl + ", iconUrl=" + iconUrl
				+ ", imgUrlList=" + imgUrlList + ", imgUrl1=" + imgUrl1
				+ ", imgUrl2=" + imgUrl2 + ", imgUrl3=" + imgUrl3
				+ ", imgUrl4=" + imgUrl4 + ", addTime=" + addTime + ", total="
				+ total + ", pageNum=" + pageNum + "]";
	}
	
}
