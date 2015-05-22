package com.vsoyou.sdk.ad.entity;

import java.io.Serializable;

public class AdCallBackResult implements Serializable {

	private static final long serialVersionUID = 2148899102186231130L;
	
	public boolean success;
	public String message;
	
	@Override
	public String toString() {
		return "AdCallBackResult [success=" + success + ", message=" + message
				+ "]";
	}

}
