package com.vsoyou.sdk.main.entity;

import java.io.Serializable;
import java.util.ArrayList;

import com.vsoyou.sdk.entity.Result;

public class ThesInfoResult extends Result implements Serializable {

	private static final long serialVersionUID = -82820952445764979L;
	
	public ArrayList<PayTypeEntity> payTypeList = new ArrayList<PayTypeEntity>();
	
	public ProductEntity productEntity;

	@Override
	public String toString() {
		return "ThesInfoResult [payTypeList=" + payTypeList
				+ ", productEntity=" + productEntity + ", success=" + success
				+ ", message=" + message + ", code=" + code + "]";
	}
	
	

}
