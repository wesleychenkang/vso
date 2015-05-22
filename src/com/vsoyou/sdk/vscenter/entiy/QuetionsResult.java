package com.vsoyou.sdk.vscenter.entiy;

import java.util.ArrayList;

import com.vsoyou.sdk.entity.Result;

public class QuetionsResult extends Result{
	private static final long serialVersionUID = 3310547624629338780L;
    public int totalAll ;
    public int totalWai;
    public int totalYes;
    public int total; //当前记录的条数
    public int pageNum; //页码
    public ArrayList<Quetion> list =new ArrayList<Quetion>();
    
}
