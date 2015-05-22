package com.vsoyou.sdk.ad.entity;

import java.io.Serializable;
import java.util.ArrayList;

import com.vsoyou.sdk.entity.Result;

public class Topic extends Result implements Serializable{

	private static final long serialVersionUID = -731326684380673711L;
	
	public int total;
	
	public int pageNum;
	
	public ArrayList<AppEntity> appList = new ArrayList<AppEntity>();

}
