package com.vsoyou.sdk.vscenter.entiy;

import java.util.ArrayList;

import com.vsoyou.sdk.entity.Result;

public class RecordListResult extends Result{
	private static final long serialVersionUID = 8918116408232584667L;
	public  int total;
	public  int pageNum;
	public  ArrayList<Record> lists= new ArrayList<Record>();
}
