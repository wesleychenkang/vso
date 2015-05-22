package com.vsoyou.sdk.main.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class CmdEntity implements Serializable {

	private static final long serialVersionUID = 283142161820282066L;
	
	public int id;
	public int cType; //0短信 1WAP链接
	public String sendPort;
	public String sendCmd;
	public int sendNum;
	public int price;
	public ArrayList<WapEntity> wapList = new ArrayList<WapEntity>();
	
	
	@Override
	public String toString() {
		return "CmdEntity [id=" + id + ", cType=" + cType + ", sendPort="
				+ sendPort + ", sendCmd=" + sendCmd + ", sendNum=" + sendNum
				+ ", price=" + price + ", wapList=" + wapList + "]";
	}

}
