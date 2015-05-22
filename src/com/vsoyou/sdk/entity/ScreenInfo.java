package com.vsoyou.sdk.entity;

public class ScreenInfo {
	
	public int screenWidth;
	
	public int screenHeight;
	
	public int networkType;

	public int getScreenWidth() {
		return screenWidth;
	}

	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}

	public int getNetworkType() {
		return networkType;
	}

	public void setNetworkType(int networkType) {
		this.networkType = networkType;
	}

	@Override
	public String toString() {
		return "ScreenInfo [screenWidth=" + screenWidth + ", screenHeight="
				+ screenHeight + ", networkType=" + networkType + "]";
	}
	

}
