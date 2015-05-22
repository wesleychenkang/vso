package com.vsoyou.sdk.main.entity;

import java.io.Serializable;

import org.json.JSONObject;

public class KeyEntity implements Serializable {

	private static final long serialVersionUID = -7314970526779191549L;
	
	public String key;
	
	public long time;
	
	public KeyEntity(String key, long time) {
		this.key = key;
		this.time = time;
	}
	
	public String getJsonStr(){
		String str = "";
		try {
			JSONObject json = new JSONObject();
			json.put("k_key", key);
			json.put("k_time", time);
			str = json.toString();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return str;
	}

}
