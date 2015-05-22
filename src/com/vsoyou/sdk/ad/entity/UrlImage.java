package com.vsoyou.sdk.ad.entity;

import java.io.Serializable;

import android.graphics.drawable.Drawable;

/**
 * contain the url and the image.
 * 
 * @author Administrator
 * 
 */
public class UrlImage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UrlImage() {

	}

	public UrlImage(String url) {
		this.url = url;
	}

	public UrlImage(String url, Drawable image) {
		super();
		this.url = url;
		this.image = image;
	}

	/**
	 * the software icon url.
	 */
	public String url;
	/**
	 * the software icon drawable.
	 */
	public Drawable image;
}
