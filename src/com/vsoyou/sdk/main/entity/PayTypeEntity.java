package com.vsoyou.sdk.main.entity;

import java.io.Serializable;

public class PayTypeEntity implements Serializable {
	
	private static final long serialVersionUID = -2036770528014381348L;
	
	public int id;
	public String titleName;
	public String enName;
	public String pid;
	public String seller_id;
	public String keyType;
	public String public_Key;
	public String private_Key;
	public String notify_url;
	public String request_url;
	
	@Override
	public String toString() {
		return "PayTypeEntity [id=" + id + ", titleName=" + titleName
				+ ", enName=" + enName + ", pid=" + pid + ", seller_id="
				+ seller_id + ", keyType=" + keyType + ", public_Key="
				+ public_Key + ", private_Key=" + private_Key + ", notify_url="
				+ notify_url + ", request_url=" + request_url + "]";
	}
	
	
}
