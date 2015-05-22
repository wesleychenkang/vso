package com.vsoyou.sdk.vscenter.entiy;

import com.vsoyou.sdk.entity.Result;

public class PersonCetnerResult extends Result{

	private static final long serialVersionUID = -383272253804935180L;
	public static final String KEY = "PersonCetnerResult";
	public String phoneNumber;
	public boolean phoneNumberStatus;
	public String email;
	public boolean emailStatus;
	/**客服QQ与客服电话*/
	public String serviceInfo;
}
