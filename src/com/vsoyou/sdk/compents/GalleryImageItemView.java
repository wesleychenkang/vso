package com.vsoyou.sdk.compents;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.vsoyou.sdk.resources.ResourceLoader;

public class GalleryImageItemView extends LinearLayout {

	private Context context;
	public ImageView imageView;

	public GalleryImageItemView(Context context) {
		super(context);
		this.context = context;
		init();
	}

	public GalleryImageItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}

	private void init() {
		this.setGravity(Gravity.CENTER);
		this.setOrientation(LinearLayout.VERTICAL);
		imageView = new ImageView(context);
		LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(
				200, 300);
		imageView.setImageDrawable(ResourceLoader
				.getBitmapDrawable("data_7.pvd"));
		imageView.setScaleType(ScaleType.FIT_CENTER);
		imageParams.gravity = Gravity.CENTER;
		this.addView(imageView, imageParams);
	}

}
